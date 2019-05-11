package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.constants.DBInscriptionFields;
import ar.edu.itba.paw.interfaces.daos.ChangaDao;
import ar.edu.itba.paw.interfaces.daos.Dao;
import ar.edu.itba.paw.interfaces.daos.InscriptionDao;
import ar.edu.itba.paw.interfaces.daos.UserDao;
import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Function;

import static ar.edu.itba.paw.constants.DBInscriptionFields.*;
import static ar.edu.itba.paw.constants.DBTableName.changas;
import static ar.edu.itba.paw.constants.DBTableName.user_inscribed;
import static ar.edu.itba.paw.interfaces.util.Validation.ErrorCodes.*;
import static ar.edu.itba.paw.models.InscriptionState.optout;
import static ar.edu.itba.paw.models.InscriptionState.requested;

@Repository
public class InscriptionJdbcDao implements InscriptionDao {

    private final static RowMapper<Inscription> ROW_MAPPER = (rs, rowNum) -> inscriptionFromRS(rs);

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;

    @Autowired
    private UserDao userDao;


    @Autowired
    private ChangaDao changaDao;

    @Autowired
    public InscriptionJdbcDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(ds)
                .withTableName(user_inscribed.name())
                .usingColumns(user_id.name(), changa_id.name());
    }

    private <T> Either<List<Pair<T, Inscription>>, Validation> getter (
            Dao<T> dao, String colName, long id, Function<Inscription,Long> inscGetId) {

        final List<Inscription> inscriptionList = jdbcTemplate.query(
                String.format("SELECT * FROM %s WHERE %s = ?", user_inscribed // todo cambiar query() por otra cosa
                        , colName),
                ROW_MAPPER,
                id
        );
        final List<Pair<T,Inscription>> pairList = new LinkedList<>();
        for (Inscription insc: inscriptionList) {
            Either<T, Validation> either = dao.getById(inscGetId.apply(insc));
            if(!either.isValuePresent()){
                return Either.alternative(either.getAlternative());
            }
            pairList.add(Pair.buildPair(either.getValue(),insc));
        }
        return Either.value(pairList);
    }

    @Override
    /* Return the changas the user of id=userId is inscribed in */
    public Either<List<Pair<Changa, Inscription>>, Validation> getUserInscriptions(long userId) {
        return this.getter(changaDao, user_id.name(), userId, Inscription::getChanga_id);
    }

    @Override
    /* Returns the users that are inscribed in a changa of id=changaId */
    public Either<List<Pair<User, Inscription>>, Validation>  getInscribedUsers(long changaId) {
        return this.getter(userDao, changa_id.name(), changaId, Inscription::getUser_id);
    }

    @Override
    public Validation inscribeInChanga(long userId, long changaId) {
        // We check if the user is already inscribed
        Either<Inscription, Validation> insc = getInscription(userId, changaId);
        if (insc.isValuePresent()){
            return changeUserStateInChanga(insc.getValue(), requested);
        } else { // can be OK(user needs to be inscribbed)
            if (insc.getAlternative().getEc() == USER_NOT_INSCRIBED){
                // else we add him
                return forceInscribeInChanga(userId, changaId);
            } else {
                return insc.getAlternative();
            }
        }
    }

    private Validation forceInscribeInChanga(long userId, long changaId) {
        Map<String, Object> row = inscriptionToTableRow(userId, changaId);
        try {
            jdbcInsert
                    .execute(row);
        } catch (DataAccessException ex) {
            return new Validation(DATABASE_ERROR);
        }
        return new Validation(OK);
    }

    @Override
    public Either<Inscription, Validation> getInscription(long userId, long changaId) {
        final List<Inscription> list  = jdbcTemplate.query(
                String.format("SELECT * FROM %s WHERE %s = ?  AND %s = ?", user_inscribed.name()
                        , changa_id.name(), user_id.name()), ROW_MAPPER, changaId, userId);
        if(list.isEmpty() ) {
            return Either.alternative(new Validation(USER_NOT_INSCRIBED));
        } else if (list.size() > 1){
            return Either.alternative(new Validation(DATABASE_ERROR));
        } else {
            return Either.value(list.get(0));
        }
    }

    @Override
    public Validation changeUserStateInChanga(Inscription insc, InscriptionState newState) throws DataAccessException {
        // we assume the service has checked that the change can be done
        int rowsAffected;
        String sql = String.format("UPDATE %s SET %s = ? WHERE %s = ? AND %s = ?", user_inscribed.name(), state.name(), user_id.name(), changa_id.name());
        try {
            rowsAffected = this.jdbcTemplate.update(
                    sql,
                    newState.name(), insc.getUser_id(), insc.getChanga_id());
            if (rowsAffected != 1) {
                throw new RecoverableDataAccessException("rowsAffected != 1");
            }
        } catch (DataAccessException e) {
            System.out.println(e.getMessage());
            return new Validation(DATABASE_ERROR);
        }
        return new Validation(OK);
    }

    @Override
    public Validation changeUserStateInChanga(long userId, long changaId, InscriptionState state) {
        Either<Inscription, Validation> insc = getInscription(userId,changaId);
        if (!insc.isValuePresent()){
            return insc.getAlternative();
        }
        return changeUserStateInChanga(insc.getValue(), state);
    }

    @Override
    public Either<Boolean, Validation> isUserInscribedInChanga(long userId, long changaId) {
        Either<Inscription, Validation> insc = getInscription(userId,changaId);
        if(!insc.isValuePresent()){
            return Either.alternative(insc.getAlternative());
        }
        return Either.value(true);
    }

    @Override
    public boolean hasInscribedUsers(long changaId) {
        Optional<Inscription> optional = jdbcTemplate.query(String.format("SELECT * FROM %s WHERE %s = ?", user_inscribed.name() , changa_id.name()), ROW_MAPPER, changaId).stream().findAny();
        return optional.isPresent();
    }

    private void generateRandomInscriptions() {
        int N_INSCRIPTIONS = 1000;
        int N_USERS_CHANGAS = 100;
        Random rchanga = new Random();
        Random ruser = new Random();
        for (int i = 0; i < N_INSCRIPTIONS; i++) {
            inscribeInChanga(ruser.nextInt(N_USERS_CHANGAS),rchanga.nextInt(N_USERS_CHANGAS));
        }

    }

    /**
     * The 'state' column is not passed because it is set with a default value
     * automatically by the db
     * */
    private Map<String, Object> inscriptionToTableRow(long us_id, long ch_id) {
        Map<String,Object> resp = new HashMap<>();
        resp.put(user_id.name(), us_id);
        resp.put(changa_id.name(), ch_id);
        resp.put(state.name(), requested);
        return resp;
    }

    private static Inscription inscriptionFromRS(ResultSet rs) throws SQLException {
        return new Inscription.Builder()
                .withUserId(rs.getLong(user_id.name()))
                .withChangaId(rs.getLong(changa_id.name()))
                .withState(InscriptionState.valueOf(rs.getString(state.name())))
                .build();
    }

}


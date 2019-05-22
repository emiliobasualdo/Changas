package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.ChangaDao;
import ar.edu.itba.paw.interfaces.daos.InscriptionDao;
import ar.edu.itba.paw.interfaces.daos.UserDao;
import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static ar.edu.itba.paw.constants.DBInscriptionFields.*;
import static ar.edu.itba.paw.constants.DBTableName.user_inscribed;
import static ar.edu.itba.paw.interfaces.util.Validation.*;
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

    @Override
    /* Return the changas the user of id=userId is inscribed in */
    public Either<List<Pair<Changa, Inscription>>, Validation> getUserInscriptions(boolean equals, InscriptionState filterState, long userId) {
        String secondWhere = resolveSecondWhere(filterState, equals);

        final List<Inscription> inscriptionList = jdbcTemplate.query(
                String.format("SELECT * FROM %s WHERE %s = ? %s", user_inscribed
                        , user_id.name(), secondWhere),
                ROW_MAPPER,
                userId
        );

        final List<Pair<Changa,Inscription>> pairList = new LinkedList<>();
        for (Inscription insc: inscriptionList) {
            Either<Changa, Validation> either = changaDao.getById(insc.getChanga_id());
            if(!either.isValuePresent()){
                return Either.alternative(either.getAlternative());
            }
            pairList.add(Pair.buildPair(either.getValue(), insc));
        }

        return Either.value(pairList);
    }

    private String resolveSecondWhere(InscriptionState filterState, boolean equals) {
        // todo copiar método que armé en changaDao para filtrar
        String secondWhere = "";
        if(filterState != null) {
            secondWhere = "AND "+ state.name();
            if (equals) {
                secondWhere = secondWhere + " = ";
            } else {
                secondWhere = secondWhere + " != ";
            }
            secondWhere = secondWhere + "'" + filterState.name() + "'";
        }
        return secondWhere;
    }

    @Override
    /* Returns the users that are inscribed in a changa of id=changaId */
    public Either<List<Pair<User, Inscription>>, Validation>  getInscribedUsers(long changaId) {
        Either<List<Inscription>, Validation> either = getInscriptionList(changaId);
        if (!either.isValuePresent()) {
            return Either.alternative(either.getAlternative());
        }
        return populateList(either.getValue());
    }

    @Override
    public Either<List<Inscription>, Validation> getInscriptions(long changa_id) {
        return getInscriptionList(changa_id);
    }

    private Either<List<Pair<User, Inscription>>, Validation> populateList(List<Inscription> inscriptionList) {
        final List<Pair<User,Inscription>> pairList = new LinkedList<>();
        for (Inscription insc: inscriptionList) {
            Either<User, Validation> either = userDao.getById(insc.getUser_id());
            if(!either.isValuePresent()){
                return Either.alternative(either.getAlternative());
            }
            pairList.add(Pair.buildPair(either.getValue(),insc));
        }
        return Either.value(pairList);
    }

    @Override
    /* Returns the users that are accepted in a changa of id=changaId */
    public Either<List<Pair<User, Inscription>>, Validation>  getAcceptedUsers(long changaId) {
            final List<Inscription> inscriptionList = jdbcTemplate.query(
                    String.format("SELECT * FROM %s WHERE %s = ? AND %s = ?", user_inscribed
                            , changa_id.name(), state.name() ),
                    ROW_MAPPER,
                    changaId,
                    InscriptionState.accepted.name()

            );
        return populateList(inscriptionList);
    }

    @Override
    /* An Inscription implies that the user is inscribed OR he had inscribed him self before and optout */
    public Validation inscribeInChanga(long userId, long changaId) {
        Map<String, Object> row = inscriptionToTableRow(userId, changaId);
        try {
            jdbcInsert.execute(row);
        } catch (Exception ex) {
            return DATABASE_ERROR.withMessage(ex.getMessage());
        }
        return OK;
    }

    @Override
    public Either<Inscription, Validation> getInscription(long userId, long changaId) {
        final List<Inscription> list  = jdbcTemplate.query(
                String.format("SELECT * FROM %s WHERE %s = ?  AND %s = ?", user_inscribed.name()
                        , changa_id.name(), user_id.name()), ROW_MAPPER, changaId, userId);
        if(list.isEmpty() ) {
            return Either.alternative(USER_NOT_INSCRIBED);
        } else if (list.size() > 1){
            return Either.alternative(DATABASE_ERROR);
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
                    newState.toString(), insc.getUser_id(), insc.getChanga_id());
            if (rowsAffected != 1) {
                throw new RecoverableDataAccessException("rowsAffected != 1");
            }
        } catch (DataAccessException e) {
            System.out.println(e.getMessage());
            return DATABASE_ERROR;
        }
        return OK;
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
        //generateRandomInscriptions();
        final List<Inscription> list  = jdbcTemplate.query(
                String.format("SELECT * FROM %s WHERE %s = ?  AND %s = ?", user_inscribed.name()
                        , changa_id.name(), user_id.name()), ROW_MAPPER, changaId, userId);

        if (list.size() > 1) {
            return Either.alternative(DATABASE_ERROR);
        } else if (list.isEmpty()) {
            return Either.value(false);
        } else if (list.get(0).getState().compareTo(InscriptionState.optout) == 0) {
                return Either.value(false);
        } else {
            return Either.value(true);
        }
    }

    @Override
    public Validation setRating(long userId, long changaId, double ratingNum) {
        // we assume the service has checked that the change can be done
        String sql = String.format("UPDATE %s SET %s = ? WHERE %s = ? AND %s = ?", user_inscribed.name(), rating.name(), user_id.name(), changa_id.name());
        try {
            jdbcTemplate.update(
                    sql,
                    ratingNum, userId, changaId);
        } catch (DataAccessException e) {
            System.out.println(e.getMessage());
            return DATABASE_ERROR;
        }
        return OK;
    }

    private Either<List<Inscription>, Validation> getInscriptionList(long changaId) {
        List<Inscription> list;
        try {
            list = jdbcTemplate.query(
                    String.format("SELECT * FROM %s WHERE %s = ?", user_inscribed
                            , changa_id.name()),
                    ROW_MAPPER,
                    changaId
            );
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return Either.alternative(DATABASE_ERROR.withMessage(ex.getMessage()));
        }
        return Either.value(list);
    }

    @Override
    public boolean hasInscribedUsers(long changaId) {
        Optional<Inscription> optional = jdbcTemplate.query(String.format("SELECT * FROM %s WHERE %s = ?", user_inscribed.name() , changa_id.name()), ROW_MAPPER, changaId).stream().findAny();
        return optional.isPresent();
    }

    private void generateRandomInscriptions() {
        int N_USERS_CHANGAS = 99;
        InscriptionState rState;
        long userId, changaId;
        for (int i = 1; i < N_USERS_CHANGAS; i++) {
            userId = i%N_USERS_CHANGAS + 1;
            changaId = i%N_USERS_CHANGAS + 1;
            System.out.println(userId +" "+ changaId);
            inscribeInChanga(userId,changaId);
            switch (i%3){
                case 0:
                    rState = InscriptionState.declined;
                    break;
                case 1:
                    rState = requested;
                    break;
                default:
                    rState = InscriptionState.accepted;
            }
            changeUserStateInChanga(userId, changaId, rState);
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
        resp.put(rating.name(), rating.def);
        resp.put(state.name(), requested.toString());
        return resp;
    }

    private static Inscription inscriptionFromRS(ResultSet rs) throws SQLException {
        return new Inscription.Builder()
                .withUserId(rs.getLong(user_id.name()))
                .withChangaId(rs.getLong(changa_id.name()))
                .withState(InscriptionState.valueOf(rs.getString(state.name())))
                .withRating(rs.getDouble(rating.name()))
                .build();
    }

}


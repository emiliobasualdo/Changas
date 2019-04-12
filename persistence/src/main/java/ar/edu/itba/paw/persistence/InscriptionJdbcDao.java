package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.ChangaDao;
import ar.edu.itba.paw.interfaces.daos.Dao;
import ar.edu.itba.paw.interfaces.daos.InscriptionDao;
import ar.edu.itba.paw.interfaces.daos.UserDao;
import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.Changa;
import ar.edu.itba.paw.models.Either;
import ar.edu.itba.paw.models.Inscription;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static ar.edu.itba.paw.constants.DBInscriptionFields.*;
import static ar.edu.itba.paw.constants.DBTableName.user_inscribed;
import static ar.edu.itba.paw.interfaces.util.Validation.ErrorCodes.OK;
import static ar.edu.itba.paw.interfaces.util.Validation.ErrorCodes.UNKNOWN_ERROR;

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
                .withTableName(user_inscribed.TN());
    }

    private <T> Either<Map<T, Inscription>, Validation> getter (
            Dao<T> dao, String colName, long id, Function<Inscription,Long> inscGetId) {

        final List<Inscription> list = jdbcTemplate.query(
                String.format("SELECT * FROM %s WHERE %s = %d", user_inscribed.TN() // todo cambiar query() por otra cosa
                        , colName, id ),
                ROW_MAPPER
        );
        final Map<T,Inscription> map = new HashMap<>();
        for (Inscription insc: list) {
            Either<T, Validation> either = dao.getById(inscGetId.apply(insc));
            if(!either.isValuePresent()){
                return Either.alternative(either.getAlternative());
            }
            map.put(either.getValue(),insc);
        }
        return Either.value(map);
    }

    @Override
    /* Return the changas the user of id=userId is inscribed in */
    public Either<Map<Changa, Inscription>, Validation> getUserInscriptions(long userId) {
        return this.getter(changaDao, user_id.name(), userId, Inscription::getChanga_id);
    }

    @Override
    /* Returns the users that are inscribed in a changa of id=changaId */
    public Either<Map<User, Inscription>, Validation> getInscribedUsers(long changaId) {
        return this.getter(userDao, changa_id.name(), changaId, Inscription::getUser_id);
    }

    @Override
    public Validation uninscribeFromChanga(long userId, long changaId) {
        //TODO pilo
        return null;
    }

    @Override
    public Validation inscribeInChanga(long userId, long changaId) {
        Map<String, Object> row = inscriptionToTableRow(userId, changaId);
        try {
            jdbcInsert.execute(row);
        } catch (DuplicateKeyException e ){
            return new Validation(UNKNOWN_ERROR);
        }
        return new Validation(OK);
    }

    @Override
    public Validation changeUserStateInChanga(long userId, long changaId, String state) {
        return null;
    }

    @Override
    public Either<Boolean, Validation> isUserInscribedInChanga(long userId, long changaId) {
        final List<Inscription> list  = jdbcTemplate.query(
                String.format("SELECT * FROM %s WHERE %s = %d  AND %s = %d", user_inscribed.TN()
                , changa_id.name(), changaId, user_id.name(), userId), ROW_MAPPER );
        return Either.value(!list.isEmpty());
    }

    @Override
    public Either<Boolean, Validation> hasInscribedUsers(long changa_id) {
        return null;
    }
    // todo change sate
    
    /**
     * The 'state' column is not passed because it is set with a default value
     * automatically by the db
     * */
    private Map<String, Object> inscriptionToTableRow(long us_id, long ch_id) {
        Map<String,Object> resp = new HashMap<>();
        resp.put(user_id.name(), us_id);
        resp.put(changa_id.name(), ch_id);
        return resp;
    }

    private static Inscription inscriptionFromRS(ResultSet rs) throws SQLException {
        return new Inscription.Builder()
                .withUserId(rs.getLong(user_id.name()))
                .withChangaId(rs.getLong(changa_id.name()))
                .withState(rs.getString(state.name()))
                .build();
    }

}

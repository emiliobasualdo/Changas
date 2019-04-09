package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.InscriptionDao;
import ar.edu.itba.paw.interfaces.daos.UserDao;
import ar.edu.itba.paw.interfaces.util.ValidationError;
import ar.edu.itba.paw.models.Changa;
import ar.edu.itba.paw.models.Either;
import ar.edu.itba.paw.models.User;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
import static ar.edu.itba.paw.interfaces.util.ErrorCodes.ALREADY_INSCRIBED;
import static ar.edu.itba.paw.interfaces.util.ErrorCodes.DATABASE_ERROR;

@Repository
public class InscriptionJdbcDao implements InscriptionDao {

    private final static RowMapper<Inscription> ROW_MAPPER = (rs, rowNum) -> inscriptionFromRS(rs);

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;

    @Autowired
    private UserDao userDao;

    @Autowired
    public InscriptionJdbcDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(ds)
                .withTableName(user_inscribed.TN())
                .usingGeneratedKeyColumns(changa_id.name());
    }

    @Override
    public Either<Boolean, ValidationError> inscribeInChanga(User user, Changa changa) {
        return this.inscribeInChanga(user.getUser_id(), changa.getChanga_id());
    }

    @Override
    public Either<Boolean, ValidationError> inscribeInChanga(long user_id, long changa_id) {
        Map<String, Object> row = inscriptionToTableRow(user_id, changa_id);
        int rowsAffected;
        //TODO se hace con try catch o se hace una query antes para ver si el usuario ya esta inscripto en la changa? q es mejor?
        try {
            rowsAffected = jdbcInsert.execute(row);
            if (rowsAffected <= 0) {
                return Either.alternative(new ValidationError(DATABASE_ERROR.getMessage(), DATABASE_ERROR.getId()));
            }
        } catch (DataIntegrityViolationException ex) {
            return Either.alternative(new ValidationError(ALREADY_INSCRIBED.getMessage(), ALREADY_INSCRIBED.getId()));
            //TODO MAITE preguntar si es mejor devolver directo un ValidationError que tenga un codigo para sin errores en vez de hacer el either con Boolean
        }

        return Either.value(true);
    }


    @Override
    public List<Pair<User, String>> getInscribedInChanga(Changa changa) {
        return getInscribedInChanga(changa.getChanga_id());
    }

    @Override
    public List<Pair<User, String>> getInscribedInChanga(long id) {
        final List<Inscription> list = jdbcTemplate.query(
                String.format("SELECT * FROM %s WHERE %s = %d", user_inscribed.TN()
                        , changa_id.name(), id),
                ROW_MAPPER
        );
        final List<Pair<User,String>> usersList = new ArrayList<>();
        if (list.isEmpty()) {
            return usersList;
        }
        for (Inscription insc: list) {
            usersList.add(new Pair<>(userDao.findById(insc.user_id).getValue(), insc.state));
        }
        return usersList;
    }

    @Override
    public boolean isUserInscribedInChanga(long userId, long changaId) {
        final List<Inscription> list  = jdbcTemplate.query(String.format("SELECT * FROM %s WHERE %s = %d  AND %s = %d", user_inscribed.TN()
                , changa_id.name(), changaId, user_id.name(), userId), ROW_MAPPER );
        return !list.isEmpty();
    }
    // todo change sate

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
        return resp;
    }

    private static Inscription inscriptionFromRS(ResultSet rs) throws SQLException {
        return new Inscription(
                rs.getLong(user_id.name()),
                rs.getLong(changa_id.name()),
                rs.getString(state.name()));
    }

    private static class Inscription {
        private final long user_id;
        private final long changa_id;
        private final String state;

        Inscription(long user_id, long changa_id, String state) {
            this.user_id = user_id;
            this.changa_id = changa_id;
            this.state = state;
        }
    }
}

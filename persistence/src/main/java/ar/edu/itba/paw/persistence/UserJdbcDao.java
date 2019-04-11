package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.UserDao;
import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.Either;
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
import java.util.*;

import static ar.edu.itba.paw.constants.DBTableName.users;
import static ar.edu.itba.paw.constants.DBUserFields.*;
import static ar.edu.itba.paw.interfaces.util.Validation.ErrorCodes.*;

@Repository
public class UserJdbcDao implements UserDao {

    private final static RowMapper<User> ROW_MAPPER = (rs, rowNum) -> userFromRS(rs);

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;

    @Autowired
    public UserJdbcDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(ds)
                .withTableName(users.TN())
                .usingGeneratedKeyColumns(user_id.name());
    }

    @Override
    public  Either<User, Validation> getById(final long id) {
        final List<User> list = jdbcTemplate
                .query(
                        String.format("SELECT * FROM %s WHERE %s = %d", users.TN(),user_id.name() ,id),
                        ROW_MAPPER
                );
        if (list.isEmpty()) {
            return Either.alternative(new Validation(NO_SUCH_USER));
        }
        return Either.value(list.get(0));
    }

    @Override
    public Either<User, Validation> findByMail(String mail) {
        final List<User> list = jdbcTemplate
                .query(String.format("SELECT * FROM %s WHERE %s = '%s'", users.TN(),email.name(), mail), ROW_MAPPER);
        if (list.isEmpty()) {
            return Either.alternative(new Validation(NO_SUCH_USER));
        }
        return Either.value(list.get(0)); // todo get(0) mal
    }

    @Override
    public Either<User, Validation> create(final User user) {
        // todo si no se insertó ninguna fila, what pass?
        int rowsAffected;
        Map<String, Object> userRow = userToTableRow(user);
        try {
            rowsAffected = jdbcInsert.execute(userRow);

        } catch (DuplicateKeyException e ){
            return Either.alternative(new Validation(DATABASE_ERROR));
        }
        if (rowsAffected < 1) {
            return Either.alternative(new Validation(DATABASE_ERROR));
        }
        // todo Preguntar que onda esto
        final List<User> list = jdbcTemplate.query(
                String.format("SELECT * FROM %s WHERE %s = '%s' AND %s = '%s' ", users.TN(),
                        email.name(), user.getEmail(),
                        passwd.name(), user.getPasswd()),
                ROW_MAPPER
        );
        /*TODO MAITE
         en este punto se creó el usuario, osea que la query anterior si o si debería de devolver al usuario.
         Este chequeo lo saco o lo dejo por si JUSTO se cayo la base de datos y no lo pudo levantar?
         */
        if (list.isEmpty()) {
            return Either.alternative(new Validation(DATABASE_ERROR));
        }

        return Either.value(list.get(0));
    }

   /* @Override
    public List<User> createUsers() {
        return generateRandomUsers();
    }*/

    @Override
    public Either<User, Validation> getUser(User user) { // todo No puede recibir parametro de tipo User
        final List<User> list = jdbcTemplate.query(
                String.format("SELECT * FROM %s WHERE %s = '%s' AND %s = '%s'", users.TN(),
                        email.name(), user.getEmail(),
                        passwd.name(), user.getPasswd()
                ),
                ROW_MAPPER
        );
        if (list.isEmpty()) {
            return Either.alternative(new Validation(INVALID_COMBINATION));
        }
        return Either.value(list.get(0));
    }

    private List<User> generateRandomUsers() {
        int N_USERS = 100;
        String[] tel = {"34234", "1341", "12312", "123123", "123123"};
        String[] name = {"Pilo", "Jime", "Fer", "Mauricio", "Cristina"};
        String[] surname = {"Basualdo ", "Gomez", "Lozano", "Macrio", "Kirschner"};
        String[] email = {"@hotmail.com", "@hotmail.com", "@hotmail.com", "@hotmail.com", "@hotmail.com"};
        String[] passwd = {"San ", "Flor", "Cheto", "Quinta", "Feranandez"};
        Random r = new Random();
        int max = 5;
        List<User> resp = new ArrayList<>();
        for (int i = 0; i < N_USERS; i++) {
            resp.add(create(new User.Builder()
                    .withName(name[r.nextInt(max)])
                    .withSurname(surname[r.nextInt(max)])
                    .withTel(tel[r.nextInt(max)])
                    .withEmail(i+email[r.nextInt(max)])
                    .withPasswd(passwd[r.nextInt(max)])
                    .build()
                    ).getValue()
            );
        }
        return resp;
    }

    private static User userFromRS(ResultSet rs) throws SQLException {
        return new User.Builder(rs.getLong(user_id.name()))
                .withName(rs.getString(name.name()))
                .withSurname(rs.getString(surname.name()))
                .withTel(rs.getString(tel.name()))
                .withEmail(rs.getString(email.name()))
                .withPasswd(rs.getString(passwd.name()))
                .build();
    }

    private Map<String, Object> userToTableRow(User us) {
        Map<String, Object> resp = new HashMap<>();
        resp.put(name.name(), us.getName());
        resp.put(surname.name(), us.getSurname());
        resp.put(tel.name(), us.getTel());
        resp.put(email.name(), us.getEmail());
        resp.put(passwd.name(), us.getPasswd());
        return resp;
    }
}

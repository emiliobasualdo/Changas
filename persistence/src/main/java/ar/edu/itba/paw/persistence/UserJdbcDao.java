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
import static ar.edu.itba.paw.constants.DBUserFields.email;
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
                .withTableName(users.name())
                .usingGeneratedKeyColumns(user_id.name());
    }

    @Override
    public  Either<User, Validation> getById(final long id) {
        final List<User> list = jdbcTemplate
                .query(
                        String.format("SELECT * FROM %s WHERE %s = %d", users.name(),user_id.name() ,id),
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
                .query(String.format("SELECT * FROM %s WHERE %s = '%s'", users.name(),email.name(), mail), ROW_MAPPER);
        if (list.isEmpty()) {
            return Either.alternative(new Validation(NO_SUCH_USER));
        }
        return Either.value(list.get(0)); // todo get(0) mal
    }

    @Override
    public Either<User, Validation> create(final User.Builder userBuilder) {
        // todo si no se insertó ninguna fila, what pass?
        int rowsAffected;
        Map<String, Object> userRow = userToTableRow(userBuilder);
        try {
            rowsAffected = jdbcInsert.execute(userRow);

        } catch (DuplicateKeyException e ){
            return Either.alternative(new Validation(DATABASE_ERROR));
        }
        if (rowsAffected < 1) {
            return Either.alternative(new Validation(DATABASE_ERROR));
        }

        //TODO investigar si hay una forma de obtener el userId cuando se hace el insert
        return getUser(userBuilder);
    }


    private Map<String, Object> userToTableRow(User.Builder userBuilder) {
        Map<String, Object> resp = new HashMap<>();
        resp.put(name.name(), userBuilder.getName());
        resp.put(surname.name(), userBuilder.getSurname());
        resp.put(tel.name(), userBuilder.getTel());
        resp.put(email.name(), userBuilder.getEmail());
        resp.put(passwd.name(), userBuilder.getPasswd());
        return resp;
    }

    private static User userFromRS(ResultSet rs) throws SQLException {
        return build(rs.getLong(user_id.name()), new User.Builder()
                                                .withName(rs.getString(name.name()))
                                                .withSurname(rs.getString(surname.name()))
                                                .withTel(rs.getString(tel.name()))
                                                .withEmail(rs.getString(email.name()))
                                                .withPasswd(rs.getString(passwd.name()))
        );
    }

    private static User build(long userId, User.Builder userBuilder) {
        return new User(userId, userBuilder);
    }

   /* @Override
    public List<User> createUsers() {
        return generateRandomUsers();
    }*/

    @Override
    public Either<User, Validation> getUser(final User.Builder userBuilder) {
        final List<User> list = jdbcTemplate.query(
                String.format("SELECT * FROM %s WHERE %s = '%s' AND %s = '%s'", users.name(),
                        email.name(), userBuilder.getEmail(),
                        passwd.name(), userBuilder.getPasswd()
                ),
                ROW_MAPPER
        );
        if (list.isEmpty()) {
            //TODO chequear si es q el mail no pertenece a un usuario para hacer INVALID_EMAIL
            return Either.alternative(new Validation(INVALID_COMBINATION));
        }
        return Either.value(list.get(0));
    }


    private Either<User, Validation> getUserFromUserId(long userId) {
        final List<User> list = jdbcTemplate.query(
                String.format("SELECT * FROM %s WHERE %s = '%s'", users.name(),
                        user_id.name(), userId),
                ROW_MAPPER
        );

        if (list.isEmpty()) {
            return Either.alternative(new Validation(DATABASE_ERROR));
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
                    ).getValue()
            );
        }
        return resp;
    }

}

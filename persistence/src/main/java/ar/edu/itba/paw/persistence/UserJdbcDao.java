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
                .withTableName(users.name())
                .usingGeneratedKeyColumns(user_id.name());
    }

    @Override
    public  Either<User, Validation> getById(final long id) {
        final List<User> list = jdbcTemplate
                .query(
                        String.format("SELECT * FROM %s WHERE %s = ?", users.name(),user_id.name()),
                        ROW_MAPPER,
                        id
                );
        if (list.isEmpty()) {
            return Either.alternative(new Validation(NO_SUCH_USER));
        }
        return Either.value(list.get(0));
    }

    @Override
    public Either<User, Validation> create(final User.Builder userBuilder) {
        //TODO hacer que la base de datos acepte la getGeneratedKeys feature. Mientras tanto usamos el código de abajo
        Number userId;
        Map<String, Object> userRow = userToTableRow(userBuilder);
        try {
            userId = jdbcInsert.executeAndReturnKey(userRow);
        } catch (DuplicateKeyException e ) {
            return Either.alternative(new Validation(USER_ALREADY_EXISTS));
        } catch (Exception e ) {
            System.err.println(e);
            return Either.alternative(new Validation(DATABASE_ERROR));
        }

        return getUserFromUserId(userId.longValue());
    }

    @Override
    public Either<User, Validation> findByMail(String mail) {

        final List<User> list = jdbcTemplate
                .query(String.format("SELECT * FROM %s WHERE %s = ?", users.name(),email.name()),
                        ROW_MAPPER, mail
                );
        if (list.isEmpty()) {
            return Either.alternative(new Validation(NO_SUCH_USER));
        }
        if (list.size() > 1){
            return Either.alternative(new Validation(DATABASE_ERROR));
        }
        return Either.value(list.get(0));
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
                String.format("SELECT * FROM %s WHERE %s = ? AND %s = ?", users.name(),
                        email.name(),
                        passwd.name()
                ),
                ROW_MAPPER,
                userBuilder.getEmail(), userBuilder.getPasswd()
        );
        if (list.isEmpty()) {
            //TODO chequear si es q el mail no pertenece a un usuario para hacer INVALID_EMAIL
            return Either.alternative(new Validation(INVALID_COMBINATION));
        }
        return Either.value(list.get(0));
    }

    private Either<User, Validation> getUserFromUserId(long userId) {
        final List<User> list = jdbcTemplate.query(
                String.format("SELECT * FROM %s WHERE %s = ?", users.name(),
                        user_id.name()),
                ROW_MAPPER,
                userId
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

package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.UserDao;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
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
    public User findById(final long id) {
        final List<User> list = jdbcTemplate
                .query(
                        String.format("SELECT * FROM %s WHERE user_id = '%s'", users.TN(), id),
                        ROW_MAPPER
                );
        if (list.isEmpty()) {
            return null; // todo <---- null
        }
        return list.get(0);
    }

    @Override
    public User create(final User user) {
        // si no se insertó ninguna fila, what pass?
        Map<String, Object> userRow = userToTableRow(user);
        int rowsAffected = jdbcInsert.execute(userRow);
        if (rowsAffected < 1) {
            // todo maiteeeee??
        }
        // todo Preguntar que onda esto
        final List<User> list = jdbcTemplate.query(
                String.format("SELECT * FROM %s WHERE %s = '%s' AND %s = '%s' AND %s = '%s'", users.TN(),
                        name.name(), user.getName(),
                        surname.name(), user.getSurname(),
                        tel.name(), user.getTel()),
                ROW_MAPPER
        );
        return list.get(0);
    }

    @Override
    public List<User> createUsers() {
        return generateRandomUsers();
    }

    @Override
    public List<User> getAll() {
        return generateRandomUsers();
    }

    @Override
    public User getUser(User user) {
        final List<User> list = jdbcTemplate.query(
                String.format("SELECT * FROM %s WHERE %s = '%s' AND %s = '%s'", users.TN(),
                        email.name(), user.getEmail(),
                        passwd.name(), user.getPasswd()
                ),
                ROW_MAPPER
        );
        return list.get(0);
    }

    private List<User> generateRandomUsers() {
        int N_USERS = 100;
        String[] tel = {"34234", "1341", "12312", "123123", "123123"};
        String[] name = {"San Telmo", "Flores", "Talar del cheto", "Quinta presidencial", "Calle 13"};
        String[] surname = {"San ", "Flor", "Cheto", "Quinta", "Feranandez"};
        String[] email = {"a@hotmail.com", "b@hotmail.com", "c@hotmail.com", "d@hotmail.com", "e@hotmail.com"};
        String[] passwd = {"San ", "Flor", "Cheto", "Quinta", "Feranandez"};
        Random r = new Random();
        int max = 5;
        List<User> resp = new ArrayList<>();
        for (int i = 0; i < N_USERS; i++) {
            resp.add(create(new User.Builder()
                    .withName(name[r.nextInt(max)])
                    .withSurname(surname[r.nextInt(max)])
                    .withTel(tel[r.nextInt(max)])
                    .withEmail(email[r.nextInt(max)])
                    .withPasswd(passwd[r.nextInt(max)])
                    .build()
                    )
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

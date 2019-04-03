package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.UserDao;
import ar.edu.itba.paw.models.Changa;
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

import static ar.edu.itba.paw.constants.DBTableName.USERS;

@Repository
public class UserJdbcDao implements UserDao {

    private final static RowMapper<User> ROW_MAPPER = (rs, rowNum) -> userFromRS(rs);

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;

    @Autowired
    public UserJdbcDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        createTable();
    }

    @Override
    public User findById(final long id) {
        final List<User> list = jdbcTemplate
            .query(
                "SELECT * FROM ? WHERE id = ?",
                ROW_MAPPER,
                USERS.TN(),
                id
        );
        if (list.isEmpty()) {
            return null; // todo <---- null
        }
        return list.get(0);
    }

    @Override
    public User create(final User user) {
        final Number userId = jdbcInsert.executeAndReturnKey(userToTableRow(user));
        return new User(user, userId.longValue());
    }

    @Override
    public List<User> getAll() {
        return generateRandomUsers();
    }

    private List<User> generateRandomUsers() {
        int N_USERS = 100;
        long[] id = {1431234, 1234,3134343,1234,321};
        String[] tel = {"34234", "1341", "12312", "123123", "123123"};
        String[] name = {"San Telmo", "Flores", "Talar del cheto", "Quinta presidencial", "Calle 13"};
        String[] surname = {"San ", "Flor", "Cheto", "Quinta", "Feranandez"};
        Random r = new Random();
        int max = 5;
        List<User> resp = new ArrayList<>();
        for (int i = 0; i < N_USERS; i++) {
            resp.add(new User(
                    id[r.nextInt(max)],
                    name[r.nextInt(max)],
                    tel[r.nextInt(max)],
                    surname[r.nextInt(max)]
            ));
        }
        return resp;
    }

    @Override
    public void createTable() {
        String query =
                "CREATE TABLE "+USERS.TN()+" ( " +
                    "user_id SERIAL PRIMARY KEY, " +
                    "name VARCHAR(100), " +
                    "surname VARCHAR(100), " +
                    "tel varchar(10)" +
                ");";
        jdbcTemplate.execute(query);
    }

    private static User userFromRS(ResultSet rs) throws SQLException {
        return new User(
                rs.getLong("user_id"),
                rs.getString("name"),
                rs.getString("surname"),
                rs.getString("tel")
        );
    }

    private Map<String, Object> userToTableRow(User us) {
        Map<String, Object> resp = new HashMap<>();
        resp.put("user_id", us.getUser_id());
        resp.put("name", us.getName());
        resp.put("surname", us.getSurname());
        resp.put("tel", us.getTel());
        return resp;
    }
}

package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.ChangaDao;
import ar.edu.itba.paw.models.Address;
import ar.edu.itba.paw.models.Changa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

import static ar.edu.itba.paw.constants.DBChangaFields.*;
import static ar.edu.itba.paw.constants.DBTableName.changas;

@Repository
public class ChangaJdbcDao implements ChangaDao {
    private final static RowMapper<Changa> ROW_MAPPER = (rs, rowNum) -> changaFromRS(rs);

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;

    @Autowired
    public ChangaJdbcDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(ds)
                .withTableName(changas.TN())
                .usingGeneratedKeyColumns(changa_id.name());
    }

    public Changa findById(final long id) {
        final List<Changa> list = jdbcTemplate.query(
                "SELECT * FROM ? WHERE "+changa_id+" = ?",
                ROW_MAPPER,
                changas.TN(),
                id
        );
        if (list.isEmpty()) {
            return null; // todo <---- null
        }
        return list.get(0);
    }

    @Override
    public Changa create(final Changa changa) {
        // si no se insertó ninguna fila, what pass?
        Map<String, Object> changaRow = changaToTableRow(changa);
        int rowsAffected = jdbcInsert.execute(changaRow);
        if (rowsAffected < 1) {
            // todo maiteeeee??
        }
        // todo Preguntar que onda esto
        final List<Changa> list = jdbcTemplate.query(
                String.format("SELECT * FROM %s WHERE %s = %d AND %s = '%s'", changas.TN(), user_id.name(), changa.getUser_id(), title.name(), changa.getTitle()),
                ROW_MAPPER
        );
        return list.get(0);
    }

    @Override
    public List<Changa> getAll() {
        return generateRandomChangas();
    }

    private List<Changa> generateRandomChangas() {
        int N_CHANGAS = 100;
        long[] userId = {1431234, 1234,3134343,1234,321};
        String[] title = {"Lavar el perro", "Lavar los platos", "Lavarme el culo", "Prositushon", "Se busca nene de 5 años",};
        String[] description = {"Hay que hacerlo la palo", "Vigorosooo", "Full energetic", "Dale candela", "Muevete",};
        double[] price = {13123, 123, 312, 1, 231};
        Address[] address = {new Address("Calle", "San telmo", 22),new Address("Calle", "San telmo", 22),new Address("Calle", "San telmo", 22),new Address("Calle", "San telmo", 22),new Address("Calle", "San telmo", 22)};
        String[] state = {"done", "emitted", "closed", "settled", "settled"};
        LocalDateTime[] createdAt = {LocalDateTime.now(),LocalDateTime.now(),LocalDateTime.now(),LocalDateTime.now(),LocalDateTime.now()};
        Random r = new Random();
        int max = 5;
        List<Changa> resp = new ArrayList<>();
        for (int i = 0; i < N_CHANGAS; i++) {
            resp.add(new Changa.Builder()
                    .withUserId(userId[r.nextInt(max)])
                    .withPrice(price[r.nextInt(max)])
                    .withState(state[r.nextInt(max)])
                    .withTitle(title[r.nextInt(max)])
                    .createdAt(createdAt[r.nextInt(max)])
                    .withDescription(description[r.nextInt(max)])
                    .atAddress(address[r.nextInt(max)])
                    .build());
        }
        return resp;
    }

    private static Changa changaFromRS(ResultSet rs) throws SQLException {
        return new Changa.Builder(rs.getLong(changa_id.name()))
                .withUserId(rs.getLong(user_id.name()))
                .withTitle(rs.getString(title.name()))
                .withDescription(rs.getString(description.name()))
                .withPrice(rs.getDouble(price.name()))
                //.atAddress(rs.getObject(address.name(), Address.class))
                .withState(rs.getString(state.name()))
                //.createdAt(rs.getObject(address.name(), LocalDateTime.class))
                .build();

    }

    private Map<String, Object> changaToTableRow(Changa ch) {
        Map<String, Object> resp = new HashMap<>();
        resp.put(user_id.toString(), ch.getUser_id());
        resp.put(title.toString(), ch.getTitle());
        resp.put(description.toString(), ch.getDescription());
        resp.put(price.toString(), ch.getPrice());
        //resp.put(address.toString(), addressToTableRow(ch.getAddress()));
        //resp.put(creation_date.toString(), Timestamp.valueOf(ch.getCreationDate()));
        resp.put(state.toString(), ch.getState());
        return resp;
    }

    private Map<String, Object> addressToTableRow(Address address) {
        Map<String, Object> resp = new HashMap<>();
        resp.put(street.toString(), address.getStreet());
        resp.put(neighborhood.toString(), address.getNeighborhood());
        resp.put(number.toString(), address.getNumber());
        return resp;
    }
}
package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.constants.DBChangaFields;
import ar.edu.itba.paw.interfaces.ChangaDao;
import ar.edu.itba.paw.models.Changa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static ar.edu.itba.paw.constants.DBTableName.CHANGAS;

@Repository
public class ChangaJdbcDao implements ChangaDao {
    private final static RowMapper<Changa> ROW_MAPPER = (rs, rowNum) -> changaFromRS(rs);

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;

    @Autowired
    public ChangaJdbcDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcTemplate.execute(createTableQuery());
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(CHANGAS.TN()).usingGeneratedKeyColumns("ownerName");
    }

    private String createTableQuery() {
        StringBuilder resp = new StringBuilder("CREATE TABLE IF NOT EXISTS " + CHANGAS.TN());
        resp.append("(");
        for (DBChangaFields field: DBChangaFields.values()) {
            resp.append(field.getFQN());
            resp.append(", ");
        }
        resp.deleteCharAt(resp.length()-1);// todo falta aca // We remove the extra coma
        resp.append(")");
        return resp.toString();

    }

    @Override
    public Changa findById(final long id) {
        final List<Changa> list = jdbcTemplate.query(
                "SELECT * FROM ? WHERE id = ?",
                ROW_MAPPER,
                CHANGAS.TN(),
                id
        );
        if (list.isEmpty()) {
            return null; // todo <---- null
        }
        return list.get(0);
    }

    @Override
    public Changa create(final Changa changa) {
        final Number changaId = jdbcInsert.executeAndReturnKey(changaToTableRow(changa));
        return new Changa(changa, changaId.longValue());
    }

    @Override
    public List<Changa> getAllChangas() {
        return generateRandomChangas();
    }

    private List<Changa> generateRandomChangas() {
        int N_CHANGAS = 100;
        long[] ownerId = {1431234, 1234,3134343,1234,321};
        String[] title = {"Lavar el perro", "Lavar los platos", "Lavarme el culo", "Prositushon", "Se busca nene de 5 años",};
        String[] description = {"Hay que hacerlo la palo", "Vigorosooo", "Full energetic", "Dale candela", "Muevete",};
        double[] price = {13123, 123, 312, 1, 231};
        String[] neighborhood = {"San Telmo", "Flores", "Talar del cheto", "Quinta presidencial", "Calle 13"};
        Random r = new Random();
        int max = 5;
        List<Changa> resp = new ArrayList<>();
        for (int i = 0; i < N_CHANGAS; i++) {
            resp.add(create(new Changa(
                    ownerId[r.nextInt(max)],
                    title[r.nextInt(max)],
                    description[r.nextInt(max)],
                    price[r.nextInt(max)],
                    neighborhood[r.nextInt(max)]
            )));
        }
        return resp;
    }

    private static Changa changaFromRS(ResultSet rs) throws SQLException {
        return new Changa(
            rs.getLong("id"),
            rs.getLong("ownerId"),
            rs.getString("ownerName"),
            rs.getString("description"),
            rs.getDouble("price"),
            rs.getString("neighborhood")
        );
    }

    private Map<String, Object> changaToTableRow(Changa ch) {
        Map<String, Object> resp = new HashMap<>();
        resp.put("ownerId", ch.getownerId());
        resp.put("title", ch.getTitle());
        resp.put("description", ch.getDescription());
        resp.put("price", ch.getPrice());
        resp.put("neighborhood", ch.getNeighborhood() );
        return resp;
    }
}
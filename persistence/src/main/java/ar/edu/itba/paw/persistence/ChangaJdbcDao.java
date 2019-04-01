package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.constants.DBConstants;
import ar.edu.itba.paw.interfaces.ChangaDao;
import ar.edu.itba.paw.models.Changa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Repository
public class ChangaJdbcDao implements ChangaDao {
    private final static RowMapper<Changa> ROW_MAPPER = (rs, rowNum) -> new Changa(rs);

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;

    @Autowired
    public ChangaJdbcDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcTemplate.execute(
                        "CREATE TABLE IF NOT EXISTS"+ Changa.dbName()
                                +"(ownerName SERIAL PRIMARY KEY, username varchar(100))");
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(Changa.dbName()).usingGeneratedKeyColumns("ownerName");
    }
    @Override
    public Changa findById(final long id) {
        final List<Changa> list = jdbcTemplate.query("SELECT * FROM "+DBConstants.CHANGAS.getTableName()+" WHERE userid = ?", ROW_MAPPER, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public Changa create(final Changa changa) {
        final Number userId = jdbcInsert.executeAndReturnKey(changa.toTableRow());
        return new Changa(changa, userId.longValue());
    }

    @Override
    public List<Changa> getAllChangas() {
        return randomChangas();
    }

    private List<Changa> randomChangas() {
        int N_CHANGAS = 100;
        String[] ownerName = {"Juan Carlos", "Camila", "Sofi Murandi", "Rodrigo Escarapietra", "Carmen Villaurquiza"};
        String[] ownerPhone = {"1133071114", "1133071114", "1133071114", "1133071114", "1133071114",};
        String[] title = {"Lavar el perro", "Lavar los platos", "Lavarme el culo", "Prositushon", "Se busca nene de 5 años",};
        String[] description = {"Hay que hacerlo la palo", "Vigorosooo", "Full energetic", "Dale candela", "Muevete",};
        double[] price = {13123, 123, 312, 1, 231};
        String[] neighborhood = {"San Telmo", "Flores", "Talar del cheto", "Quinta presidencial", "Calle 13"};
        Random r = new Random();
        int max = 5;
        List<Changa> resp = new ArrayList<>();
        for (int i = 0; i < N_CHANGAS; i++) {
            resp.add(new Changa(
                    ownerName[r.nextInt(max)],
                    ownerPhone[r.nextInt(max)],
                    title[r.nextInt(max)],
                    description[r.nextInt(max)],
                    price[r.nextInt(max)],
                    neighborhood[r.nextInt(max)]
            ));
        }
        return resp;
    }
}
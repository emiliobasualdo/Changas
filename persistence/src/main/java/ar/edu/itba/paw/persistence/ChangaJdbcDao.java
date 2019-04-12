package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.ChangaDao;
import ar.edu.itba.paw.interfaces.daos.UserDao;
import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.Changa;
import ar.edu.itba.paw.models.Either;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
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
import static ar.edu.itba.paw.interfaces.util.Validation.ErrorCodes.DATABASE_ERROR;
import static ar.edu.itba.paw.interfaces.util.Validation.ErrorCodes.NO_SUCH_USER;

@Repository
public class ChangaJdbcDao implements ChangaDao {
    private final static RowMapper<Changa> ROW_MAPPER = (rs, rowNum) -> changaFromRS(rs);

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;
    @Autowired
    private UserDao usersDao;

    @Autowired
    public ChangaJdbcDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(ds)
                .withTableName(changas.TN())
                .usingGeneratedKeyColumns(changa_id.name());
    }

    @Override
    public Either<Changa, Validation> getById(final long id) {
        final List<Changa> list = jdbcTemplate.query(
                String.format("SELECT * FROM %s WHERE %s = %d",changas.TN(), changa_id.name(), id),
                ROW_MAPPER
        );
        if (list.isEmpty()) {
            return Either.alternative(new Validation(NO_SUCH_USER));
        }
        if(list.size() > 1){
            return Either.alternative(new Validation(DATABASE_ERROR));
        }
        return Either.value(list.get(0));
    }

    @Override
    public Either<Changa, Validation> create(final Changa changa) {
        // si no se insertó ninguna fila, what pass?
        Map<String, Object> changaRow = changaToTableRow(changa);
        int newId;
        try {
            newId = jdbcInsert.execute(changaRow);

        } catch (DuplicateKeyException e ){
            return Either.alternative(new Validation(DATABASE_ERROR));
        }
        return Either.value(new Changa.Builder(changa, newId).build());
    }

    @Override
    public Either<List<Changa>, Validation> getAll() {
        List<Changa> resp = jdbcTemplate.query(
                String.format("SELECT * FROM %s ", changas.TN()),
                ROW_MAPPER
        );

        return Either.value(resp);
    }

    @Override
    public Either<List<Changa>, Validation> getUserOwnedChangas(long id) {
        return Either.value(
            jdbcTemplate.query(
                String.format("SELECT * FROM %s WHERE %s = %d", changas.TN(),
                        user_id.name(), id),
                ROW_MAPPER
            )
        );
    }

    @Override
    public Either<Changa, Validation> update(Changa changa) {
        //TODO pilo
        return null;
    }

    @Override
    public Validation delete(long changaId) {
        return null;
    }

    private List<Changa> generateRandomChangas() {
        int N_CHANGAS = 100;
        long[] userId = {1, 2,3,4,5};
        String[] title = {"Lavar el perro", "Lavar los platos", "Lavarme el culo", "Prositushon", "Se busca nene de 5 años",};
        String[] description = {"Hay que hacerlo la palo", "Vigorosooo", "Full energetic", "Dale candela", "Muevete",};
        double[] price = {13123, 123, 312, 1, 231};
        String[] neigh = {"San Telmo", "Juarez", "Martinez", "San Isidro", "San Fer"};
        String[] calle = {"calle1", "calle2", "calle3", "calle4", "calle5"};
        String[] state = {"done", "emitted", "closed", "settled", "settled"};
        int[] number = {13123, 123, 312, 1, 231};
        LocalDateTime[] createdAt = {LocalDateTime.now(),LocalDateTime.now(),LocalDateTime.now(),LocalDateTime.now(),LocalDateTime.now()};
        Random r = new Random();
        int max = 5;
        List<Changa> resp = new ArrayList<>();
        for (int i = 0; i < N_CHANGAS; i++) {
            resp.add(
                create(
                    new Changa.Builder()
                    .withUserId(userId[r.nextInt(max)])
                    .withPrice(price[r.nextInt(max)])
                    .withState(state[r.nextInt(max)])
                    .withTitle(title[r.nextInt(max)])
                    .createdAt(createdAt[r.nextInt(max)])
                    .withDescription(description[r.nextInt(max)])
                    .atAddress(calle[r.nextInt(max)],neigh[r.nextInt(max)],number[r.nextInt(max)])
                    .build()
                ).getValue()
            );
        }
        return resp;
    }

    private static Changa changaFromRS(ResultSet rs) throws SQLException {
        return new Changa.Builder(rs.getLong(changa_id.name()))
                .withUserId(rs.getLong(user_id.name()))
                .withTitle(rs.getString(title.name()))
                .withDescription(rs.getString(description.name()))
                .withPrice(rs.getDouble(price.name()))
                .atAddress(rs.getString(street.name()),rs.getString(neighborhood.name()),rs.getInt(number.name()) )
                .withState(rs.getString(state.name()))
                //.createdAt(rs.getObject(creation_date.name(), LocalDateTime.class))
                .build();

    }

    private Map<String, Object> changaToTableRow(Changa ch) {
        Map<String, Object> resp = new HashMap<>();
        resp.put(user_id.toString(), ch.getUser_id());
        resp.put(title.toString(), ch.getTitle());
        resp.put(description.toString(), ch.getDescription());
        resp.put(price.toString(), ch.getPrice());
        resp.put(street.toString(), ch.getStreet());
        resp.put(neighborhood.toString(), ch.getNeighborhood());
        resp.put(number.toString(), ch.getNumber());
        //resp.put(creation_date.toString(), Timestamp.valueOf(ch.getCreationDate()));
        resp.put(state.toString(), ch.getState());
        return resp;
    }
}
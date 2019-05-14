package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.ChangaDao;
import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.Changa;
import ar.edu.itba.paw.models.ChangaState;
import ar.edu.itba.paw.models.Either;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.RecoverableDataAccessException;
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
import static ar.edu.itba.paw.interfaces.util.Validation.*;

@Repository
public class ChangaJdbcDao implements ChangaDao {
    private final static RowMapper<Changa> ROW_MAPPER = (rs, rowNum) -> changaFromRS(rs);
    private final static int PAGE_SIZE = 50;

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;

    @Autowired
    public ChangaJdbcDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(ds)
                .withTableName(changas.name())
                .usingGeneratedKeyColumns(changa_id.name());
    }

    @Override
    public Either<Changa, Validation> getById(final long id) {
        final List<Changa> list = jdbcTemplate.query(
                String.format("SELECT * FROM %s WHERE %s = ?",changas.name(), changa_id.name()),
                ROW_MAPPER, id
        );
        if (list.isEmpty()) {
            return Either.alternative(NO_SUCH_CHANGA);
        }
        if(list.size() > 1){
            return Either.alternative(DATABASE_ERROR);
        }
        return Either.value(list.get(0));
    }

    @Override
    public Either<Changa, Validation> create(final Changa.Builder changaBuilder) {
        Number changaId;
        Map<String, Object> changaRow = changaToTableRow(changaBuilder);
        try {
            changaId = jdbcInsert.executeAndReturnKey(changaRow);
        } catch (DataIntegrityViolationException e ) {
            return Either.alternative(NO_SUCH_USER);
        } catch (Exception e ) {
            System.err.println(e.getMessage());
            return Either.alternative(DATABASE_ERROR);
        }
        return getById(changaId.longValue());
    }

    @Override
    public Either<List<Changa>, Validation> getAll(ChangaState filterState, int pageNum) {
        if (pageNum < 0) {
            return Either.alternative(ILLEGAL_VALUE.withMessage("Page number must be greater than zero"));
        }

        List<Changa> resp = jdbcTemplate.query(
                String.format("SELECT * FROM %s WHERE %s = ? ORDER BY %s LIMIT %d OFFSET %d",
                        changas.name(), state.name(), title.name(), PAGE_SIZE, PAGE_SIZE * pageNum),
                 ROW_MAPPER,
                filterState.name()
        );
        return Either.value(resp);
    }

    @Override
    public Either<List<Changa>, Validation> getUserOwnedChangas(long id) {
        return Either.value(
            jdbcTemplate.query(
                String.format("SELECT * FROM %s WHERE %s = ?", changas.name(),
                        user_id.name()),
                ROW_MAPPER,
                id
            )
        );
    }

    @Override
    public Either<Changa, Validation> update(final long changaId, Changa.Builder changaBuilder) {
        int updatedChangas = jdbcTemplate.update(
                String.format("UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ? ",
                    changas.name(),
                    street.name(),
                    neighborhood.name(),
                    number.name(),
                    title.name(),
                    description.name(),
                    state.name(),
                    price.name(),
                    changa_id.name()),

                changaBuilder.getStreet(), changaBuilder.getNeighborhood(),
                changaBuilder.getNumber(), changaBuilder.getTitle(),
                changaBuilder.getDescription(), changaBuilder.getState().toString(),
                changaBuilder.getPrice(), changaId
        );

        // updatedChangas != 1 => rollback! fue un error
    return updatedChangas == 1 ? getById(changaId) : Either.alternative(NO_SUCH_CHANGA);
    }

    @Override
    public Either<Changa, Validation> changeChangaState(long changaId, ChangaState newState) {
        // we assume the service has checked that the change can be done
        try {
            int rowsAffected = this.jdbcTemplate.update(
                    String.format("UPDATE %s set %s = ? WHERE %s = ? ", changas.name(), state.name(), changa_id.name()),
                    newState.name(), changaId);
            if (rowsAffected != 1) {
                throw new RecoverableDataAccessException("rowsAffected != 1");
            }
        } catch (DataAccessException e) {
            System.out.println(e.getMessage());
            return Either.alternative(DATABASE_ERROR);
        }
        return getById(changaId);
    }

    private List<Changa> generateRandomChangas() {
        int N_CHANGAS = 100;
        long[] userId = {1, 2,3,4,5};
        String[] title = {"Lavar el perro", "Lavar los platos", "Lavarme el culo", "Prositushon", "Se busca nene de 5 años",};
        String[] description = {"Hay que hacerlo la palo", "Vigorosooo", "Full energetic", "Dale candela", "Muevete",};
        double[] price = {13123, 123, 312, 1, 231};
        String[] neigh = {"San Telmo", "Juarez", "Martinez", "San Isidro", "San Fer"};
        String[] calle = {"calle1", "calle2", "calle3", "calle4", "calle5"};
        ChangaState[] state = {ChangaState.emitted, ChangaState.settled, ChangaState.closed, ChangaState.done};
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
                ).getValue()
            );
        }
        return resp;
    }

    private static Changa changaFromRS(ResultSet rs) throws SQLException {
        return build (rs.getLong(changa_id.name()), new Changa.Builder()
                                                    .withUserId(rs.getLong(user_id.name()))
                                                    .withTitle(rs.getString(title.name()))
                                                    .withDescription(rs.getString(description.name()))
                                                    .withPrice(rs.getDouble(price.name()))
                                                    .atAddress(rs.getString(street.name()),rs.getString(neighborhood.name()),rs.getInt(number.name()) )
                                                    .withState(ChangaState. valueOf(rs.getString(state.name())))
        );

    }

    private static Changa build(long changaId, Changa.Builder changaBuilder) {
        return new Changa(changaId, changaBuilder);
    }

    private Map<String, Object> changaToTableRow(Changa.Builder changaBuilder) {
        Map<String, Object> resp = new HashMap<>();
        resp.put(user_id.toString(),  changaBuilder.getUser_id());
        resp.put(title.toString(),  changaBuilder.getTitle());
        resp.put(description.toString(),  changaBuilder.getDescription());
        resp.put(price.toString(),  changaBuilder.getPrice());
        resp.put(street.toString(),  changaBuilder.getStreet());
        resp.put(neighborhood.toString(),  changaBuilder.getNeighborhood());
        resp.put(number.toString(),  changaBuilder.getNumber());
        // date convertion
        java.sql.Date sqldate = java.sql.Date.valueOf(changaBuilder.getCreation_date().toLocalDate());
        resp.put(creation_date.toString(), sqldate);
        if(changaBuilder.getState() == null) {
            changaBuilder.withState(ChangaState.emitted);
        }
        resp.put(state.toString(), changaBuilder.getState().toString());
        return resp;
    }
}
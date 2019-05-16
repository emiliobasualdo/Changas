package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.filtersDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static ar.edu.itba.paw.constants.DBFilterFields.*;
import static ar.edu.itba.paw.constants.DBTableName.categories;

@Repository
public class FiltersJdbcDao implements filtersDao {

    private final static RowMapper<String> ROW_MAPPER = (rs, rowNum) -> categoryFromRS(rs);

    private static String categoryFromRS(ResultSet rs) throws SQLException {
        return rs.getString(key.name());
    }

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public FiltersJdbcDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    private List<String> get(String table) {
        return jdbcTemplate.query(
                String.format("SELECT * FROM %s ORDER BY %s", table,
                        key.name()),
                ROW_MAPPER
        );
    }

    @Override
    public List<String> getCategories() {
        return this.get(categories.name());
    }

    @Override
    public List<String> getNeighborhood() {
        return this.get(neighborhood.name());
    }

}

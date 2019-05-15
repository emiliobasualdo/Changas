package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.CategoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static ar.edu.itba.paw.constants.DBCategoriesFields.*;
import static ar.edu.itba.paw.constants.DBTableName.categories;

@Repository
public class CategoryJdbcDao implements CategoryDao {

    private final static RowMapper<String> ROW_MAPPER = (rs, rowNum) -> categoryFromRS(rs);

    private static String categoryFromRS(ResultSet rs) throws SQLException {
        return rs.getString(key.name());
    }

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public CategoryJdbcDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public List<String> getCategories() {
        return jdbcTemplate.query(
                String.format("SELECT * FROM %s ORDER BY %s", categories.name(),
                        key.name()),
                ROW_MAPPER
        );
    }

}

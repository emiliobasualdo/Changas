package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.CategoryDao;
import ar.edu.itba.paw.models.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

import static ar.edu.itba.paw.constants.DBCategoriesFields.*;
import static ar.edu.itba.paw.constants.DBTableName.categories;

@Repository
public class CategoryJdbcDao implements CategoryDao {

    private final static RowMapper<Pair<String, String>> ROW_MAPPER = (rs, rowNum) -> categoryFromRS(rs);

    private static Pair<String, String> categoryFromRS(ResultSet rs) throws SQLException {
        return Pair.buildPair(rs.getString(key.name()), rs.getString(message.name()));
    }

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public CategoryJdbcDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public List<Pair<String, String>> getCategories(Locale filterLocale) {
        final List<Pair<String, String>> list  = jdbcTemplate.query(
                String.format("SELECT key, message FROM %s WHERE %s = ? ORDER BY %s ASC", categories.name()
                        , locale.name(), message.name()),
                ROW_MAPPER,
                filterLocale.toString()
        );
        if(list.isEmpty() ) {
            // we get a default
            return getCategories(Locale.ENGLISH);
        }
        return list;
    }

}

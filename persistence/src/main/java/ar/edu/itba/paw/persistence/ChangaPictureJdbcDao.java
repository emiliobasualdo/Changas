package ar.edu.itba.paw.persistence;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ChangaPictureJdbcDao {
    private final static RowMapper<String> ROW_MAPPER = (rs, rowNum) -> changaPictureFromRS(rs);

    private static String  changaPictureFromRS(ResultSet rs) throws SQLException {
        return rs.getString(key.name());
    }

}

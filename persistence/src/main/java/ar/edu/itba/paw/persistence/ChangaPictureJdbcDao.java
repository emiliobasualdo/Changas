package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.ChangaPicture;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static ar.edu.itba.paw.constants.DBChangaPictureFields.*;

public class ChangaPictureJdbcDao {
    private final static RowMapper<ChangaPicture> ROW_MAPPER = (rs, rowNum) -> changaPictureFromRS(rs);

    private static ChangaPicture changaPictureFromRS(ResultSet rs) throws SQLException {
        return new ChangaPicture(new ChangaPicture.Builder(rs.getLong(changa_id.name()), rs.getString(img_reference.name())));
    }

    private Map<String, Object> changaPictureToTableRow(ChangaPicture.Builder changaPictureBuilder) {
        Map<String, Object> resp = new HashMap<>();
        resp.put(changa_id.name(), changaPictureBuilder.getChangaId());
        resp.put(img_reference.name(), changaPictureBuilder.getImageReference());
        return resp;
    }




}

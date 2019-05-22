package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.UserPictureDao;
import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.Either;
import ar.edu.itba.paw.models.Picture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ar.edu.itba.paw.constants.DBChangaPictureFields.img_blobl;
import static ar.edu.itba.paw.constants.DBTableName.user_picture;
import static ar.edu.itba.paw.constants.DBUserPictureFields.*;
import static ar.edu.itba.paw.interfaces.util.Validation.*;

@Repository
public class UserPictureJdbcDao implements UserPictureDao {
    private final static RowMapper<Picture> ROW_MAPPER = (rs, rowNum) -> userPictureFromRS(rs);
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;

    @Autowired
    public UserPictureJdbcDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(ds)
                .withTableName(user_picture.name())
                .usingColumns(user_id.name(), img_blobl.name());
    }

    private static Picture userPictureFromRS(ResultSet rs) throws SQLException {
        return new Picture(new Picture.Builder(rs.getLong(user_id.name()), rs.getBinaryStream(img_blobl.name())));
    }

    private Map<String, Object> userPictureToTableRow(long userId, OutputStream imageByteStream) {
        Map<String, Object> resp = new HashMap<>();
        resp.put(user_id.name(), userId);
        resp.put(img_blobl.name(), imageByteStream);
        return resp;
    }

    @Override
    public Validation putImage(long userId, OutputStream imageByteStream) {
        Map<String, Object> row = userPictureToTableRow(userId, imageByteStream);
        try {
            jdbcInsert
                    .execute(row);
        } catch (DuplicateKeyException ex) {
            int updatedImage = jdbcTemplate.update(String.format("UPDATE %s SET %s = ?  WHERE %s = ?",
                    user_picture.name(),
                    img_blobl.name(),
                    user_id.name()
                    ),
                    imageByteStream, userId);
            return updatedImage == 1 ? OK : IMAGE_COULDNT_BE_UPDATED;
        } catch (Exception ex) {
            return IMAGE_COULDNT_BE_SAVED;
        }
        return OK;
    }


    @Override
    public Either<InputStream, Validation> getImage(long userId) {
        final List<Picture> list = jdbcTemplate.query(
                String.format("SELECT * FROM %s WHERE %s = ?", user_picture.name(), user_id.name()),
                ROW_MAPPER, userId
        );
        if (list.isEmpty()) {
            return Either.alternative(NO_SUCH_USER);
        }
        if(list.size() > 1){
            return Either.alternative(DATABASE_ERROR);
        }
        return Either.value(list.get(0).getImageByteStream());
    }
}

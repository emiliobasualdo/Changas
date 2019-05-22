package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.ChangaPictureDao;
import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.Picture;
import ar.edu.itba.paw.models.Either;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.DataSource;
import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ar.edu.itba.paw.constants.DBChangaPictureFields.*;
import static ar.edu.itba.paw.constants.DBInscriptionFields.changa_id;
import static ar.edu.itba.paw.constants.DBTableName.changa_picture;
import static ar.edu.itba.paw.interfaces.util.Validation.*;

@Repository
public class ChangaPictureJdbcDao implements ChangaPictureDao {
    private final static RowMapper<Picture> ROW_MAPPER = (rs, rowNum) -> changaPictureFromRS(rs);
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;

    @Autowired
    public ChangaPictureJdbcDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(ds)
                .withTableName(changa_picture.name())
                .usingColumns(changa_id.name(), img_blobl.name());
    }

    private static Picture changaPictureFromRS(ResultSet rs) throws SQLException {
        return new Picture(new Picture.Builder(rs.getLong(changa_id.name()), rs.getBinaryStream(img_blobl.name())));
    }

    private Map<String, Object> changaPictureToTableRow(long changaId, FileInputStream imageByteStream) {
        MultipartFile file;
        byte[] bytes = null;
        try {
            bytes = "/home/maite/Logo-ITBA-azul.png".getBytes();
            //bytes = IOUtils.toByteArray("/home/maite/Logo-ITBA-azul.png");
            System.out.println("a");
        } catch (Exception e) {
            System.out.println("b");
            e.printStackTrace();
        }
        Map<String, Object> resp = new HashMap<>();
        resp.put(changa_id.name(), changaId);
        resp.put(img_blobl.name(), bytes);
        return resp;
    }

//    private Map<String, Object> changaPictureToTableRow(Picture.Builder changaPictureBuilder) {
//        return changaPictureToTableRow(changaPictureBuilder.getChangaId(),changaPictureBuilder.getImageByteStream() );
//    }

    @Override
    public Validation putImage(long changaId, FileInputStream imageByteStream) {
        System.out.println("adaf" + imageByteStream);


        Map<String, Object> row = changaPictureToTableRow(changaId, imageByteStream);
        try {
            jdbcInsert
                    .execute(row);
        } catch (DuplicateKeyException ex) {
            int updatedImage = jdbcTemplate.update(String.format("UPDATE %s SET %s = ? WHERE %s = ?",
                    changa_picture.name(),
                    img_blobl.name(),
                    changa_id.name()
                    ),
                    imageByteStream, changaId);
            return updatedImage == 1 ? OK : IMAGE_COULDNT_BE_UPDATED;
        } catch (Exception ex) {
            System.out.println("aca");
            ex.printStackTrace();
            return IMAGE_COULDNT_BE_SAVED;
        }
        return OK;
    }

    @Override
    public Either<InputStream, Validation> getImage(long changaId) {
        final List<Picture> list = jdbcTemplate.query(
                String.format("SELECT * FROM %s WHERE %s = ?",changa_picture.name(), changa_id.name()),
                ROW_MAPPER, changaId
        );
        if (list.isEmpty()) {
            return Either.alternative(NO_SUCH_CHANGA);
        }
        if(list.size() > 1){
            return Either.alternative(DATABASE_ERROR);
        }
        return Either.value(list.get(0).getImageByteStream());
    }


}

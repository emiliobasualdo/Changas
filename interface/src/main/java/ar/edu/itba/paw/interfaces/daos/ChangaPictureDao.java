package ar.edu.itba.paw.interfaces.daos;


import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.Either;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

public interface ChangaPictureDao {
    Validation putImage(long changaId, MultipartFile multipartFile);
    Either<byte[], Validation> getImage(long changaId);
}

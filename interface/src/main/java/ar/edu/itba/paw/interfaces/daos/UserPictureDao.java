package ar.edu.itba.paw.interfaces.daos;

import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.Either;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.io.OutputStream;

public interface UserPictureDao {
    Validation putImage(long userId, MultipartFile multipartFile);
    Either<byte[], Validation> getImage(long userId);
}

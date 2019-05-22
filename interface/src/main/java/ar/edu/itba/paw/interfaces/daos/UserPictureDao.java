package ar.edu.itba.paw.interfaces.daos;

import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.Either;

import java.io.InputStream;
import java.io.OutputStream;

public interface UserPictureDao {
    Validation putImage(long userId, OutputStream imageByteStream);
    Either<InputStream, Validation> getImage(long userId);
}

package ar.edu.itba.paw.interfaces.daos;


import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.Either;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

public interface ChangaPictureDao {
    Validation putImage(long changaId, OutputStream fileInputStream);
    Either<InputStream, Validation> getImage(long changaId);
}

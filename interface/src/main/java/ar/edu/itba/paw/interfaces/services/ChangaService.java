package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.Changa;
import ar.edu.itba.paw.models.ChangaState;
import ar.edu.itba.paw.models.Either;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * DAO should be limited to only add/update/insert/select Entity
 * objects into/from database and that's all. If you want to do
 * anything extra in terms of logic, add it to service layer.
 * This will help in making code modular and easily replaceable
 * when database is replaced (for some part of data)
 * */
public interface ChangaService {
    Either<Changa, Validation> create(final Changa.Builder changaBuilder);
    Either<Changa, Validation> update(final long changaId, final Changa.Builder changaBuilder);
    Either<List<Changa>, Validation> getUserOwnedChangas(final long userId);
    Either<Changa, Validation> getChangaById(final long changaId);
    Either<List<Changa>, Validation> getEmittedChangas(int pageNum);
    Either<List<Changa>, Validation> getEmittedChangasFiltered(int pageNum, String category, String title, String locality);
    Either<Integer, Validation> getECFPageCount(String category, String title, String locality);
    Either<Changa, Validation> changeChangaState(long changaId, ChangaState newState);
    Either<Changa, Validation> changeChangaState(Changa changa, ChangaState newState);
    Either<List<Changa>, Validation> getUserOpenChangas(long id);
    Either<String, Validation> putImage(long changaId, MultipartFile multipartFile);
    Either<byte[], Validation> getImage(long changaId, String imageName);
}

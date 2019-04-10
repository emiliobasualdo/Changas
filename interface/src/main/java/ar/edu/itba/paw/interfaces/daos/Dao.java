package ar.edu.itba.paw.interfaces.daos;


import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.Either;

public interface Dao<T> {
    Either<T, Validation> getById(long changa_id);

}

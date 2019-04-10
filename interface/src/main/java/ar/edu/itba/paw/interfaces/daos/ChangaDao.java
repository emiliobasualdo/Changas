package ar.edu.itba.paw.interfaces.daos;

import ar.edu.itba.paw.interfaces.util.ValidationError;
import ar.edu.itba.paw.models.Changa;
import ar.edu.itba.paw.models.Either;

import java.util.List;

public interface ChangaDao extends Dao {

    Either<Changa, ValidationError> findById(final long id);
    Either<Changa, ValidationError> create(final Changa changa);
    List<Changa> getAll();
    List<Changa> findByUserId(final long user_id);
}

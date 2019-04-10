package ar.edu.itba.paw.interfaces.daos;

import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.Changa;
import ar.edu.itba.paw.models.Either;

import java.util.List;

public interface ChangaDao extends Dao<Changa> {

    Either<Changa, Validation> getById(final long id);
    Either<Changa, Validation> create(final Changa.Builder changaBuilder);
    Either<List<Changa>, Validation> getAll();
    Either<List<Changa>, Validation> getUserOwnedChangas(final long user_id);
    Either<Changa, Validation> getChanga(final Changa.Builder changaBuilder);
    Either<Changa, Validation> update(final long changaId, Changa.Builder changaBuilder);
    Validation delete(long changaId);
}

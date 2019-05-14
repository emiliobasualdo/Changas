package ar.edu.itba.paw.interfaces.daos;

import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.Changa;
import ar.edu.itba.paw.models.ChangaState;
import ar.edu.itba.paw.models.Either;

import java.util.List;

public interface ChangaDao extends Dao<Changa> {

    Either<Changa, Validation> getById(final long id);
    Either<Changa, Validation> create(final Changa.Builder changaBuilder);
    Either<List<Changa>, Validation> getAll(ChangaState state, int pageNum);
    Either<List<Changa>, Validation> getByCategory(ChangaState state, int pageNum, String filterCategory);
    Either<List<Changa>, Validation> getUserOwnedChangas(final long user_id);
    Either<Changa, Validation> update(final long changaId, Changa.Builder changaBuilder);
    Either<Changa, Validation> changeChangaState(long changaId, ChangaState state);

}

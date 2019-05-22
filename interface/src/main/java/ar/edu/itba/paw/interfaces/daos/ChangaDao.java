package ar.edu.itba.paw.interfaces.daos;

import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.Changa;
import ar.edu.itba.paw.models.ChangaState;
import ar.edu.itba.paw.models.Either;

import java.util.List;

public interface ChangaDao {

    Either<Changa, Validation> getById(final long id);
    Either<Changa, Validation> create(final Changa.Builder changaBuilder);
    Either<List<Changa>, Validation> getAll(ChangaState state, int pageNum);
    Either<List<Changa>, Validation> getFiltered(ChangaState state, int pageNum, String filterCategory, String filterTitle, String filterLocalitie);
    Either<List<Changa>, Validation> getUserOwnedChangas(final long user_id);
    Either<List<Changa>, Validation> getUserOpenChangas(long id);
    Either<Changa, Validation> update(final long changaId, Changa.Builder changaBuilder);
    Either<Changa, Validation> changeChangaState(long changaId, ChangaState state);
    Either<Integer, Validation> getFilteredPageCount(ChangaState emitted, String category, String title, String locality);
}

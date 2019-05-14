package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.Changa;
import ar.edu.itba.paw.models.ChangaState;
import ar.edu.itba.paw.models.Either;

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
    Either<List<Changa>, Validation> getAllEmittedChangas(int pageNum);
    Either<Changa, Validation> changeChangaState(long changaId, ChangaState newState);
    Either<Changa, Validation> changeChangaState(Changa changa, ChangaState newState);
    Either<List<Changa>, Validation> getUserEmittedChangas(long id);

}

package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.daos.ChangaDao;
import ar.edu.itba.paw.interfaces.daos.InscriptionDao;
import ar.edu.itba.paw.interfaces.services.ChangaService;
import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.Changa;
import ar.edu.itba.paw.models.ChangaState;
import ar.edu.itba.paw.models.Either;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static ar.edu.itba.paw.interfaces.util.Validation.*;

@Repository
public class ChangaServiceImpl implements ChangaService {

    @Autowired
    private ChangaDao chDao;

    @Autowired
    private InscriptionDao inDao;

    @Override
    public Either<List<Changa>, Validation> getAllEmittedChangas(int pageNum) {
        if (pageNum < 0) {
            return Either.alternative(ILLEGAL_VALUE.withMessage("Page number must be greater than zero"));
        }
        return chDao.getAll(ChangaState.emitted, pageNum);
    }

    @Override
    public Either<Changa, Validation> create(final Changa.Builder changaBuilder) {
        return chDao.create(changaBuilder);
    }

    /*NOTA: en el parametro changaBuilder se debe de pasar un changaBuilder con los campos updateados y con los campos antiguos de los que
    no fueron updateados. Si se quiere hacer un modificado más rápido, hacer funciones q updateen campos específicos. Me parece innecesario porque no tenemos
    muchos campos
    * */

    @Override
    public Either<Changa, Validation> update(final long changaId, final Changa.Builder changaBuilder) {
        Either<Changa, Validation> old = chDao.getById(changaId);

        if(!old.isValuePresent()) {
            return old;
        }

        // we will update a changa ONLY if no changueros are inscribed in it
        // todo permitir cambiar campos menores como fotos
        if(inDao.hasInscribedUsers(changaId)) {
            return Either.alternative(USERS_INSCRIBED);
        }

        return chDao.update(changaId, changaBuilder);
    }

    @Override
    public Either<Changa, Validation> getChangaById(final long id){
        return chDao.getById(id);
    }

    @Override
    public Either<List<Changa>, Validation> getUserOwnedChangas(long user_id) {
        return chDao.getUserOwnedChangas(user_id);
    }

    @Override
    public Either<Changa, Validation> changeChangaState(long changaId, ChangaState newState) {
        Either<Changa, Validation> oldChanga = chDao.getById(changaId);
        if (oldChanga.isValuePresent())
            return this.changeChangaState(oldChanga.getValue(), newState);
        else
            return oldChanga;
    }

    @Override
    public Either<Changa, Validation> changeChangaState(Changa changa, ChangaState newState) {
        // todo check que el usuario logeado es el que la emitió
        if (ChangaState.changeIsPossible(changa.getState(), newState))
            return chDao.changeChangaState(changa.getChanga_id(), newState);
        else
            return Either.alternative(CHANGE_NOT_POSSIBLE);
    }

    @Override
    public Either<List<Changa>, Validation> getUserEmittedChangas(long id) {
        List<Changa> resp = getUserOwnedChangas(id).getValue();
        resp.removeIf(e -> e.getState() == ChangaState.closed || e.getState() == ChangaState.done || e.getState() == ChangaState.settled);
        return Either.value(resp);
    }

}

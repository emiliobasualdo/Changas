package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.daos.ChangaDao;
import ar.edu.itba.paw.interfaces.daos.InscriptionDao;
import ar.edu.itba.paw.interfaces.services.ChangaService;
import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.Changa;
import ar.edu.itba.paw.models.Either;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static ar.edu.itba.paw.interfaces.util.Validation.ErrorCodes.USERS_INSCRIBED;

@Repository
public class ChangaServiceImpl implements ChangaService {

    @Autowired
    private ChangaDao chDao;

    @Autowired
    private InscriptionDao inDao;

    @Override
    public Either<List<Changa>, Validation> getAllChangas() {
        return chDao.getAll();
    }

    @Override
    public Either<Changa, Validation> create(final Changa.Builder changaBuilder) {
        return chDao.create(changaBuilder);
    }

    // todo NEFASTO cambiar ya
    @Override
    public Either<Changa, Validation> update(final long changaId, final Changa.Builder changaBuilder) {
        Either<Changa, Validation> old = chDao.getById(changaId);
        // if changa exists
        if(!old.isValuePresent()){
            return Either.alternative(old.getAlternative());
        }

        // we will update a changa ONLY if no changueros are inscribed in it
        Either<Boolean, Validation> either = inDao.hasInscribedUsers(changaId);

        // If there was no problem
        if(!either.isValuePresent()){
            return Either.alternative(either.getAlternative()); // todo como solucionar esto? No hay necesidad de hacer new, podría retornar la otra instancia de either
        }

        // if there are no changueros inscribed
        if (!either.getValue()) {
            return chDao.update(changaId, changaBuilder); // todo mal, cambiar de changa a builder
        }
        return Either.alternative(new Validation(USERS_INSCRIBED));
    }

    @Override
    public Validation delete(long changaId) {
        return chDao.delete(changaId);
    }

    @Override
    public Either<Changa, Validation> getChangaById(final long id){
        return chDao.getById(id);
    }

    @Override
    public Either<List<Changa>, Validation> getUserOwnedChangas(long user_id) {
        return chDao.getUserOwnedChangas(user_id);
    }
}

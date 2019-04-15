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
    /*NOTA: en el parametro changaBuilder se debe de pasar un changaBuilder con los campos updateados y con los campos antiguos de los que
    no fueron updateados. Si se quiere hacer un modificado más rápido, hacer funciones q updateen campos específicos. Me parece innecesario porque no tenemos
    muchos campos
    * */
    //Todo validar q el user que quiere modificar la changa es el user dueño
    @Override
    public Either<Changa, Validation> update(final long changaId, final Changa.Builder changaBuilder) {
        Either<Changa, Validation> old = chDao.getById(changaId);

        if(!old.isValuePresent()){
            return old;
        }

        // we will update a changa ONLY if no changueros are inscribed in it
        Either<Boolean, Validation > hasInscribedUsers = inDao.hasInscribedUsers(changaId);

        if (!hasInscribedUsers.isValuePresent()){
            return Either.alternative(hasInscribedUsers.getAlternative());
        }

        if(hasInscribedUsers.getValue()) {
            return Either.alternative(new Validation(USERS_INSCRIBED));
        }

        return chDao.update(changaId, changaBuilder);
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

package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.daos.ChangaDao;
import ar.edu.itba.paw.interfaces.daos.InscriptionDao;
import ar.edu.itba.paw.interfaces.services.AuthenticationService;
import ar.edu.itba.paw.interfaces.services.ChangaService;
import ar.edu.itba.paw.interfaces.services.FileManagerService;
import ar.edu.itba.paw.interfaces.util.FileConventions;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.Changa;
import ar.edu.itba.paw.models.ChangaState;
import ar.edu.itba.paw.models.Either;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.List;

import static ar.edu.itba.paw.interfaces.util.Validation.*;

@Transactional
@Service
public class ChangaServiceImpl implements ChangaService {

    @Autowired
    private ChangaDao chDao;

    @Autowired
    private InscriptionDao inDao;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private FileConventions fc;

    @Override
    public Either<List<Changa>, Validation> getEmittedChangas(int pageNum) {
        if (pageNum < 0) {
            return Either.alternative(ILLEGAL_VALUE.withMessage("Page number must be greater than zero"));
        }
        return chDao.getAll(ChangaState.emitted, pageNum);
    }

    @Override
    public Either<List<Changa>, Validation> getEmittedChangasFiltered(int pageNum, String category, String title, String locality) {
        if (pageNum < 0) {
            return Either.alternative(ILLEGAL_VALUE.withMessage("Page number must be greater than zero"));
        }
        return chDao.getFiltered(ChangaState.emitted, pageNum, category, title, locality);
    }

    @Override
    public Either<Integer, Validation> getECFPageCount(String category, String title, String locality) {
        return chDao.getFilteredPageCount(ChangaState.emitted, category, title, locality);
    }

    @Override
    public Either<Changa, Validation> create(final Changa.Builder changaBuilder) {
        if (!userService.isUserEnabled(changaBuilder.getUser_id())){
            return Either.alternative(DISABLED_USER);
        }
        return chDao.create(changaBuilder);
    }

    /*NOTA: en el parametro changaBuilder se debe de pasar un changaBuilder con los campos updateados y con los campos antiguos de los que
    no fueron updateados. Si se quiere hacer un modificado más rápido, hacer funciones q updateen campos específicos. Me parece innecesario porque no tenemos
    muchos campos
    * */

    @Override
    public Either<Changa, Validation> update(final long changaId, final Changa.Builder changaBuilder) {
        //We check if the changa to be edited exists
        Either<Changa, Validation> old = chDao.getById(changaId);
        if(!old.isValuePresent()) {
            return old;
        }
        //Only owners of the changa can update it
        if(!isLoggedUserAuthorizedToUpdateChanga(old.getValue().getUser_id())){
            return Either.alternative(UNAUTHORIZED);
        }
        // We will update a changa ONLY if no changueros are inscribed in it
        // todo permitir cambiar campos menores como fotos
        if(inDao.hasInscribedUsers(changaId)) {
            return Either.alternative(USERS_INSCRIBED);
        }
        return chDao.update(changaId, changaBuilder);
    }

    private boolean isLoggedUserAuthorizedToUpdateChanga(long changaOwnerId) {
        return authenticationService.getLoggedUser().isPresent() && authenticationService.getLoggedUser().get().getUser_id() == changaOwnerId;
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
        //We check if the user who wants to change the changa's state is the changa owner
        if (!isLoggedUserAuthorizedToUpdateChanga(changa.getUser_id())) {
            return Either.alternative(UNAUTHORIZED);
        }
        if (ChangaState.changeIsPossible(changa.getState(), newState)) {
            // if changa has NO changueros inscribed, it can not be settled. only closed
            if (newState == ChangaState.settled && !inDao.hasInscribedUsers(changa.getChanga_id())) {
                return Either.alternative(SETTLE_WHEN_EMPTY);
            }
            return chDao.changeChangaState(changa.getChanga_id(), newState);
        } else
            return Either.alternative(CHANGE_NOT_POSSIBLE);
    }

    @Override
    public Either<List<Changa>, Validation> getUserOpenChangas(long id) {
        return chDao.getUserOpenChangas(id);
    }

    @Override
    public Either<String, Validation> putImage(String changaId, OutputStream os) {
        // if(chDao.changaExists(os))
        // Todo no borrar este comment
        // En un futuro chDao.putImage debería retornar un Either<Long, Validation>
        // siendo el Long el key de la imagen entre las imágenes de la
        // changa cosa de poder armar el nombre del archivo dinamicamente
        /*Validation validation = chDao.putImage(changaId, os);
        if (validation.isError()) {
            return Either.alternative(validation);
        }
        return Either.value(fc.createName("changa", changaId)); // todo falta extension*/
        return Either.alternative(OK);
    }

    @Override
    public Either<byte[], Validation> getImage(String changaId, String imageName) {
        //if (FileConventions.isValidImageName)
        /*Either<InputStream, Validation> image = chDao.getImage(changaId);
        try {
            return Either.value(IOUtils.toByteArray(image.getValue()));
        } catch (IOException e) {
            return Either.alternative(DATABASE_ERROR.withMessage(e.getMessage()));
        }*/
        return Either.alternative(OK);
    }

}

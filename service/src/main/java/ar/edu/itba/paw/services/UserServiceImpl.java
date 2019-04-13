package ar.edu.itba.paw.services;
import ar.edu.itba.paw.interfaces.daos.UserDao;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.Either;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import static ar.edu.itba.paw.interfaces.util.Validation.ErrorCodes.DATABASE_ERROR;
import static ar.edu.itba.paw.interfaces.util.Validation.ErrorCodes.USER_ALREADY_EXISTS;

@Service
@Primary
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public Either<User, Validation> findById(long id) {
        return userDao.getById(id);
    }

    @Override
    public Either<User, Validation> findByMail(String mail) {
        return userDao.findByMail(mail);
    }

    @Override
    public  Either<User, Validation> register(final User.Builder userBuilder) {

        Either<User, Validation> either = userDao.findByMail(userBuilder.getEmail());

        // if error is from database
        if(!either.isValuePresent() && either.getAlternative().getEc() == DATABASE_ERROR){
            return either;
        }
        /*TODO MAITE
        hacer un || chequeando que el username no exista. Agregar username al UserBuilder.
        */
        // email is allready in use
        if(either.isValuePresent()) {
            return Either.alternative(new Validation(USER_ALREADY_EXISTS));
        }
        return userDao.create(userBuilder);
    }

    @Override
    public  Either<User, Validation> logIn(final User.Builder userBuilder) {
        return userDao.getUser(userBuilder);
    }


}

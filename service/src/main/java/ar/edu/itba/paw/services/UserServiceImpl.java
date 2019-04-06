package ar.edu.itba.paw.services;
import ar.edu.itba.paw.interfaces.daos.UserDao;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.interfaces.util.ValidationError;
import ar.edu.itba.paw.models.Either;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;


    @Override
    public Either<User, ValidationError> findById(long id) {
        return null;
    }

    @Override
    public  Either<User, ValidationError> register(User user) { // todo caso de que ya exista??
//        //Either<User, ValidationError> either = userDao.findByMail(user.getMail());
//
//        //TODO hacer un || chequeando que el username no exista. Agregar username al UserBuilder.
//        if(either.isValuePresent()) {
//            return either;
//        }
        return userDao.create(user);
    }

    @Override
    public  Either<User, ValidationError> logIn(User user) {
        return userDao.getUser(user);
    }
}

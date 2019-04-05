package ar.edu.itba.paw.services;
import ar.edu.itba.paw.interfaces.util.ValidationError;
import ar.edu.itba.paw.interfaces.daos.UserDao;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.util.Either;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import static ar.edu.itba.paw.interfaces.util.ErrorCodes.*;

@Service
@Primary
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;


    @Override
    public Either<User, ValidationError> create(String username, String password, String name, String surname, String phone) {
        if (password == null) {
            return Either.alternative(new ValidationError(INVALID_PASSWORD.getMessage(), INVALID_PASSWORD.getId()));
        }
        if (username == null || username.length() == 0 || userDao.findByUsername(username).isValuePresent() ) {
            return Either.alternative(new ValidationError(INVALID_USERNAME.getMessage(), INVALID_USERNAME.getId()));
        }

        return userDao.create(username, password, name, surname, phone);
    }

}

package ar.edu.itba.paw.services;
import ar.edu.itba.paw.interfaces.daos.UserDao;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.interfaces.util.Validation;
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
    public Either<User, Validation> findById(long id) {
        return userDao.getById(id);
    }

    @Override
    public  Either<User, Validation> register(User user) {
        /*TODO MAITE
        preguntar si en vez de hacer la query findByMail es mejor
        directamente crear el usuario y, si ya existe, catchear la excepcion]
        de la base de datos. como sabes q esa exception es la q viola una determinada key?
         */
        Either<User, Validation> either = userDao.findByMail(user.getEmail());

        /*TODO MAITE
        Hacer username unique en la tabla
        hacer un || chequeando que el username no exista. Agregar username al UserBuilder.
        */
        if(either.isValuePresent()) {
            return either;
        }
        return userDao.create(user);
    }

    @Override
    public  Either<User, Validation> logIn(User user) {
        return userDao.getUser(user);
    }


}

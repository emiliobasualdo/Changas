package ar.edu.itba.paw.persistence;


import ar.edu.itba.paw.interfaces.util.ValidationError;
import ar.edu.itba.paw.interfaces.daos.UserDao;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.util.Either;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {

    @Override
    public Either<User, ValidationError> findById(long id) {
        return null;
    }

    @Override
    public Either<User, ValidationError> findByUsername(String username) {
        return null;
    }

    @Override
    public Either<User, ValidationError> create(String username, String password, String name, String surname, String phone) {
        return null;
    }

}

package ar.edu.itba.paw.interfaces.daos;

import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.Either;
import ar.edu.itba.paw.models.User;

/**
 * DAO should be limited to only add/update/insert/select Entity
 * objects into/from database and that's all. If you want to do
 * anything extra in terms of logic, add it to service layer.
 * This will help in making code modular and easily replaceable
 * when database is replaced (for some part of data)
 * */
public interface UserDao extends Dao<User>{
    Either<User, Validation> getById(final long id);
    Either<User, Validation> findByMail(final String mail);
    Either<User, Validation> create(final User.Builder userBuilder);
    Either<User, Validation> getUser(final User.Builder userBuilder);
    void setUserStatus(final long userId, boolean status);
    void updatePassword(final long id, final String password);
    Either<User, Validation> update(final long userId, User.Builder userBuilder);
    //List<User> createUsers();
}

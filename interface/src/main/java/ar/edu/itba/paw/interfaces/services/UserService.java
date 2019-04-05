package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.interfaces.util.ValidationError;
import ar.edu.itba.paw.util.Either;
import ar.edu.itba.paw.models.User;


/**
 * DAO should be limited to only add/update/insert/select Entity
 * objects into/from database and that's all. If you want to do
 * anything extra in terms of logic, add it to service layer.
 * This will help in making code modular and easily replaceable
 * when database is replaced (for some part of data)
 * */

public interface UserService {
    Either<User, ValidationError> create(String username, String password, String name, String surname, String phone);
    String toString();
}

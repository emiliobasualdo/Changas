package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.Either;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.VerificationToken;

/**
 * DAO should be limited to only add/update/insert/select Entity
 * objects into/from database and that's all. If you want to do
 * anything extra in terms of logic, add it to service layer.
 * This will help in making code modular and easily replaceable
 * when database is replaced (for some part of data)
 * */
public interface UserService {
    Either<User, Validation> findById(long id);
    String toString();
    Either<User, Validation> findByMail(String mail);
    Either<User, Validation> register(final User.Builder userBuilder);
    void createVerificationToken(User user, String token);
    Either<VerificationToken, Validation> getVerificationToken(String VerificationToken);
    Either<VerificationToken, Validation> getVerificationTokenWithRole(final long userId, final String VerificationToken);
    void setUserEnabledStatus(final long userId, final boolean status);
    void resetPassword(final long id, final String password);
    void confirmMailVerification(final long userId, final long tokenId);
}

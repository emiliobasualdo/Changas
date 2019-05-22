package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.Either;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserTokenState;
import ar.edu.itba.paw.models.VerificationToken;
import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;

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
    Either<VerificationToken, Validation> createVerificationToken(User user);
    Either<VerificationToken, Validation> getVerificationToken(String VerificationToken);
    Validation setUserEnabledStatus(final long userId, final boolean status);
    Either<VerificationToken, Validation> getVerificationTokenWithRole(final long userId, final String VerificationToken);
    void resetPassword(final long id, final String password);
    void confirmMailVerification(final long userId, final long tokenId);
    Either<User, Validation> update(final long userId, User.Builder userBuilder);
    Either<UserTokenState, Validation> getUserTokenState(VerificationToken verificationToken);
    Either<VerificationToken, Validation> createNewVerificationToken(String existingTokenValue);
    Either<String, Validation> putImage(long userId, MultipartFile multipartFile);
    Either<byte[], Validation> getImage(long userId, String imageName);
    boolean isUserEnabled(long user_id);

    Validation addRating(long userId, double newRating);
}

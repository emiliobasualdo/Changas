package ar.edu.itba.paw.services;
import ar.edu.itba.paw.interfaces.daos.UserDao;
import ar.edu.itba.paw.interfaces.daos.UserPictureDao;
import ar.edu.itba.paw.interfaces.daos.VerificationTokenDao;
import ar.edu.itba.paw.interfaces.services.AuthenticationService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.interfaces.util.FileConventions;
import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.Either;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserTokenState;
import ar.edu.itba.paw.models.VerificationToken;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Calendar;
import java.util.UUID;

import static ar.edu.itba.paw.interfaces.util.Validation.*;
import static ar.edu.itba.paw.models.UserTokenState.*;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private VerificationTokenDao verificationTokenDao;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private FileConventions fc;

    @Autowired
    private UserPictureDao userPictureDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public Either<User, Validation> findById(long id) {
        return userDao.getById(id);
    }

    @Override
    public Either<User, Validation> findByMail(String mail) {
        return userDao.findByMail(mail.toLowerCase());
    }

    @Override
    public  Either<User, Validation> register(final User.Builder userBuilder) {
        Either<User, Validation> either = userDao.findByMail(userBuilder.getEmail().toLowerCase());

        // if error is from database
        if(!either.isValuePresent() && either.getAlternative() == DATABASE_ERROR){
            LOGGER.error("Attempted to register a user but failed because a Data Base error");
            return either;
        }
        // email is already in use
        if(either.isValuePresent()) {
            return Either.alternative(USER_ALREADY_EXISTS);
        }
        // else (!either.isValuePresent() && either.getAlternative() == NO_SUCH_USER o similar)
        userBuilder.withPasswd(passwordEncoder.encode(userBuilder.getPasswd()));
        return userDao.create(new User.Builder(userBuilder.withEmail(userBuilder.getEmail().toLowerCase())));
    }

    @Override
    public Either<VerificationToken, Validation> createVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken.Builder myToken = new VerificationToken.Builder(token, user.getUser_id());
        return verificationTokenDao.save(myToken);
    }

    @Override
    public Either<VerificationToken, Validation> createNewVerificationToken(String existingTokenValue) {
        Either<VerificationToken, Validation> existingToken = verificationTokenDao.findByToken(existingTokenValue);
        if(!existingToken.isValuePresent()) {
            return Either.alternative(INEXISTENT_TOKEN);
        }
        Either<User, Validation> user = userDao.getById(existingToken.getValue().getUserId());
        if(!user.isValuePresent()) {
            return Either.alternative(NO_SUCH_USER);
        }
        return createVerificationToken(user.getValue());
    }

    @Override
    public boolean isUserEnabled(long user_id) {
        Either<User, Validation> user = findById(user_id);
        return user.isValuePresent() && user.getValue().isEnabled();
    }

    @Override
    public Either<VerificationToken, Validation> getVerificationToken(String tokenString) {
        return verificationTokenDao.findByToken(tokenString);
    }

    @Override
    public Either<UserTokenState, Validation> getUserTokenState(VerificationToken verificationToken) {
        Either<VerificationToken, Validation> validToken = verificationTokenDao.findByToken(verificationToken.getToken());
        if(!validToken.isValuePresent()){
           return Either.alternative(INEXISTENT_TOKEN);
        }

        Either<User, Validation> user = userDao.getById(verificationToken.getUserId());
        if(!user.isValuePresent()) {
            return Either.alternative(SERVER_ERROR);
        }

        Calendar cal = Calendar.getInstance();
        boolean tokenExpired = verificationToken.getExpiryDate().getTime() - cal.getTime().getTime() <= 0;
        boolean userEnabled = user.getValue().isEnabled();

        if(tokenExpired){
            if (userEnabled){
                return Either.value(USER_ENABLED_EXPIRED_TOKEN);
            }
            return Either.value(USER_DISABLED_EXPIRED_TOKEN);
        }

        if(userEnabled) {
            return Either.value(USER_ENABLED_VALID_TOKEN);
        }
        return Either.value(USER_DISABLED_VALID_TOKEN);
    }

    @Override
    public Either<VerificationToken, Validation> getVerificationTokenWithRole(final long userId, final String VerificationToken) {
        Either<VerificationToken, Validation> verificationToken = verificationTokenDao.findByToken(VerificationToken);
        if(!verificationToken.isValuePresent() || Long.compare(verificationToken.getValue().getUserId(), userId) != 0){
            return Either.alternative(Validation.INEXISTENT_TOKEN);
        }
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getValue().getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            return Either.alternative(EXPIRED_TOKEN);
        }
        Authentication auth = new UsernamePasswordAuthenticationToken(
                this.findById(verificationToken.getValue().getUserId()), null, Arrays.asList( new SimpleGrantedAuthority("CHANGE_PASSWORD_PRIVILEGE")));
        SecurityContextHolder.getContext().setAuthentication(auth);
        return verificationToken;
    }

    @Override
    public Validation setUserEnabledStatus(long userId, boolean status) {
        return userDao.setUserStatus(userId, status);
    }

    @Override
    public void resetPassword(long id, String password) {
        userDao.updatePassword(id, passwordEncoder.encode(password));
    }

    @Override
    public void confirmMailVerification(final long userId, final long tokenId) {
        setUserEnabledStatus(userId, true); // falta hacer if del retorno de serUser...
        verificationTokenDao.delete(tokenId);
    }

    @Override
    public Either<User, Validation> update(final long userId, User.Builder userBuilder) {
        if(!authenticationService.isLoggedUserAuthorizedToUpdateUser(userId)){
            return Either.alternative(UNAUTHORIZED);
        }
        return userDao.update(userId, userBuilder);
    }

    @Override
    public Validation addRating(long userId, double newRating) {
        Either<User, Validation> either = userDao.getById(userId);
        if (!either.isValuePresent()) {
            return either.getAlternative();
        }
        newRating = (newRating + either.getValue().getRating() + 0.5) / 2;
        return userDao.setRating(userId, newRating);
    }

    @Override
    public Either<String, Validation> putImage(long userId, OutputStream os) {
        //We check if the user exists
        Either<User, Validation> user = userDao.getById(userId);
        if (!user.isValuePresent())
            return Either.alternative(IMAGE_COULDNT_BE_SAVED);

        //
        if(!isLoggedUserAuthorizedToUpdateUser(userId)){
            return Either.alternative(UNAUTHORIZED);
        }

        // Todo no borrar este comment
        // En un futuro userPictureDao.putImage debería retornar un Either<Long, Validation>
        // siendo el Long el key de la imagen entre las imágenes de la
        // changa cosa de poder armar el nombre del archivo dinamicamente
        Validation validation = userPictureDao.putImage(userId, os);
        if (validation.isError()) {
            return Either.alternative(validation);
        }
        return Either.value(fc.createName("changa", String.valueOf(userId))); // todo falta extension
    }

    @Override
    public Either<byte[], Validation> getImage(long userId, String imageName) {
        //if (FileConventions.isValidImageName)
        Either<InputStream, Validation> image = userPictureDao.getImage(userId);
        try {
            return Either.value(IOUtils.toByteArray(image.getValue()));
        } catch (IOException e) {
            return Either.alternative(DATABASE_ERROR.withMessage(e.getMessage()));
        }
    }

}

package ar.edu.itba.paw.services;
import ar.edu.itba.paw.interfaces.daos.UserDao;
import ar.edu.itba.paw.interfaces.daos.VerificationTokenDao;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.Either;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Calendar;

import static ar.edu.itba.paw.interfaces.util.Validation.ErrorCodes.DATABASE_ERROR;
import static ar.edu.itba.paw.interfaces.util.Validation.ErrorCodes.EXPIRED_TOKEN;
import static ar.edu.itba.paw.interfaces.util.Validation.ErrorCodes.USER_ALREADY_EXISTS;

@Service
@Primary
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private VerificationTokenDao verificationTokenDao;

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
        userBuilder.withPasswd(passwordEncoder.encode(userBuilder.getPasswd()));
        return userDao.create(userBuilder);
    }



    @Override
    public void createVerificationToken(User user, String token)  {
        VerificationToken.Builder myToken = new VerificationToken.Builder(token, user.getUser_id());
        verificationTokenDao.save(myToken);
    }

    @Override
    public Either<VerificationToken, Validation> getVerificationToken(String VerificationToken) {
        Either<VerificationToken, Validation> verificationToken = verificationTokenDao.findByToken(VerificationToken);
        if(!verificationToken.isValuePresent()){
            return verificationToken;
        }
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getValue().getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            return Either.alternative(new Validation(EXPIRED_TOKEN));
        }
        return verificationToken;
    }

    @Override
    public Either<VerificationToken, Validation> getVerificationTokenWithRole(final long userId, final String VerificationToken) {
        Either<VerificationToken, Validation> verificationToken = verificationTokenDao.findByToken(VerificationToken);
        if(!verificationToken.isValuePresent() || Long.compare(verificationToken.getValue().getUserId(), userId) != 0){
            return Either.alternative(new Validation(Validation.ErrorCodes.INEXISTENT_TOKEN));
        }
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getValue().getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            return Either.alternative(new Validation(EXPIRED_TOKEN));
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


}

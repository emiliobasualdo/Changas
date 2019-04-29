package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.daos.VerificationTokenDao;
import ar.edu.itba.paw.interfaces.services.VerificationTokenRepositoryService;
import ar.edu.itba.paw.models.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class VerificationTokenRepositoryServiceImpl implements VerificationTokenRepositoryService {

    @Autowired
    private VerificationTokenDao verificationTokenDao;

    @Override
    public Optional<VerificationToken> findByToken(String token) {
        return verificationTokenDao.findByToken(token);
    }

    @Override
    public void save(VerificationToken.Builder token) {
        verificationTokenDao.save(token);
    }
}

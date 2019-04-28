package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.services.VerificationTokenRepositoryService;
import ar.edu.itba.paw.models.VerificationToken;
import org.springframework.stereotype.Repository;

@Repository
public class VerificationTokenRepositoryServiceImpl implements VerificationTokenRepositoryService {

    public void save(VerificationToken myToken) {
        //em.persist(myToken);
    }
}

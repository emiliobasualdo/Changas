package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.VerificationToken;

public interface VerificationTokenRepositoryService {
    void save(VerificationToken myToken);
}

package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.VerificationToken;

import java.util.Optional;

public interface VerificationTokenRepositoryService {
    Optional<VerificationToken> findByToken(String token);
    void save(VerificationToken.Builder token);
}

package ar.edu.itba.paw.interfaces.daos;

import ar.edu.itba.paw.models.VerificationToken;

import java.util.Optional;

public interface VerificationTokenDao {
    Optional<VerificationToken> findByToken(String token);
    void save(VerificationToken.Builder token);
}

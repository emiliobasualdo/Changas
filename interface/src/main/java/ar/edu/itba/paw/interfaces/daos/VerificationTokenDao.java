package ar.edu.itba.paw.interfaces.daos;

import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.Either;
import ar.edu.itba.paw.models.VerificationToken;

public interface VerificationTokenDao {
    Either<VerificationToken, Validation> findByToken(String token);
    Either<VerificationToken, Validation> save(VerificationToken.Builder token);
    void delete(final long tokenId);
}

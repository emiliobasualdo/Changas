package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.interfaces.util.ValidationError;
import ar.edu.itba.paw.models.Changa;
import ar.edu.itba.paw.models.Either;

import java.util.List;

/**
 * DAO should be limited to only add/update/insert/select Entity
 * objects into/from database and that's all. If you want to do
 * anything extra in terms of logic, add it to service layer.
 * This will help in making code modular and easily replaceable
 * when database is replaced (for some part of data)
 * */
public interface ChangaService {
    List<Changa> getChangas();
    Either<Changa, ValidationError> create(Changa username);

    // todo english please boys
    // este metodo lo uso para mostrar una changa cuando el usuario clickea en alguna de la lista de la pagina principal,
    // va a retornar una changa constante solo para probar.
    Either<Changa, ValidationError> getById(final long id);
    List<Changa> findByUserId(final long user_id);
}

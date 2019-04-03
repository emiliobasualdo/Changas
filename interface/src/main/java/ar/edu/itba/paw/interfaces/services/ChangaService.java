package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Changa;
import ar.edu.itba.paw.models.User;

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
}

package ar.edu.itba.paw.interfaces.daos;

import ar.edu.itba.paw.models.Changa;

import java.util.List;

public interface ChangaDao extends Dao {

    Changa findById(final long id);
    Changa create(final Changa changa);
    List<Changa> getAll();
}

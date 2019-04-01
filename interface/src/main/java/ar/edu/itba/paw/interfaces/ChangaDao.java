package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Changa;

import javax.swing.tree.RowMapper;
import java.util.List;

public interface ChangaDao {
    List<Changa> getAllChangas();
    Changa findById(final long id);
    Changa create(Changa changa);
}

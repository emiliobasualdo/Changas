package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.daos.ChangaDao;
import ar.edu.itba.paw.interfaces.services.ChangaService;
import ar.edu.itba.paw.models.Changa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class ChangaServiceImpl implements ChangaService {

    @Autowired
    private ChangaDao dao;

    @Override
    public List<Changa> getChangas() {
        return dao.getAll();
    }

    @Override
    public Changa create(final Changa changa) {
        return dao.create(changa);
    }
}

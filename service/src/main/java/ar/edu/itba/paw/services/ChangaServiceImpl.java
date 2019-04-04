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
    public Changa create(long ownerId, String title, String description, double price, String neighborhood) { // TODO: alvaro dice que la mejor manera es esta, preguntar a juan si se puede hacer otra cosa
        return dao.create(ownerId, title, description, price, neighborhood);
    }
}

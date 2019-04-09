package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.daos.ChangaDao;
import ar.edu.itba.paw.interfaces.services.ChangaService;
import ar.edu.itba.paw.interfaces.util.ValidationError;
import ar.edu.itba.paw.models.Changa;
import ar.edu.itba.paw.models.Either;
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
    public Either<Changa, ValidationError> create(final Changa changa) {
        return dao.create(changa);
    }

    @Override
    public Either<Changa, ValidationError> getById(final long id){
        return dao.findById(id);
    }

    @Override
    public List<Changa> findByUserId(long user_id) {
        return dao.findByUserId(user_id);
        //return dao.getAll();
    }
}

package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.daos.filtersDao;
import ar.edu.itba.paw.interfaces.services.filtersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class FiltersServiceImpl implements filtersService {
    @Autowired
    private filtersDao catDao;

    @Override
    public List<String> getCategories() {
        return catDao.getCategories();
    }

    @Override
    public List<String> getLocalities() {
        return catDao.getLocalities();
    }
}

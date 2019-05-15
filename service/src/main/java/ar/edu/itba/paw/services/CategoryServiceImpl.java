package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.daos.CategoryDao;
import ar.edu.itba.paw.interfaces.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryDao catDao;

    @Override
    public List<String> getCategories() {
        return catDao.getCategories();
    }
}

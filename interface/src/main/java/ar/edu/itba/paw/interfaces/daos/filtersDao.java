package ar.edu.itba.paw.interfaces.daos;

import java.util.List;

public interface filtersDao {
    List<String> getCategories();
    List<String> getLocalities();
}

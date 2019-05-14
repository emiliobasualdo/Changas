package ar.edu.itba.paw.interfaces.daos;

import ar.edu.itba.paw.models.Pair;

import java.util.List;
import java.util.Locale;

public interface CategoryDao {
    List<Pair<String, String>> getCategories(Locale locale);
}

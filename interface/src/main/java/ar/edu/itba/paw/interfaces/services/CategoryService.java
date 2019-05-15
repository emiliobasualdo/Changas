package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Pair;

import java.util.List;
import java.util.Locale;

public interface CategoryService {
    List<Pair<String, String>> getCategories(Locale locale);
}

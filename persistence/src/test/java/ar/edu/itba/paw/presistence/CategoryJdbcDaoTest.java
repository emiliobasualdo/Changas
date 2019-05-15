package ar.edu.itba.paw.presistence;

import ar.edu.itba.paw.models.Pair;
import ar.edu.itba.paw.persistence.CategoryJdbcDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;
import java.util.List;
import java.util.Locale;

import static org.junit.Assert.assertEquals;

@Sql("classpath:sql/a_create_tables.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Rollback
public class CategoryJdbcDaoTest {

    @Autowired
    private DataSource ds;

    @Autowired
    private CategoryJdbcDao categoryDao;

    //TODO No puedo usar métodos auxiliares de la clase que quiero probar, solo los métodos que quiero probar

    @Test
    public void testGetByLocaleEnglish_returnsEnglishList() {
        // SETUP:
        // EJERCITAR
        final List<Pair<String, String>> list = categoryDao.getCategories(Locale.ENGLISH);
        // ASSERT
        assertEquals("Education", list.get(0).getValue());
        assertEquals("Home maintenance", list.get(1).getValue());
    }

    @Test
    public void testGetByLocaleGerman_returnsEnglishList() {
        // SETUP:
        // EJERCITAR
        final List<Pair<String, String>> list = categoryDao.getCategories(Locale.GERMAN);
        // ASSERT
        assertEquals("Education", list.get(0).getValue());
        assertEquals("Home maintenance", list.get(1).getValue());
    }

    @Test
    public void testGetByLocaleSpanish_returnsSpanishList() {
        // SETUP:
        // EJERCITAR
        final List<Pair<String, String>> list = categoryDao.getCategories(Locale.forLanguageTag("es"));
        // ASSERT
        assertEquals("Ayuda de software", list.get(0).getValue());
        assertEquals("Educación", list.get(1).getValue());
    }

}


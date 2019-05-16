package ar.edu.itba.paw.presistence;

import ar.edu.itba.paw.persistence.FiltersJdbcDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Rollback
public class FiltersJdbcDaoTest {

    @Autowired
    private DataSource ds;

    @Autowired
    private FiltersJdbcDao filterDao;

    //TODO No puedo usar métodos auxiliares de la clase que quiero probar, solo los métodos que quiero probar

    @Test
    public void testGetCategoires_returnsList() {
        // SETUP:
        // EJERCITAR
        final List<String> list = filterDao.getCategories();
        // ASSERT
        assertEquals("education", list.get(0));
        assertEquals("home", list.get(1));
    }

    @Test
    public void testGetNeighborhoods_returnsList() {
        // SETUP:
        // EJERCITAR
        final List<String> list = filterDao.getNeighborhoods();
        // ASSERT
        assertEquals("11 de Septiembre", list.get(0));
        assertEquals("20 de Junio", list.get(1));
    }
}


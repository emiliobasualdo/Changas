package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.GeneralDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class GeneralJdbcDao implements GeneralDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public GeneralJdbcDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        createTables();
    }

    @Override
    public void createTables() { // todo preguntar como mejorar esto
        String query =
            "CREATE TABLE IF NOT EXISTS users ( " +
                "user_id    SERIAL PRIMARY KEY, " +
                "name       VARCHAR(100), " +
                "surname    VARCHAR(100), " +
                "tel        varchar(10)" +
            "); " +

            "DO $$ BEGIN " +
                "CREATE TYPE address AS" +
                    "(" +
                        "street         VARCHAR(100), " +
                        "neighborhood 	VARCHAR(100), " +
                        "number 		INTEGER" +
                    "); " +
            "EXCEPTION " +
                    "WHEN duplicate_object THEN null;" +
            "END $$;" +

            "CREATE TABLE IF NOT EXISTS changas ( " +
                "changa_id      SERIAL PRIMARY KEY, " +
                "user_id        SERIAL, " +
                "address        address, " +
                "creation_date  TIMESTAMP, " +
                "title          VARCHAR(100), " +
                "description    VARCHAR(100), " +
                "state 			VARCHAR(100), " +
                "price 			DOUBLE PRECISION, " +
                "FOREIGN KEY (user_id) REFERENCES users(user_id)" +
            "); " +

            "CREATE TABLE IF NOT EXISTS user_owns ( " +
                "user_id        SERIAL, " +
                "changa_id      SERIAL UNIQUE, " +
                "FOREIGN KEY (user_id) REFERENCES users(user_id), " +
                "FOREIGN KEY (changa_id) REFERENCES changas(changa_id)" +
            "); " +

            "CREATE TABLE IF NOT EXISTS user_inscribed ( " +
                "user_id        SERIAL , " +
                "changa_id      SERIAL, " +
                "state 		    INTEGER, " +
                "FOREIGN KEY (user_id) REFERENCES users(user_id), " +
                "FOREIGN KEY (changa_id) REFERENCES changas(changa_id)" +
            ");" +

                "DO\n" +
                "$do$\n" +
                "BEGIN\n" +
                "IF EXISTS (SELECT * FROM public.users LIMIT 1) THEN\n" +
                "   SELECT * FROM public.users LIMIT 1;\n" +
                "ELSE \n" +

                    "INSERT INTO public.users (user_id, name, surname, tel) VALUES (1, 'Pilo', 'Basualdo', '0033071114');\n" +

                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (3, 1, null, null, 'en casa', 'Limpiar el gato', 'done', 1231);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (4, 1, null, null, 'en casa', 'Limpiar el gato', 'done', 1231);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (5, 1, null, null, 'en casa', 'Limpiar el gato', 'done', 1231);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (6, 1, null, null, 'en casa', 'Limpiar el gato', 'done', 1231);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (7, 1, null, null, 'en casa', 'Limpiar el gato', 'done', 1231);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (8, 1, null, null, 'en casa', 'Limpiar el gato', 'done', 1231);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (9, 1, null, null, 'en casa', 'Limpiar el gato', 'done', 1231);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (10, 1, null, null, 'Se busca nene de 5 años', 'Full energetic', 'settled', 231);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (11, 1, null, null, 'Prositushon', 'Vigorosooo', 'settled', 13123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (12, 1, null, null, 'Lavar los platos', 'Full energetic', 'settled', 13123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (13, 1, null, null, 'Lavarme el culo', 'Vigorosooo', 'closed', 123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (14, 1, null, null, 'Lavar los platos', 'Vigorosooo', 'settled', 13123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (15, 1, null, null, 'Prositushon', 'Dale candela', 'emitted', 13123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (16, 1, null, null, 'Lavar el perro', 'Full energetic', 'settled', 13123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (17, 1, null, null, 'Se busca nene de 5 años', 'Hay que hacerlo la palo', 'emitted', 123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (18, 1, null, null, 'Lavarme el culo', 'Vigorosooo', 'settled', 312);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (19, 1, null, null, 'Lavarme el culo', 'Dale candela', 'settled', 13123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (22, 1, null, null, 'Se busca nene de 5 años', 'Full energetic', 'emitted', 123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (23, 1, null, null, 'Se busca nene de 5 años', 'Muevete', 'settled', 13123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (24, 1, null, null, 'Se busca nene de 5 años', 'Hay que hacerlo la palo', 'settled', 231);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (25, 1, null, null, 'Prositushon', 'Muevete', 'emitted', 123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (26, 1, null, null, 'Lavarme el culo', 'Vigorosooo', 'done', 312);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (27, 1, null, null, 'Se busca nene de 5 años', 'Full energetic', 'closed', 1);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (28, 1, null, null, 'Lavar el perro', 'Full energetic', 'settled', 1);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (29, 1, null, null, 'Prositushon', 'Muevete', 'settled', 312);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (30, 1, null, null, 'Prositushon', 'Hay que hacerlo la palo', 'emitted', 231);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (31, 1, null, null, 'Lavar el perro', 'Hay que hacerlo la palo', 'emitted', 1);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (32, 1, null, null, 'Prositushon', 'Dale candela', 'emitted', 1);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (33, 1, null, null, 'Lavar el perro', 'Full energetic', 'closed', 312);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (34, 1, null, null, 'Prositushon', 'Vigorosooo', 'settled', 312);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (35, 1, null, null, 'Se busca nene de 5 años', 'Muevete', 'closed', 312);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (36, 1, null, null, 'Lavar el perro', 'Vigorosooo', 'closed', 231);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (37, 1, null, null, 'Prositushon', 'Full energetic', 'settled', 231);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (38, 1, null, null, 'Prositushon', 'Dale candela', 'settled', 123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (39, 1, null, null, 'Lavarme el culo', 'Muevete', 'closed', 231);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (40, 1, null, null, 'Lavar los platos', 'Muevete', 'settled', 123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (41, 1, null, null, 'Lavarme el culo', 'Hay que hacerlo la palo', 'closed', 123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (42, 1, null, null, 'Lavarme el culo', 'Muevete', 'emitted', 1);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (43, 1, null, null, 'Se busca nene de 5 años', 'Hay que hacerlo la palo', 'settled', 1);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (44, 1, null, null, 'Lavar el perro', 'Muevete', 'done', 13123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (45, 1, null, null, 'Lavar los platos', 'Dale candela', 'closed', 123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (46, 1, null, null, 'Prositushon', 'Hay que hacerlo la palo', 'closed', 312);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (47, 1, null, null, 'Se busca nene de 5 años', 'Hay que hacerlo la palo', 'settled', 231);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (48, 1, null, null, 'Se busca nene de 5 años', 'Full energetic', 'done', 1);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (49, 1, null, null, 'Prositushon', 'Dale candela', 'settled', 13123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (50, 1, null, null, 'Se busca nene de 5 años', 'Dale candela', 'settled', 1);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (51, 1, null, null, 'Se busca nene de 5 años', 'Hay que hacerlo la palo', 'settled', 123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (52, 1, null, null, 'Prositushon', 'Dale candela', 'emitted', 231);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (53, 1, null, null, 'Lavar los platos', 'Vigorosooo', 'settled', 1);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (54, 1, null, null, 'Lavar los platos', 'Dale candela', 'closed', 312);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (55, 1, null, null, 'Lavar el perro', 'Dale candela', 'settled', 1);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (56, 1, null, null, 'Lavarme el culo', 'Dale candela', 'settled', 312);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (57, 1, null, null, 'Lavar los platos', 'Dale candela', 'closed', 13123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (58, 1, null, null, 'Lavar el perro', 'Vigorosooo', 'settled', 1);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (59, 1, null, null, 'Se busca nene de 5 años', 'Vigorosooo', 'settled', 312);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (60, 1, null, null, 'Lavar los platos', 'Vigorosooo', 'settled', 123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (61, 1, null, null, 'Lavar los platos', 'Hay que hacerlo la palo', 'done', 312);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (62, 1, null, null, 'Lavar los platos', 'Dale candela', 'done', 312);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (63, 1, null, null, 'Lavar el perro', 'Full energetic', 'closed', 312);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (64, 1, null, null, 'Se busca nene de 5 años', 'Hay que hacerlo la palo', 'settled', 13123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (65, 1, null, null, 'Se busca nene de 5 años', 'Vigorosooo', 'emitted', 123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (66, 1, null, null, 'Lavar los platos', 'Vigorosooo', 'closed', 123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (67, 1, null, null, 'Prositushon', 'Full energetic', 'settled', 123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (68, 1, null, null, 'Lavar el perro', 'Muevete', 'settled', 123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (69, 1, null, null, 'Prositushon', 'Hay que hacerlo la palo', 'settled', 123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (70, 1, null, null, 'Lavar el perro', 'Hay que hacerlo la palo', 'emitted', 13123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (71, 1, null, null, 'Lavar los platos', 'Muevete', 'emitted', 1);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (72, 1, null, null, 'Se busca nene de 5 años', 'Dale candela', 'settled', 312);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (73, 1, null, null, 'Lavarme el culo', 'Vigorosooo', 'settled', 123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (74, 1, null, null, 'Prositushon', 'Dale candela', 'settled', 231);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (75, 1, null, null, 'Lavar el perro', 'Vigorosooo', 'emitted', 13123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (76, 1, null, null, 'Lavarme el culo', 'Full energetic', 'settled', 312);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (77, 1, null, null, 'Lavarme el culo', 'Full energetic', 'closed', 1);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (78, 1, null, null, 'Lavarme el culo', 'Full energetic', 'emitted', 13123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (79, 1, null, null, 'Se busca nene de 5 años', 'Muevete', 'settled', 312);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (80, 1, null, null, 'Lavar los platos', 'Vigorosooo', 'done', 1);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (81, 1, null, null, 'Lavar el perro', 'Dale candela', 'settled', 13123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (82, 1, null, null, 'Se busca nene de 5 años', 'Full energetic', 'settled', 123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (83, 1, null, null, 'Lavarme el culo', 'Full energetic', 'emitted', 123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (84, 1, null, null, 'Se busca nene de 5 años', 'Full energetic', 'emitted', 123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (85, 1, null, null, 'Lavar los platos', 'Dale candela', 'done', 231);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (86, 1, null, null, 'Se busca nene de 5 años', 'Dale candela', 'emitted', 231);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (87, 1, null, null, 'Lavar el perro', 'Hay que hacerlo la palo', 'settled', 123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (88, 1, null, null, 'Se busca nene de 5 años', 'Muevete', 'settled', 13123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (89, 1, null, null, 'Se busca nene de 5 años', 'Hay que hacerlo la palo', 'settled', 231);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (90, 1, null, null, 'Lavar el perro', 'Vigorosooo', 'done', 231);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (91, 1, null, null, 'Se busca nene de 5 años', 'Dale candela', 'closed', 123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (92, 1, null, null, 'Prositushon', 'Dale candela', 'settled', 13123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (93, 1, null, null, 'Lavarme el culo', 'Hay que hacerlo la palo', 'emitted', 13123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (94, 1, null, null, 'Se busca nene de 5 años', 'Vigorosooo', 'done', 312);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (95, 1, null, null, 'Lavar los platos', 'Dale candela', 'closed', 312);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (96, 1, null, null, 'Lavarme el culo', 'Vigorosooo', 'closed', 231);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (97, 1, null, null, 'Prositushon', 'Muevete', 'settled', 312);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (98, 1, null, null, 'Lavar el perro', 'Dale candela', 'done', 312);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (99, 1, null, null, 'Lavar el perro', 'Muevete', 'closed', 1);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (100, 1, null, null, 'Prositushon', 'Hay que hacerlo la palo', 'emitted', 231);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (101, 1, null, null, 'Lavar el perro', 'Vigorosooo', 'emitted', 123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (102, 1, null, null, 'Lavar los platos', 'Muevete', 'settled', 312);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (103, 1, null, null, 'Prositushon', 'Muevete', 'closed', 1);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (104, 1, null, null, 'Lavarme el culo', 'Hay que hacerlo la palo', 'emitted', 1);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (105, 1, null, null, 'Lavarme el culo', 'Hay que hacerlo la palo', 'emitted', 123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (106, 1, null, null, 'Lavar el perro', 'Vigorosooo', 'done', 13123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (107, 1, null, null, 'Lavarme el culo', 'Vigorosooo', 'emitted', 13123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (108, 1, null, null, 'Lavar los platos', 'Vigorosooo', 'done', 312);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (109, 1, null, null, 'Prositushon', 'Muevete', 'emitted', 1);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (110, 1, null, null, 'Se busca nene de 5 años', 'Hay que hacerlo la palo', 'closed', 231);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (111, 1, null, null, 'Lavar los platos', 'Dale candela', 'closed', 123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (112, 1, null, null, 'Se busca nene de 5 años', 'Muevete', 'settled', 13123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (113, 1, null, null, 'Lavar el perro', 'Vigorosooo', 'settled', 312);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (114, 1, null, null, 'Lavar el perro', 'Muevete', 'emitted', 312);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (115, 1, null, null, 'Lavar los platos', 'Muevete', 'done', 123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (116, 1, null, null, 'Lavar los platos', 'Vigorosooo', 'emitted', 231);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (117, 1, null, null, 'Se busca nene de 5 años', 'Full energetic', 'closed', 13123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (118, 1, null, null, 'Prositushon', 'Muevete', 'emitted', 123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (119, 1, null, null, 'Se busca nene de 5 años', 'Vigorosooo', 'settled', 123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (120, 1, null, null, 'Prositushon', 'Full energetic', 'settled', 312);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (121, 1, null, null, 'Lavarme el culo', 'Vigorosooo', 'done', 1);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (122, 1, null, null, 'Prositushon', 'Full energetic', 'settled', 1);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (123, 1, null, null, 'Se busca nene de 5 años', 'Hay que hacerlo la palo', 'emitted', 312);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (124, 1, null, null, 'Lavar los platos', 'Muevete', 'done', 231);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (125, 1, null, null, 'Lavar los platos', 'Dale candela', 'emitted', 13123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (126, 1, null, null, 'Lavar el perro', 'Vigorosooo', 'settled', 1);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (127, 1, null, null, 'Lavarme el culo', 'Muevete', 'emitted', 123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (128, 1, null, null, 'Prositushon', 'Dale candela', 'done', 312);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (129, 1, null, null, 'Se busca nene de 5 años', 'Hay que hacerlo la palo', 'closed', 312);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (130, 1, null, null, 'Lavar los platos', 'Full energetic', 'settled', 231);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (131, 1, null, null, 'Se busca nene de 5 años', 'Full energetic', 'settled', 1);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (132, 1, null, null, 'Se busca nene de 5 años', 'Muevete', 'done', 312);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (133, 1, null, null, 'Lavar los platos', 'Dale candela', 'settled', 1);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (134, 1, null, null, 'Prositushon', 'Dale candela', 'settled', 123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (135, 1, null, null, 'Lavar los platos', 'Muevete', 'settled', 13123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (136, 1, null, null, 'Lavar el perro', 'Dale candela', 'settled', 123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (137, 1, null, null, 'Lavar los platos', 'Hay que hacerlo la palo', 'settled', 13123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (138, 1, null, null, 'Lavar los platos', 'Vigorosooo', 'settled', 123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (139, 1, null, null, 'Lavar los platos', 'Vigorosooo', 'settled', 231);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (140, 1, null, null, 'Prositushon', 'Vigorosooo', 'settled', 312);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (141, 1, null, null, 'Lavarme el culo', 'Vigorosooo', 'emitted', 231);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (142, 1, null, null, 'Lavar el perro', 'Hay que hacerlo la palo', 'settled', 123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (143, 1, null, null, 'Lavar el perro', 'Full energetic', 'settled', 312);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (144, 1, null, null, 'Lavarme el culo', 'Vigorosooo', 'emitted', 1);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (145, 1, null, null, 'Lavar los platos', 'Vigorosooo', 'settled', 123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (146, 1, null, null, 'Lavar el perro', 'Dale candela', 'done', 123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (147, 1, null, null, 'Prositushon', 'Dale candela', 'closed', 312);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (148, 1, null, null, 'Prositushon', 'Full energetic', 'emitted', 312);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (149, 1, null, null, 'Se busca nene de 5 años', 'Vigorosooo', 'settled', 1);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (150, 1, null, null, 'Se busca nene de 5 años', 'Muevete', 'settled', 13123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (151, 1, null, null, 'Lavar el perro', 'Hay que hacerlo la palo', 'closed', 1);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (152, 1, null, null, 'Se busca nene de 5 años', 'Muevete', 'done', 231);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (153, 1, null, null, 'Lavar los platos', 'Muevete', 'emitted', 231);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (154, 1, null, null, 'Lavar los platos', 'Hay que hacerlo la palo', 'settled', 123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (155, 1, null, null, 'Lavarme el culo', 'Full energetic', 'emitted', 123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (156, 1, null, null, 'Lavar el perro', 'Dale candela', 'settled', 312);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (157, 1, null, null, 'Prositushon', 'Hay que hacerlo la palo', 'done', 123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (158, 1, null, null, 'Prositushon', 'Hay que hacerlo la palo', 'done', 13123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (159, 1, null, null, 'Lavar los platos', 'Hay que hacerlo la palo', 'closed', 123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (160, 1, null, null, 'Se busca nene de 5 años', 'Full energetic', 'settled', 231);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (161, 1, null, null, 'Lavar el perro', 'Muevete', 'closed', 312);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (162, 1, null, null, 'Lavar los platos', 'Vigorosooo', 'settled', 13123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (163, 1, null, null, 'Se busca nene de 5 años', 'Full energetic', 'settled', 123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (164, 1, null, null, 'Se busca nene de 5 años', 'Full energetic', 'closed', 123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (165, 1, null, null, 'Lavarme el culo', 'Hay que hacerlo la palo', 'done', 1);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (166, 1, null, null, 'Prositushon', 'Dale candela', 'settled', 123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (167, 1, null, null, 'Se busca nene de 5 años', 'Vigorosooo', 'emitted', 123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (168, 1, null, null, 'Lavarme el culo', 'Full energetic', 'emitted', 1);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (169, 1, null, null, 'Lavar el perro', 'Muevete', 'settled', 1);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (170, 1, null, null, 'Lavar los platos', 'Hay que hacerlo la palo', 'settled', 13123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (171, 1, null, null, 'Prositushon', 'Hay que hacerlo la palo', 'settled', 231);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (172, 1, null, null, 'Se busca nene de 5 años', 'Dale candela', 'closed', 13123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (173, 1, null, null, 'Prositushon', 'Hay que hacerlo la palo', 'done', 231);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (174, 1, null, null, 'Prositushon', 'Dale candela', 'settled', 231);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (175, 1, null, null, 'Se busca nene de 5 años', 'Full energetic', 'closed', 312);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (176, 1, null, null, 'Lavar los platos', 'Vigorosooo', 'settled', 312);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (177, 1, null, null, 'Lavar el perro', 'Muevete', 'closed', 312);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (178, 1, null, null, 'Lavar los platos', 'Dale candela', 'settled', 1);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (179, 1, null, null, 'Lavarme el culo', 'Muevete', 'done', 231);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (180, 1, null, null, 'Lavar el perro', 'Hay que hacerlo la palo', 'settled', 123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (181, 1, null, null, 'Se busca nene de 5 años', 'Vigorosooo', 'emitted', 1);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (182, 1, null, null, 'Lavarme el culo', 'Muevete', 'closed', 312);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (183, 1, null, null, 'Prositushon', 'Full energetic', 'emitted', 312);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (184, 1, null, null, 'Prositushon', 'Vigorosooo', 'settled', 13123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (185, 1, null, null, 'Prositushon', 'Dale candela', 'settled', 123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (186, 1, null, null, 'Lavar el perro', 'Dale candela', 'closed', 13123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (187, 1, null, null, 'Se busca nene de 5 años', 'Hay que hacerlo la palo', 'settled', 231);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (188, 1, null, null, 'Se busca nene de 5 años', 'Muevete', 'done', 123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (189, 1, null, null, 'Lavar los platos', 'Muevete', 'settled', 1);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (190, 1, null, null, 'Prositushon', 'Muevete', 'done', 13123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (191, 1, null, null, 'Lavar el perro', 'Hay que hacerlo la palo', 'done', 312);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (192, 1, null, null, 'Lavar los platos', 'Hay que hacerlo la palo', 'closed', 13123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (193, 1, null, null, 'Lavarme el culo', 'Hay que hacerlo la palo', 'done', 13123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (194, 1, null, null, 'Lavar los platos', 'Hay que hacerlo la palo', 'settled', 1);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (195, 1, null, null, 'Lavar el perro', 'Full energetic', 'done', 13123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (196, 1, null, null, 'Lavar el perro', 'Full energetic', 'done', 231);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (197, 1, null, null, 'Lavar el perro', 'Full energetic', 'closed', 312);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (198, 1, null, null, 'Se busca nene de 5 años', 'Dale candela', 'done', 312);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (199, 1, null, null, 'Lavarme el culo', 'Dale candela', 'done', 13123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (200, 1, null, null, 'Lavarme el culo', 'Dale candela', 'emitted', 231);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (201, 1, null, null, 'Prositushon', 'Hay que hacerlo la palo', 'closed', 123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (202, 1, null, null, 'Se busca nene de 5 años', 'Dale candela', 'closed', 1);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (203, 1, null, null, 'Lavar los platos', 'Hay que hacerlo la palo', 'emitted', 231);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (204, 1, null, null, 'Lavar el perro', 'Muevete', 'settled', 13123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (205, 1, null, null, 'Prositushon', 'Hay que hacerlo la palo', 'settled', 312);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (206, 1, null, null, 'Prositushon', 'Hay que hacerlo la palo', 'settled', 13123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (207, 1, null, null, 'Se busca nene de 5 años', 'Muevete', 'closed', 123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (208, 1, null, null, 'Lavar los platos', 'Full energetic', 'settled', 13123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (209, 1, null, null, 'Prositushon', 'Muevete', 'emitted', 312);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (210, 1, null, null, 'Se busca nene de 5 años', 'Vigorosooo', 'settled', 123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (211, 1, null, null, 'Lavarme el culo', 'Hay que hacerlo la palo', 'settled', 13123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (212, 1, null, null, 'Prositushon', 'Muevete', 'settled', 231);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (213, 1, null, null, 'Se busca nene de 5 años', 'Muevete', 'settled', 123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (214, 1, null, null, 'Prositushon', 'Full energetic', 'emitted', 123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (215, 1, null, null, 'Se busca nene de 5 años', 'Hay que hacerlo la palo', 'done', 312);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (216, 1, null, null, 'Prositushon', 'Muevete', 'emitted', 312);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (217, 1, null, null, 'Lavar el perro', 'Muevete', 'closed', 312);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (218, 1, null, null, 'Lavar el perro', 'Hay que hacerlo la palo', 'emitted', 13123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (219, 1, null, null, 'Lavar los platos', 'Hay que hacerlo la palo', 'emitted', 123);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (220, 1, null, null, 'Lavarme el culo', 'Full energetic', 'closed', 1);\n" +
                    "INSERT INTO public.changas (changa_id, user_id, address, creation_date, title, description, state, price) VALUES (221, 1, null, null, 'Lavar los platos', 'Muevete', 'emitted', 123);\n" +
                "\n" +
                "END IF;\n" +
                "END\n" +
                "$do$";
        jdbcTemplate.execute(query);
    }
}

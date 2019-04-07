package ar.edu.itba.paw.services;
import ar.edu.itba.paw.interfaces.daos.UserDao;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;


    @Override
    public User findById(long id) {
        // harcodeo esto solo para probar la vista del perfil de un usuario
        return new User.Builder()
                .withEmail("prueba@gmail.com")
                .withPasswd("prueba123")
                .withName("prueba")
                .withSurname("probando")
                .build();
    }

    @Override
    public User register(User user) { // todo caso de que ya exista??
        return userDao.create(user);
    }

    @Override
    public User logIn(User user) {
        return userDao.getUser(user);
    }

    @Override
    public User getUser(User user) {
        return userDao.getUser(user);
    }
}

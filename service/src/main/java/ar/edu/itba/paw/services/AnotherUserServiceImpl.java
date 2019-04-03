package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.User;
import org.springframework.stereotype.Service;

@Service
public class AnotherUserServiceImpl implements UserService {

    @Override
    public User findById(long id) {
        return null;
    }
}

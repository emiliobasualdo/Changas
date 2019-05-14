package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.Either;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;

@Component
public class CostumeUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService us;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {

        final Either<User, Validation> either = us.findByMail(username);

        if (!either.isValuePresent()) {
            throw new UsernameNotFoundException("No user by the name " + username);
        }

        final User user = either.getValue();

        final Collection<? extends GrantedAuthority> authorities = Arrays.asList(
                new SimpleGrantedAuthority("ROLE_USER")
        );
        return new org.springframework.security.core.userdetails.User(username, user.getPasswd(), //TODO: preguntar que onda aca, la pass va a ser cualquier cosa ya que esta hasheada, se usa para algo??
                user.isEnabled(),
                true,
                true,
                true,
                authorities);
    }

}

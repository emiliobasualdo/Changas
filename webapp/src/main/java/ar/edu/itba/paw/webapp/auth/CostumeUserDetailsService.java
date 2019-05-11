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

    private boolean accountNonExpired = true;
    private boolean credentialsNonExpired = true;
    private boolean accountNonLocked = true;


    @Autowired
    private UserService us;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {

        final Either<User, Validation> either = us.findByMail(username); // en nuestro caso username es el mail

        if (!either.isValuePresent()) {
            throw new UsernameNotFoundException("No user by the name " + username);
        }

        final User user = either.getValue();

        final Collection<? extends GrantedAuthority> authorities = Arrays.asList(
                new SimpleGrantedAuthority("ROLE_USER")
                //new SimpleGrantedAuthority("ROLE_ADMIN") // que onda los admin??
        );
        return new org.springframework.security.core.userdetails.User(username, user.getPasswd(),
                user.isEnabled(),
                accountNonExpired,
                credentialsNonExpired,
                accountNonLocked,
                authorities);
    }

}

package ar.edu.itba.paw.webapp.forms;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserLoginForm {

    @NotEmpty
    @Email
    private String username;

    @NotEmpty
    @Size(min = 8, max = 100)
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserLoginForm{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

package ar.edu.itba.paw.webapp.forms;

import ar.edu.itba.paw.webapp.constraints.EmailInUse;
import ar.edu.itba.paw.webapp.constraints.EqualFields;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@EqualFields(field = "password", fieldMatch = "repeatPassword")
public class UserRegisterForm {

    @NotEmpty
    @Email
    @EmailInUse
    private String email;

    @NotEmpty
    @Size(min = 8, max = 100)
    private String password;

    //TODO: solucionar problema que no muestra mensaje de error
    @NotEmpty
    @Size(min = 8, max = 100)
    private String repeatPassword;

    @NotEmpty
    @Pattern(regexp = "[a-zA-Z ]+")
    private String name;

    @NotEmpty
    @Pattern(regexp = "[a-zA-Z ]+")
    private String surname;

    @NotEmpty
    @Size(min = 6, max = 25)
    @Pattern(regexp = "[0-9]+")
    private String telephone;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserRegisterForm{" +
                /*"username='" + username + '\'' + */
                "password='" + password + '\'' +
                ", repeatPassword='" + repeatPassword + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", telephone='" + telephone + '\'' +
                '}';
    }
}

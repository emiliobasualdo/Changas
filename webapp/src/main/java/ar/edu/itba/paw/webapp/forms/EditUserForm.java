package ar.edu.itba.paw.webapp.forms;

import ar.edu.itba.paw.webapp.constraints.EmailInUse;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class EditUserForm {

    @NotEmpty
    @Email
    @EmailInUse
    private String email;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    @Override
    public String toString() {
        return "EditUserForm{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", telephone='" + telephone + '\'' +
                '}';
    }
}

package ar.edu.itba.paw.webapp.forms;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserRegisterForm {
    //TODO MAITE DESCOMENTAR PATTERNS Y TAMAÑOS (los comento asi pueden probar rapido)
    @NotNull
    //@Size(min = 4, max = 100)
    @Pattern(regexp = "[a-zA-Z0-9]+")
    private String username;

    @NotNull
    @Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
    private String email;

    @NotNull
    //@Size(min = 6, max = 100)
    private String password;

    @NotNull
    //@Size(min = 6, max = 100)
    private String repeatPassword;

    @NotNull
    @Pattern(regexp = "[a-zA-Z]+[ ]*[a-zA-z]*")
    private String name;

    @NotNull
    @Pattern(regexp = "[a-zA-Z]+[ ]*[a-zA-z]*")
    private String surname;

    @NotNull
    //@Size(min = 6, max = 25)
    @Pattern(regexp = "[0-9]+")
    private String telephone;

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
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", repeatPassword='" + repeatPassword + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", telephone='" + telephone + '\'' +
                '}';
    }
}

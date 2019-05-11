package ar.edu.itba.paw.webapp.forms;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;

public class ForgotPasswordForm {
    //    @Size(min = 6, max = 100)
//    @Pattern(regexp = "[a-zA-Z0-9]+")
    @NotNull
    @Email
    private String mail;

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Override
    public String toString() {
        return "ForgotPasswordForm{" +
                "mail='" + mail + '\'' +
                '}';
    }
}

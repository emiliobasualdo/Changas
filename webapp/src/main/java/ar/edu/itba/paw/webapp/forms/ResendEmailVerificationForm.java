package ar.edu.itba.paw.webapp.forms;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class ResendEmailVerificationForm {
    @NotEmpty
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
        return "ResendEmailVerificationForm{" +
                "mail='" + mail + '\'' +
                '}';
    }
}

package ar.edu.itba.paw.webapp.forms;

import ar.edu.itba.paw.webapp.constraints.EqualFields;
import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.Size;

@EqualFields(field = "newPassword1", fieldMatch = "newPassword2")
public class ResetPasswordForm {

    @NotEmpty
    @Size(min = 8, max = 100)
    private String newPassword1;

    @NotEmpty
    @Size(min = 8, max = 100)
    private String newPassword2;

    public String getNewPassword() {
        return newPassword1;
    }

    public String getNewPassword1() {
        return newPassword1;
    }

    public void setNewPassword1(String newPassword1) {
        this.newPassword1 = newPassword1;
    }

    public String getNewPassword2() {
        return newPassword2;
    }

    public void setNewPassword2(String newPassword2) {
        this.newPassword2 = newPassword2;
    }

    @Override
    public String toString() {
        return "ResetPasswordForm{" +
                ", newPassword1='" + newPassword1 + '\'' +
                ", newPassword2='" + newPassword2 + '\'' +
                '}';
    }
}

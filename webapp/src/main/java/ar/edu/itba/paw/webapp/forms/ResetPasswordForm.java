package ar.edu.itba.paw.webapp.forms;

public class ResetPasswordForm {
    //    @Size(min = 6, max = 100)
//    @Pattern(regexp = "[a-zA-Z0-9]+")
    private String mail;

    //    @Size(min = 6, max = 100)
    private String newPassword1;

    private String newPassword2;

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
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
                "mail='" + mail + '\'' +
                ", newPassword1='" + newPassword1 + '\'' +
                ", newPassword2='" + newPassword2 + '\'' +
                '}';
    }
}

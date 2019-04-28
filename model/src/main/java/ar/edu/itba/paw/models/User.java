package ar.edu.itba.paw.models;

public class User {

    private long user_id;
    private  String name;
    private  String surname;
    private  String tel;
    private  String email;
    private  String passwd;
    private boolean enabled;

    private User(){
    }

    public long getUser_id() {
        return user_id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getTel() {
        return tel;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswd() {
        return passwd;
    }

    public boolean getEnabled() {return enabled;}

    public User (long id, User.Builder ub) {
        user_id = id;
        name = ub.getName();
        surname = ub.getSurname();
        tel = ub.getTel();
        email = ub.getEmail();
        passwd = ub.getPasswd();
        enabled = ub.getEnabled();
    }

    public static class Builder {
        private String name;
        private String surname;
        private String tel;
        private String email;
        private String passwd;
        private boolean enabled;

        public Builder() {
            this.enabled = false;
        }

        public User.Builder withName(String name){
            this.name = name;
            return this;
        }

        public User.Builder withSurname(String surname){
            this.surname = surname;
            return this;
        }

        public User.Builder withTel(String tel){
            this.tel = tel;
            return this;
        }

        public User.Builder withEmail(String email){
            this.email = email;
            return this;
        }

        public User.Builder withPasswd(String passwd){
            this.passwd = passwd;
            return this;
        }

        public User.Builder enabled (boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public String getEmail() {
            return email;
        }

        public String getPasswd() {
            return passwd;
        }

        public String getName() {
            return name;
        }

        public String getSurname() {
            return surname;
        }

        public String getTel() {
            return tel;
        }

        public boolean getEnabled() {return enabled;}


    }

    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", tel='" + tel + '\'' +
                ", email='" + email + '\'' +
                ", passwd='" + passwd + '\'' +
                '}';
    }


}

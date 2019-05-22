package ar.edu.itba.paw.models;

public class User {

    private long user_id;
    private String name;
    private String surname;
    private String tel;
    private String email;
    private String passwd;
    private double rating;
    private boolean enabled;

    private User(){
    }

    public double getRating() {
        return rating;
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

    public boolean isEnabled() {return enabled;}

    public User (long id, User.Builder ub) {
        user_id = id;
        name = ub.getName();
        surname = ub.getSurname();
        tel = ub.getTel();
        email = ub.getEmail();
        passwd = ub.getPasswd();
        enabled = ub.isEnabled();
        rating = ub.getRating();
    }

    public static class Builder {
        private String name;
        private String surname;
        private String tel;
        private String email;
        private String passwd;
        private double rating;
        private boolean enabled;

        public Builder() {
            this.enabled = false;
        }

        public Builder(Builder userBuilder) {
            this.name = userBuilder.name;
            this.surname = userBuilder.surname;
            this.tel = userBuilder.tel;
            this.email = userBuilder.email.toLowerCase();
            this.passwd = userBuilder.passwd;
            this.enabled = userBuilder.enabled;
            this.rating = userBuilder.rating;

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
            this.email = email.toLowerCase();
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

        public User.Builder withRating(double rating) {
            this.rating = rating;
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

        public boolean isEnabled() {return enabled;}

        public double getRating() {
            return this.rating;
        }
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

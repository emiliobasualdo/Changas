package ar.edu.itba.paw.models;

public class User {

    private long user_id;
    private  String name;
    private  String surname;
    private  String tel;
    private  String email;
    private  String passwd;

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

    public static class Builder {

        private static final int NO_ID = -1;
        private long user_id;
        private String name;
        private String surname;
        private String tel;
        private  String email;
        private  String passwd;

        public Builder() {
            this.user_id = NO_ID;
        }
        public Builder(long user_id) {
            this.user_id = user_id;
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

        public User build(){
            //Here we create the actual bank account object, which is always in a fully initialised state when it's returned.
            User user = new User();  //Since the builder is in the BankAccount class, we can invoke its private constructor.
            user.user_id = this.user_id;
            user.name = this.name;
            user.surname = this.surname;
            user.tel = this.tel;
            user.email = this.email;
            user.passwd = this.passwd;
            return user;
        }
    }
}

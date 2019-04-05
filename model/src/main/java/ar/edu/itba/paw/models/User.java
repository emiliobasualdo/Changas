package ar.edu.itba.paw.models;


public class User {
   private String id;
   private String username;
   private String password;
   private String name;
   private String surname;
   private String phone;
   private String email;


    private static class Builder {
        /*Required parameters*/
        private String username;
        private String password;
        private String name;
        private String surname;
        private String phone;
        /*Optional parameters initialized to default values*/
        private String id = null;
        private String email = null;

        public Builder(String username, String password, String name, String surname, String phone) {
            this.username = username;
            this.password = password;
            this.name = name;
            this.surname = surname;
            this.phone = phone;
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

    private User (Builder builder) {
        this.username = builder.username;
        this.password = builder.password;
        this.name = builder.name;
        this.surname = builder.surname;
        this.phone = builder.phone;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}

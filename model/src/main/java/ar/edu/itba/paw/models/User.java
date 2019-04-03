package ar.edu.itba.paw.models;

public class User {
    private static final int NO_ID = -1;

    private final long user_id;
    private final String name;
    private final String surname;
    private final String tel;

    public User(long user_id, String name, String surname, String tel) {
        this.user_id = user_id;
        this.name = name;
        this.surname = surname;
        this.tel = tel;
    }

    public User(String name, String surname, String tel) {
        this(NO_ID ,name, surname, tel);
    }

    public User(User oldUser, long id) {
        this(id, oldUser.name, oldUser.surname, oldUser.tel);
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
}

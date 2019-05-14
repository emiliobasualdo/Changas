package ar.edu.itba.paw.interfaces.util;

public enum Validation {

    // General
    OK("OK"),
    DATABASE_ERROR("Database error"),
    ILLEGAL_VALUE("This value is not allowed"),

    // Changas,
    NO_SUCH_CHANGA("Invalid changa id"),
    ILLEGAL_ACTION("This action is not alloed:"),

    // Users
    INVALID_PASSWORD("Invalid password"),
    INVALID_USERNAME("Invalid username"),
    NO_SUCH_USER("No such user"),
    INVALID_MAIL("Invalid mail"),
    INVALID_COMBINATION("The email and password combination is invalid"),
    USER_ALREADY_EXISTS("The email provided is already in use"),

    // Inscription
    USER_ALREADY_INSCRIBED("User already inscribed in changa"),
    USER_NOT_INSCRIBED("The user is not inscribed in the changa"),
    USERS_INSCRIBED("There are users inscribed in the changa"), // we cant edit a changa if users are inscribed
    NO_USERS_INSCRIBED("There are no users inscribed in the changa"),
    CHANGE_NOT_POSSIBLE("Such change is not allowed to be done"),
    USER_OWNS_THE_CHANGA("An owner can not inscribe himself in his changa"),

    //Verification Token
    INEXISTENT_TOKEN("Inexistent token"),
    EXPIRED_TOKEN("Expired token"),
    USER_NOT_LOGGED_IN("User must be logged in to user such functionality");

    private String message; // NOT final on purpose


    Validation(String message) {
        this.message = message;
    }


    public String getMessage() {
        return message;
    }

    public Validation withMessage(String msg) {
        this.message = this.message + ": " + msg;
        return this;
    }

    public boolean isOk() {
        return this == Validation.OK;
    }

    public boolean isError() {
        return !this.isOk();
    }
}


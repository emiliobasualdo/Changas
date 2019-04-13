package ar.edu.itba.paw.interfaces.util;

// todo interface Errors
// todo, donde iría está implementación? porque no es una interfaz
public class Validation {
    private final ErrorCodes ec;

    //preguntar lo de final
    public Validation(final ErrorCodes ec) {
        this.ec = ec;
    }

    public ErrorCodes getEc() {
        return ec;
    }

    public String getMessage() {
        return ec.getMessage();
    }

    public boolean isOk() {
        return ec == ErrorCodes.OK;
    }

    public boolean isError() {
        return ! this.isOk();
    }

    public enum ErrorCodes {

        // General
        OK                      ("OK"),
        DATABASE_ERROR          ("Database error"),

        // Changas

        // Users
        INVALID_PASSWORD        ("Invalid password"),
        INVALID_USERNAME        ("Invalid username"),
        NO_SUCH_USER            ("No such user"),
        INVALID_MAIL            ("Invalid mail"),
        INVALID_COMBINATION     ("The email and password combination is invalid"),
        USER_ALREADY_EXISTS     ("The email provided is already in use"),

        // Inscription
        USER_ALREADY_INSCRIBED  ("User already inscribed in changa"),
        USER_NOT_INSCRIBED      ("The user is not inscribed in the changa"),
        USERS_INSCRIBED         ("There are users inscribed in the changa"), // we cant edit a changa if users are inscribed
        CHANGE_NOT_POSSIBLE     ("Such change is not allowed to be done"),
        ;

        private final String message;

        ErrorCodes(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}

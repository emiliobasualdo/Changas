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
        UNKNOWN_ERROR          ("UNKNOWN_ERROR"),

        // Changas

        // Users
        INVALID_PASSWORD        ("Invalid password"),
        INVALID_USERNAME        ("Invalid username"),
        NO_SUCH_USER            ("No such user"),
        INVALID_MAIL            ("Invalid mail"),
        INVALID_COMBINATION     ("The email and password combination is invalid"),
        USER_ALREADY_EXISTS     ("The email provided is already in use"),

        // Inscription
        ALREADY_INSCRIBED       ("User already inscribed in changa"),
        USERS_INSCRIBED         ("There are users inscribed in the changa"),
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

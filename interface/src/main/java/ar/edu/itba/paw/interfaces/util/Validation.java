package ar.edu.itba.paw.interfaces.util;

import org.springframework.http.HttpStatus;

public enum Validation {

    // General
    OK("OK", HttpStatus.OK),
    DATABASE_ERROR("Database error", HttpStatus.SERVICE_UNAVAILABLE),
    ILLEGAL_VALUE("This value is not allowed", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED("Unauthorized action", HttpStatus.BAD_REQUEST),

    // Changas,
    NO_SUCH_CHANGA("Invalid changa id", HttpStatus.BAD_REQUEST),
    ILLEGAL_ACTION("This action is not alloed:", HttpStatus.UNAUTHORIZED),

    // Users
    INVALID_PASSWORD("Invalid password", HttpStatus.BAD_REQUEST),
    INVALID_USERNAME("Invalid username", HttpStatus.BAD_REQUEST),
    NO_SUCH_USER("No such user", HttpStatus.BAD_REQUEST),
    INVALID_MAIL("Invalid mail", HttpStatus.BAD_REQUEST),
    INVALID_COMBINATION("The email and password combination is invalid", HttpStatus.BAD_REQUEST),
    USER_ALREADY_EXISTS("The email provided is already in use", HttpStatus.BAD_REQUEST),
    DISABLED_USER ("The user isn't enabled", HttpStatus.BAD_REQUEST),

    // Inscription
    USER_ALREADY_INSCRIBED("User already inscribed in changa", HttpStatus.BAD_REQUEST),
    USER_NOT_INSCRIBED("The user is not inscribed in the changa", HttpStatus.BAD_REQUEST),
    USERS_INSCRIBED("There are users inscribed in the changa", HttpStatus.BAD_REQUEST), // we cant edit a changa if users are inscribed
    NO_USERS_INSCRIBED("There are no users inscribed in the changa", HttpStatus.BAD_REQUEST),
    CHANGE_NOT_POSSIBLE("Such change is not allowed to be done", HttpStatus.BAD_REQUEST),
    SETTLE_WHEN_EMPTY("Changa is not allowed to pass from emitted to settled when not chagueros are inscribed", HttpStatus.BAD_REQUEST),
    USER_OWNS_THE_CHANGA("An owner can not inscribe himself in his changa", HttpStatus.BAD_REQUEST),

    //Verification Token
    INEXISTENT_TOKEN("Inexistent token", HttpStatus.BAD_REQUEST),
    EXPIRED_TOKEN("Expired token", HttpStatus.BAD_REQUEST),
    USER_NOT_LOGGED_IN("User must be logged in to user such functionality", HttpStatus.BAD_REQUEST),

    // Categories
    NO_SUCH_LOCALE("This locale is not defined in the db", HttpStatus.BAD_REQUEST),

    //Email
    EMAIL_ERROR("Email couldn't be sent", HttpStatus.SERVICE_UNAVAILABLE)
    ;


    private String message; // NOT final on purpose
    private HttpStatus httpStatus;


    Validation(String message, HttpStatus status) {
        this.message = message;
    }


    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
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


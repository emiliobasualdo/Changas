package ar.edu.itba.paw.interfaces.util;

public enum ErrorCodes {
    INVALID_PASSWORD(0, "Invalid password"),
    INVALID_USERNAME(1, "Invalid username"),
    DATABASE_ERROR (2, "Database error"),
    INVALID_ID (3, "Invalid id"),
    INVALID_USER(4, "Invalid user"),
    INVALID_MAIL(5, "Invalid mail"),
    ;

    private final int id;
    private final String message;

    ErrorCodes(int id, String message) {
        this.id = id;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }
}

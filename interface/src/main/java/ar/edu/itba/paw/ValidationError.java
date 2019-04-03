package ar.edu.itba.paw;

public class ValidationError {
    private final String message;
    private final int code;

    //preguntar lo de final
    public ValidationError (final String message, final int code) {
        this.message = message;
        this.code = code;
    }
}

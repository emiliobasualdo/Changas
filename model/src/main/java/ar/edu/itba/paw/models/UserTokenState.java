package ar.edu.itba.paw.models;

public enum UserTokenState {
    USER_ENABLED_EXPIRED_TOKEN("User enabled, expired token"),
    USER_ENABLED_VALID_TOKEN("User enabled, valid token"),
    USER_DISABLED_EXPIRED_TOKEN("User disabled, expired token"),
    USER_DISABLED_VALID_TOKEN("User disabled, valid token"),
    ;

    final String description;

    UserTokenState(String descr) {
        this.description = descr;
    }
}

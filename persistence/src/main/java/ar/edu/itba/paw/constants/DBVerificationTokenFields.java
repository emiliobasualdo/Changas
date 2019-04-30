package ar.edu.itba.paw.constants;

public enum DBVerificationTokenFields {

    token_id        ("token_id", "SERIAL", -1),
    user_id         ("user_id", "SERIAL", -1),
    token           ("token", "VARCHAR", 255),
    expiry_date     ("expiry_date", "DATE", -1);

    final String colName;
    final String type;
    final int length;

    DBVerificationTokenFields(String colName, String type, int length) {
        this.colName = colName;
        this.type = type;
        this.length = length;
    }
}

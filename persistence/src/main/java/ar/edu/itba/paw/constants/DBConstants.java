package ar.edu.itba.paw.constants;

public enum DBConstants {

    CHANGAS ("changas"),
    USERS   ("users"),
    ;

    final String tableName;   // in kilograms
    DBConstants(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }
}

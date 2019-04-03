package ar.edu.itba.paw.constants;

public enum DBTableName {

    CHANGAS ("changas"),
    USERS   ("users"),
    ;

    final String tableName;
    DBTableName(String tableName) {
        this.tableName = tableName;
    }

    public String TN() {
        return tableName;
    }
}

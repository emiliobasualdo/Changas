package ar.edu.itba.paw.constants;

public enum DBTableName {

    changas("changas"),
    users("users"),
    ;

    final String tableName;
    DBTableName(String tableName) {
        this.tableName = tableName;
    }

    public String TN() {
        return tableName;
    }
}

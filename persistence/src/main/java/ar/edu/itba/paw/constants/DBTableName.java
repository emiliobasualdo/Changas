package ar.edu.itba.paw.constants;

public enum DBTableName {

    changas("changas"),
    users("users"),
    user_inscribed("user_inscribed"),
    user_owns("user_owns")
    ;

    final String tableName;
    DBTableName(String tableName) {
        this.tableName = tableName;
    }

    public String TN() {
        return tableName;
    }
}

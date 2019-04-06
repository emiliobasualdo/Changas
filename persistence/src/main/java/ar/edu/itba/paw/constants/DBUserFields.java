package ar.edu.itba.paw.constants;

public enum DBUserFields {


    user_id     ("user_id", "SERIAL",-1),
    name        ("name", "VARCHAR",400),
    surname     ("surname", "VARCHAR",100),
    tel         ("tel", "VARCHAR",100),
    email        ("email", "VARCHAR",100),
    passwd      ("passwd", "VARCHAR",100);

    final String colName;
    final String type;
    final int length;

    DBUserFields(String colName, String type, int length) {
        this.colName = colName;
        this.type = type;
        this.length = length;
    }

}

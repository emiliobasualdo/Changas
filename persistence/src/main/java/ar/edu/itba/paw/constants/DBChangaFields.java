package ar.edu.itba.paw.constants;

public enum DBChangaFields {

    changa_id       ("changa_id","SERIAL",-1),
    user_id         ("user_id", "SERIAL",-1),
    creation_date   ("creation_date", "TIMESTAMP", -1),
    title           ("title", "VARCHAR",100),
    description     ("description", "VARCHAR",400),
    state           ("state", "VARCHAR",100),
    price           ("price", "DOUBLE PRECISION",-1),
    street          ("street", "VARCHAR",100),
    neighborhood    ("neighborhood", "VARCHAR",100),
    number          ("number", "INTEGER",-1);

    final String name;
    final String type;
    final int length;

    DBChangaFields(String name,String type, int length) {
        this.name = name;
        this.type = type;
        this.length = length;
    }

}

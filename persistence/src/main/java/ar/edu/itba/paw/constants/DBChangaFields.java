package ar.edu.itba.paw.constants;

public enum DBChangaFields {

    id    ("id", "SERIAL PRIMARY KEY",-1),
    owner ("ownerId", "SERIAL", -1),
    title ("title", "varchar", 100),
    descr ("description", "varchar", 400),
    price ("price", "double", -1),
    neigh ("neighborhood", "varchar", 100),
    ;

    final String name;
    final String type;
    final int length;

    DBChangaFields(String name, String type, int length) {
        this.name = name;
        this.type = type;
        this.length = length;
    }

    public String getFQN() {
        return name +" "+ type + (length != -1 ? "("+length+")":"") + " ";
    }
}

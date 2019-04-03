package ar.edu.itba.paw.constants;

public enum DBChangaFields {

    changa_id ("id",-1),
    owner ("ownerId", -1),
    title ("title", 100),
    descr ("description", 400),
    price ("price", -1),
    neigh ("neighborhood", 100);

    final String name;
    final int length;

    DBChangaFields(String name, int length) {
        this.name = name;
        this.length = length;
    }

}

package ar.edu.itba.paw.constants;

public enum DBFilterFields {

    key          ("key", "VARCHAR",30);

    final String name;
    final String type;
    final int length;

    DBFilterFields(String name, String type, int length) {
        this.name = name;
        this.type = type;
        this.length = length;
    }

}

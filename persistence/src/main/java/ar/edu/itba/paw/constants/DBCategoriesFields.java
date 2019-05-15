package ar.edu.itba.paw.constants;

public enum DBCategoriesFields {

    locale       ("locale","VARCHAR",5),
    key          ("key", "VARCHAR",30),
    message      ("message", "VARCHAR",30);

    final String name;
    final String type;
    final int length;

    DBCategoriesFields(String name, String type, int length) {
        this.name = name;
        this.type = type;
        this.length = length;
    }

}

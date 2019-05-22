package ar.edu.itba.paw.constants;

public enum DBChangaPictureFields {
    changa_id       ("changa_id","SERIAL",-1),
    img_blobl   ("img byte stream", "bytea", -1);

    final String name;
    final String type;
    final int length;

    DBChangaPictureFields(String name, String type, int length) {
        this.name = name;
        this.type = type;
        this.length = length;
    }
}

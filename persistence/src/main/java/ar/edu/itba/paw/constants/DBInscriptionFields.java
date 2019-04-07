package ar.edu.itba.paw.constants;

public enum DBInscriptionFields {

    user_id("user_id","SERIAL" , -1),
    changa_id("changa_id","SERIAL", -1),
    state("state","VARCHAR", 100);

    String name, type;
    int length;
    DBInscriptionFields(String name, String type, int length) {
        this.name = name;
        this.type = type;
        this.length = length;
    }
}

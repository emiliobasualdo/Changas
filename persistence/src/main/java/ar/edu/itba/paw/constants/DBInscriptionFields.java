package ar.edu.itba.paw.constants;

public enum DBInscriptionFields {

    user_id("SERIAL" , -1),
    changa_id("SERIAL", -1),
    state("VARCHAR", 100);

    String type;
    int length;
    DBInscriptionFields( String type, int length) {
        this.type = type;
        this.length = length;
    }
}

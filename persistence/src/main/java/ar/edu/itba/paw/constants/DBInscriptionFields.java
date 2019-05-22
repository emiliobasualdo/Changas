package ar.edu.itba.paw.constants;

public enum DBInscriptionFields {

    user_id     ("SERIAL" , -1, -1),
    changa_id   ("SERIAL", -1, -1),
    rating      ("DOUBLE PRECISION",-1, -1),
    state       ("VARCHAR", 100, -1);

    String type;
    int length;
    public double def; // default
    DBInscriptionFields( String type, int length, double def) {
        this.type = type;
        this.length = length;
        this.def = def;
    }
}

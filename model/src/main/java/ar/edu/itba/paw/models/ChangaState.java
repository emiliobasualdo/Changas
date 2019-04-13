package ar.edu.itba.paw.models;

public enum ChangaState {

    emitted ("emitted", "The changa has been created by a user and posted"),
    settled ("settled", "The owner of the changa has choosen one or more changueros and has closed the inscriptions"),
    done ("done", "The changa has been taken care of"),
    closed ("closed", "The changa has been closed by user after inactivity"),
    ;

    final String name;
    final String description;

    ChangaState(String name, String descr) {
        this.name = name;
        this.description = descr;
    }

}

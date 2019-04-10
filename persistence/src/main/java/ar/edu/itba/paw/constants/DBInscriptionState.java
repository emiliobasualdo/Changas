package ar.edu.itba.paw.constants;

import ar.edu.itba.paw.interfaces.util.State;

public enum DBInscriptionState implements State {

    requested ("requested", "The changuero has requested to participate in the changa"),
    accepted ("accepted", "The owner of the changa has accepted the changuero to be part of the task"),
    declined ("declined", "The owner of the changa has declined the changuero to be part of the task"),
    ;

    final String name;
    final String description;

    DBInscriptionState(String name, String descr) {
        this.name = name;
        this.description = descr;
    }

    public String value() {
        return String.format("'%s'", this.name);
    }

    @Override
    public String getState() {
        return name;
    }
}

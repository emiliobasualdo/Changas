package ar.edu.itba.paw.models;

public enum InscriptionState {

    requested ( "The changuero has requested to participate in the changa"),
    accepted ( "The owner of the changa has accepted the changuero to be part of the task"),
    declined ("The owner of the changa has declined the changuero to be part of the task"),
    optout   ("The changuero has decided to leave the changa"),
    ;

    final String description;

    InscriptionState(String descr) {
        this.description = descr;
    }

    public static boolean changeIsPossible(InscriptionState oldState, InscriptionState newState) {
        // can I go from i to j?
        int[][] posibleChanges = {
                {0, 1, 1, 1}, // from requested
                {0, 0, 1, 1}, // from accepted
                {0, 1, 0, 0}, // from declined
                {1, 0, 0, 0}, // from optout
        };
        return posibleChanges[oldState.ordinal()][newState.ordinal()] == 1;
    }

}

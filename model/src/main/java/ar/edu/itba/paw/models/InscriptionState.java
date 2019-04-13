package ar.edu.itba.paw.models;

public enum InscriptionState {

    requested (0, "The changuero has requested to participate in the changa"),
    accepted (1, "The owner of the changa has accepted the changuero to be part of the task"),
    declined (2,"The owner of the changa has declined the changuero to be part of the task"),
    ;

    final int num;
    final String description;

    InscriptionState(int num, String descr) {
        this.description = descr;
        this.num = num;
    }

    public static boolean changeIsPossible(InscriptionState oldState, InscriptionState newState) {
        // can I go from i to j?
        int[][] posibleChanges = {
                {0, 1, 1}, // from requested
                {0, 0, 1}, // from accepted
                {0, 1, 0}, // from declined
        };
        return posibleChanges[oldState.num][newState.num] == 1;
    }

}

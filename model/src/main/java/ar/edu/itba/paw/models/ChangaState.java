package ar.edu.itba.paw.models;

public enum ChangaState {

    emitted (0,"emitted",   "The changa has been created by a user and posted"),
    settled (1,"settled",   "The owner of the changa has choosen one or more changueros and has closed the inscriptions"),
    done    (2,"done",      "The changa has been taken care of"),
    closed  (3,"closed",    "The changa has been closed by user for any reason or after inactivity or by admin"),
    ;

    final int num;
    final String name;
    final String description;

    ChangaState(int num, String name, String descr) {
        this.num = num;
        this.name = name;
        this.description = descr;
    }

    public static boolean changeIsPossible(ChangaState oldState, ChangaState newState) {
        // can I go from i to j?
        int[][] posibleChanges = {
                {0, 1, 0, 1}, // from emitted
                {0, 0, 1, 1}, // from settled
                {0, 0, 0, 0}, // from done
                {0, 0, 0, 0}, // from closed
        };
        return posibleChanges[oldState.num][newState.num] == 1;
    }
}

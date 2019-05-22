package ar.edu.itba.paw.models;

public class Inscription {
    private long user_id;
    private long changa_id;
    private InscriptionState state;
    private double rating;

    private Inscription(){
    }

    public long getUser_id() {
        return user_id;
    }

    public long getChanga_id() {
        return changa_id;
    }

    public InscriptionState getState() {
        return state;
    }

    public double getRating() {
        return rating;
    }

    public static class Builder {

        private static final long NO_ID = -1;
        private long user_id;
        private long changa_id;
        private InscriptionState state;
        private double rating;

        public Builder() {
            this.user_id = NO_ID;
        }

        public Inscription.Builder withUserId(long userId){
            this.user_id = userId;
            return this;
        }
        public Inscription.Builder withChangaId(long changaId){
            this.changa_id = changaId;
            return this;
        }
        public Inscription.Builder withState(InscriptionState state){
            this.state = state;
            return this;
        }
        public Inscription.Builder withRating(double rating) {
            this.rating = rating;
            return this;
        }

        public Inscription build(){
            //Here we create the actual bank account object, which is always in a fully initialised state when it's returned.
            Inscription insc = new Inscription();  //Since the builder is in the BankAccount class, we can invoke its private constructor.
            insc.changa_id = this.changa_id;
            insc.user_id = this.user_id;
            insc.state = this.state;
            insc.rating = this.rating;
            return insc;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof Inscription)) {
            return false;
        }

        Inscription i = (Inscription) o;

        return i.changa_id == changa_id && i.user_id == user_id;
    }
}
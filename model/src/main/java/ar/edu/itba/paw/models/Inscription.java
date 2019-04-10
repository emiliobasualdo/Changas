package ar.edu.itba.paw.models;

public class Inscription {
    private long user_id;
    private long changa_id;
    private String state;

    private Inscription(){
    }

    public long getUser_id() {
        return user_id;
    }

    public long getChanga_id() {
        return changa_id;
    }

    public String getState() {
        return state;
    }


    public static class Builder {

        private static final long NO_ID = -1;
        private long user_id;
        private long changa_id;
        private String state;

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
        public Inscription.Builder withState(String state){
            this.state = state;
            return this;
        }

        public Inscription build(){
            //Here we create the actual bank account object, which is always in a fully initialised state when it's returned.
            Inscription insc = new Inscription();  //Since the builder is in the BankAccount class, we can invoke its private constructor.
            insc.changa_id = this.changa_id;
            insc.user_id = this.user_id;
            insc.state = this.state;
            return insc;
        }
    }
}
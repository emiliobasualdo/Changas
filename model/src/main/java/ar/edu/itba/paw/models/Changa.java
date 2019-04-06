package ar.edu.itba.paw.models;

import java.time.LocalDateTime;

public class Changa {

    private static final long NO_ID = -1;

    private long changa_id;
    private long user_id;
    private String street;
    private String neighborhood;
    private int number;
    private LocalDateTime creation_date;
    private String title;
    private String description;
    private String state;
    private double price;

    // required to make constructor private
    private Changa() { }

    public long getChanga_id() {
        return changa_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public String getStreet() {
        return street;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public int getNumber() {
        return number;
    }

    public LocalDateTime getCreation_date() {
        return creation_date;
    }


    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public String getState() {
        return state;
    }

    public static class Builder {

        private long changa_id;
        private long user_id;
        private LocalDateTime creation_date;
        private String title;
        private String description;
        private String state;
        private double price;
        private String street;
        private String neighborhood;
        private int number;

        public Builder() {
            this.changa_id = NO_ID;
        }
        public Builder(long changa_id) {
            this.changa_id = changa_id;
        }
        public Builder withUserId(long user_id){
            this.user_id = user_id;
            return this;
        }
        public Builder createdAt(LocalDateTime creation_date){
            this.creation_date = creation_date;
            return this;
        }
        public Builder withTitle(String title){
            this.title = title;
            return this;
        }
        public Builder withDescription(String description){
            this.description = description;
            return this;
        }
        public Builder withState(String state){
            this.state = state;
            return this;
        }
        public Builder withPrice(double price){
            this.price = price;
            return this;
        }
        public Builder atAddress(String street,String neighborhood,int number) {
            this.street = street;
            this.neighborhood = neighborhood;
            this.number = number;
            return this;
        }

        public Changa build(){
            //Here we create the actual bank account object, which is always in a fully initialised state when it's returned.
            Changa changa = new Changa();  //Since the builder is in the BankAccount class, we can invoke its private constructor.
            changa.changa_id = this.changa_id;
            changa.user_id = this.user_id;
            changa.creation_date = this.creation_date;
            changa.title = this.title;
            changa.description = this.description;
            changa.state = this.state;
            changa.price = this.price;
            changa.street = this.street;
            changa.neighborhood = this.neighborhood;
            changa.number = this.number;
            return changa;
        }
    }
}

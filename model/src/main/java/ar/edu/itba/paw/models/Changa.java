package ar.edu.itba.paw.models;

import java.time.LocalDateTime;

public class Changa {

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

    public String getStreet() { return street; }

    public String getNeighborhood() { return neighborhood; }

    public int getNumber() { return number; }

    public LocalDateTime getCreation_date() { return creation_date; }

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

    public Changa (long id, Changa.Builder cb) {
        changa_id = id;
        user_id = cb.getUser_id();
        street = cb.getStreet();
        neighborhood = cb.getNeighborhood();
        number = cb.getNumber();
        creation_date = cb.getCreation_date();
        title = cb.getTitle();
        description = cb.getDescription();
        state = cb.getState();
        price = cb.getPrice();
    }

    public static class Builder {
        private long user_id;
        private LocalDateTime creation_date;
        private String title;
        private String description;
        private String state;
        private double price;
        private String street;
        private String neighborhood;
        private int number;


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

        public long getUser_id() {
            return user_id;
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

        public String getState() {
            return state;
        }

        public double getPrice() {
            return price;
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

//        public Changa build(){
//            Changa changa = new Changa();
//            changa.changa_id = this.changa_id;
//            changa.user_id = this.user_id;
//            changa.creation_date = this.creation_date;
//            changa.title = this.title;
//            changa.description = this.description;
//            changa.state = this.state;
//            changa.price = this.price;
//            changa.street = this.street;
//            changa.neighborhood = this.neighborhood;
//            changa.number = this.number;
//            return changa;
//        }
    }
}

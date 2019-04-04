package ar.edu.itba.paw.models;

public class Address {
    private String street;
    private String neighborhood;
    private int number;

    public Address(String street, String neighborhood, int number) {
        this.street = street;
        this.neighborhood = neighborhood;
        this.number = number;
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
}

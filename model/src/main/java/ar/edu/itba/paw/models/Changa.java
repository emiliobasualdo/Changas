package ar.edu.itba.paw.models;

public class Changa {

    private static int ID = 0;

    private final int id;

    private final String ownerName;
    private final String ownerPhone;
    private final String title;
    private final String description;
    private final double price;
    private final String neighborhood;
    public Changa(String ownerName, String ownerPhone, String title, String description, double price, String neighborhood) {

        this.id = Changa.ID ++;
        this.ownerName = ownerName;
        this.ownerPhone = ownerPhone;
        this.title = title;
        this.description = description;
        this.price = price;
        this.neighborhood = neighborhood;
    }

    public int getId() {
        return id;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getOwnerPhone() {
        return ownerPhone;
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

    public String getNeighborhood() {
        return neighborhood;
    }
}

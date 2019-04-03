package ar.edu.itba.paw.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Changa {

    private static final long NO_ID = -1;

    private final long id;
    private final long ownerId;
    private final String title;
    private final String description;
    private final double price;
    private final String neighborhood;

    // todo, hacer el builder
    public Changa(long id, long ownerId, String title, String description, double price, String neighborhood) {
        this.id = id;
        this.ownerId = ownerId;
        this.title = title;
        this.description = description;
        this.price = price;
        this.neighborhood = neighborhood;
    }

    public Changa(long ownerId, String title, String description, double price, String neighborhood) {
        this(NO_ID, ownerId ,title,description,price,neighborhood);
    }

    public Changa(Changa oldChanga, long id) {
        this(id,  oldChanga.ownerId, oldChanga.title, oldChanga.description, oldChanga.price, oldChanga.neighborhood);
    }

    public long getId() {
        return id;
    }

    public long getownerId() {
        return ownerId;
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

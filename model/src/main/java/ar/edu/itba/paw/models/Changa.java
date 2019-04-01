package ar.edu.itba.paw.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Changa {

    private long id;
    private final String ownerName;
    private final String ownerPhone;
    private final String title;
    private final String description;
    private final double price;
    private final String neighborhood;

    public Changa(String ownerName, String ownerPhone, String title, String description, double price, String neighborhood) {
        this.ownerName = ownerName;
        this.ownerPhone = ownerPhone;
        this.title = title;
        this.description = description;
        this.price = price;
        this.neighborhood = neighborhood;
    }

    public Changa(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.ownerName = rs.getString("ownerName");
        this.ownerPhone = rs.getString("ownerPhone");
        this.title = rs.getString("ownerName");
        this.description = rs.getString("description");
        this.price = rs.getDouble("price");
        this.neighborhood = rs.getString("neighborhood");
    }

    public Changa(Changa oldChanga, long id) {
        this.id = id;
        this.ownerName = oldChanga.ownerName;
        this.ownerPhone = oldChanga.ownerPhone;
        this.title = oldChanga.title;
        this.description = oldChanga.description;
        this.price = oldChanga.price;
        this.neighborhood = oldChanga.neighborhood;
    }

    public static String dbName() {
        return Changa.class.getName().toLowerCase();
    }

    public Map<String, Object> toTableRow() {
        Map<String, Object> resp = new HashMap<>();
        resp.put("ownerName", this.ownerName );
        resp.put("ownerPhone", this.ownerPhone );
        resp.put("title", this.title );
        resp.put("description", this.description );
        resp.put("price", this.price );
        resp.put("neighborhood", this.neighborhood );
        return resp;
    }

    public Changa clone() throws CloneNotSupportedException {
        return (Changa) super.clone();
    }

    public long getId() {
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

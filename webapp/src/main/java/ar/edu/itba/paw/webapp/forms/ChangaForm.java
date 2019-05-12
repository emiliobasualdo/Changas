package ar.edu.itba.paw.webapp.forms;

import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.*;

public class ChangaForm { //TODO: el NOTNULL es una caca

    @NotEmpty
    @Size(min = 6, max = 100) @Pattern(regexp = "[A-Za-z0-9 ]+")
    private String title;

    @NotEmpty
    @Size(min = 6, max = 100) @Pattern(regexp = "[A-Za-z0-9\\s_.,!\"'/$]+")
    private String description;

    //TODO; verificar que no este vacio
    @Min(1)
    private double price;

    @NotEmpty
    @Size(min = 6, max = 100) @Pattern(regexp = "[a-zA-Z 0-9]+")
    private String neighborhood;

    @NotEmpty
    @Size(min = 6, max = 100) @Pattern(regexp = "[a-zA-Z 0-9]+")
    private String street;

    //TODO; verificar que no este vacio
    @Min(0)
    private int number;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
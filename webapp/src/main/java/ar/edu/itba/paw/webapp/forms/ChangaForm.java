package ar.edu.itba.paw.webapp.forms;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;

public class ChangaForm {

    @NotEmpty
    @Size(min = 10, max = 50) @Pattern(regexp = "[A-Za-z0-9áéíóúñü ]+")
    private String title;

    @NotEmpty
    @Size(min = 25, max = 1000) @Pattern(regexp = "[A-Za-z0-9\\s_.,:;&()#$%@?|{}!\"'/áéíóúñü ]+") //TODO: agrandar limite de texto en la bd (muy chico 100)
    private String description;

    @NotNull
    @Min(value = 1)
    @Max(value = 20000)
    private double price;

    @NotEmpty
    @Size(max = 100) @Pattern(regexp = "[a-zA-Záéíóúñü 0-9]+")
    private String neighborhood;

    @NotEmpty
    @Size(max = 100) @Pattern(regexp = "[a-zA-Záéíóúñü 0-9]+")
    private String street;

    @NotNull
    @Min(value = 0)
    @Max(value = 20000)
    private int number;

    @NotEmpty
    @Size(min = 4, max = 100) @Pattern(regexp = "[a-zA-Záéíóúñü 0-9]+")
    private String category;

    private MultipartFile photo;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String cat) {
        this.category = cat;
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

    public MultipartFile getPhoto() {
        return photo;
    }

    public void setPhoto(MultipartFile photo) {
        this.photo = photo;
    }
}
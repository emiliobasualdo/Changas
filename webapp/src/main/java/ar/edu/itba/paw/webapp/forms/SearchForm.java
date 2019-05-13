package ar.edu.itba.paw.webapp.forms;

import ar.edu.itba.paw.webapp.constraints.EqualFields;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class SearchForm {

    @NotEmpty
    @Pattern(regexp = "[a-zA-Z ]+")
    private String search;

    private String category;

    public String getSearch(){
        return search;
    }

    public void setSearch(String search){
        this.search=search;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "UserRegisterForm{" + "search='" + search + '\'' + ", category='" + category + '\'' + "}";
    }
}

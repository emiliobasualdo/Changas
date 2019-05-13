package ar.edu.itba.paw.webapp.constraints;

import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.Either;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import ar.edu.itba.paw.interfaces.util.Validation;

public class EmailInUseValidation implements ConstraintValidator<EmailInUse, Object> {

    @Autowired
    private UserService us;

    private String email;

    @Override
    public void initialize(EmailInUse constraintAnnotation) {
        this.email = constraintAnnotation.email();

    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext context) {
        String fieldEmail = (String) new BeanWrapperImpl(o).getPropertyValue(email);
        if (!us.findByMail(fieldEmail).isValuePresent()) {
            return false;
        }
        return true;
    }
}

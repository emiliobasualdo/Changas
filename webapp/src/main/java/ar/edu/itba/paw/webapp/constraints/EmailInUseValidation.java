package ar.edu.itba.paw.webapp.constraints;

import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.Either;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import ar.edu.itba.paw.interfaces.util.Validation;

public class EmailInUseValidation implements ConstraintValidator<EmailInUse, String> {

    @Autowired
    private UserService us;

    @Override
    public void initialize(EmailInUse constraintAnnotation) {
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return !us.findByMail(email).isValuePresent();
    }
}

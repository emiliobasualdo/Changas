package ar.edu.itba.paw.webapp.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = EmailInUseValidation.class)
@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface EmailInUse {

    String message() default "Email already in use";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}

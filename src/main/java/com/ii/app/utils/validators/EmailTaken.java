package com.ii.app.utils.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EmailTakenValidatorImpl.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailTaken {
    String message() default "{EmailTaken}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

package com.nerdysoft.pdpproject.validation.annotations;

import com.nerdysoft.pdpproject.validation.CreateCStatusValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = CreateCStatusValidator.class)
public @interface CreateCStatus {

    String message() default "Status not allow";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

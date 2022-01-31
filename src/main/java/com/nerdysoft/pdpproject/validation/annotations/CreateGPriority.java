package com.nerdysoft.pdpproject.validation.annotations;

import com.nerdysoft.pdpproject.validation.CreateCStatusValidator;
import com.nerdysoft.pdpproject.validation.CreateGPriorityValidator;

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
@Constraint(validatedBy = CreateGPriorityValidator.class)
public @interface CreateGPriority {
    String message() default "Priority not allow";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

package com.nerdysoft.pdpproject.validation;

import com.nerdysoft.pdpproject.entity.enums.CriteriaStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintValidatorContext;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CreateCStatusValidatorTest {

    @InjectMocks
    private CreateCStatusValidator validator;

    @Mock
    private ConstraintValidatorContext context;

    @Test
    void isValid() {


        boolean valid = validator.isValid(CriteriaStatus.NEW, context);
        boolean unValid = validator.isValid(CriteriaStatus.DONE, context);

        assertTrue(valid);
        assertFalse(unValid);

    }
}
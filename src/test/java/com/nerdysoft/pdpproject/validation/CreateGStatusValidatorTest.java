package com.nerdysoft.pdpproject.validation;

import com.nerdysoft.pdpproject.entity.enums.CriteriaStatus;
import com.nerdysoft.pdpproject.entity.enums.GoalStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintValidatorContext;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CreateGStatusValidatorTest {

    @Mock
    private ConstraintValidatorContext context;

    @InjectMocks
    private CreateGStatusValidator validator;

    @Test
    void isValid() {

        boolean valid = validator.isValid(GoalStatus.NEW, context);
        boolean unValid = validator.isValid(GoalStatus.COMPLETED, context);

        assertTrue(valid);
        assertFalse(unValid);
    }
}
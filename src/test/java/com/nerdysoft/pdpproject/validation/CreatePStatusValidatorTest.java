package com.nerdysoft.pdpproject.validation;

import com.nerdysoft.pdpproject.entity.enums.GoalStatus;
import com.nerdysoft.pdpproject.entity.enums.PlanStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintValidatorContext;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CreatePStatusValidatorTest {

    @Mock
    private ConstraintValidatorContext context;

    @InjectMocks
    private CreatePStatusValidator validator;

    @Test
    void isValid() {
        boolean valid = validator.isValid(PlanStatus.NEW, context);
        boolean unValid = validator.isValid(PlanStatus.COMPLETED, context);

        assertTrue(valid);
        assertFalse(unValid);
    }
}
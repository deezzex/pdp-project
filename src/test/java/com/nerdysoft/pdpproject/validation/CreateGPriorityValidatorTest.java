package com.nerdysoft.pdpproject.validation;

import com.nerdysoft.pdpproject.entity.enums.CriteriaStatus;
import com.nerdysoft.pdpproject.entity.enums.GoalPriority;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintValidatorContext;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CreateGPriorityValidatorTest {

    @Mock
    private ConstraintValidatorContext context;

    @InjectMocks
    private CreateGPriorityValidator validator;

    @Test
    void isValid() {


        boolean valid = validator.isValid(GoalPriority.LOW, context);
        boolean unValid = validator.isValid(GoalPriority.CRITICAL, context);

        assertTrue(valid);
        assertFalse(unValid);

    }

}
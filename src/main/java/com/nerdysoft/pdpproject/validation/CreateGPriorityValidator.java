package com.nerdysoft.pdpproject.validation;

import com.nerdysoft.pdpproject.entity.enums.CriteriaStatus;
import com.nerdysoft.pdpproject.entity.enums.GoalPriority;
import com.nerdysoft.pdpproject.validation.annotations.CreateCStatus;
import com.nerdysoft.pdpproject.validation.annotations.CreateGPriority;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class CreateGPriorityValidator implements ConstraintValidator<CreateGPriority, GoalPriority> {

    private final List<GoalPriority> priorities = List.of(GoalPriority.LOW, GoalPriority.MEDIUM, GoalPriority.HIGH);

    @Override
    public boolean isValid(GoalPriority goalPriority, ConstraintValidatorContext constraintValidatorContext) {
        return priorities.contains(goalPriority);
    }
}

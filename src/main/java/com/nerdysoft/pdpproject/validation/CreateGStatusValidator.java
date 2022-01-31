package com.nerdysoft.pdpproject.validation;

import com.nerdysoft.pdpproject.entity.enums.GoalPriority;
import com.nerdysoft.pdpproject.entity.enums.GoalStatus;
import com.nerdysoft.pdpproject.validation.annotations.CreateGStatus;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class CreateGStatusValidator implements ConstraintValidator<CreateGStatus, GoalStatus> {

    private final List<GoalStatus> statuses = List.of(GoalStatus.NEW);

    @Override
    public boolean isValid(GoalStatus goalStatus, ConstraintValidatorContext constraintValidatorContext) {
        return statuses.contains(goalStatus);
    }
}

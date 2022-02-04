package com.nerdysoft.pdpproject.validation;

import com.nerdysoft.pdpproject.entity.enums.PlanStatus;
import com.nerdysoft.pdpproject.validation.annotations.CreatePStatus;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class CreatePStatusValidator implements ConstraintValidator<CreatePStatus, PlanStatus> {

    private final List<PlanStatus> statuses = List.of(PlanStatus.NEW);

    @Override
    public boolean isValid(PlanStatus planStatus, ConstraintValidatorContext constraintValidatorContext) {
        return statuses.contains(planStatus);
    }
}

package com.nerdysoft.pdpproject.validation;

import com.nerdysoft.pdpproject.entity.enums.CriteriaStatus;
import com.nerdysoft.pdpproject.validation.annotations.CreateCStatus;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class CreateCStatusValidator implements ConstraintValidator<CreateCStatus, CriteriaStatus> {

    private List<CriteriaStatus> status = List.of(CriteriaStatus.NEW);

    @Override
    public boolean isValid(CriteriaStatus criteriaStatus, ConstraintValidatorContext context) {
        return status.contains(criteriaStatus);
    }
}

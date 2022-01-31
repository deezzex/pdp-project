package com.nerdysoft.pdpproject.entity.dto;

import com.nerdysoft.pdpproject.entity.SuccessCriteria;
import com.nerdysoft.pdpproject.entity.enums.CriteriaStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SCDto {
    private Long id;

    private String title;

    private String description;

    private CriteriaStatus criteriaStatus;

    public static SCDto from(SuccessCriteria successCriteria){

        return SCDto.builder()
                .id(successCriteria.getId())
                .title(successCriteria.getTitle())
                .description(successCriteria.getDescription())
                .criteriaStatus(successCriteria.getCriteriaStatus())
                .build();
    }
}

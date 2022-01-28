package com.nerdysoft.pdpproject.entity.dto;

import com.nerdysoft.pdpproject.entity.Plan;
import com.nerdysoft.pdpproject.entity.enums.PlanStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class PlanDto {

    private Long id;

    private LocalDate startDate;

    private LocalDate endDate;

    private String description;

    private PlanStatus planStatus;

    private List<GoalDto> goalDtos;

    public static PlanDto from(Plan plan){

        return PlanDto.builder()
                .id(plan.getId())
                .startDate(plan.getStartDate())
                .endDate(plan.getEndDate())
                .description(plan.getDescription())
                .planStatus(plan.getPlanStatus())
                .goalDtos(plan.getGoals().stream().map(GoalDto::from).collect(Collectors.toList()))
                .build();
    }
}

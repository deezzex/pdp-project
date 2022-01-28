package com.nerdysoft.pdpproject.entity.dto;

import com.nerdysoft.pdpproject.entity.Goal;
import com.nerdysoft.pdpproject.entity.enums.GoalPriority;
import com.nerdysoft.pdpproject.entity.enums.GoalStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class GoalDto {
    private Long id;

    private String title;

    private String description;

    private LocalDate startDate;

    private LocalDate planedEndDate;

    private LocalDate endDate;

    private GoalPriority goalPriority;

    private GoalStatus goalStatus;

    private String comment;

    private Integer mark;

    private List<SuccessCriteriaDto> criteriaDtos;

    public static GoalDto from(Goal goal){

        return GoalDto.builder()
                .id(goal.getId())
                .title(goal.getTitle())
                .description(goal.getDescription())
                .startDate(goal.getStartDate())
                .planedEndDate(goal.getPlanedEndDate())
                .endDate(goal.getEndDate())
                .goalPriority(goal.getGoalPriority())
                .goalStatus(goal.getGoalStatus())
                .comment(goal.getComment())
                .mark(goal.getMark())
                .criteriaDtos(goal.getSuccessCriteria().stream().map(SuccessCriteriaDto::from).collect(Collectors.toList()))
                .build();
    }
}

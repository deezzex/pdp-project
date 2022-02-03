package com.nerdysoft.pdpproject.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nerdysoft.pdpproject.entity.dto.PlanDto;
import com.nerdysoft.pdpproject.entity.enums.PlanStatus;
import com.nerdysoft.pdpproject.validation.annotations.CreatePStatus;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @FutureOrPresent(message = "Start date must be now or in the future")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @Future(message = "End date must be in the future")
    private LocalDate endDate;

    @Size(min = 5, max = 80, message = "Size of description must be between 5 and 80 chars")
    @NotEmpty(message = "Description must be filled")
    private String description;

    @CreatePStatus
    @Enumerated(EnumType.STRING)
    private PlanStatus planStatus;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "plan_id")
    @ToString.Exclude
    private List<Goal> goals = new ArrayList<>();

    public void addGoal(Goal goal){
        goals.add(goal);
    }

    public void removeGoal(Goal goal){
        goals.remove(goal);
    }

    public static Plan from(PlanDto planDto){
        Plan plan = new Plan();

        plan.setStartDate(planDto.getStartDate());
        plan.setEndDate(planDto.getEndDate());
        plan.setDescription(planDto.getDescription());
        plan.setPlanStatus(planDto.getPlanStatus());

        return plan;
    }

    public Plan(Long id, LocalDate startDate, LocalDate endDate, String description, PlanStatus planStatus) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.planStatus = planStatus;
    }

    public Plan(Long id, LocalDate startDate, LocalDate endDate, String description, PlanStatus planStatus, List<Goal> goals) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.planStatus = planStatus;
        this.goals = goals;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Plan plan = (Plan) o;
        return id != null && Objects.equals(id, plan.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

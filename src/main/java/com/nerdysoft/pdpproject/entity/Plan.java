package com.nerdysoft.pdpproject.entity;

import com.nerdysoft.pdpproject.entity.dto.PlanDto;
import com.nerdysoft.pdpproject.entity.enums.PlanStatus;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
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

    private LocalDate startDate;

    private LocalDate endDate;

    private String description;

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

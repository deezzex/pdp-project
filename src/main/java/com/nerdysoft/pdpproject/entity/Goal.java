package com.nerdysoft.pdpproject.entity;

import com.nerdysoft.pdpproject.entity.dto.GoalDto;
import com.nerdysoft.pdpproject.entity.enums.GoalPriority;
import com.nerdysoft.pdpproject.entity.enums.GoalStatus;
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
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private String description;

    private LocalDate startDate;

    private LocalDate planedEndDate;

    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private GoalPriority goalPriority;

    @Enumerated(EnumType.STRING)
    private GoalStatus goalStatus;

    private String comment;

    private Integer mark;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "goal_id")
    @ToString.Exclude
    private List<SuccessCriteria> successCriteria = new ArrayList<>();

    @ManyToOne
    private Plan plan;

    public void addCriteria(SuccessCriteria criteria){
        successCriteria.add(criteria);
    }

    public void removeCriteria(SuccessCriteria criteria){
        successCriteria.remove(criteria);
    }

    public static Goal from(GoalDto goalDto){
        Goal goal = new Goal();

        goal.setTitle(goalDto.getTitle());
        goal.setDescription(goalDto.getDescription());
        goal.setStartDate(goalDto.getStartDate());
        goal.setPlanedEndDate(goalDto.getPlanedEndDate());
        goal.setEndDate(goalDto.getEndDate());
        goal.setGoalPriority(goalDto.getGoalPriority());
        goal.setGoalStatus(goalDto.getGoalStatus());
        goal.setComment(goalDto.getComment());
        goal.setMark(goalDto.getMark());

        return goal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Goal goal = (Goal) o;
        return id != null && Objects.equals(id, goal.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

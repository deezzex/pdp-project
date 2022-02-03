package com.nerdysoft.pdpproject.entity;

import com.nerdysoft.pdpproject.entity.dto.GoalDto;
import com.nerdysoft.pdpproject.entity.enums.GoalPriority;
import com.nerdysoft.pdpproject.entity.enums.GoalStatus;
import com.nerdysoft.pdpproject.validation.annotations.CreateGPriority;
import com.nerdysoft.pdpproject.validation.annotations.CreateGStatus;
import lombok.*;
import org.hibernate.Hibernate;

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
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(min = 3, max = 20, message = "Size of title must be between 3 and 20 chars")
    @NotEmpty(message = "Title must be filled")
    private String title;

    @Size(min = 5, max = 80, message = "Size of description must be between 5 and 80 chars")
    @NotEmpty(message = "Description must be filled")
    private String description;

    @FutureOrPresent(message = "Start date must be now or in the future")
    private LocalDate startDate;

    @Future(message = "Planed end date must be in the future")
    private LocalDate planedEndDate;

    @Future(message = "End date must be in the future")
    private LocalDate endDate;

    @CreateGPriority
    @Enumerated(EnumType.STRING)
    private GoalPriority goalPriority;

    @CreateGStatus
    @Enumerated(EnumType.STRING)
    private GoalStatus goalStatus;

    @Size(min = 5, max = 40, message = "Size of comment must be between 5 and 40 chars")
    private String comment;

    @Min(value = 1, message = "Mark must be bigger than 0")
    @Max(value = 10, message = "Mark must be less equal 10")
    private Integer mark;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "goal_id")
    @ToString.Exclude
    private List<SuccessCriteria> successCriteria = new ArrayList<>();

    @ManyToOne
    private Plan plan;

    public Goal(Long id, String title, String description, LocalDate startDate, LocalDate planedEndDate, LocalDate endDate, GoalPriority goalPriority, GoalStatus goalStatus, String comment, Integer mark) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.planedEndDate = planedEndDate;
        this.endDate = endDate;
        this.goalPriority = goalPriority;
        this.goalStatus = goalStatus;
        this.comment = comment;
        this.mark = mark;
    }

    public Goal(Long id, String title, String description, LocalDate startDate, LocalDate planedEndDate, LocalDate endDate, GoalPriority goalPriority, GoalStatus goalStatus, String comment, Integer mark, List<SuccessCriteria> successCriteria) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.planedEndDate = planedEndDate;
        this.endDate = endDate;
        this.goalPriority = goalPriority;
        this.goalStatus = goalStatus;
        this.comment = comment;
        this.mark = mark;
        this.successCriteria = successCriteria;
    }

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

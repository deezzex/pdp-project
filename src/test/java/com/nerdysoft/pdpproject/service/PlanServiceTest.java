package com.nerdysoft.pdpproject.service;

import com.nerdysoft.pdpproject.entity.Goal;
import com.nerdysoft.pdpproject.entity.Plan;
import com.nerdysoft.pdpproject.entity.SuccessCriteria;
import com.nerdysoft.pdpproject.entity.enums.CriteriaStatus;
import com.nerdysoft.pdpproject.entity.enums.GoalPriority;
import com.nerdysoft.pdpproject.entity.enums.GoalStatus;
import com.nerdysoft.pdpproject.entity.enums.PlanStatus;
import com.nerdysoft.pdpproject.repository.PlanRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlanServiceTest {

    @Mock
    private PlanRepository repository;

    @Mock
    private GoalService goalService;

    @InjectMocks
    private PlanService service;

    @Test
    void create() {
        Plan plan = new Plan(1L, LocalDate.of(2022,12,12),LocalDate.of(2023,12,12), "test", PlanStatus.NEW);

        service.create(plan);

        verify(repository, times(1)).save(plan);
    }

    @Test
    void findById() {
        when(repository.findById(1L)).thenReturn(Optional.of(new Plan(1L, LocalDate.of(2022,12,12),LocalDate.of(2023,12,12), "test", PlanStatus.NEW)));

        Optional<Plan> plan = service.findById(1L);

        plan.ifPresent(criteria -> assertEquals("test", criteria.getDescription()));
    }

    @Test
    void findAll() {
        ArrayList<Plan> list = new ArrayList<>();

        Plan plan1 = new Plan(1L, LocalDate.of(2022,12,12),LocalDate.of(2023,12,12), "test", PlanStatus.NEW);
        Plan plan2 = new Plan(2L, LocalDate.of(2022,12,12),LocalDate.of(2023,12,12), "test", PlanStatus.NEW);

        list.add(plan1);
        list.add(plan2);

        when(repository.findAll()).thenReturn(list);

        List<Plan> plansFromService = service.findAll();

        assertEquals(2, plansFromService.size());
        verify(repository, times(1)).findAll();

    }

    @Test
    void update() {
        Plan plan = new Plan(1L, LocalDate.of(2022,12,12),LocalDate.of(2023,12,12), "test", PlanStatus.NEW);
        when(repository.findById(1L)).thenReturn(Optional.of(plan));

        Plan updatedPlan = service.update(1L, new Plan(1L, LocalDate.of(2022,12,12),LocalDate.of(2023,12,12), "test2", PlanStatus.NEW));

        assertEquals("test2", updatedPlan.getDescription());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void delete() {
        boolean delete = service.delete(1L);
        repository.delete(new Plan());

        assertTrue(delete);
        verify(repository, times(2)).findById(1L);
    }

    @Test
    void addGoalToPlan() {
        Plan plan = new Plan(1L, LocalDate.of(2022,12,12),LocalDate.of(2023,12,12), "test", PlanStatus.NEW);
        Goal goal = new Goal(2L,"title1","description1", LocalDate.of(2022,12,12),LocalDate.of(2023,12,12),LocalDate.of(2024,12,12), GoalPriority.LOW, GoalStatus.NEW,"comment",4);

        when(repository.findById(1L)).thenReturn(Optional.of(plan));
        when(goalService.findById(2L)).thenReturn(Optional.of(new Goal()));

        plan.addGoal(goal);
        service.addGoalToPlan(1L, 2L);

        assertTrue(plan.getGoals().contains(goal));
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void removeGoalFromPlan() {
        Plan plan = new Plan(1L, LocalDate.of(2022,12,12),LocalDate.of(2023,12,12), "test", PlanStatus.NEW);
        Goal goal = new Goal(2L,"title1","description1", LocalDate.of(2022,12,12),LocalDate.of(2023,12,12),LocalDate.of(2024,12,12), GoalPriority.LOW, GoalStatus.NEW,"comment",4);

        when(repository.findById(1L)).thenReturn(Optional.of(plan));
        when(goalService.findById(2L)).thenReturn(Optional.of(new Goal()));

        plan.removeGoal(goal);
        Plan plan1 = service.removeGoalFromPlan(1L, 2L);

        assertEquals(1L, (long) plan1.getId());
        assertFalse(plan.getGoals().contains(goal));
        verify(repository, times(1)).findById(1L);
    }
}
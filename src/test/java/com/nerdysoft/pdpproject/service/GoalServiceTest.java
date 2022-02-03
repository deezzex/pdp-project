package com.nerdysoft.pdpproject.service;

import com.nerdysoft.pdpproject.entity.Goal;
import com.nerdysoft.pdpproject.entity.SuccessCriteria;
import com.nerdysoft.pdpproject.entity.enums.CriteriaStatus;
import com.nerdysoft.pdpproject.entity.enums.GoalPriority;
import com.nerdysoft.pdpproject.entity.enums.GoalStatus;
import com.nerdysoft.pdpproject.repository.GoalRepository;
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
class GoalServiceTest {

    @Mock
    private GoalRepository repository;

    @Mock
    private SuccessCriteriaService successCriteriaService;

    @InjectMocks
    private GoalService service;

    @Test
    void createGoal() {
        Goal goal = new Goal(1L,"title","description", LocalDate.of(2022,12,12),LocalDate.of(2023,12,12),LocalDate.of(2024,12,12), GoalPriority.LOW, GoalStatus.NEW,"comment",4);

        service.createGoal(goal);

        verify(repository, times(1)).save(goal);
    }

    @Test
    void findById() {

        when(repository.findById(1L)).thenReturn(Optional.of(new Goal(1L,"title","description", LocalDate.of(2022,12,12),LocalDate.of(2023,12,12),LocalDate.of(2024,12,12), GoalPriority.LOW, GoalStatus.NEW,"comment",4)));

        Optional<Goal> goal = service.findById(1L);

        goal.ifPresent(criteria -> assertEquals("title", criteria.getTitle()));
    }

    @Test
    void findAll() {
        ArrayList<Goal> list = new ArrayList<>();

        Goal goal1 = new Goal(1L,"title1","description1", LocalDate.of(2022,12,12),LocalDate.of(2023,12,12),LocalDate.of(2024,12,12), GoalPriority.LOW, GoalStatus.NEW,"comment",4);
        Goal goal2 = new Goal(2L,"title2","description2", LocalDate.of(2022,12,12),LocalDate.of(2023,12,12),LocalDate.of(2024,12,12), GoalPriority.LOW, GoalStatus.NEW,"comment",4);

        list.add(goal1);
        list.add(goal2);

        when(repository.findAll()).thenReturn(list);

        List<Goal> goalsFromService = service.findAll();

        assertEquals(2, goalsFromService.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void update() {
        Goal goal = new Goal(1L,"title1","description1", LocalDate.of(2022,12,12),LocalDate.of(2023,12,12),LocalDate.of(2024,12,12), GoalPriority.LOW, GoalStatus.NEW,"comment",4);
        when(repository.findById(1L)).thenReturn(Optional.of(goal));

        Goal updatedGoal = service.update(1L, new Goal(1L,"title2","description1", LocalDate.of(2022,12,12),LocalDate.of(2023,12,12),LocalDate.of(2024,12,12), GoalPriority.LOW, GoalStatus.NEW,"comment",4));

        assertEquals("title2", updatedGoal.getTitle());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void delete() {
        boolean delete = service.delete(1L);
        repository.delete(new Goal());

        assertTrue(delete);
        verify(repository, times(2)).findById(1L);
    }

    @Test
    void addCriteriaToGoal() {
        Goal goal = new Goal(1L,"title1","description1", LocalDate.of(2022,12,12),LocalDate.of(2023,12,12),LocalDate.of(2024,12,12), GoalPriority.LOW, GoalStatus.NEW,"comment",4);
        SuccessCriteria criteria = new SuccessCriteria(1L, "test", "test", CriteriaStatus.NEW);
        when(repository.findById(1L)).thenReturn(Optional.of(goal));
        when(successCriteriaService.findById(2L)).thenReturn(Optional.of(new SuccessCriteria()));

        goal.addCriteria(criteria);
        service.addCriteriaToGoal(1L, 2L);

        assertTrue(goal.getSuccessCriteria().contains(criteria));
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void removeCriteriaFromGoal() {
        Goal goal = new Goal(1L,"title1","description1", LocalDate.of(2022,12,12),LocalDate.of(2023,12,12),LocalDate.of(2024,12,12), GoalPriority.LOW, GoalStatus.NEW,"comment",4);
        SuccessCriteria criteria = new SuccessCriteria(1L, "test", "test", CriteriaStatus.NEW);


        when(repository.findById(1L)).thenReturn(Optional.of(goal));
        when(successCriteriaService.findById(2L)).thenReturn(Optional.of(new SuccessCriteria()));

        goal.removeCriteria(criteria);
        Goal goal1 = service.removeCriteriaFromGoal(1L, 2L);

        assertEquals(1L, (long) goal1.getId());
        assertFalse(goal.getSuccessCriteria().contains(criteria));
        verify(repository, times(1)).findById(1L);
    }

}
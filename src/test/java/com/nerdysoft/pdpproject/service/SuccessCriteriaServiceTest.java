package com.nerdysoft.pdpproject.service;

import com.nerdysoft.pdpproject.entity.SuccessCriteria;
import com.nerdysoft.pdpproject.entity.dto.SCDto;
import com.nerdysoft.pdpproject.entity.enums.CriteriaStatus;
import com.nerdysoft.pdpproject.repository.SuccessCriteriaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SuccessCriteriaServiceTest {

    @Mock
    private SuccessCriteriaRepository repository;

    @InjectMocks
    private SuccessCriteriaService successCriteriaService;

    @Test
    void createCriteria() {
        SuccessCriteria criteria = new SuccessCriteria(1L, "test", "test", CriteriaStatus.NEW);

        successCriteriaService.createCriteria(criteria);

        verify(repository, times(1)).save(criteria);
    }

    @Test
    void findById() {

        when(repository.findById(1L)).thenReturn(Optional.of(new SuccessCriteria(1L, "test", "test", CriteriaStatus.NEW)));

        Optional<SuccessCriteria> maybeCriteria = successCriteriaService.findById(1L);

        maybeCriteria.ifPresent(criteria -> assertEquals("test", criteria.getTitle()));

    }

    @Test
    void findAll() {
        ArrayList<SuccessCriteria> list = new ArrayList<>();

        SuccessCriteria criteria1 = new SuccessCriteria(1L, "test1", "test1", CriteriaStatus.NEW);
        SuccessCriteria criteria2 = new SuccessCriteria(2L, "test2", "test2", CriteriaStatus.NEW);

        list.add(criteria1);
        list.add(criteria2);

        when(repository.findAll()).thenReturn(list);

        List<SuccessCriteria> successCriteriaFromService = successCriteriaService.findAll();

        assertEquals(2, successCriteriaFromService.size());
        verify(repository, times(1)).findAll();


    }

    @Test
    void update() {
        SuccessCriteria criteria = new SuccessCriteria(1L, "test", "test", CriteriaStatus.NEW);
        when(repository.findById(1L)).thenReturn(Optional.of(criteria));

        SuccessCriteria updatedCriteria = successCriteriaService.update(1L, new SuccessCriteria(2L, "test2", "test2", CriteriaStatus.NEW));

        assertEquals("test2", updatedCriteria.getTitle());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void delete() {
        boolean delete = successCriteriaService.delete(1L);

        assertTrue(delete);
        verify(repository, times(2)).findById(1L);
    }
}
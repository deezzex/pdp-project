package com.nerdysoft.pdpproject.controller;

import com.nerdysoft.pdpproject.entity.Goal;
import com.nerdysoft.pdpproject.entity.SuccessCriteria;
import com.nerdysoft.pdpproject.entity.enums.CriteriaStatus;
import com.nerdysoft.pdpproject.entity.enums.GoalPriority;
import com.nerdysoft.pdpproject.entity.enums.GoalStatus;
import com.nerdysoft.pdpproject.service.GoalService;
import com.nerdysoft.pdpproject.service.SuccessCriteriaService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@WebMvcTest(value = GoalController.class)
class GoalControllerTest {

    @MockBean
    private GoalService goalService;

    @Autowired
    private MockMvc mockMvc;

    private static final String EXPECTED_ARR = "[{\"id\":94,\"title\":\"title\",\"description\":\"description\",\"startDate\":\"2022-12-12\",\"planedEndDate\":\"2023-12-12\",\"endDate\":\"2024-12-12\",\"goalPriority\":\"LOW\",\"goalStatus\":\"NEW\",\"comment\":\"comment\",\"mark\":4,\"criteriaDtos\":[]}]";
    private static final String EXPECTED_OBJ = "{\"id\":94,\"title\":\"title\",\"description\":\"description\",\"startDate\":\"2022-12-12\",\"planedEndDate\":\"2023-12-12\",\"endDate\":\"2024-12-12\",\"goalPriority\":\"LOW\",\"goalStatus\":\"NEW\",\"comment\":\"comment\",\"mark\":4,\"criteriaDtos\":[]}";

    @SneakyThrows
    @Test
    void createGoal() {

        Goal mockGoal = new Goal(94L, "title","description",LocalDate.of(2022,12,12),LocalDate.of(2023,12,12),LocalDate.of(2024,12,12), GoalPriority.LOW, GoalStatus.NEW,"comment",4);

        when(goalService.createGoal(Mockito.any(Goal.class))).thenReturn(mockGoal);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/goal/add").accept(MediaType.APPLICATION_JSON)
                .content("{\"id\":94,\"title\":\"title\",\"description\":\"description\",\"startDate\":\"2022-12-12\",\"planedEndDate\":\"2023-12-12\",\"endDate\":\"2024-12-12\",\"goalPriority\":\"LOW\",\"goalStatus\":\"NEW\",\"comment\":\"comment\",\"mark\":4,\"criteriaDtos\":[]}")
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());

        assertEquals(EXPECTED_OBJ, response.getContentAsString());


    }

    @Test
    void getAllGoals() throws Exception {
        when(goalService.findAll()).thenReturn(List.of(new Goal(94L, "title","description",LocalDate.of(2022,12,12),LocalDate.of(2023,12,12),LocalDate.of(2024,12,12), GoalPriority.LOW, GoalStatus.NEW,"comment",4)));

        MockHttpServletRequestBuilder requestBuilder = get("/goal")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        JSONAssert.assertEquals(EXPECTED_ARR, mvcResult.getResponse().getContentAsString(), false);

    }

    @Test
    void getGoal() throws Exception {
        when(goalService.findById(anyLong())).thenReturn(Optional.of(new Goal(94L,"title","description",LocalDate.of(2022,12,12),LocalDate.of(2023,12,12),LocalDate.of(2024,12,12), GoalPriority.LOW, GoalStatus.NEW,"comment",4)));

        MockHttpServletRequestBuilder requestBuilder = get("/goal/94")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        System.out.println(mvcResult.getResponse());


        JSONAssert.assertEquals(EXPECTED_OBJ, mvcResult.getResponse().getContentAsString(), false);
    }

    @SneakyThrows
    @Test
    void updateGoal() {
        Goal mockGoal = new Goal(94L,"title","description",LocalDate.of(2022,12,12),LocalDate.of(2023,12,12),LocalDate.of(2024,12,12), GoalPriority.LOW, GoalStatus.NEW,"comment",4);

        when(goalService.update(anyLong(), Mockito.any(Goal.class))).thenReturn(mockGoal);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/goal/94").accept(MediaType.APPLICATION_JSON)
                .content("{\"id\":94,\"title\":\"title\",\"description\":\"description\",\"startDate\":\"2022-12-12\",\"planedEndDate\":\"2023-12-12\",\"endDate\":\"2024-12-12\",\"goalPriority\":\"LOW\",\"goalStatus\":\"NEW\",\"comment\":\"comment\",\"mark\":4,\"criteriaDtos\":[]}")
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());

        assertEquals(EXPECTED_OBJ, response.getContentAsString());
    }

    @SneakyThrows
    @Test
    void removeGoal() {
        when(goalService.delete(anyLong())).thenReturn(true);

        MockHttpServletRequestBuilder requestBuilder = delete("/goal/94")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        System.out.println(mvcResult.getResponse());

        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());

        assertEquals(0, mvcResult.getResponse().getContentLength());
    }

    @Test
    void addCriteriaToGoal() throws Exception {
        when(goalService.addCriteriaToGoal(anyLong(),anyLong()))
                .thenReturn(new Goal(94L,"title","description",LocalDate.of(2022,12,12),LocalDate.of(2023,12,12),LocalDate.of(2024,12,12), GoalPriority.LOW, GoalStatus.NEW,"comment",4, List.of(new SuccessCriteria(94L,"testCreate", "testDescription", CriteriaStatus.NEW))));

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/goal/94/add/93").accept(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        JSONAssert.assertEquals("{\"id\":94,\"title\":\"title\",\"description\":\"description\",\"startDate\":\"2022-12-12\",\"planedEndDate\":\"2023-12-12\",\"endDate\":\"2024-12-12\",\"goalPriority\":\"LOW\",\"goalStatus\":\"NEW\",\"comment\":\"comment\",\"mark\":4,\"criteriaDtos\":[{\"id\":94,\"title\":\"testCreate\",\"description\":\"testDescription\",\"criteriaStatus\":\"NEW\"}]}",
                mvcResult.getResponse().getContentAsString(), false);
    }

    @SneakyThrows
    @Test
    void deleteCriteriaFromGoal() {
        when(goalService.removeCriteriaFromGoal(anyLong(),anyLong()))
                .thenReturn(new Goal(94L,"title","description",LocalDate.of(2022,12,12),LocalDate.of(2023,12,12),LocalDate.of(2024,12,12), GoalPriority.LOW, GoalStatus.NEW,"comment",4));

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/goal/94/delete/93").accept(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        JSONAssert.assertEquals("{\"id\":94,\"title\":\"title\",\"description\":\"description\",\"startDate\":\"2022-12-12\",\"planedEndDate\":\"2023-12-12\",\"endDate\":\"2024-12-12\",\"goalPriority\":\"LOW\",\"goalStatus\":\"NEW\",\"comment\":\"comment\",\"mark\":4,\"criteriaDtos\":[]}",
                mvcResult.getResponse().getContentAsString(), false);
    }
}
package com.nerdysoft.pdpproject.controller;

import com.nerdysoft.pdpproject.entity.Goal;
import com.nerdysoft.pdpproject.entity.Plan;
import com.nerdysoft.pdpproject.entity.enums.GoalPriority;
import com.nerdysoft.pdpproject.entity.enums.GoalStatus;
import com.nerdysoft.pdpproject.entity.enums.PlanStatus;
import com.nerdysoft.pdpproject.service.PlanService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@WebMvcTest(value = PlanController.class)
class PlanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlanService planService;

    private static final String EXPECTED_OBJ = "{\"id\":94,\"startDate\":\"2022-12-12\",\"endDate\":\"2023-12-12\",\"description\":\"test\",\"planStatus\":\"NEW\",\"goalDtos\":[]}";
    public static final String EXPECTED_ARR = "[{\"id\":94,\"startDate\":\"2022-12-12\",\"endDate\":\"2023-12-12\",\"description\":\"test\",\"planStatus\":\"NEW\",\"goalDtos\":[]}]";
    @SneakyThrows
    @Test
    void createPlan() {
        Plan mockPlan = new Plan(94L, LocalDate.of(2022,12,12),LocalDate.of(2023,12,12), "test", PlanStatus.NEW);

        when(planService.create(Mockito.any(Plan.class))).thenReturn(mockPlan);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/plan/add").accept(MediaType.APPLICATION_JSON)
                .content("{\"id\":94,\"startDate\":\"2022-12-12\",\"endDate\":\"2023-12-12\",\"description\":\"test\",\"planStatus\":\"NEW\"}")
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());

        assertEquals(EXPECTED_OBJ, response.getContentAsString());

    }

    @SneakyThrows
    @Test
    void getAllPlans() {
        when(planService.findAll()).thenReturn(List.of(new Plan(94L, LocalDate.of(2022,12,12),LocalDate.of(2023,12,12), "test", PlanStatus.NEW)));

        MockHttpServletRequestBuilder requestBuilder = get("/plan")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        JSONAssert.assertEquals(EXPECTED_ARR, mvcResult.getResponse().getContentAsString(), false);
    }

    @Test
    void getPlan() throws Exception {
        when(planService.findById(anyLong())).thenReturn(Optional.of(new Plan(94L, LocalDate.of(2022,12,12),LocalDate.of(2023,12,12), "test", PlanStatus.NEW)));

        MockHttpServletRequestBuilder requestBuilder = get("/plan/94")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        System.out.println(mvcResult.getResponse());


        JSONAssert.assertEquals(EXPECTED_OBJ, mvcResult.getResponse().getContentAsString(), false);
    }

    @SneakyThrows
    @Test
    void updatePlan() {
        Plan mockPlan = new Plan(94L, LocalDate.of(2022,12,12),LocalDate.of(2023,12,12), "test", PlanStatus.NEW);

        when(planService.update(anyLong(), Mockito.any(Plan.class))).thenReturn(mockPlan);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/plan/94").accept(MediaType.APPLICATION_JSON)
                .content("{\"id\":94,\"startDate\":\"2022-12-12\",\"endDate\":\"2023-12-12\",\"description\":\"test\",\"planStatus\":\"NEW\",\"goalDtos\":[]}")
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());

        assertEquals(EXPECTED_OBJ, response.getContentAsString());
    }

    @SneakyThrows
    @Test
    void removePlan() {
        when(planService.delete(anyLong())).thenReturn(anyBoolean());

        MockHttpServletRequestBuilder requestBuilder = delete("/plan/94")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        System.out.println(mvcResult.getResponse());

        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());

        assertEquals(0, mvcResult.getResponse().getContentLength());
    }

    @SneakyThrows
    @Test
    void addCriteriaToGoal() {
        when(planService.addGoalToPlan(anyLong(),anyLong()))
                .thenReturn(new Plan(94L, LocalDate.of(2022,12,12),LocalDate.of(2023,12,12), "test", PlanStatus.NEW, List.of(new Goal(94L,"title","description",LocalDate.of(2022,12,12),LocalDate.of(2023,12,12),LocalDate.of(2024,12,12), GoalPriority.LOW, GoalStatus.NEW,"comment",4))));

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/plan/94/add/93").accept(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        JSONAssert.assertEquals("{\"id\":94,\"startDate\":\"2022-12-12\",\"endDate\":\"2023-12-12\",\"description\":\"test\",\"planStatus\":\"NEW\",\"goalDtos\":[{\"id\":94,\"title\":\"title\",\"description\":\"description\",\"startDate\":\"2022-12-12\",\"planedEndDate\":\"2023-12-12\",\"endDate\":\"2024-12-12\",\"goalPriority\":\"LOW\",\"goalStatus\":\"NEW\",\"comment\":\"comment\",\"mark\":4,\"criteriaDtos\":[]}]}",
                mvcResult.getResponse().getContentAsString(), false);
    }

    @SneakyThrows
    @Test
    void deleteCriteriaFromGoal() {
        when(planService.removeGoalFromPlan(anyLong(),anyLong()))
                .thenReturn(new Plan(94L, LocalDate.of(2022,12,12),LocalDate.of(2023,12,12), "test", PlanStatus.NEW));

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/plan/94/delete/93").accept(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        JSONAssert.assertEquals(EXPECTED_OBJ,
                mvcResult.getResponse().getContentAsString(), false);
    }
}
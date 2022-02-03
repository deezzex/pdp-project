package com.nerdysoft.pdpproject.controller;

import com.nerdysoft.pdpproject.entity.SuccessCriteria;
import com.nerdysoft.pdpproject.entity.enums.CriteriaStatus;
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
import org.springframework.http.*;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@WebMvcTest(value = SuccessCriteriaController.class)
class SuccessCriteriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SuccessCriteriaService successCriteriaService;

    private static final String EXPECTED_OBJ = "{\"id\":null,\"title\":\"testCreate\",\"description\":\"testDescription\",\"criteriaStatus\":\"NEW\"}";
    private static final String EXPECTED_OBJ_UPD = "{\"id\":94,\"title\":\"testCreate\",\"description\":\"testDescription\",\"criteriaStatus\":\"NEW\"}";
    private static final String EXPECTED_ARR = "[{\"id\":null,\"title\":\"testCreate\",\"description\":\"testDescription\",\"criteriaStatus\":\"NEW\"}]";
    @SneakyThrows
    @Test
    void createSuccessCriteria() {
        SuccessCriteria mockSuccessCriteria = new SuccessCriteria("testCreate", "testDescription", CriteriaStatus.NEW);

        when(successCriteriaService.createCriteria(Mockito.any(SuccessCriteria.class))).thenReturn(mockSuccessCriteria);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/criteria/add").accept(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"testCreate\",\"description\":\"testDescription\",\"criteriaStatus\":\"NEW\"}")
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());

        assertEquals(EXPECTED_OBJ, response.getContentAsString());
    }

    @SneakyThrows
    @Test
    void getCriteria() {
        when(successCriteriaService.findAll()).thenReturn(List.of(new SuccessCriteria("testCreate", "testDescription", CriteriaStatus.NEW)));

        MockHttpServletRequestBuilder requestBuilder = get("/criteria")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        System.out.println(mvcResult.getResponse().getStatus());


        JSONAssert.assertEquals(EXPECTED_ARR, mvcResult.getResponse().getContentAsString(), false);
    }


    @SneakyThrows
    @Test
    void getOneCriteria() {
        when(successCriteriaService.findById(anyLong())).thenReturn(Optional.of(new SuccessCriteria("testCreate", "testDescription", CriteriaStatus.NEW)));

        MockHttpServletRequestBuilder requestBuilder = get("/criteria/94")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        System.out.println(mvcResult.getResponse());


        JSONAssert.assertEquals(EXPECTED_OBJ, mvcResult.getResponse().getContentAsString(), false);
    }

    @SneakyThrows
    @Test
    void updateCriteria() {
        SuccessCriteria mockSuccessCriteria = new SuccessCriteria(94L,"testCreate", "testDescription", CriteriaStatus.NEW);

        when(successCriteriaService.update(anyLong(), Mockito.any(SuccessCriteria.class))).thenReturn(mockSuccessCriteria);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/criteria/94").accept(MediaType.APPLICATION_JSON)
                .content("{\"id\":\"94\",\"title\":\"testCreate\",\"description\":\"testDescription\",\"criteriaStatus\":\"NEW\"}")
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());

        assertEquals(EXPECTED_OBJ_UPD, response.getContentAsString());
    }

    @SneakyThrows
    @Test
    void removeCriteria() {
        when(successCriteriaService.delete(anyLong())).thenReturn(true);

        MockHttpServletRequestBuilder requestBuilder = delete("/criteria/94")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        System.out.println(mvcResult.getResponse());

        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());

        assertEquals(0, mvcResult.getResponse().getContentLength());
    }
}
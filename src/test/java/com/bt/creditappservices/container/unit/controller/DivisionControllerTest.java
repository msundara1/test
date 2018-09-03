package com.bt.creditappservices.container.unit.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.bt.creditappservices.model.DivisionEntity;
import com.bt.creditappservices.model.request.DivisionsRequest;
import com.bt.creditappservices.service.DivisionsV1ServiceImpl;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

/**
 * @author mvishwanath
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DivisionControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private DivisionsV1ServiceImpl service;

    private String baseURL = "/v1/creditapp";
    private DivisionEntity firstEntity;
    private DivisionEntity secondEntity;
    private List<DivisionEntity> allEntities;

    @Before
    public void setUp(){
        // given
        firstEntity = new DivisionEntity();
        firstEntity.setName("user1");
        firstEntity.setTenantId("10");
        firstEntity.setParentDivisionKey(null);
        firstEntity.setLastModifiedBy("user1");
        firstEntity.setDisplayName("XYZ company");
        firstEntity.setDescription("XYZ description");
        firstEntity.setCreatedBy("user1");

        secondEntity = new DivisionEntity();
        secondEntity.setName("user2");
        secondEntity.setTenantId("10");
        secondEntity.setLastModifiedBy("user2");
        secondEntity.setDisplayName("PBC");
        secondEntity.setDescription("PBC company description");
        secondEntity.setCreatedBy("user2");
        allEntities = Collections.singletonList(firstEntity);
    }

    @Test
    public void testListAllDivisions() throws Exception{
        //when
        given(service.getAllDivisions()).willReturn(allEntities);

        //then
        mvc.perform(get(baseURL + "/divisions")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].createdBy", Matchers.is("user1")));
    }

    @Test
    public void testListAllDivision_NoContent() throws Exception{
        //when
        given(service.getAllDivisions()).willReturn(null);
        //then
        mvc.perform(get(baseURL + "/divisions")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());
    }

    @Test
    public void testListTenantDivisions_NoContent() throws Exception{
        //when
        given(service.getAllDivisions()).willReturn(null);
        //then
        mvc.perform(get(baseURL + "/divisions")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());
    }

    @Test
    public void testListTenantDivisions() throws Exception{
        //when
        given(service.getAllTenantDivisions("10")).willReturn(allEntities);

        //then
        mvc.perform(get(baseURL + "/{tenantId}/divisions", "10")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].createdBy", Matchers.is("user1")));
    }

    @Test
    public void testGetTenantDivision() throws Exception{
        //when
        given(service.getDivision(any())).willReturn(firstEntity);

        //then
        mvc.perform(get(baseURL + "/{tenantId}/divisions/{divisionId}","10","6f835577-bab1-402a-b46a-adcabdb3f37e")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.createdBy", Matchers.is("user1")));
    }

    @Test
    public void testCreateTenantDivision() throws Exception{
        String exampleDivisionJson = "{\n" + "  \"tenantId\" : \"10\",\n" + "  \"active\": true,\n" + "  \"name\": \"user1\",\n"
            + "  \"displayName\": \"XYZ company\",\n" + "  \"description\": \"XYZ description\",\n"
            + "  \"isActive\": true,\n" + "  \"createdBy\" : \"user1\",\n" + "  \"lastModifiedBy\" : \"user1\"\n" + "}";
        //when
        when(service.createDivision(any(DivisionsRequest.class))).thenReturn("");

        //then
        mvc.perform(post(baseURL + "/{tenantId}/divisions","10")
            .accept(MediaType.APPLICATION_JSON).content(exampleDivisionJson)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated());

    }

    @Test
    public void testUpdateTenantDivision() throws Exception{
        String exampleDivisionJson = "{\n" + "  \"active\": true,\n" + "  \"name\": \"user3\",\n"
            + "  \"displayName\": \"XYZ company\",\n" + "  \"description\": \"XYZ description\",\n"
            + "  \"isActive\": true,\n" + "  \"createdBy\" : \"user1\",\n" + "  \"lastModifiedBy\" : \"user3\"\n" + "}";
        DivisionEntity exampleDivisionEntity = new DivisionEntity();
        exampleDivisionEntity.setName("user3");
        exampleDivisionEntity.setTenantId("10");
        exampleDivisionEntity.setParentDivisionKey(null);
        exampleDivisionEntity.setLastModifiedBy("user3");
        exampleDivisionEntity.setDisplayName("XYZ company");
        exampleDivisionEntity.setDescription("XYZ description");
        exampleDivisionEntity.setCreatedBy("user1");

        //when
        doReturn(exampleDivisionEntity).when(service).updateDivision(UUID.randomUUID(), any(DivisionsRequest.class));

        //then
        mvc.perform(put(baseURL + "/{tenantId}/divisions/{divisionId}","10","6f835577-bab1-402a-b46a-adcabdb3f37e")
            .accept(MediaType.APPLICATION_JSON).content(exampleDivisionJson)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.createdBy", Matchers.is("user1")))
            .andExpect(jsonPath("$.lastModifiedBy", Matchers.is("user3")))
            .andExpect(jsonPath("$.description", Matchers.is("XYZ description")));

    }

    @Test
    public void testDeleteTenantDivision() throws Exception{
        //when
        doNothing().when(service).deleteDivision(UUID.randomUUID());

        //then
        mvc.perform(delete(baseURL + "/{tenantId/divisions/{divisionId}", "10", "6f835577-bab1-402a-b46a-adcabdb3f37e")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

    }

}

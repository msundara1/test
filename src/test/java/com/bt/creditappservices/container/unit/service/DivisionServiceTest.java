package com.bt.creditappservices.container.unit.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.bt.creditappservices.exceptions.ResourceNotFoundException;
import com.bt.creditappservices.model.DivisionEntity;
import com.bt.creditappservices.model.request.DivisionsRequest;
import com.bt.creditappservices.repository.DivisionsRepository;
import com.bt.creditappservices.service.DivisionsV1ServiceImpl;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

/**
 * @author mvishwanath
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DivisionServiceTest {
    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private DivisionsV1ServiceImpl service;

    @Mock
    private DivisionsRepository dao;

    private DivisionEntity divisionEntity1;
    private List<DivisionEntity> allEntities;
    UUID uuid;

    @Before
    public void setUp(){

        MockitoAnnotations.initMocks(this);
        //given
        uuid = UUID.fromString("976f2d5d-acdf-438f-a743-cb5bcad87045");
        divisionEntity1 = new DivisionEntity();
        divisionEntity1.setDivisionKey(uuid);
        divisionEntity1.setName("testUser1");
        divisionEntity1.setTenantId("10");
        divisionEntity1.setLastModifiedBy("testUser1");
        divisionEntity1.setDisplayName("PNC company");
        divisionEntity1.setDescription("PNC description");
        divisionEntity1.setCreatedBy("testUser1");

        allEntities = Collections.singletonList(divisionEntity1);
    }

    @Test
    public void testGetAllTenantDivisions() throws Exception {

    }
    @Test
    public void testGetAllDivisions() throws Exception{
        //when
        when(dao.findAll()).thenReturn(allEntities);

        //then
        List<DivisionEntity> divisionEntity = service.getAllDivisions();
        assertEquals(1,divisionEntity.size());

    }

    @Test
    public void testGetDivision() throws Exception{
        //when
        when(dao.findById(any())).thenReturn(Optional.of(divisionEntity1));
        //then
        DivisionEntity retrievedDivision =  service.getDivision(uuid);
        assertEquals(divisionEntity1,retrievedDivision);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetDivision_ResourceNotFoundException() throws Exception{
        //when
        doThrow(new ResourceNotFoundException()).when(dao).findById(any());
        //then
        service.getDivision(uuid);
    }

    @Test
    public void testCreateDivision() throws Exception{
        DivisionsRequest request = new DivisionsRequest();
        request.setTenantId("4");
        request.setDisplayName("PNC company");
        request.setDescription("PNC description");
        request.setIsActive(true);
        request.setName("testUser1");
        request.setUserName("testUser1");
        //when
        when(dao.existsByTenantId(anyString())).thenReturn(true);
        when(dao.save(Mockito.any(DivisionEntity.class)))
            .thenAnswer(i -> {
                DivisionEntity division = (DivisionEntity) i.getArguments()[0];
                division.setTenantId("4");
                division.setDivisionKey(uuid);
                division.setDisplayName("PNC company");
                division.setDescription("PNC description");
                division.setIsActive(true);
                division.setName("testUser1");
                division.setLastModifiedBy("testUser1");
                division.setCreatedBy("testUser1");
                return division;
                 });

        //then
        String retrievedUUID = service.createDivision(request);
        assertNotNull(retrievedUUID);
        assertEquals("976f2d5d-acdf-438f-a743-cb5bcad87045",retrievedUUID);
    }

    @Test( expected = NullPointerException.class )
    public void testCreateDivision_NullPointerException() throws Exception {
        service.createDivision(null) ;
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testCreateDivision_ResourceNotFoundException() throws Exception {
        DivisionsRequest request1 = new DivisionsRequest();
        request1.setTenantId("10");
        request1.setDisplayName("PNC company");
        request1.setDescription("PNC description");
        request1.setIsActive(true);
        request1.setName("testUser1");
        request1.setUserName("testUser1");
        //when
        doThrow(new ResourceNotFoundException()).when(dao).existsByTenantId(Mockito.any(String.class));
        when(dao.findById(any())).thenReturn(Optional.of(divisionEntity1));
        //then
        service.createDivision(request1);
    }

    @Test(expected = RuntimeException.class)
    public void testCreateDivision_Exception() throws Exception {
        DivisionsRequest request1 = new DivisionsRequest();
        request1.setTenantId("10");
        request1.setDisplayName("PNC company");
        request1.setDescription("PNC description");
        request1.setIsActive(true);
        request1.setName("testUser1");
        request1.setUserName("testUser1");
        //when
        when(dao.existsByTenantId(anyString())).thenReturn(true);
        doThrow(RuntimeException.class).when(dao).save(Mockito.any(DivisionEntity.class));
        //then
        service.createDivision(request1);
    }

    @Test
    public void testUpdateDivision() throws Exception{
        DivisionsRequest request1 = new DivisionsRequest();
        request1.setTenantId("10");
        request1.setDisplayName("PNC company");
        request1.setDescription("PNC description");
        request1.setIsActive(true);
        request1.setName("testUser1");
        request1.setUserName("testUser1");
        //when
        when(dao.existsByTenantId(anyString())).thenReturn(true);
        when(dao.findById(any())).thenReturn(Optional.of(divisionEntity1));
        when(dao.save(Mockito.any(DivisionEntity.class)))
            .thenAnswer(i -> {
                DivisionEntity division = (DivisionEntity) i.getArguments()[0];
                division.setTenantId("10");
                division.setDivisionKey(uuid);
                division.setDisplayName("PNC company");
                division.setDescription("PNC description");
                division.setIsActive(true);
                division.setName("testUser1");
                division.setLastModifiedBy("testUser1");
                division.setCreatedBy("testUser1");
                return division;
            });

        //then
        DivisionEntity retrievedDivision = service.updateDivision(uuid, request1);
        assertEquals("PNC company",retrievedDivision.getDisplayName());
        assertEquals(divisionEntity1.getDisplayName(),retrievedDivision.getDisplayName());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testUpdateDivision_ResourceNotFoundException() throws Exception {
        DivisionsRequest request1 = new DivisionsRequest();
        request1.setTenantId("10");
        request1.setDisplayName("PNC company");
        request1.setDescription("PNC description");
        request1.setIsActive(true);
        request1.setName("testUser1");
        request1.setUserName("testUser1");
        //when
        doThrow(new ResourceNotFoundException()).when(dao).existsByTenantId(Mockito.any(String.class));
        when(dao.findById(any())).thenReturn(Optional.of(divisionEntity1));
        //then
        service.updateDivision(uuid, request1);
    }

    @Test(expected = RuntimeException.class)
    public void testUpdateDivision_Exception() throws Exception {
        DivisionsRequest request1 = new DivisionsRequest();
        request1.setTenantId("10");
        request1.setDisplayName("PNC company");
        request1.setDescription("PNC description");
        request1.setIsActive(true);
        request1.setName("testUser1");
        request1.setUserName("testUser1");
        //when
        when(dao.existsByTenantId(anyString())).thenReturn(true);
        doThrow(RuntimeException.class).when(dao).save(Mockito.any(DivisionEntity.class));
        when(dao.findById(any())).thenReturn(Optional.of(divisionEntity1));
        //then
        service.updateDivision(uuid, request1);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testUpdateDivision_FindById_ResourceNotFoundException() throws Exception {
        DivisionsRequest request1 = new DivisionsRequest();
        request1.setTenantId("10");
        request1.setDisplayName("PNC company");
        request1.setDescription("PNC description");
        request1.setIsActive(true);
        request1.setName("testUser1");
        request1.setUserName("testUser1");
        //when
        when(dao.existsByTenantId(anyString())).thenReturn(true);
        when(dao.save(Mockito.any(DivisionEntity.class))).thenReturn(new DivisionEntity());
        doThrow(new ResourceNotFoundException()).when(dao).findById(any());
        //then
        service.updateDivision(uuid, request1);
    }

    @Test
    public void testDeleteDivision() throws Exception{
        //when
        when(dao.findById(any())).thenReturn(Optional.of(divisionEntity1));
        doNothing().when(dao).delete(Mockito.any(DivisionEntity.class));
        //then
        service.deleteDivision(uuid);
        verify(dao, times(1)).delete(divisionEntity1);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testDeleteDivision_ResourceNotFoundException() throws Exception{
        //when
        doThrow(new ResourceNotFoundException()).when(dao).findById(any());
        doNothing().when(dao).delete(Mockito.any(DivisionEntity.class));
        //then
        service.getDivision(uuid);
    }

    //    @Test( expected = NullPointerException.class )
    //    public void testDeleteDivision_NullPointerException() throws Exception{
    //        //then
    //        service.deleteDivision(null);
    //    }

}

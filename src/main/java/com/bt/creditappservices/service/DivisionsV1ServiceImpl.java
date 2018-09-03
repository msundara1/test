package com.bt.creditappservices.service;

import com.bt.creditappservices.exceptions.ResourceNotFoundException;
import com.bt.creditappservices.model.DivisionEntity;
import com.bt.creditappservices.model.request.DivisionsRequest;
import com.bt.creditappservices.repository.DivisionsRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author msundara
 */
@Service
public class DivisionsV1ServiceImpl implements DivisionsService {

  @Autowired
  DivisionsRepository dao;

  public List<DivisionEntity> getAllDivisions() {
    return dao.findAll();
  }

  public List<DivisionEntity> getAllTenantDivisions(String tenantId) {
    if(!dao.existsByTenantId(tenantId)){
      throw new ResourceNotFoundException("TenantId " + tenantId + " not found");
    }
    return dao.findAllDivisionsByTenantId(tenantId);
  }

  public DivisionEntity getDivision(UUID divisionId) {
    return dao.findById(divisionId).orElseThrow(() -> new ResourceNotFoundException("Division " + divisionId + " not found"));
  }

  public String createDivision(DivisionsRequest request) {
    if(!dao.existsByTenantId(request.getTenantId())){
      throw new ResourceNotFoundException("TenantId " + request.getTenantId() + " not found");
    }
    DivisionEntity entity = new DivisionEntity();
    entity.setTenantId(request.getTenantId());
    entity.setName(request.getName());
    entity.setDisplayName(request.getDisplayName());
    entity.setDescription(request.getDescription());
    entity.setIsActive(request.getIsActive());
    entity.setCreatedBy(request.getUserName());
    entity.setLastModifiedBy(request.getUserName());
    String uid = dao.save(entity).getDivisionKey().toString();
    return uid;
  }

  public DivisionEntity updateDivision(UUID divisionId, DivisionsRequest request) {
    if(!dao.existsByTenantId(request.getTenantId())){
      throw new ResourceNotFoundException("TenantId " + request.getTenantId() + " not found");
    }
    DivisionEntity entity = dao.findById(divisionId)
        .orElseThrow(() -> new ResourceNotFoundException("Division " + divisionId + " not found"));
    entity.setName(request.getName());
    entity.setDisplayName(request.getDisplayName());
    entity.setDescription(request.getDescription());
    entity.setIsActive(request.getIsActive());
    entity.setLastModifiedBy(request.getUserName());
    return dao.save(entity);
  }

  public void deleteDivision(UUID divisionId) {
    DivisionEntity entity = dao.findById(divisionId)
        .orElseThrow(() -> new ResourceNotFoundException("Division " + divisionId + " not found"));

    dao.delete(entity);
  }


}

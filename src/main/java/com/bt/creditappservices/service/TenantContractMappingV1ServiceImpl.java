package com.bt.creditappservices.service;

import com.bt.creditappservices.exceptions.ResourceExistsException;
import com.bt.creditappservices.exceptions.ResourceNotFoundException;
import com.bt.creditappservices.model.TenantContractMappingEntity;
import com.bt.creditappservices.model.request.TenantContractMappingRequest;
import com.bt.creditappservices.repository.TenantContractMappingRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author msundara
 */
@Service
public class TenantContractMappingV1ServiceImpl implements TenantContractMappingService {

  @Autowired
  TenantContractMappingRepository dao;

  public List<TenantContractMappingEntity> getAllTenantContractMapping() {
    return dao.findAll();
  }

  public TenantContractMappingEntity getTenantContractMapping(String tenantId) {
    return dao.getContractByTenantId(tenantId);
  }

  public String createTenantContractMapping(TenantContractMappingRequest request) {
    TenantContractMappingEntity entity = dao.getContractByTenantId(request.getTenantId());
    if (entity != null) {
      throw new ResourceExistsException("Tenant contract already exists");
    }
    entity = new TenantContractMappingEntity();
    entity.setTenantId(request.getTenantId());
    entity.setClientId(request.getClientId());
    entity.setContractId(request.getContractId());
    entity.setCreatedBy(request.getUserName());
    entity.setLastModifiedBy(request.getUserName());
    String uid = dao.save(entity).getId().toString();
    return uid;
  }

  public TenantContractMappingEntity updateTenantContractMapping(UUID id, TenantContractMappingRequest request) {
    TenantContractMappingEntity entity = dao.findContractByTenantIdAndTenantContractKey(request.getTenantId(), id);
    if (entity == null) {
      throw new ResourceNotFoundException("Tenant contract " + id + " not found");
    }
    entity.setTenantId(request.getTenantId());
    entity.setClientId(request.getClientId());
    entity.setContractId(request.getContractId());
    entity.setLastModifiedBy(request.getUserName());
    return dao.save(entity);
  }

  public void deleteTenantContractMapping(String tenantId, UUID id) {
    TenantContractMappingEntity entity = dao.findContractByTenantIdAndTenantContractKey(tenantId, id);
    if (entity == null) {
      throw new ResourceNotFoundException("Tenant contract " + id + " not found");
    }
    dao.delete(entity);
  }
}
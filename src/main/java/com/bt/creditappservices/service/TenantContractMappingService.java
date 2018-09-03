package com.bt.creditappservices.service;

import com.bt.creditappservices.model.TenantContractMappingEntity;
import com.bt.creditappservices.model.request.TenantContractMappingRequest;
import java.util.List;
import java.util.UUID;

/**
 * @author msundara
 */
public interface TenantContractMappingService {

  List<TenantContractMappingEntity> getAllTenantContractMapping();

  TenantContractMappingEntity getTenantContractMapping(String tenantId);

  String createTenantContractMapping(TenantContractMappingRequest request);

  TenantContractMappingEntity updateTenantContractMapping(UUID id, TenantContractMappingRequest request);

  void deleteTenantContractMapping(String tenantId, UUID id);
}

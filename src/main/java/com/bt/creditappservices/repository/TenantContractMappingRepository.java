package com.bt.creditappservices.repository;

import com.bt.creditappservices.model.TenantContractMappingEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author msundara
 */
@Repository
public interface TenantContractMappingRepository extends JpaRepository<TenantContractMappingEntity, UUID> {

  @Query(value = "SELECT top 1 * FROM tenantContract WHERE tenantId = :tenantId", nativeQuery = true)
  public TenantContractMappingEntity getContractByTenantId(@Param("tenantId") String tenantId);

  @Query(value = "SELECT top 1 * FROM tenantContract WHERE tenantContractKey = :tenantContractKey and tenantId = :tenantId", nativeQuery = true)
  public TenantContractMappingEntity findContractByTenantIdAndTenantContractKey(@Param("tenantId") String tenantId, @Param("tenantContractKey") UUID tenantContractKey);
}

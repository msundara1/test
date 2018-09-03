package com.bt.creditappservices.repository;

import com.bt.creditappservices.model.DivisionEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author msundara
 */
@Repository
public interface DivisionsRepository extends JpaRepository<DivisionEntity, UUID> {

  String EXIST_TENANTID = "SELECT COUNT(t.tenantId) > 0 FROM TenantContract t WHERE t.tenantId = :tenantId";
  String FIND_DIVISIONS_BY_TENANTID = "SELECT d.divisionkey, d.tenantId, d.parentDivisionKey, d.name, d.displayName, d.description, d.isActive, "
      + "      d.createdBy, d.createdDate, d.lastModifiedBy, d.lastModifiedDate "
      + "      FROM Division d WHERE d.tenantId = :tenantId";


  @Query(value = EXIST_TENANTID, nativeQuery = true)
  public boolean existsByTenantId(@Param("tenantId") String tenantId);

  @Query(value = FIND_DIVISIONS_BY_TENANTID, nativeQuery = true)
  public List<DivisionEntity> findAllDivisionsByTenantId(@Param("tenantId") String tenantId);

}

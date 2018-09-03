package com.bt.creditappservices.repository;

import com.bt.creditappservices.model.FieldEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author mvishwanath
 */
@Repository
public interface FieldRepository extends JpaRepository<FieldEntity, UUID> {

    String EXIST_TENANTID = "SELECT COUNT(t.tenantId) > 0 FROM TenantContract t WHERE t.tenantId = :tenantId";

    @Query(value = EXIST_TENANTID, nativeQuery = true)
    public boolean existsByTenantId(@Param("tenantId") String tenantId);


}

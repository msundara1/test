package com.bt.creditappservices.repository;

import com.bt.creditappservices.model.SectionEntity;
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
public interface SectionsRepository extends JpaRepository<SectionEntity, UUID> {


  @Query(value = "SELECT * FROM section WHERE tenantId = :tenantId", nativeQuery = true)
  public List<SectionEntity> listTenantSections(@Param("tenantId") String tenantId);

  @Query(value = "SELECT * FROM section WHERE sectionKey = :sectionKey and tenantId = :tenantId", nativeQuery = true)
  public SectionEntity findSectionByTenantIdAndSectionKey(@Param("tenantId") String tenantId,
      @Param("sectionKey") UUID sectionKey);
}

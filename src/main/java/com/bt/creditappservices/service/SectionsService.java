package com.bt.creditappservices.service;

import com.bt.creditappservices.model.SectionEntity;
import com.bt.creditappservices.model.request.SectionsRequest;
import java.util.List;
import java.util.UUID;

/**
 * @author msundara
 */
public interface SectionsService {

  List<SectionEntity> listSections();

  List<SectionEntity> listTenantSections(String tenantId);

  SectionEntity getTenantSection(String tenantId, UUID sectionId);

  String createSection(SectionsRequest request);

  SectionEntity updateSection(UUID sectionId, SectionsRequest request);

  void deleteTenantSection(String tenantId, UUID sectionId);
}

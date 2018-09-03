package com.bt.creditappservices.service;

import com.bt.creditappservices.exceptions.ResourceNotFoundException;
import com.bt.creditappservices.model.SectionEntity;
import com.bt.creditappservices.model.request.SectionsRequest;
import com.bt.creditappservices.repository.SectionsRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author msundara
 */
@Service
public class SectionsV1ServiceImpl implements SectionsService {

  @Autowired
  SectionsRepository dao;

  public List<SectionEntity> listSections() {
    return dao.findAll();
  }

  public List<SectionEntity> listTenantSections(String tenantId) {
    return dao.listTenantSections(tenantId);
  }

  public SectionEntity getTenantSection(String tenantId, UUID sectionId) {
    SectionEntity entity = dao.findSectionByTenantIdAndSectionKey(tenantId, sectionId);
    if (entity == null) {
      throw new ResourceNotFoundException("Section " + sectionId + " not found");
    }
    return entity;
  }

  public String createSection(SectionsRequest request) {
    SectionEntity entity = new SectionEntity();
    entity.setTenantId(request.getTenantId());
    entity.setName(request.getName());
    entity.setDisplayName(request.getDisplayName());
    entity.setDescription(request.getDescription());
    entity.setIsActive(request.getIsActive());
    entity.setCreatedBy(request.getUserName());
    entity.setLastModifiedBy(request.getUserName());
    String uid = dao.save(entity).getSectionKey().toString();
    return uid;
  }

  public SectionEntity updateSection(UUID sectionId, SectionsRequest request) {
    SectionEntity entity = dao.findSectionByTenantIdAndSectionKey(request.getTenantId(), sectionId);
    if (entity == null) {
      throw new ResourceNotFoundException("Section " + sectionId + " not found");
    }
    entity.setTenantId(request.getTenantId());
    entity.setName(request.getName());
    entity.setDisplayName(request.getDisplayName());
    entity.setDescription(request.getDescription());
    entity.setIsActive(request.getIsActive());
    entity.setLastModifiedBy(request.getUserName());
    return dao.save(entity);
  }

  public void deleteTenantSection(String tenantId, UUID sectionId) {
    SectionEntity entity = dao.findSectionByTenantIdAndSectionKey(tenantId, sectionId);
    if (entity == null) {
      throw new ResourceNotFoundException("Section " + sectionId + " not found");
    }
    dao.delete(entity);
  }
}

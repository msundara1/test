package com.bt.creditappservices.service;

import com.bt.creditappservices.exceptions.ResourceNotFoundException;
import com.bt.creditappservices.model.FieldEntity;
import com.bt.creditappservices.model.request.FieldRequest;
import com.bt.creditappservices.repository.FieldRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author mvishwanath
 */
@Service
public class FieldV1ServiceImpl implements FieldService {

    @Autowired
    FieldRepository dao;

    public List<FieldEntity> getAllFields() {
        return dao.findAll();
    }

    public List<FieldEntity> getAllTenantFields(String tenantId) {
        if(!dao.existsByTenantId(tenantId)){
            throw new ResourceNotFoundException("TenantId " + tenantId + " not found");
        }
//        return dao.findAllFieldsByTenantId(tenantId);
        return dao.findAll();
    }

    public FieldEntity getField(UUID FieldId) {
        return dao.findById(FieldId).orElseThrow(() -> new ResourceNotFoundException("Field " + FieldId + " not found"));
    }

    public String createField(FieldRequest request) {
        if(!dao.existsByTenantId(request.getTenantId())){
            throw new ResourceNotFoundException("TenantId " + request.getTenantId() + " not found");
        }
        FieldEntity entity = new FieldEntity();
        entity.setTenantId(request.getTenantId());
        entity.setName(request.getName());
        entity.setDisplayName(request.getDisplayName());
        entity.setDescription(request.getDescription());
        entity.setIsActive(request.getIsActive());
        entity.setCreatedBy(request.getUserName());
        entity.setLastModifiedBy(request.getUserName());
        String uid = dao.save(entity).getFieldKey().toString();
        return uid;
    }

    public FieldEntity updateField(UUID FieldId, FieldRequest request) {
        if(!dao.existsByTenantId(request.getTenantId())){
            throw new ResourceNotFoundException("TenantId " + request.getTenantId() + " not found");
        }
        FieldEntity entity = dao.findById(FieldId)
            .orElseThrow(() -> new ResourceNotFoundException("Field " + FieldId + " not found"));
        entity.setName(request.getName());
        entity.setDisplayName(request.getDisplayName());
        entity.setDescription(request.getDescription());
        entity.setIsActive(request.getIsActive());
        entity.setLastModifiedBy(request.getUserName());
        return dao.save(entity);
    }

    public void deleteField(UUID FieldId) {
        FieldEntity entity = dao.findById(FieldId)
            .orElseThrow(() -> new ResourceNotFoundException("Field " + FieldId + " not found"));

        dao.delete(entity);
    }
}

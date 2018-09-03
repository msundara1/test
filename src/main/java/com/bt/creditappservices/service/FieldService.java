package com.bt.creditappservices.service;

import com.bt.creditappservices.model.FieldEntity;
import com.bt.creditappservices.model.request.FieldRequest;
import java.util.List;
import java.util.UUID;

/**
 * @author mvishwanath
 */
public interface FieldService {

    List<FieldEntity> getAllFields();

    List<FieldEntity> getAllTenantFields(String tenantId);

    FieldEntity getField(UUID fieldId);

    String createField(FieldRequest request);

    FieldEntity updateField(UUID fieldId, FieldRequest request);

    void deleteField(UUID fieldId);
}


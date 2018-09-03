package com.bt.creditappservices.service;

import com.bt.creditappservices.model.DivisionEntity;
import com.bt.creditappservices.model.request.DivisionsRequest;
import java.util.List;
import java.util.UUID;

/**
 * @author msundara
 */
public interface DivisionsService {

  List<DivisionEntity> getAllDivisions();

  List<DivisionEntity> getAllTenantDivisions(String tenantId);

  DivisionEntity getDivision(UUID divisionId);

  String createDivision(DivisionsRequest request);

  DivisionEntity updateDivision(UUID divisionId, DivisionsRequest request);

  void deleteDivision(UUID divisionId);
}

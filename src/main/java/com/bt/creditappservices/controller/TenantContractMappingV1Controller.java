package com.bt.creditappservices.controller;

import com.bt.creditappservices.model.TenantContractMappingEntity;
import com.bt.creditappservices.model.request.TenantContractMappingRequest;
import com.bt.creditappservices.model.response.ErrorResponse;
import com.bt.creditappservices.model.response.TenantContractResponse;
import com.bt.creditappservices.service.TenantContractMappingService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * @author msundara
 */
@RestController
@RequestMapping("/v1/creditapp")
public class TenantContractMappingV1Controller {

  @Autowired
  TenantContractMappingService service;

  @ApiOperation(value = "Retrieves all contract details for internal testing",
      notes = "Use this API to retrieve tenant contract details for internal",
      tags = "Tenant contract", position = 0)
  @RequestMapping(value = "/contracts",
      method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Contract details", response = TenantContractResponse.class),
      @ApiResponse(code = 204, message = "Contracts not found"),
      @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
      @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class)})
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<?> listAllContracts() {
    List<TenantContractMappingEntity> entity = service.getAllTenantContractMapping();
    if (null == entity || entity.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity<>(entity, HttpStatus.OK);
  }

  @ApiOperation(value = "Retrieves tenant contract details",
      notes = "Use this API to retrieve tenant contract details",
      tags = "Tenant contract", position = 0)
  @RequestMapping(value = "/{tenantId}/contracts",
      method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Contract details", response = TenantContractResponse.class),
      @ApiResponse(code = 204, message = "Contracts not found"),
      @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
      @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class)})
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<?> listTenantContract(@PathVariable String tenantId) {
    TenantContractMappingEntity entity = service.getTenantContractMapping(tenantId);
    if (null == entity) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity<>(entity, HttpStatus.OK);
  }

  @ApiOperation(value = "Create tenant contract",
      notes = "Use this API to map contractId to tenant",
      tags = "Tenant contract", position = 0)
  @RequestMapping(value = "/{tenantId}/contracts",
      method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ApiResponses(value = {
      @ApiResponse(code = 201, message = "Contract created"),
      @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
      @ApiResponse(code = 409, message = "Contract already exists"),
      @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class)})
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<?> createTenantContract(@PathVariable String tenantId,
      @RequestBody TenantContractMappingRequest tenantContractRequest) {
    tenantContractRequest.setTenantId(tenantId);
    tenantContractRequest.setUserName("test1234");
    String tenantContractKey = service.createTenantContractMapping(tenantContractRequest);

    URI location = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{contractId}")
        .buildAndExpand(tenantContractKey)
        .toUri();
    return ResponseEntity.created(location).build();
  }

  @ApiOperation(value = "Updates tenant contract",
      notes = "Use this API to update contractId to tenant",
      tags = "Tenant contract", position = 0)
  @RequestMapping(value = "/{tenantId}/contracts/{id}",
      method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Contract details updated", response = TenantContractResponse.class),
      @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
      @ApiResponse(code = 404, message = "Contract not found"),
      @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class)})
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<?> updateTenantContract(@PathVariable String tenantId, @PathVariable UUID id,
      @RequestBody TenantContractMappingRequest tenantContractRequest) {
    tenantContractRequest.setTenantId(tenantId);
    tenantContractRequest.setUserName("test1234");
    return new ResponseEntity<>(service.updateTenantContractMapping(id, tenantContractRequest), HttpStatus.OK);
  }

  @ApiOperation(value = "Deletes tenant contract",
      notes = "Use this API to delete a tenant contract",
      tags = "Tenant contract", position = 0)
  @RequestMapping(value = "/{tenantId}/contracts/{id}",
      method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ApiResponses(value = {
      @ApiResponse(code = 204, message = "Tenant contract deleted"),
      @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
      @ApiResponse(code = 404, message = "Contract not found"),
      @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class)})
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public ResponseEntity<?> deleteTenantContract(@PathVariable String tenantId, @PathVariable UUID id) {
    service.deleteTenantContractMapping(tenantId, id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}

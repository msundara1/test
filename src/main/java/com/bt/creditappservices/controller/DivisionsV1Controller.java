package com.bt.creditappservices.controller;

import com.bt.creditappservices.model.DivisionEntity;
import com.bt.creditappservices.model.request.DivisionsRequest;
import com.bt.creditappservices.model.response.DivisionsResponse;
import com.bt.creditappservices.model.response.ErrorResponse;
import com.bt.creditappservices.service.DivisionsService;
import com.bt.creditappservices.validation.DivisionRequestValidator;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
/**
 * @author msundara
 */
@RestController
@RequestMapping("/v1/creditapp")
public class DivisionsV1Controller {

  @Autowired
  DivisionsService service;

    private DivisionRequestValidator divisionRequestValidator;

    @Autowired
    public DivisionsV1Controller(DivisionRequestValidator divisionRequestValidator) {
      this.divisionRequestValidator = divisionRequestValidator;
    }

    @InitBinder("divisionsRequest")
    public void setupBinder(WebDataBinder binder) {
      binder.addValidators(divisionRequestValidator);
    }

  @ApiOperation(value = "Retrieves all divisions details for internal testing",
      notes = "Use this API to retrieve divisions details for internal testing",
      tags = "Divisions", position = 0)
  @GetMapping(value="/divisions", produces = "application/json")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Divisions details", response = DivisionsResponse.class),
      @ApiResponse(code = 204, message = "Divisions not found"),
      @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
      @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class)})
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<?> listAllDivisions() {
    List<DivisionEntity> entity = service.getAllDivisions();
    if (null == entity || entity.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity<>(entity, HttpStatus.OK);
  }

  @ApiOperation(value = "Retrieves all divisions of a tenant",
      notes = "Use this API to retrieve all divisions details of a tenant",
      tags = "Divisions", position = 1)
  @GetMapping(value = "/{tenantId}/divisions", produces = "application/json")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "List of divisions", response = DivisionsResponse.class),
      @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
      @ApiResponse(code = 204, message = "Divisions not found"),
      @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class)})
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<?> listTenantDivisions(@PathVariable String tenantId) {
    List<DivisionEntity> divisions = service.getAllTenantDivisions(tenantId);
    if (divisions.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity<>(divisions, HttpStatus.OK);
  }

  @ApiOperation(value = "Retrieves particular division of a tenant",
      notes = "Use the divisions API to retrieve division of a tenant",
      tags = "Divisions", position = 2)
  @GetMapping(value = "/{tenantId}/divisions/{divisionId}", produces = "application/json")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Retrieves division details", response = DivisionsResponse.class),
      @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
      @ApiResponse(code = 404, message = "Division not found"),
      @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class)})
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<?> getTenantDivision(@PathVariable String tenantId, @PathVariable UUID divisionId) throws
      MethodArgumentTypeMismatchException {
    return new ResponseEntity<>(service.getDivision(divisionId), HttpStatus.OK);
  }

  @ApiOperation(value = "Creates a new division for a tenant",
      notes = "Use this  API to create a new division for a tenant",
      tags = "Divisions", position = 3)
  @PostMapping(value= "/{tenantId}/divisions", produces = "application/json")
  @ApiResponses(value = {
      @ApiResponse(code = 201, message = "Division created"),
      @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
      @ApiResponse(code = 409, message = "Conflict - Division Exists"),
      @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class)})
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<?> createTenantDivision(@PathVariable String tenantId,  @Valid @RequestBody DivisionsRequest divisionsRequest) {
    divisionsRequest.setTenantId(tenantId);
    divisionsRequest.setUserName("test1234");
    String divisionId = service.createDivision(divisionsRequest);
    URI location = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{divisionId}")
        .buildAndExpand(divisionId)
        .toUri();
    return ResponseEntity.created(location).build();
  }

  @ApiOperation(value = "Updates details of a particular division of a tenant",
      notes = "Use this API to update details for a particular division of a tenant",
      tags = "Divisions", position = 4)
  @PutMapping(value ="/{tenantId}/divisions/{divisionId}", produces = "application/json")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Division details updated", response = DivisionsResponse.class),
      @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
      @ApiResponse(code = 404, message = "Division not found"),
      @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class)})
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<?> updateTenantDivision(@PathVariable String tenantId, @PathVariable UUID divisionId,
      @RequestBody DivisionsRequest divisionsRequest) throws MethodArgumentTypeMismatchException {
    divisionsRequest.setTenantId(tenantId);
    divisionsRequest.setUserName("test1234");
    return new ResponseEntity<>(service.updateDivision(divisionId, divisionsRequest), HttpStatus.OK);
  }

  @ApiOperation(value = "Deletes a particular division of a tenant",
      notes = "Use this API to delete a particular division of a tenant",
      tags = "Divisions", position = 5)
  @DeleteMapping(value ="/{tenantId}/divisions/{divisionId}", produces = "application/json")
  @ApiResponses(value = {
      @ApiResponse(code = 204, message = "Tenant division deleted"),
      @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
      @ApiResponse(code = 404, message = "Division not found"),
      @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class)})
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public ResponseEntity<?> deleteTenantDivision(@PathVariable String tenantId, @PathVariable UUID divisionId)
      throws MethodArgumentTypeMismatchException {
    service.deleteDivision(divisionId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}

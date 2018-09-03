package com.bt.creditappservices.controller;

import com.bt.creditappservices.exceptions.ResourceExistsException;
import com.bt.creditappservices.exceptions.ResourceNotFoundException;
import com.bt.creditappservices.model.response.CreditApplicationsResponse;
import com.bt.creditappservices.model.response.ErrorResponse;
import com.bt.creditappservices.service.CreditApplicationsV1Service;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.net.URI;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * @author msundara
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/creditapp")
public class CreditApplicationsV1Controller {

  private CreditApplicationsV1Service _service;

  @ApiOperation(value = "Retrieves all credit applications of a tenant",
      notes = "Use this API to retrieve all credit applications of a tenant",
      tags = "Credit Applications")
  @RequestMapping(value = "/{tenantId}/creditapplications",
      method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "List of credit applications of a tenant", response = CreditApplicationsResponse.class),
      @ApiResponse(code = 204, message = "Credit applications not found"),
      @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
      @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class)})
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<?> listTenantCreditApplications(@PathVariable String tenantId) {
    int totalRecords = 0;
    if (totalRecords == 0) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity<>(new CreditApplicationsResponse(), HttpStatus.OK);
  }

  @ApiOperation(value = "Retrieves all credit applications from particular division of a tenant",
      notes = "Use this API to retrieve all credit applications from particular division of a tenant",
      tags = "Credit Applications")
  @RequestMapping(value = "/{tenantId}/{divisionId}/creditapplications",
      method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "List of credit applications from particular division of a client", response = CreditApplicationsResponse.class),
      @ApiResponse(code = 204, message = "Credit applications not found"),
      @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
      @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class)})
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<?> listTenantDivisionCreditApplications(@PathVariable String tenantId, @PathVariable String divisionId) {
    int totalRecords = 0;
    if (totalRecords == 0) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity<>(new CreditApplicationsResponse(), HttpStatus.OK);
  }

  @ApiOperation(value = "Retrieves a particular credit application of a tenant",
      notes = "Use this API to retrieve a particular credit application of a tenant",
      tags = "Credit Applications")
  @RequestMapping(value = "/{tenantId}/creditapplications/{applicationId}",
      method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Retrieves a particular credit application", response = CreditApplicationsResponse.class),
      @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
      @ApiResponse(code = 404, message = "Credit application not found"),
      @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class)})
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<?> getTenantCreditApplication(@PathVariable String tenantId, @PathVariable String applicationId) {
    boolean found = true;
    if (!found) {
      throw new ResourceNotFoundException("Credit application " + applicationId + " not found");
    }
    return new ResponseEntity<>(new CreditApplicationsResponse(), HttpStatus.OK);
  }

  @ApiOperation(value = "Retrieves a particular credit application of a tenant division",
      notes = "Use this API to retrieve a particular credit application of a tenant division",
      tags = "Credit Applications")
  @RequestMapping(value = "/{tenantId}/{divisionId}/creditapplications/{applicationId}",
      method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Retrieves a particular credit application", response = CreditApplicationsResponse.class),
      @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
      @ApiResponse(code = 404, message = "Credit application not found"),
      @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class)})
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<?> getTenantDivisonCreditApplication(@PathVariable String tenantId, @PathVariable String divisionId,
      @PathVariable String applicationId) {
    boolean found = true;
    if (!found) {
      throw new ResourceNotFoundException("Credit application " + applicationId + " not found");
    }
    return new ResponseEntity<>(new CreditApplicationsResponse(), HttpStatus.OK);
  }

  @ApiOperation(value = "Creates new credit application for a tenant",
      notes = "Use this API to create a new credit application for a tenant",
      tags = "Credit Applications")
  @RequestMapping(value = "/{tenantId}/creditapplications",
      method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ApiResponses(value = {@ApiResponse(code = 201, message = "Credit application created"),
      @ApiResponse(code = 409, message = "Conflict - Application exists", response = ErrorResponse.class),
      @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
      @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class)})
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<?> createTenantCreditApplication(@PathVariable String tenantId) {
    boolean found = true;
    if (found) {
      throw new ResourceExistsException("Application exists");
    }
    long applicationId = 0;
    URI location = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{applicationId}")
        .buildAndExpand(applicationId)
        .toUri();
    return ResponseEntity.created(location).build();
  }

  @ApiOperation(value = "Creates new credit application for a tenant division",
      notes = "Use this API to create a new credit application for a tenant division",
      tags = "Credit Applications")
  @RequestMapping(value = "/{tenantId}/{divisionId}/creditapplications",
      method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ApiResponses(value = {@ApiResponse(code = 201, message = "Credit application created"),
      @ApiResponse(code = 409, message = "Conflict - Application exists", response = ErrorResponse.class),
      @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
      @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class)})
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<?> createTenantDivisionCreditApplication(@PathVariable String tenantId, @PathVariable String divisionId) {
    boolean found = true;
    if (found) {
      throw new ResourceExistsException("Application exists");
    }
    long applicationId = 0;
    URI location = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{applicationId}")
        .buildAndExpand(applicationId)
        .toUri();
    return ResponseEntity.created(location).build();
  }

  @ApiOperation(value = "Updates a particular credit applications of a tenant",
      notes = "Use this API to update a particular credit applications of a tenant",
      tags = "Credit Applications")
  @RequestMapping(value = "/{tenantId}/creditapplications/{applicationId}",
      method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Credit application updated", response = CreditApplicationsResponse.class),
      @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
      @ApiResponse(code = 404, message = "Credit application not found"),
      @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class)})
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<?> updateTenantCreditApplication(@PathVariable String tenantId, @PathVariable String applicationId) {
    boolean found = true;
    if (!found) {
      throw new ResourceNotFoundException("Credit application " + applicationId + " not found");
    }
    return new ResponseEntity<>(new CreditApplicationsResponse(), HttpStatus.OK);
  }

  @ApiOperation(value = "Updates a particular credit applications of a tenant division",
      notes = "Use this API to update a particular credit applications of a tenant division",
      tags = "Credit Applications")
  @RequestMapping(value = "/{tenantId}/{divisionId}/creditapplications/{applicationId}",
      method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Credit application updated", response = CreditApplicationsResponse.class),
      @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
      @ApiResponse(code = 404, message = "Credit application not found"),
      @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class)})
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<?> updateTenantDivisionCreditApplication(@PathVariable String tenantId, @PathVariable String divisionId,
      @PathVariable String applicationId) {
    boolean found = true;
    if (!found) {
      throw new ResourceNotFoundException("Credit application " + applicationId + " not found");
    }
    return new ResponseEntity<>(new CreditApplicationsResponse(), HttpStatus.OK);
  }

  @ApiOperation(value = "Deletes a particular credit applications of a tenant",
      notes = "Use this API to delete a particular credit applications of a tenant",
      tags = "Credit Applications")
  @RequestMapping(value = "/{tenantId}/creditapplications/{applicationId}",
      method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ApiResponses(value = {@ApiResponse(code = 204, message = "Credit application deleted"),
      @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
      @ApiResponse(code = 404, message = "Credit application not found"),
      @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class)})
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public ResponseEntity<?> deleteTenantCreditApplication(@PathVariable String tenantId, @PathVariable String applicationId) {
    boolean found = true;
    if (!found) {
      throw new ResourceNotFoundException("Credit application " + applicationId + " not found");
    }
    return new ResponseEntity<>(new CreditApplicationsResponse(), HttpStatus.NO_CONTENT);
  }

  @ApiOperation(value = "Deletes a particular credit applications of a tenant division",
      notes = "Use this API to delete a particular credit applications of a tenant  division",
      tags = "Credit Applications")
  @RequestMapping(value = "/{tenantId}/{divisionId}/creditapplications/{applicationId}",
      method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ApiResponses(value = {@ApiResponse(code = 204, message = "Credit application deleted"),
      @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
      @ApiResponse(code = 404, message = "Credit application not found"),
      @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class)})
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public ResponseEntity<?> deleteTenantDivisionCreditApplication(@PathVariable String tenantId, @PathVariable String applicationId) {
    boolean found = true;
    if (!found) {
      throw new ResourceNotFoundException("Credit application " + applicationId + " not found");
    }
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
  
}

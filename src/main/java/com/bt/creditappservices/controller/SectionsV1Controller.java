package com.bt.creditappservices.controller;

import com.bt.creditappservices.model.SectionEntity;
import com.bt.creditappservices.model.request.SectionsRequest;
import com.bt.creditappservices.model.response.ErrorResponse;
import com.bt.creditappservices.model.response.SectionsResponse;
import com.bt.creditappservices.model.response.TenantContractResponse;
import com.bt.creditappservices.service.SectionsService;
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
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/creditapp")
public class SectionsV1Controller {

  @Autowired
  SectionsService service;

  @ApiOperation(value = "Retrieves all contract details for internal testing",
      notes = "Use this API to retrieve tenant contract details for internal  testing",
      tags = "Sections", position = 0)
  @RequestMapping(value = "/sections",
      method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Sections details", response = TenantContractResponse.class),
      @ApiResponse(code = 204, message = "Sections not found"),
      @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
      @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class)})
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<?> listAllSections() {
    List<SectionEntity> entity = service.listSections();
    if (null == entity || entity.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity<>(entity, HttpStatus.OK);
  }

  @ApiOperation(value = "Retrieves all sections of a tenant",
      notes = "Use this API to retrieve all sections details of a tenant",
      tags = "Sections", position = 1)
  @RequestMapping(value = "/{tenantId}/sections",
      method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ApiResponses(value = {@ApiResponse(code = 200, message = "List of sections", response = SectionsResponse.class),
      @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
      @ApiResponse(code = 204, message = "Sections not found"),
      @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class)})
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<?> listTenantSections(@PathVariable String tenantId) {
    List<SectionEntity> sections = service.listTenantSections(tenantId);
    if (sections.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity<>(sections, HttpStatus.OK);
  }

  @ApiOperation(value = "Retrieves particular section of a tenant",
      notes = "Use the sections API to retrieve section of a tenant",
      tags = "Sections", position = 2)
  @RequestMapping(value = "/{tenantId}/sections/{id}",
      method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Retrieves section details", response = SectionsResponse.class),
      @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
      @ApiResponse(code = 404, message = "Section not found"),
      @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class)})
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<?> getTenantSection(@PathVariable String tenantId, @PathVariable UUID id) {
    return new ResponseEntity<>(service.getTenantSection(tenantId, id), HttpStatus.OK);
  }

  @ApiOperation(value = "Creates a new section for a tenant",
      notes = "Use this  API to create a new section for a tenant",
      tags = "Sections", position = 3)
  @RequestMapping(value = "/{tenantId}/sections",
      method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ApiResponses(value = {@ApiResponse(code = 201, message = "section created"),
      @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
      @ApiResponse(code = 409, message = "Conflict - section Exists"),
      @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class)})
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<?> createTenantSection(@PathVariable String tenantId, @RequestBody SectionsRequest sectionsRequest) {
    sectionsRequest.setTenantId(tenantId);
    sectionsRequest.setUserName("test1234");
    String sectionId = service.createSection(sectionsRequest);
    URI location = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{sectionId}")
        .buildAndExpand(sectionId)
        .toUri();
    return ResponseEntity.created(location).build();
  }

  @ApiOperation(value = "Updates details of a particular section of a tenant",
      notes = "Use this API to update details for a particular section of a tenant",
      tags = "Sections", position = 4)
  @RequestMapping(value = "/{tenantId}/sections/{id}",
      method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ApiResponses(value = {@ApiResponse(code = 200, message = "section details updated", response = SectionsResponse.class),
      @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
      @ApiResponse(code = 404, message = "section not found"),
      @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class)})
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<?> updateTenantsection(@PathVariable String tenantId, @PathVariable UUID id,
      @RequestBody SectionsRequest sectionsRequest) {
    sectionsRequest.setTenantId(tenantId);
    sectionsRequest.setUserName("test1234");
    return new ResponseEntity<>(service.updateSection(id, sectionsRequest), HttpStatus.OK);
  }

  @ApiOperation(value = "Deletes a particular section of a tenant",
      notes = "Use this API to delete a particular section of a tenant",
      tags = "Sections", position = 5)
  @RequestMapping(value = "/{tenantId}/sections/{id}",
      method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ApiResponses(value = {
      @ApiResponse(code = 204, message = "Tenant section deleted"),
      @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
      @ApiResponse(code = 404, message = "section not found"),
      @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class)})
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public ResponseEntity<?> deleteTenantsection(@PathVariable String tenantId, @PathVariable UUID id) {
    service.deleteTenantSection(tenantId, id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
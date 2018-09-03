package com.bt.creditappservices.controller;

import com.bt.creditappservices.exceptions.ResourceExistsException;
import com.bt.creditappservices.exceptions.ResourceNotFoundException;
import com.bt.creditappservices.model.FieldEntity;
import com.bt.creditappservices.model.request.FieldRequest;
import com.bt.creditappservices.model.response.ErrorResponse;
import com.bt.creditappservices.model.response.FieldsResponse;
import com.bt.creditappservices.service.FieldService;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * @author mvishwanath
 */
@RestController
@RequestMapping("/v1/creditapp")
public class FieldV1Controller {

    @Autowired
    FieldService service;

    @ApiOperation(value = "Retrieves all fields",
        notes = "Use this API to retrieve all fields",
        tags = "Fields")
    @ApiModelProperty(position = 0)
    @GetMapping(value="/fields", produces = "application/json")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "List of fields", response = FieldsResponse.class),
        @ApiResponse(code = 204, message = "Fields not found"),
        @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class)})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> listFields() {
        List<FieldEntity> entity = service.getAllFields();
        if (null == entity || entity.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    @ApiOperation(value = "Retrieves all fields of a tenant",
        notes = "Use this API to retrieve all fields of a tenant",
        tags = "Fields")
    @ApiModelProperty(position = 0)
    @GetMapping(value="/{tenantId}/fields", produces = "application/json")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "List of fields", response = FieldsResponse.class),
        @ApiResponse(code = 204, message = "Fields not found"),
        @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class)})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> listTenantFields(@PathVariable String tenantId) {
        int totalContracts = 0;
        if (totalContracts == 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(new FieldsResponse(), HttpStatus.OK);
    }

    @ApiOperation(value = "Retrieves particular field details of a tenant",
        notes = "Use this API to retrieve field details of a tenant",
        tags = "Fields")
    @ApiModelProperty(position = 4)
    @GetMapping(value="/{tenantId}/fields/{fieldId}", produces = "application/json")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Retrieves field details", response = FieldsResponse.class),
        @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Field not found"),
        @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class)})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getTenantField(@PathVariable String tenantId, @PathVariable String fieldId) {
        boolean found = true;
        if (!found) {
            throw new ResourceNotFoundException("FieldId " + fieldId + " not found");
        }
        return new ResponseEntity<>(new FieldsResponse(), HttpStatus.OK);
    }

    @ApiOperation(value = "Creates a new field for a tenant",
        notes = "Use this  API to create a new field for a tenant",
        tags = "Fields")
    @PostMapping(value="/{tenantId}/fields", produces = "application/json")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Created new field for a tenant"),
        @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
        @ApiResponse(code = 409, message = "Conflict - Field exists"),
        @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class)})
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createTenantField(@PathVariable String tenantId, @RequestBody FieldRequest fieldsRequest) {
        boolean found = true;
        if (found) {
            throw new ResourceExistsException("Field already exits");
        }
        long fieldId = 0;
        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{fieldId}")
            .buildAndExpand(fieldId)
            .toUri();
        return ResponseEntity.created(location).build();
    }

    @ApiOperation(value = "Updates details of a particular field of a tenant",
        notes = "Use this API to update details for a particular field of a tenant",
        tags = "Fields")
    @PutMapping(value="/{tenantId}/fields/{fieldId}", produces = "application/json")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Field details updated", response = FieldsResponse.class),
        @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Field not found"),
        @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class)})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> updateTenantField(@PathVariable String tenantId, @PathVariable String fieldId,
        @RequestBody FieldRequest fieldsRequest) {
        boolean found = true;
        if (!found) {
            throw new ResourceNotFoundException("FieldId " + fieldId + " not found");
        }
        return new ResponseEntity<>(new FieldsResponse(), HttpStatus.OK);
    }

    @ApiOperation(value = "Deletes a particular field of a tenant",
        notes = "Use this API to delete a particular  of a tenant",
        tags = "Fields")
    @DeleteMapping(value ="/{tenantId}/fields/{fieldId}", produces = "application/json")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "Field deleted"),
        @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Field not found"),
        @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class)})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> deleteTenantField(@PathVariable String tenantId, @PathVariable String fieldId) {
        boolean found = true;
        if (!found) {
            throw new ResourceNotFoundException("FieldId " + fieldId + " not found");
        }
        return new ResponseEntity<>(new FieldsResponse(), HttpStatus.NOT_FOUND);
    }
}

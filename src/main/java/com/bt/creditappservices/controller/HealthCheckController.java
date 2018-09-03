package com.bt.creditappservices.controller;

import com.bt.creditappservices.model.response.ErrorResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author msundara
 */
@RestController
@RequestMapping("/v1")
public class HealthCheckController {

  @ApiOperation(value = "Returns OK if the service is up",
      tags = "Service Health Check", position = 0)
  @RequestMapping(value = "/up",
      method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Returns OK if the service is up", response = String.class),
      @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
      @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class)})
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<?> up() {
    return new ResponseEntity<>("OK", HttpStatus.OK);
  }

  @ApiOperation(value = "Returns OK if the service is healthy",
      tags = "Service Health Check", position = 0)
  @RequestMapping(value = "/health",
      method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Returns OK if the service is healthy", response = String.class),
      @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
      @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class)})
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<?> health() {
    return new ResponseEntity<>("HEALTHY", HttpStatus.OK);
  }

  @ApiOperation(value = "Returns OK if the service is fready",
      tags = "Service Health Check", position = 0)
  @RequestMapping(value = "/ready",
      method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Returns OK if the service is ready", response = String.class),
      @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
      @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class)})
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<?> ready() {
    return new ResponseEntity<>("READY", HttpStatus.OK);
  }
}

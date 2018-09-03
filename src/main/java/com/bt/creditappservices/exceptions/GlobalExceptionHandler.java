package com.bt.creditappservices.exceptions;

import com.bt.creditappservices.model.common.Error;
import com.bt.creditappservices.model.response.ErrorResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * @author msundara
 */
@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
    List<Error> errors = new ArrayList<>();
    for (ObjectError error : ex.getBindingResult().getAllErrors()) {
      Error currentError = new Error();
      currentError.setErrorType("Request Error");
      currentError.setMessage(error.getDefaultMessage());
      errors.add(currentError);
    }
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setErrors(errors);
    return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
    List<Error> errors = new ArrayList<>();
    for (ConstraintViolation error : ex.getConstraintViolations()) {
      Error currentError = new Error();
      currentError.setErrorType("Request Error");
      currentError.setMessage(error.getMessage());
      errors.add(currentError);
    }
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setErrors(errors);
    return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ResourceExistsException.class)
  @ResponseBody
  @ResponseStatus(HttpStatus.CONFLICT)
  public ResponseEntity<ErrorResponse> handleResourceExistsException(ResourceExistsException e) {
    Error error = new Error();
    error.setErrorType("Record already exists in data store");
    error.setMessage(e.getMessage());
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setErrors(Arrays.asList(error));
    return new ResponseEntity(errorResponse, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  @ResponseBody
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<ErrorResponse> handleRecordNotFoundException(ResourceNotFoundException e) {
    Error error = new Error();
    error.setErrorType("Record Not Found");
    error.setMessage(e.getMessage());
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setErrors(Arrays.asList(error));
    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  @ResponseBody
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
    e.printStackTrace();
    Error error = new Error();
    error.setErrorType("Internal Error");
    error.setMessage("Enter a valid Id");
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setErrors(Arrays.asList(error));
    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(Exception.class)
  @ResponseBody
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<ErrorResponse> handleGenericException(Exception e) {
    e.printStackTrace();
    Error error = new Error();
    error.setErrorType("Internal Error");
    error.setMessage(e.getMessage());
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setErrors(Arrays.asList(error));
    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }

}

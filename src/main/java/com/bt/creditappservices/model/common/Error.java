package com.bt.creditappservices.model.common;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author msundara
 */
public class Error {

  @ApiModelProperty(notes = "Error type", example = "Request error", position = 1)
  private String errorType;

  @ApiModelProperty(notes = "Error message", example = "User ID is required", position = 2)
  private String message;

  public String getErrorType() {
    return errorType;
  }

  public void setErrorType(String errorType) {
    this.errorType = errorType;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}

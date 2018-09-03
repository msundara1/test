package com.bt.creditappservices.model.response;

import com.bt.creditappservices.model.common.Error;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;

/**
 * @author msundara
 */
public class ErrorResponse {

  @ApiModelProperty(notes = "Status", example = "false", required = true, position = 0)
  private boolean success = false;

  @ApiModelProperty(notes = "List of errors", required = true, position = 1)
  List<Error> errors;

  public List<Error> getErrors() {
    return errors;
  }

  public void setErrors(List<Error> errors) {
    this.errors = errors;
  }
}

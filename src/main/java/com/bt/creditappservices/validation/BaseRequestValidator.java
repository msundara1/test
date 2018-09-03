package com.bt.creditappservices.validation;

import com.bt.creditappservices.model.request.BaseRequest;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author msundara
 */
public class BaseRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return BaseRequestValidator.class.isAssignableFrom(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    BaseRequest request = (BaseRequest) target;
  }
}

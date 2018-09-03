package com.bt.creditappservices.validation;

import com.bt.creditappservices.model.request.TenantContractMappingRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

/**
 * @author msundara
 */
@Component
public class TenantContractRequestValidator extends BaseRequestValidator {

  @Override
  public boolean supports(Class<?> clazz) {
    return TenantContractMappingRequest.class.isAssignableFrom(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    super.validate(target, errors);
  }
}

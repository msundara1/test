package com.bt.creditappservices.validation;

import com.bt.creditappservices.model.request.DivisionsRequest;
import com.bt.creditappservices.model.request.TenantContractMappingRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

/**
 * @author mvishwanath
 */
@Component
public class DivisionRequestValidator extends BaseRequestValidator {

    @Override
    public boolean supports(Class<?> clazz) {
        return DivisionsRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        super.validate(target, errors);

        DivisionsRequest request = (DivisionsRequest) target;
    }
}

package com.bt.creditappservices.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author msundara
 */
public class TenantContractResponse extends BaseResponse {

  @ApiModelProperty(notes = "Company id", example = "1", required = true, position = 1)
  @JsonPropertyDescription("Company id")
  @JsonProperty(required = true, defaultValue = "1", index = 1)
  private String companyId;

  @ApiModelProperty(notes = "Contract id", example = "1", required = true, position = 2)
  @JsonPropertyDescription("Contract id")
  @JsonProperty(required = true, defaultValue = "1", index = 2)
  private long contractId;

  public String getCompanyId() {
    return companyId;
  }

  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }

  public long getContractId() {
    return contractId;
  }

  public void setContractId(long contractId) {
    this.contractId = contractId;
  }
}

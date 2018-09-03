package com.bt.creditappservices.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author msundara
 */
public class TenantContractMappingRequest extends BaseRequest {

  @ApiModelProperty(notes = "Client Id", example = "123456", required = true, position = 0)
  @JsonPropertyDescription("Client Id")
  @JsonProperty(required = true, defaultValue = "123456", index = 0)
  private long clientId;

  @ApiModelProperty(notes = "Contract Id", example = "123456", required = true, position = 0)
  @JsonPropertyDescription("Contract Id")
  @JsonProperty(required = true, defaultValue = "123456", index = 0)
  private long contractId;

  public long getClientId() {
    return clientId;
  }

  public void setClientId(long clientId) {
    this.clientId = clientId;
  }

  public long getContractId() {
    return contractId;
  }

  public void setContractId(long contractId) {
    this.contractId = contractId;
  }
}

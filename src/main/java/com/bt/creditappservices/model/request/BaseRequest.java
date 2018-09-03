package com.bt.creditappservices.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author msundara
 */
public class BaseRequest {

  @ApiModelProperty(notes = "Tenant Id", required = true, position = 0, hidden = true)
  @JsonPropertyDescription("Tenant Id")
  @JsonProperty(required = true, defaultValue = "", index = 0)
  private String tenantId;

  @ApiModelProperty(notes = "User name", required = true, position = 0, hidden = true)
  @JsonPropertyDescription("User name")
  @JsonProperty(required = true, defaultValue = "", index = 0)
  private String userName;

  public String getTenantId() {
    return tenantId;
  }

  public void setTenantId(String tenantId) {
    this.tenantId = tenantId;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }
}

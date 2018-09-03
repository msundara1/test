package com.bt.creditappservices.model.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

/**
 * @author msundara
 */
public class BaseEntity {

  @JsonPropertyDescription("User id")
  @JsonProperty(required = true, index = 0)
  private String userId;

  @JsonPropertyDescription("Tenant id")
  @JsonProperty(required = true, index = 1)
  private String tenantId;

  @JsonPropertyDescription("company id")
  @JsonProperty(required = true, defaultValue = "0", index = 2)
  private String companyId;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getTenantId() {
    return tenantId;
  }

  public void setTenantId(String tenantId) {
    this.tenantId = tenantId;
  }

  public String getCompanyId() {
    return companyId;
  }

  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }
}

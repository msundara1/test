package com.bt.creditappservices.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author msundara
 */
public class SectionsRequest extends BaseRequest {

  @ApiModelProperty(notes = "Section name", example = "Company", required = true, position = 0)
  @JsonPropertyDescription("Section name")
  @JsonProperty(required = true, defaultValue = "", index = 0)
  private String name;

  @ApiModelProperty(notes = "Section display name", example = "Company details", required = true, position = 1)
  @JsonPropertyDescription("Section display name")
  @JsonProperty(required = false, defaultValue = "", index = 1)
  private String displayName;

  @ApiModelProperty(notes = "Section description", example = "Company basic details", required = true, position = 2)
  @JsonPropertyDescription("Section description")
  @JsonProperty(required = false, defaultValue = "", index = 2)
  private String description;

  @ApiModelProperty(notes = "is active", example = "true", required = true, position = 3)
  @JsonPropertyDescription("is active")
  @JsonProperty(required = false, defaultValue = "true", index = 3)
  private boolean isActive;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public boolean getIsActive() {
    return isActive;
  }

  public void setIsActive(boolean isActive) {
    this.isActive = isActive;
  }
}

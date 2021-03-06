package com.bt.creditappservices.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author mvishwanath
 */
public class FieldRequest extends BaseRequest {

    @ApiModelProperty(notes = "Field name", example = "firstName", required = true, position = 0)
    @JsonPropertyDescription("Field name")
    @JsonProperty(required = true, defaultValue = "", index = 0)
    private String name;

    @ApiModelProperty(notes = "Field display name", example = "First Name", required = true, position = 1)
    @JsonPropertyDescription("Field display name")
    @JsonProperty(required = false, defaultValue = "", index = 1)
    private String displayName;

    @ApiModelProperty(notes = "Field description", example = "First Name", required = true, position = 2)
    @JsonPropertyDescription("Field description")
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

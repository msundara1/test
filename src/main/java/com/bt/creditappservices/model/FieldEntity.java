package com.bt.creditappservices.model;

import com.bt.creditappservices.model.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

/**
 * @author msundara
 */
@Entity
@Table(name = "field")
@JsonIgnoreProperties(value = {"createdon", "lastModifiedon", "position"},
    allowGetters = true)
public class FieldEntity extends BaseEntity {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "fieldkey", columnDefinition = "BINARY(16)")
  private java.util.UUID fieldKey;

  @Column(name = "parentfieldkey")
  private UUID parentFieldKey;

  @NotBlank
  @Column(name = "name")
  private String name;

  @NotBlank
  @Column(name = "displayname")
  private String displayName;

  @NotBlank
  @Column(name = "description")
  private String description;

  @Column(name = "isactive")
  private boolean isActive = true;

  @NotBlank
  @Column(name = "createdby")
  private String createdBy;

  @Column(name = "createdon", nullable = true, updatable = false)
  @Temporal(TemporalType.TIMESTAMP)
  @CreatedDate
  private Date createdOn;

  @NotBlank
  @Column(name = "lastmodifiedby")
  private String lastModifiedBy;

  @Column(name = "lastmodifiedon", nullable = true, updatable = false)
  @Temporal(TemporalType.TIMESTAMP)
  @LastModifiedDate
  private Date lastModifiedOn;

  @Column(name = "position", nullable = true)
  private String position;

  @Column(name = "value", nullable = true)
  private String value;

  @Column(name = "defaultValue", nullable = true)
  private String defaultValue;

  @Column(name = "format", nullable = true)
  private String format;

  @Column(name = "rules", nullable = true)
  private String rules;

  @Column(name = "documents", nullable = true)
  private String documents;

  @Column(name = "isoptional", nullable = true)
  private String isOptional;

  @Column(name = "isvisible", nullable = true)
  private String isVisible;

  @Column(name = "fieldkey", nullable = true)
  public UUID getFieldKey() {
    return fieldKey;
  }

  public void setFieldKey(UUID fieldKey) {
    this.fieldKey = fieldKey;
  }

  public UUID getParentFieldKey() {
    return parentFieldKey;
  }

  public void setParentFieldKey(UUID parentFieldKey) {
    this.parentFieldKey = parentFieldKey;
  }

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
    isActive = isActive;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public Date getCreatedOn() {
    return createdOn;
  }

  public void setCreatedOn(Date createdOn) {
    this.createdOn = createdOn;
  }

  public String getLastModifiedBy() {
    return lastModifiedBy;
  }

  public void setLastModifiedBy(String lastModifiedBy) {
    this.lastModifiedBy = lastModifiedBy;
  }

  public Date getLastModifiedOn() {
    return lastModifiedOn;
  }

  public void setLastModifiedOn(Date lastModifiedOn) {
    this.lastModifiedOn = lastModifiedOn;
  }

  public String getPosition() {
    return position;
  }

  public void setPosition(String position) {
    this.position = position;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getDefaultValue() {
    return defaultValue;
  }

  public void setDefaultValue(String defaultValue) {
    this.defaultValue = defaultValue;
  }

  public String getFormat() {
    return format;
  }

  public void setFormat(String format) {
    this.format = format;
  }

  public String getRules() {
    return rules;
  }

  public void setRules(String rules) {
    this.rules = rules;
  }

  public String getDocuments() {
    return documents;
  }

  public void setDocuments(String documents) {
    this.documents = documents;
  }

  public String getIsOptional() {
    return isOptional;
  }

  public void setIsOptional(String isOptional) {
    this.isOptional = isOptional;
  }

  public String getIsVisible() {
    return isVisible;
  }

  public void setIsVisible(String isVisible) {
    this.isVisible = isVisible;
  }
}

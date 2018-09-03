package com.bt.creditappservices.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
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
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

/**
 * @author msundara
 */
@Entity
@Table(name = "Section")
@JsonIgnoreProperties(value = {"createdDate", "lastModifiedDate"},
    allowGetters = true)
public class SectionEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "sectionKey", columnDefinition = "BINARY(16)")
  private UUID sectionKey;

  @NotBlank
  @Column(name = "tenantId")
  private String tenantId;

  @Column(name = "parentSectionKey")
  private UUID parentSectionKey;

  @NotBlank
  @Column(name = "name")
  private String name;

  @NotBlank
  @Column(name = "displayName")
  private String displayName;

  @NotBlank
  @Column(name = "description")
  private String description;

  @Column(name = "isActive")
  private boolean isActive = true;

  @NotBlank
  @Column(name = "createdBy")
  private String createdBy;

  @Column(name = "createdDate", nullable = true, updatable = false)
  @Temporal(TemporalType.TIMESTAMP)
  @CreatedDate
  private Date createdDate;

  @NotBlank
  @Column(name = "lastModifiedBy")
  private String lastModifiedBy;

  @Column(name = "lastModifiedDate", nullable = true, updatable = false)
  @Temporal(TemporalType.TIMESTAMP)
  @LastModifiedDate
  private Date lastModifiedDate;

  public UUID getSectionKey() {
    return sectionKey;
  }

  public String getTenantId() {
    return tenantId;
  }

  public void setTenantId(String tenantId) {
    this.tenantId = tenantId;
  }

  public UUID getParentSectionKey() {
    return parentSectionKey;
  }

  public void setParentSectionKey(UUID parentSectionKey) {
    this.parentSectionKey = parentSectionKey;
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

  public boolean isActive() {
    return isActive;
  }

  public void setIsActive(boolean isActive) {
    this.isActive = isActive;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public Date getCreatedDate() {
    return createdDate;
  }

  public String getLastModifiedBy() {
    return lastModifiedBy;
  }

  public void setLastModifiedBy(String lastModifiedBy) {
    this.lastModifiedBy = lastModifiedBy;
  }

  public Date getLastModifiedDate() {
    return lastModifiedDate;
  }
}

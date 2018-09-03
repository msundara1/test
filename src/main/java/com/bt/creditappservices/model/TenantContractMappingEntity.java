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
@Table(name = "TenantContract")
@JsonIgnoreProperties(value = {"createdDate", "lastModifiedDate"},
    allowGetters = true)
public class TenantContractMappingEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "tenantContractKey", columnDefinition = "BINARY(16)")
  private UUID id;

  @NotBlank
  @Column(name = "tenantId")
  private String tenantId;

  @Column(name = "clientId")
  private long clientId;

  @Column(name = "contractId")
  private long contractId;

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

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getTenantId() {
    return tenantId;
  }

  public void setTenantId(String tenantId) {
    this.tenantId = tenantId;
  }

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
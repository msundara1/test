package com.bt.creditappservices.model;

import com.bt.creditappservices.model.common.BaseEntity;

/**
 * @author msundara
 */
public class DocumentEntity extends BaseEntity {

  private String documentId;

  private String name;

  public String getDocumentId() {
    return documentId;
  }

  public void setDocumentId(String documentId) {
    this.documentId = documentId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}

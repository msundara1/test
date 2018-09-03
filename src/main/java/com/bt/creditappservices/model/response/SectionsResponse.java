package com.bt.creditappservices.model.response;

import com.bt.creditappservices.model.DivisionEntity;
import com.bt.creditappservices.model.SectionEntity;
import java.util.List;

/**
 * @author msundara
 */
public class SectionsResponse  extends BaseResponse {

  List<SectionEntity> sectionEntityList;

  public List<SectionEntity> getSectionEntityList() {
    return sectionEntityList;
  }

  public void setSectionEntityList(List<SectionEntity> sectionEntityList) {
    this.sectionEntityList = sectionEntityList;
  }
}

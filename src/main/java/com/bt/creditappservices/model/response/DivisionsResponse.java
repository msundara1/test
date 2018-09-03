package com.bt.creditappservices.model.response;

import com.bt.creditappservices.model.DivisionEntity;
import java.util.List;

/**
 * @author msundara
 */
public class DivisionsResponse extends BaseResponse {

  List<DivisionEntity> divisionEntityList;

  public List<DivisionEntity> getDivisionEntityList() {
    return divisionEntityList;
  }

  public void setDivisionEntityList(List<DivisionEntity> divisionEntityList) {
    this.divisionEntityList = divisionEntityList;
  }
}

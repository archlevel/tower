package com.tower.service.dao;

public class Page<T> {

  private Integer pageIndex=1;
  private Integer pageSize;
  private Integer start;
  private Integer end;
  private T params;
  private String orders;
  /**
   * 获取页面序号 从1开始
   * @return
   */
  public Integer getPageIndex() {
    return pageIndex;
  }

  public void setPageIndex(Integer pageIndex) {
    this.pageIndex = pageIndex;
  }
  public Integer getPageSize() {
    return pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  public Integer getStart() {
    return start;
  }

  public void setStart(Integer start) {
    this.start = start;
  }

  public Integer getEnd() {
    return end;
  }

  public void setEnd(Integer end) {
    this.end = end;
  }

  public T getParams() {
    return params;
  }

  public void setParams(T params) {
    this.params = params;
  }

  public String getOrders() {
    return orders;
  }

  public void setOrders(String orders) {
    this.orders = orders;
  }
}

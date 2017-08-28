package com.tower.service.domain;

import java.util.List;

public class PageResult<T extends IResult> extends AbsResult implements IResult {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6656398713432429279L;

	private int total = 0;// 总记录数
	private int index = 1;// 当前页,默认查询时为 1
	private int size = 100; // 每页显示记录数
	private int count = 0; // 总页数
	private List<T> result;

	private boolean isFirst = false; // 是否为第一页
	private boolean isLast = false; // 是否为最后一页
	private boolean hasPrevious = false; // 是否有前一页
	private boolean hasNext = false; // 是否有下一页

	public PageResult(int pageIndex, int pageSize) {
		this.index = pageIndex;
		this.size = pageSize;
	}

	public PageResult(int pageIndex, int pageSize, List<T> result) {
		this.index = pageIndex;
		this.size = pageSize;
		this.result = result;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getPageIndex() {
		return index;
	}

	public void setPageIndex(int pageIndex) {
		this.index = pageIndex;
	}

	public int getPageSize() {
		return size;
	}

	public void setPageSize(int pageSize) {
		this.size = pageSize;
	}

	public int getPageCount() {
		return count;
	}

	public void setPageCount(int pageCount) {
		this.count = pageCount;
	}

	public List<T> getResult() {
		return result;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}

	public boolean isFirstPage() {
		return isFirst;
	}

	public void setFirstPage(boolean isFirstPage) {
		this.isFirst = isFirstPage;
	}

	public boolean isLastPage() {
		return isLast;
	}

	public void setLastPage(boolean isLastPage) {
		this.isLast = isLastPage;
	}

	public boolean isHasPreviousPage() {
		return hasPrevious;
	}

	public void setHasPreviousPage(boolean hasPreviousPage) {
		this.hasPrevious = hasPreviousPage;
	}

	public boolean isHasNextPage() {
		return hasNext;
	}

	public void setHasNextPage(boolean hasNextPage) {
		this.hasNext = hasNextPage;
	}

}

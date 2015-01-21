package com.easytoolsoft.easyreport.data;

public class PageInfo {
	private long totals = 0;
	private int startIndex = 0;
	private int pageSize = 50;
	private String sortItem;
	private String sortType;

	public PageInfo() {
	}

	/**
	 *
	 * @param startIndex
	 * @param pageSize
	 */
	public PageInfo(int startIndex, int pageSize) {
		this(startIndex, pageSize, "", "asc");
	}

	/**
	 *
	 * @param startIndex
	 * @param pageSize
	 * @param sortItem
	 */
	public PageInfo(int startIndex, int pageSize, String sortItem) {
		this(startIndex, pageSize, sortItem, "asc");
	}

	/**
	 * @param startIndex
	 * @param pageSize
	 * @param sortItem
	 * @param sortType (asc|desc)
	 */
	public PageInfo(int startIndex, int pageSize, String sortItem, String sortType) {
		this.startIndex = startIndex;
		this.pageSize = pageSize;
		this.sortItem = sortItem;
		this.sortType = sortType;
	}

	public int getPageSize() {
		return this.pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getSortItem() {
		return sortItem;
	}

	public void setSortItem(String sortItem) {
		this.sortItem = sortItem;
	}

	public String getSortType() {
		return sortType;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public long getTotals() {
		return this.totals;
	}

	public void setTotals(long totals) {
		this.totals = totals;
	}
}

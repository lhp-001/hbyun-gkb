package com.huabo.gkb.entity;

import java.util.List;

public class PageInfo<T> {
	
	private Integer currentPage;
	private Integer currentRecord;
	private T condition;
	private List<T> tlist;
	private Integer totalPage;
	private Integer totalRecord;
	private Integer pageSize = 10;
	
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	public Integer getCurrentRecord() {
		this.currentRecord = (this.currentPage-1)*pageSize;
		return currentRecord;
	}
	public T getCondition() {
		return condition;
	}
	public void setCondition(T condition) {
		this.condition = condition;
	}
	public List<T> getTlist() {
		return tlist;
	}
	public void setTlist(List<T> tlist) {
		this.tlist = tlist;
	}
	public Integer getTotalPage() {
		this.totalPage = this.totalRecord/this.pageSize;
		if(this.totalRecord%this.pageSize>0) {
			this.totalPage++;
		}
		return totalPage;
	}
	public Integer getTotalRecord() {
		return totalRecord;
	}
	public void setTotalRecord(Integer totalRecord) {
		this.totalRecord = totalRecord;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
}

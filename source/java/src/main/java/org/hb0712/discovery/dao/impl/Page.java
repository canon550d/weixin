package org.hb0712.discovery.dao.impl;

import java.util.List;

public class Page {
	private int previous;
	private int page=1;
	private int next;
	private int last;
	
	private int pageSize = 10;//每页大小
	private int total;//总页数
	
	List<?> list;

	public Page() {
		
	}
	
	public int getStartPosition() {
		return pageSize*(page-1);
	}
	
	public int getPrevious() {
		if(page>1) {
			previous = page-1;
		}
		return previous;
	}

	public void setPrevious(int previous) {
		this.previous = previous;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getNext() {
		if(page<1000) {
			next = page+1;
		}
		return next;
	}

	public void setNext(int next) {
		this.next = next;
	}

	public int getLast() {
		this.last = (int)Math.ceil((double)total/pageSize);
		return last;
	}

	public void setLast(int last) {
		this.last = last;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}
	
	public String toString() {
		return "page:"+this.page;
	}
}

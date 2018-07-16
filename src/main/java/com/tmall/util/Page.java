package com.tmall.util;

public class Page {
	private int start;
	private int count;
	private int total;
	private String param;
	private static final int defaultcount = 5;
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	public Page() {
		count = defaultcount;
	}
	public Page(int start, int count) {
		this();
		this.start = start;
		this.count = count;
	}
	public int getLast() {
		int last;
		if (0==total%count) {
			last = total - count;
		}else {
			last = total - total%count;
		}
		last = last<0?0:last;
		return last;
	}
	public boolean isHasPreviouse() {
		if (start==0) {
			return false;
		}
		return true;
	}
	public boolean isHasNext() {
		if (start==getLast()) {
			return false;
		}
		return true;
	}
	public int getTotalPage() {
		int totalpage;
		if (0==total%count) {
			totalpage = total/count;
		}else {
			totalpage = total/count + 1;
		}
		return totalpage==0?0:totalpage; 
	}
	@Override
	public String toString() {
		return "Page [start=" + start + ", count=" + count + ", total=" + total + ", param=" + param + ", getLast()="
				+ getLast() + ", isHasPrevious()=" + isHasPreviouse() + ", isHasNext()=" + isHasNext()
				+ ", getTotalPage()=" + getTotalPage() + "]";
	}
	
}

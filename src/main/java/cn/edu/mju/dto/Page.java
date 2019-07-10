package cn.edu.mju.dto;

import java.util.List;

public class Page<T> {

	//需要返回的数据
	private List<T> datas;
	//要开始的页数
	private int pageno;
	//总页数
	private int totalno;
	//数据库总数据大小
	private int totalsize;
	public List<T> getDatas() {
		return datas;
	}
	public void setDatas(List<T> datas) {
		this.datas = datas;
	}
	public int getPageno() {
		return pageno;
	}
	public void setPageno(int pageno) {
		this.pageno = pageno;
	}
	public int getTotalno() {
		return totalno;
	}
	public void setTotalno(int totalno) {
		this.totalno = totalno;
	}
	public int getTotalsize() {
		return totalsize;
	}
	public void setTotalsize(int totalsize) {
		this.totalsize = totalsize;
	}
	public Page() {
		super();
	}
	
	
	
	
	
}

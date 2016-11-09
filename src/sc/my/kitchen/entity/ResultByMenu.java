package sc.my.kitchen.entity;

import java.util.List;

public class ResultByMenu {

	private List<DishByName> data ;

	private String totalNum;

	private String pn;

	private String rn;

	public void setData(List<DishByName> data){
	this.data = data;
	}
	public List<DishByName> getData(){
	return this.data;
	}
	public void setTotalNum(String totalNum){
	this.totalNum = totalNum;
	}
	public String getTotalNum(){
	return this.totalNum;
	}
	public void setPn(String pn){
	this.pn = pn;
	}
	public String getPn(){
	return this.pn;
	}
	public void setRn(String rn){
	this.rn = rn;
	}
	public String getRn(){
	return this.rn;
	}
	
}

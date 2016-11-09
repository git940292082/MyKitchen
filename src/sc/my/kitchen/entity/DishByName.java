package sc.my.kitchen.entity;

import java.io.Serializable;
import java.util.List;

public class DishByName implements Serializable{

	private String id;

	private String title;

	private String tags;

	private String imtro;

	private String ingredients;

	private String burden;

	private List<String> albums ;

	private List<StepByName> steps ;

	public void setId(String id){
	this.id = id;
	}
	public String getId(){
	return this.id;
	}
	public void setTitle(String title){
	this.title = title;
	}
	public String getTitle(){
	return this.title;
	}
	public void setTags(String tags){
	this.tags = tags;
	}
	public String getTags(){
	return this.tags;
	}
	public void setImtro(String imtro){
	this.imtro = imtro;
	}
	public String getImtro(){
	return this.imtro;
	}
	public void setIngredients(String ingredients){
	this.ingredients = ingredients;
	}
	public String getIngredients(){
	return this.ingredients;
	}
	public void setBurden(String burden){
	this.burden = burden;
	}
	public String getBurden(){
	return this.burden;
	}
	public void setString(List<String> albums){
	this.albums = albums;
	}
	public List<String> getString(){
	return this.albums;
	}
	public void setSteps(List<StepByName> steps){
	this.steps = steps;
	}
	public List<StepByName> getSteps(){
	return this.steps;
	}
	
}

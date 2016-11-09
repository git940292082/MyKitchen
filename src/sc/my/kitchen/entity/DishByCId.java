package sc.my.kitchen.entity;

import java.io.Serializable;
import java.util.List;

import sc.my.kitchen.ui.SlideView;

public class DishByCId implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String title;
	private String tags;
	private String imtro;
	private String ingredients;
	private String burden;
	private String album;
	private List<StepByCId> steps;
	public SlideView slideView;
	
	public DishByCId() {
		super();
	}

	public DishByCId(String id, String title, String tags, String imtro,
			String ingredients, String burden, String album,
			List<StepByCId> steps) {
		super();
		this.id = id;
		this.title = title;
		this.tags = tags;
		this.imtro = imtro;
		this.ingredients = ingredients;
		this.burden = burden;
		this.album = album;
		this.steps = steps;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getImtro() {
		return imtro;
	}

	public void setImtro(String imtro) {
		this.imtro = imtro;
	}

	public String getIngredients() {
		return ingredients;
	}

	public void setIngredients(String ingredients) {
		this.ingredients = ingredients;
	}

	public String getBurden() {
		return burden;
	}

	public void setBurden(String burden) {
		this.burden = burden;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public List<StepByCId> getSteps() {
		return steps;
	}

	public void setSteps(List<StepByCId> steps) {
		this.steps = steps;
	}
	
	
	
}

package sc.my.kitchen.entity;

import java.io.Serializable;

public class MenuSort implements Serializable{

	private String id;
	private String name;
	private String parentId;
	
	public MenuSort() {
		super();
	}

	public MenuSort(String id, String name, String parentId) {
		super();
		this.id = id;
		this.name = name;
		this.parentId = parentId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Override
	public String toString() {
		return "MenuSort [id=" + id + ", name=" + name + ", parentId="
				+ parentId + "]";
	}
}

package sc.my.kitchen.model;


import sc.my.kitchen.app.KitchenApplication;
import sc.my.kitchen.entity.Collection;
import sc.my.kitchen.entity.DishByCId;

public class CollectionModel {

	private Collection collection;
	
	public CollectionModel() {
		collection = KitchenApplication.getCollection();
	}
	
	public boolean addDish(DishByCId dishByCId){
		return collection.add(dishByCId);
	}
	
	public void deleteDish(String id){
		collection.deleteDish(id);
	}
	
}

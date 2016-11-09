package sc.my.kitchen.entity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import sc.my.kitchen.app.KitchenApplication;

public class Collection implements Serializable{

private List<DishByCId> dishes = new ArrayList<DishByCId>();
	
	public List<DishByCId> getDishes(){
		return this.dishes;
	}

	public boolean add(DishByCId dishByCId){
		for(int i = 0; i< dishes.size(); i++){
			DishByCId dish = dishes.get(i);
			if (dish.getTitle() == dishByCId.getTitle()) {
				return false;
			}
		}
		dishes.add(dishByCId);
		saveDish();
		return true;
	}
	
	private void saveDish(){
		try {
			File file = new File(KitchenApplication.getApp().getCacheDir(), "Dishes.info");
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
			oos.writeObject(this);
			oos.flush();
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Collection readDish() {
		try {
			File file = new File(KitchenApplication.getApp().getCacheDir(), "Dishes.info");
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
			Collection collection = (Collection) ois.readObject();
			
			if(collection == null){
				return new Collection();
			}
			return collection;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new Collection();
	}
	
	public void deleteDish(String id){
		for(int i = 0; i< dishes.size(); i++){
			DishByCId dish = dishes.get(i);
			if (dish.getId().equals(id)) {
				dishes.remove(dish);
				return;
			}
		}
	}
	
}

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

public class Cart implements Serializable{
	
	private List<DishByCId> dishes = new ArrayList<DishByCId>();

	public List<DishByCId> getDishes(){
		return this.dishes;
	}

	public boolean addToCart(DishByCId dishByCId){
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
			File file = new File(KitchenApplication.getApp().getCacheDir(), "MyCartDish.info");
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
			oos.writeObject(this);
			oos.flush();
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Cart readDish() {
		try {
			File file = new File(KitchenApplication.getApp().getCacheDir(), "MyCartDish.info");
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
			Cart cart = (Cart) ois.readObject();
			
			if(cart == null){
				return new Cart();
			}
			return cart;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new Cart();
	}
	
	public void deleteDish(String id){
		for(int i = 0; i< dishes.size(); i++){
			DishByCId dish = dishes.get(i);
			if (dish.getId().equals(id)) {
				dishes.remove(dish);
			}
		}
		saveDish();
	}
	
}

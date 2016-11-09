package sc.my.kitchen.model;

import sc.my.kitchen.app.KitchenApplication;
import sc.my.kitchen.entity.Cart;
import sc.my.kitchen.entity.DishByCId;

public class CartModel {

	private Cart cart;

	public CartModel() {
		this.cart = KitchenApplication.getApp().getCart();
	}
	
	public Boolean addDishToCart(DishByCId dishByCId){
		return cart.addToCart(dishByCId);
	}
	
}

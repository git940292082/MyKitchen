package sc.my.kitchen.util;

import java.util.List;

import sc.my.kitchen.entity.DishByCId;

public interface DishByCIdCallBack {
	
	void onDishByCIdLoaded(List<DishByCId> dishes);
	
}

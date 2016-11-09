package sc.my.kitchen.util;

import java.util.List;

import sc.my.kitchen.entity.DishByCId;

public interface DishByNameCallBack {

	void onDishByNameLoaded(List<DishByCId> dish);
	
}

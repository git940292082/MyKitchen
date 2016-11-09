package sc.my.kitchen.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.util.Log;

import sc.my.kitchen.entity.DishByCId;
import sc.my.kitchen.entity.MenuSort;
import sc.my.kitchen.entity.StepByCId;
import sc.my.kitchen.entity.User;

/**
 * JSON解析类
 * @author Sheng
 *
 */
public class JSONParser {

	/**
	 * 获得分类标签列表
	 * @param sortArray
	 * @return
	 * @throws JSONException
	 */
	public static List<MenuSort> parseSortList(JSONArray sortArray) throws JSONException{
		List<MenuSort> sorts = new ArrayList<MenuSort>();
		for (int i = 0; i < sortArray.length(); i++) {
			JSONObject obj = sortArray.getJSONObject(i);
			MenuSort menuSort = new MenuSort();
			menuSort.setId(obj.getString("id"));
			menuSort.setName(obj.getString("name"));
			menuSort.setParentId(obj.getString("parentId"));
			sorts.add(menuSort);
		}
		return sorts;
	}
	
	/**
	 * 获得详细菜谱
	 * @throws JSONException 
	 */
	public static List<DishByCId> parseDishList(JSONArray dishArray) throws JSONException{
		List<DishByCId> dishes = new ArrayList<DishByCId>();
		List<StepByCId> steps = new ArrayList<StepByCId>();
		
		for(int i = 0; i < dishArray.length(); i++){
			steps.clear();
			JSONObject obj = dishArray.getJSONObject(i);
			DishByCId dish = new DishByCId();
			dish.setId(obj.getString("id"));
			dish.setTitle(obj.getString("title"));
			dish.setTags(obj.getString("tags"));
			dish.setImtro(obj.getString("imtro"));
			dish.setIngredients(obj.getString("ingredients"));
			dish.setBurden(obj.getString("burden"));
			dish.setAlbum(obj.getJSONArray("albums").getString(0));
			
			JSONArray stepArray = obj.getJSONArray("steps");
			for(int j = 0; j < stepArray.length(); j++){
				JSONObject o = stepArray.getJSONObject(j);
				StepByCId step = new StepByCId();
				step.setImg(o.getString("img"));
				step.setStep(o.getString("step"));
				steps.add(step);
			}
			dish.setSteps(steps);
			dishes.add(dish);
		}
		return dishes;
	}
	
	public static User parseUser(JSONObject obj) throws JSONException{
		User user = new User();
		user.setEmail(obj.getString("email"));
		user.setEmailVerify(obj.getBoolean("emailVerify"));
		user.setEmailVerifyCode(obj.getString("emailVerifyCode"));
		user.setId(obj.getInt("id"));
		user.setLastLoginIp(obj.getString("lastLoginIp"));
		user.setLastLoginTime(obj.getLong("lastLoginTime"));
		user.setNickname(obj.getString("nickname"));
		user.setPassword(obj.getString("password"));
		return user;
	}
	
}

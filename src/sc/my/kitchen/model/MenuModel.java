package sc.my.kitchen.model;

import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;

import android.R.integer;
import android.R.string;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.renderscript.Sampler;
import android.util.Log;

import sc.my.kitchen.entity.DishByCId;
import sc.my.kitchen.entity.MenuSort;
import sc.my.kitchen.entity.RootByMenu;
import sc.my.kitchen.util.DishByCIdCallBack;
import sc.my.kitchen.util.DishByNameCallBack;
import sc.my.kitchen.util.HttpUtils;
import sc.my.kitchen.util.JSONParser;
import sc.my.kitchen.util.MenuSortCallback;
import sc.my.kitchen.util.UrlFactory;


/**
 * 锟斤拷锟斤拷锟斤拷氐锟揭碉拷锟斤拷锟�
 * @author Sheng
 *
 */
public class MenuModel {
	
	/**
	 * 锟矫碉拷锟斤拷乇锟角╋拷碌牟锟斤拷锟�
	 * @param parentId
	 * @param callback
	 */
	public void searchSortList(final int parentId, final MenuSortCallback callback){
		
		AsyncTask<String, String, List<MenuSort>> task = new AsyncTask<String, String, List<MenuSort>>(){

			@Override
			protected List<MenuSort> doInBackground(String... params) {
				String url = UrlFactory.getSearchMenuSort(parentId);

				try {
					InputStream is = HttpUtils.getInputStream(url);
					String json = HttpUtils.isToString(is);
					
					JSONObject obj = new JSONObject(json);
					JSONArray arr = obj.getJSONArray("result");
					JSONObject o = arr.getJSONObject(0);
					JSONArray sortArr = o.getJSONArray("list");
					List<MenuSort> sorts = JSONParser.parseSortList(sortArr);	
					
					return sorts;
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}
			
			@Override
			protected void onPostExecute(List<MenuSort> result) {
				callback.onLoadMenuSort(result);
			}
			
		};
		task.execute();
		
	}
	
	public void searchDishByCId(final String id, final int startIndex, final int number, final DishByCIdCallBack callBack){
		AsyncTask<String, String, List<DishByCId>> task = new AsyncTask<String, String, List<DishByCId>>(){

			@Override
			protected List<DishByCId> doInBackground(String... params) {
				String start = startIndex + "";
				String n = number + "";
				int cid = Integer.parseInt(id);
				Log.i("sc", cid + start + n);
				
				String url = UrlFactory.getSearchDish(cid, start, n);
				
				try {
					InputStream is = HttpUtils.getInputStream(url);
					String json = HttpUtils.isToString(is);
					
					JSONObject obj = new JSONObject(json);
					JSONObject o = obj.getJSONObject("result");
					Log.i("sc", "JSONObject o --> " + o.toString());
					JSONArray arr = o.getJSONArray("data");
					Log.i("sc", "JSONArray arr -->" + arr.toString());
					List<DishByCId> dishes = JSONParser.parseDishList(arr);
					
					Log.i("sc", "MenuModel$searchDishByCId -->" + dishes.toString());
					
					return dishes;
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}
			
			@Override
			protected void onPostExecute(List<DishByCId> result) {
				callBack.onDishByCIdLoaded(result);
			}
			
		};
		task.execute();
	}
	
	public void searchDishByName(final String menu, final int number, final int startIndex, final DishByNameCallBack callBack){
		AsyncTask<String, String, List<DishByCId>> task = new AsyncTask<String, String, List<DishByCId>>(){

			@Override
			protected List<DishByCId> doInBackground(String... params) {
				
				
				try {
					
					String start = startIndex + "";
					String n = number + "";
					String m = URLEncoder.encode(menu, "UTF-8"); 
					
					String url = UrlFactory.getSearchDishByMenu(m, n, start);
					InputStream is = HttpUtils.getInputStream(url);
					String json = HttpUtils.isToString(is);
					
					JSONObject obj = new JSONObject(json);
					if (obj.getJSONObject("result") == null) {
						return null;
					}
					JSONObject o = obj.getJSONObject("result");
					JSONArray arr = o.getJSONArray("data");
					List<DishByCId> dishes = JSONParser.parseDishList(arr);
					
					
					return dishes;
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}
			
			@Override
			protected void onPostExecute(List<DishByCId> result) {
				callBack.onDishByNameLoaded(result);
			}
			
		};
		task.execute();
		
		
	}
	
	
}

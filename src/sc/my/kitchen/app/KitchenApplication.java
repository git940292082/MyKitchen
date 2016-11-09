package sc.my.kitchen.app;




import java.util.ArrayList;
import java.util.List;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.baidu.mapapi.SDKInitializer;

import sc.my.kitchen.entity.AlarmTime;
import sc.my.kitchen.entity.Cart;
import sc.my.kitchen.entity.Collection;
import sc.my.kitchen.entity.Contact;
import sc.my.kitchen.entity.DishByCId;
import sc.my.kitchen.entity.User;
import sc.my.kitchen.model.ContactCallBack;
import sc.my.kitchen.model.ContactModel;
import android.R.integer;
import android.app.Application;
import android.content.SharedPreferences;
import android.text.format.Time;
import android.util.Log;

public class KitchenApplication extends Application{

	private static KitchenApplication app;
	private static RequestQueue queue;
	private User user;
	private String token;
	private static Collection collection;
	private ContactModel model;
	private static List<Contact> contacts;
	private static List<AlarmTime> times;
	private Cart cart;
	/**
	 * 判断头像是否从照相机选择而来，false表示否，true表示确定
	 */
	private boolean isFromCamera = false;
	/**
	 * 表示从相册获得头像图片的路径
	 */
	private String avatarPath;
	private String QQNickName;
	private String QQavatarPath;
	
	@Override
	public void onCreate() {
		super.onCreate();
		app = this;
		queue = Volley.newRequestQueue(this);
		collection = new Collection();
		collection = collection.readDish();
		model = new ContactModel();
		model.getAllContacts(new ContactCallBack() {
			
			@Override
			public void onDataLoaded(Object object) {
				contacts = (List<Contact>) object;
			}
		});
		times = new ArrayList<AlarmTime>();
		Log.i("sc", "KitchenApplication -->start");
		
		cart = new Cart();
		cart = cart.readDish();
		SDKInitializer.initialize(this);
	}
	
	public static KitchenApplication getApp(){
		return app;
	}
	
	public static RequestQueue getRequestQueue(){
		return queue;
	}

	public void saveCurrentUser(User user){
		this.user = user;
	}
	
	public User getCurrentUser(){
		return this.user;
	}
	
	public void saveToken(String token){
		this.token = token;
		SharedPreferences pref = getSharedPreferences("token", MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();
		editor.putString("token", token);
		editor.commit();
	}
	
	public String getToken(){
		SharedPreferences pref = getSharedPreferences("token", MODE_PRIVATE);
		String token=pref.getString("token","");
		return token;
	}
	
	public static Collection getCollection(){
		return collection;
	}
	
	public static List<Contact> getContacts(){
		return contacts;
	}

	public static List<AlarmTime> getTimes() {
		return times;
	}

	public static void addTime(AlarmTime time){
		times.add(time);
	}
	
	public static void deleteTime(AlarmTime time){
		times.remove(time);
	}
	
	public Cart getCart(){
		return this.cart;
	}
	
	public void deleteDishInCart(String id){
		cart.deleteDish(id);
	}
	
	
	public void saveAvatarState(boolean isFromCamera){
		this.isFromCamera = isFromCamera;
		SharedPreferences p= getSharedPreferences("avatar", MODE_PRIVATE);
		SharedPreferences.Editor editor = p.edit();
		editor.putBoolean("avatar", isFromCamera);
		editor.commit();
		
	}
	
	public boolean getAvatarState(){
		SharedPreferences p = getSharedPreferences("avatar", MODE_PRIVATE);
		boolean isFromCamera=p.getBoolean("avatar", false);
		return isFromCamera;
	}
	
	public void saveAvatarPaht(String avatarPath){
		this.avatarPath = avatarPath;
		SharedPreferences p= getSharedPreferences("avatarPath", MODE_PRIVATE);
		SharedPreferences.Editor editor = p.edit();
		editor.putString("avatarPath", avatarPath);
		editor.commit();
		
	}
	
	public String getAvatarPath(){
		SharedPreferences p = getSharedPreferences("avatarPath", MODE_PRIVATE);
		String avatarPath=p.getString("avatarPath", "");
		return avatarPath;
	}
	
	public void saveQQNickName(String QQNickName){
		this.QQNickName = QQNickName;
		SharedPreferences p= getSharedPreferences("QQNickName", MODE_PRIVATE);
		SharedPreferences.Editor editor = p.edit();
		editor.putString("QQNickName", QQNickName);
		editor.commit();
		
	}
	
	public String getQQNickName(){
		SharedPreferences p = getSharedPreferences("QQNickName", MODE_PRIVATE);
		String avatarPath=p.getString("QQNickName", "");
		return avatarPath;
	}
	
	public void saveQQavatarPath(String QQavatarPath){
		this.QQavatarPath = QQavatarPath;
		SharedPreferences p= getSharedPreferences("QQavatarPath", MODE_PRIVATE);
		SharedPreferences.Editor editor = p.edit();
		editor.putString("QQavatarPath", QQavatarPath);
		editor.commit();
		
	}
	
	public String getQQavatarPath(){
		SharedPreferences p = getSharedPreferences("QQavatarPath", MODE_PRIVATE);
		String avatarPath=p.getString("QQavatarPath", "");
		return avatarPath;
	}
	
	
}

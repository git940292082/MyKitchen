package sc.my.kitchen.model;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.android.volley.Response.*;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.google.gson.JsonObject;

import sc.my.kitchen.app.KitchenApplication;
import sc.my.kitchen.entity.User;
import sc.my.kitchen.util.CommonRequest;
import sc.my.kitchen.util.Consts;
import sc.my.kitchen.util.JSONParser;

public class UserModel {

	public void getImageCode(final AsyncCallback callback){
		String url = Consts.URL_GET_IMAGE_CODE;
		ImageRequest request = new ImageRequest(url, new Listener<Bitmap>() {

			@Override
			public void onResponse(Bitmap response) {
				callback.onSuccess(response);
			}
		}, 110, 50, Bitmap.Config.ARGB_8888, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
			}
		})
		{
			@Override
			protected Response<Bitmap> parseNetworkResponse(
					NetworkResponse response) {
				Map<String, String> headers = response.headers;
				String sessionid = headers.get("Set-Cookie");
				if (sessionid != null) {
					CommonRequest.JSESSIONID = sessionid.split(";")[0];
				}
				return super.parseNetworkResponse(response);
			}
		};
		KitchenApplication.getRequestQueue().add(request);
	}
	
	public void regist(final User user, final String code, final AsyncCallback callback){
		String url = Consts.URL_USER_REGIST;
		
		CommonRequest request = new CommonRequest(Request.Method.POST, url, new Listener<String>() {

			@Override
			public void onResponse(String response) {
				try {
					JSONObject object = new JSONObject(response);
					if (object.getInt("code") == Consts.RESPONSE_CODE_SUCCESS) {
						callback.onSuccess(null);
					}
					else {
						callback.onFail(object.getString("error_msg"));
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
			}
		})
		{
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				
				Map<String, String> map = new HashMap<String, String>();
				map.put("user.email", user.getEmail());
				map.put("user.nickname", user.getNickname());
				map.put("user.password", user.getPassword());
				map.put("number", code);
				return map;
			}
		};
		KitchenApplication.getRequestQueue().add(request);
	}
	
	public void login(final String loginName, final String password, final AsyncCallback callback){
		String url = Consts.URL_USER_LOGIN;
		CommonRequest request = new CommonRequest(Request.Method.POST, url, new Listener<String>() {

			@Override
			public void onResponse(String response) {
				try {
					JSONObject jsonObject = new JSONObject(response);
					
					if (jsonObject.getInt("code") == Consts.RESPONSE_CODE_SUCCESS) {
						JSONObject userObject = jsonObject.getJSONObject("user");
						KitchenApplication app = KitchenApplication.getApp();
						app.saveCurrentUser(JSONParser.parseUser(userObject));
						
						String token = jsonObject.getString("token");
						app.saveToken(token);
						callback.onSuccess(null);
					}
					else {
						callback.onFail(jsonObject.get("error_msg"));
					}
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				callback.onFail("µÇÂ¼Ê§°Ü");
			}
		})
		{
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
				params.put("email", loginName);
				params.put("password", password);
				return params;
			}
		};
		KitchenApplication.getRequestQueue().add(request);
	}
	
	public void loginWithoutPwd(final String token, final AsyncCallback callback){
		String url = Consts.URL_USER_LOGIN_WITHOUT_PWD + "?token=" + token;
		CommonRequest request = new CommonRequest(url, new Listener<String>() {

			@Override
			public void onResponse(String response) {
				try {
					JSONObject jsonObject = new JSONObject(response);
					if (jsonObject.getInt("code") == Consts.RESPONSE_CODE_SUCCESS) {
						JSONObject userObject = jsonObject.getJSONObject("user");
						KitchenApplication app = KitchenApplication.getApp();
						app.saveCurrentUser(JSONParser.parseUser(userObject));
						callback.onSuccess(null);
					}
					else {
						callback.onFail(response);
					}
				} catch (JSONException e) {
					e.printStackTrace();
					callback.onFail(response);
				}
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				
			}
		});
		KitchenApplication.getRequestQueue().add(request);
	}
	
}
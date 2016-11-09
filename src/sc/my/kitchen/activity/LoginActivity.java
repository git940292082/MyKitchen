package sc.my.kitchen.activity;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQAuth;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import sc.my.kitchen.app.KitchenApplication;
import sc.my.kitchen.entity.User;
import sc.my.kitchen.model.AsyncCallback;
import sc.my.kitchen.model.UserModel;
import sc.my.kitchen.util.AppManager;
import sc.my.kitchen.util.DealCameraBitmap;
import sc.my.kitchen.util.DialogUtils;
import sc.my.kitchen.util.QQUtils;
import sc.my.mykitchen.R;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity  implements OnClickListener{

	private TextView tvRegist;
	private EditText etLoginUser;
	private EditText etLoginPwd;
	private Button btnLogin;
	private UserModel model;
	private ImageView ivLoginAvatar;
	private static final int ITEM1 = Menu.FIRST;
    private static final int ITEM2 = Menu.FIRST+1;
    private static int CAMERA_RESULT = 100;
	private static int RESULT_LOAD_IMAGE = 200;
	private String saveDir = Environment.getExternalStorageDirectory()
			.getPath() + "/temp_image";
	private File mPhotoFile;
	private boolean isFromCamera = false;
	private ImageView ivLoginQQ;
	private static final String TAG = LoginActivity.class.getName();
	public static String mAppid;
	public static QQAuth mQQAuth;
	private UserInfo mInfo;
	private Tencent mTencent;
	private final String APP_ID = "1105680491";// 测试时使用，真正发布的时候要换成自己的APP_ID
	/**
	 * 表示是用什么登陆的，1表示QQ，0表示其他方式
	 */
	private int qqLogin;
	private TextView tvQQNickName;
	private String nickname;
	private String QQLoginAvatar;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		AppManager.getAppManager().addActivity(this);
		
		model = new UserModel();
		Dialog loadingDialog = new Dialog(this, R.style.dialog_login_loading);
		
		initViews();
		
		setListeners();
		
		registerForContextMenu(ivLoginAvatar);
		
	}
	
	@Override
	protected void onStart() {
		final Context context = LoginActivity.this;
		final Context ctxContext = context.getApplicationContext();
		mAppid = APP_ID;
		mQQAuth = QQAuth.createInstance(mAppid, ctxContext);
		mTencent = Tencent.createInstance(mAppid, LoginActivity.this);
		super.onStart();
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		String token = KitchenApplication.getApp().getToken();
		if (token != null) {
			model.loginWithoutPwd(token, new AsyncCallback() {
				
				@Override
				public void onSuccess(Object success) {
					updateMyInfo();
				}
				
				@Override
				public void onFail(Object fail) {
					
				}
			});
		}
	}

	private void initViews() {
		tvRegist = (TextView) findViewById(R.id.tv_login_jump_regist);
		etLoginUser = (EditText) findViewById(R.id.et_login_user);
		etLoginPwd = (EditText) findViewById(R.id.et_login_password);
		btnLogin = (Button) findViewById(R.id.btn_login);
		ivLoginAvatar = (ImageView) findViewById(R.id.iv_login_avatar);
		ivLoginQQ = (ImageView) findViewById(R.id.iv_login_qq);
		tvQQNickName = (TextView) findViewById(R.id.tv_login_avatar_warm);
	}

	private void setListeners() {
		tvRegist.setOnClickListener(this);
		btnLogin.setOnClickListener(this);
		ivLoginAvatar.setOnClickListener(this);
		ivLoginQQ.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Context context = v.getContext();
		Class<?> cls = null;
		
		switch (v.getId()) {
		case R.id.tv_login_jump_regist:
			Intent intent = new Intent(LoginActivity.this, RegistActivity.class);
			startActivity(intent);
			break;
			

		case R.id.btn_login:
			
			Dialog dialog = DialogUtils.createLoadingDialog(LoginActivity.this, "正在登陆中");
			dialog.show();	
			
			String loginName = etLoginUser.getText().toString();
			String password = etLoginPwd.getText().toString();
			
			model.login(loginName, password, new AsyncCallback() {
				
				@Override
				public void onSuccess(Object success) {
					Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_LONG).show();
					setResult(RESULT_OK);
					finish();
				}
				
				@Override
				public void onFail(Object fail) {
					Toast.makeText(LoginActivity.this, "登录失败 ", Toast.LENGTH_LONG).show();
				}
			});
			break;
			
		case R.id.iv_login_qq:
			
			onClickLogin();
			break;
			
		}
		if (cls != null) {
			Intent intent = new Intent(context, cls);
			context.startActivity(intent);
		}
	}

	private void onClickLogin() {
		if (!mQQAuth.isSessionValid()) {
			IUiListener listener = new BaseUiListener() {
				@Override
				protected void doComplete(JSONObject values) {
					setResult(RESULT_OK);
					updateUserInfo();
				}
			};
			mQQAuth.login(this, "all", listener);
			// mTencent.loginWithOEM(this, "all",
			// listener,"10000144","10000144","xxxx");
			mTencent.login(this, "all", listener);
		} else {
			mQQAuth.logout(this);
			updateUserInfo();
		}
	}
	
	Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				JSONObject response = (JSONObject) msg.obj;
				if (response.has("nickname")) {
					try {
						KitchenApplication.getApp().saveQQNickName(response.getString("nickname"));
						tvQQNickName.setText(response.getString("nickname"));
					} catch (JSONException e) {
						e.printStackTrace();
					}
					
				}
			} else if (msg.what == 1) {
				Bitmap bitmap = (Bitmap) msg.obj;
				ivLoginAvatar.setImageBitmap(bitmap);
				 File appDir = new File(Environment.getExternalStorageDirectory()
							.getPath() + "/temp_image");
				    if (!appDir.exists()) {
				        appDir.mkdir();
				    }
				    String fileName = System.currentTimeMillis() + ".jpg";
				    File file = new File(appDir, fileName);
				    QQLoginAvatar =  fileName;
				    KitchenApplication.getApp().saveQQavatarPath(QQLoginAvatar);
				    try {
				        FileOutputStream fos = new FileOutputStream(file);
				        bitmap.compress(CompressFormat.JPEG, 100, fos);
				        fos.flush();
				        fos.close();
				        Log.i("sc", "保存完成" + QQLoginAvatar);
				    } catch (FileNotFoundException e) {
				        e.printStackTrace();
				    } catch (IOException e) {
				        e.printStackTrace();
				    }
			}
		}

	};
	
	private void updateUserInfo() {
		if (mQQAuth != null && mQQAuth.isSessionValid()) {
			IUiListener listener = new IUiListener() {

				@Override
				public void onError(UiError e) {
					
				}

				@Override
				public void onComplete(final Object response) {
					Message msg = new Message();
					msg.obj = response;
					msg.what = 0;
					mHandler.sendMessage(msg);
					new Thread() {

						@Override
						public void run() {
							JSONObject json = (JSONObject) response;
							if (json.has("figureurl")) {
								Bitmap bitmap = null;
								try {
									bitmap = QQUtils.getbitmap(json
											.getString("figureurl_qq_2"));
									
								} catch (JSONException e) {
									
								}
								Message msg = new Message();
								msg.obj = bitmap;
								msg.what = 1;
								mHandler.sendMessage(msg);
							}
						}

					}.start();
				}

				@Override
				public void onCancel() {
				}
			};
			mInfo = new UserInfo(this, mQQAuth.getQQToken());
			mInfo.getUserInfo(listener);

		} else {
		}
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		menu.setHeaderTitle("选择头像");
		menu.add(0,ITEM1,0,"从相册选择");
		menu.add(0,ITEM2, 0, "从照相机进行选择");
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case ITEM1:
			Intent intent = new Intent(Intent.ACTION_PICK, 
					android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			startActivityForResult(intent, RESULT_LOAD_IMAGE);
			File savePath = new File(saveDir);
			if (!savePath.exists()) {
				savePath.mkdirs();
			}
			break;

		case ITEM2:
			String state = Environment.getExternalStorageState();
			if (state.equals(Environment.MEDIA_MOUNTED)) {
				mPhotoFile = new File(saveDir, "temp.jpg");
				mPhotoFile.delete();
				if (!mPhotoFile.exists()) {
					try {
						mPhotoFile.createNewFile();
					} catch (IOException e) {
						e.printStackTrace();
						Toast.makeText(getApplication(), "照片创建失败!",
								Toast.LENGTH_LONG).show();
						break;
					}
				}
				Intent intent2 = new Intent(
						"android.media.action.IMAGE_CAPTURE");
				intent2.putExtra(MediaStore.EXTRA_OUTPUT,
						Uri.fromFile(mPhotoFile));
				startActivityForResult(intent2, CAMERA_RESULT);
			} else {
				Toast.makeText(getApplication(), "sdcard无效或没有插入!",
						Toast.LENGTH_SHORT).show();
			}
			break;
		}
		return true;
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (requestCode == CAMERA_RESULT && resultCode == RESULT_OK) {
			if (mPhotoFile != null && mPhotoFile.exists()) {
				BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
				bitmapOptions.inSampleSize = 8;
				int degree = DealCameraBitmap.readPictureDegree(mPhotoFile.getAbsolutePath());
				Bitmap bitmap = BitmapFactory.decodeFile(mPhotoFile.getPath(),
						bitmapOptions);
				bitmap = DealCameraBitmap.rotaingImageView(degree, bitmap);
				ivLoginAvatar.setImageBitmap(bitmap);
				isFromCamera = true;
				KitchenApplication.getApp().saveAvatarState(isFromCamera);
			}
		}
		if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK
				&& null != data) {
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };

			Cursor cursor = getContentResolver().query(selectedImage,
					filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			cursor.close();

			ivLoginAvatar.setImageBitmap(BitmapFactory
					.decodeFile(picturePath));
			
			isFromCamera = false;
			KitchenApplication.getApp().saveAvatarState(isFromCamera);
			KitchenApplication.getApp().saveAvatarPaht(picturePath);
		}
		
	}
	
	private void updateMyInfo(){
		User user = KitchenApplication.getApp().getCurrentUser();
		String nickName = user.getNickname();
		Log.i("sc", nickName);
		boolean isFromCamera = KitchenApplication.getApp().getAvatarState();
		if (isFromCamera) {
			mPhotoFile = new File(saveDir, "temp.jpg");
			BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
			bitmapOptions.inSampleSize = 8;
			int degree = DealCameraBitmap.readPictureDegree(mPhotoFile.getAbsolutePath());
			Bitmap bitmap = BitmapFactory.decodeFile(mPhotoFile.getPath(),
					bitmapOptions);
			bitmap = DealCameraBitmap.rotaingImageView(degree, bitmap);
			ivLoginAvatar.setImageBitmap(bitmap);
		}
		else {
			String picturePath = KitchenApplication.getApp().getAvatarPath();
			Bitmap bitmap =BitmapFactory.decodeFile(picturePath);
			ivLoginAvatar.setImageBitmap(bitmap);
		}
	}
	
	private class BaseUiListener implements IUiListener {

		@Override
		public void onComplete(Object response) {
			QQUtils.toastMessage(LoginActivity.this, "登录成功");
			doComplete((JSONObject) response);
		}

		protected void doComplete(JSONObject values) {

		}

		@Override
		public void onError(UiError e) {
			QQUtils.toastMessage(LoginActivity.this, "onError: " + e.errorDetail);
			QQUtils.dismissDialog();
		}

		@Override
		public void onCancel() {
			QQUtils.toastMessage(LoginActivity.this, "onCancel: ");
			QQUtils.dismissDialog();
		}
	}
	
	public static boolean ready(Context context) {
		if (mQQAuth == null) {
			return false;
		}
		boolean ready = mQQAuth.isSessionValid()
				&& mQQAuth.getQQToken().getOpenId() != null;
		if (!ready)
			Toast.makeText(context, "login and get openId first, please!",
					Toast.LENGTH_SHORT).show();
		return ready;
	}
	
}

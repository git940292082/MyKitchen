package sc.my.kitchen.fragment;

import java.io.File;

import sc.my.kitchen.activity.BaiduSearchActivity;
import sc.my.kitchen.activity.InvitateActivity;
import sc.my.kitchen.activity.LoginActivity;
import sc.my.kitchen.activity.MainActivity;
import sc.my.kitchen.activity.MyCollectionActivity;
import sc.my.kitchen.activity.RemindActivity;
import sc.my.kitchen.app.KitchenApplication;
import sc.my.kitchen.entity.User;
import sc.my.kitchen.model.AsyncCallback;
import sc.my.kitchen.model.UserModel;
import sc.my.kitchen.util.AppManager;
import sc.my.kitchen.util.DealCameraBitmap;
import sc.my.mykitchen.R;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MyFragment extends Fragment implements OnClickListener{
	
	private ImageView ivAvatar;
	private TextView tvNickName;
	private UserModel model;
	private RelativeLayout rlMyCollection;
	private RelativeLayout rlExit;
	private RelativeLayout rlInvitate;
	private RelativeLayout rlRemind;
	private RelativeLayout rlNearby;
	private String saveDir = Environment.getExternalStorageDirectory()
			.getPath() + "/temp_image";
	private File mPhotoFile;
	
	private static final int REQUEST_CODE_LOGIN = 1;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_my, null);
		
		model = new UserModel();
		
		
		
		initViews(view);
		
		setListeners();
		
		
	
		return view;
	}

	@Override
	public void onResume() {
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
	
	private void setListeners() {
		ivAvatar.setOnClickListener(this);
		rlMyCollection.setOnClickListener(this);
		rlExit.setOnClickListener(this);
		rlInvitate.setOnClickListener(this);
		rlRemind.setOnClickListener(this);
		rlNearby.setOnClickListener(this);
	}

	private void initViews(View view) {
		ivAvatar = (ImageView) view.findViewById(R.id.iv_my_info_head);
		tvNickName = (TextView) view.findViewById(R.id.tv_my_info_head                                                                                                                                                                                                                                                                                                                                                      );
		rlMyCollection = (RelativeLayout) view.findViewById(R.id.rl_info_my_collection);
		rlExit = (RelativeLayout) view.findViewById(R.id.rl_info_exit);
		rlInvitate = (RelativeLayout) view.findViewById(R.id.rl_info_my_invitation);
		rlRemind = (RelativeLayout) view.findViewById(R.id.rl_info_my_remind);
		rlNearby = (RelativeLayout) view.findViewById(R.id.rl_info_my_menu);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_my_info_head:
			Intent intent = new Intent(getActivity(), LoginActivity.class);
			startActivityForResult(intent, REQUEST_CODE_LOGIN);
			break;

		case R.id.rl_info_my_collection:
			Intent intent2 = new Intent(getActivity(), MyCollectionActivity.class);
			startActivity(intent2);
			break;
			
		case R.id.rl_info_exit:
			AppManager.getAppManager().finishAllActivity();
			KitchenApplication.getApp().saveToken("");
			break;
			
		case R.id.rl_info_my_invitation:
			Intent intent4 = new Intent(getActivity(), InvitateActivity.class);
			startActivity(intent4);
			break;
			  
		case R.id.rl_info_my_remind:
			Intent intent5 = new Intent(getActivity(), RemindActivity.class);
			startActivity(intent5);
			break;
			
		case R.id.rl_info_my_menu:
			Intent intent6 = new Intent(getActivity(), BaiduSearchActivity.class);
			startActivity(intent6);
			break;
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case REQUEST_CODE_LOGIN:
			Log.i("sc", "onActivityResult");
			if (resultCode == Activity.RESULT_OK) {
				Log.i("sc", "onActivityResult");
				updateMyInfo();
			}
			break;

		}
	}
	
	private void updateMyInfo(){
			String qqNickName = KitchenApplication.getApp().getQQNickName();
			if (!"".equals(KitchenApplication.getApp().getQQNickName())) {
				if (!("".equals(qqNickName))) {
					tvNickName.setText(qqNickName);
					String QQavatarPath = KitchenApplication.getApp().getQQavatarPath();
					Log.i("sc", "updateMyInfo" + QQavatarPath);
					File file = new File(saveDir,QQavatarPath);
					Bitmap bmAvatar = BitmapFactory.decodeFile(file.getAbsolutePath());
					ivAvatar.setImageBitmap(bmAvatar);
				}
				return;
			}
			
			User user = KitchenApplication.getApp().getCurrentUser();
			String nickName = user.getNickname();
//			tvNickName.setText(nickName);
			boolean isFromCamera = KitchenApplication.getApp().getAvatarState();
			if (isFromCamera) {
				mPhotoFile = new File(saveDir, "temp.jpg");
				BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
				bitmapOptions.inSampleSize = 8;
				int degree = DealCameraBitmap.readPictureDegree(mPhotoFile.getAbsolutePath());
				Bitmap bitmap = BitmapFactory.decodeFile(mPhotoFile.getPath(),
						bitmapOptions);
				bitmap = DealCameraBitmap.rotaingImageView(degree, bitmap);
				ivAvatar.setImageBitmap(bitmap);
			}
			else {
				String picturePath = KitchenApplication.getApp().getAvatarPath();
				Bitmap bitmap =BitmapFactory.decodeFile(picturePath);
				ivAvatar.setImageBitmap(bitmap);
			}
			
	}
	
}

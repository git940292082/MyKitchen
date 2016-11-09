package sc.my.kitchen.activity;

import sc.my.kitchen.entity.User;
import sc.my.kitchen.model.AsyncCallback;
import sc.my.kitchen.model.UserModel;
import sc.my.kitchen.util.AppManager;
import sc.my.mykitchen.R;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class RegistActivity extends Activity {

	private ImageView ivCode;
	private EditText etRegistEmail;
	private EditText etRegistUser;
	private EditText etRegistCode;
	private EditText etPwdFirst;
	private EditText ETPwdSecond;
	private Button btnRegist;
	private ImageButton btnBack;
	private UserModel model;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_regist);
		AppManager.getAppManager().addActivity(this);
		
		initViews();

		model = new UserModel();
		
		model.getImageCode(new AsyncCallback() {
			
			@Override
			public void onSuccess(Object success) {
				Bitmap codeBitmap = (Bitmap) success;
				ivCode.setImageBitmap(codeBitmap);
			}
			
			@Override
			public void onFail(Object fail) {
				
			}
		});
		
		setListeners();
		
	}

	private void setListeners() {
		btnRegist.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String firstPwd = etPwdFirst.getText().toString();
				String secondPwd = ETPwdSecond.getText().toString();
				
				if (!firstPwd.equals(secondPwd)) {
					Toast.makeText(RegistActivity.this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
					return;
				}
				
				User user = new User();
				user.setEmail(etRegistEmail.getText().toString());
				user.setPassword(etPwdFirst.getText().toString());
				user.setNickname(etRegistUser.getText().toString());
				String code = etRegistCode.getText().toString();
				
				model.regist(user, code, new AsyncCallback() {
					
					@Override
					public void onSuccess(Object success) {
						Toast.makeText(RegistActivity.this, "恭喜，注册成功", Toast.LENGTH_LONG).show();
						finish();
					}
					
					@Override
					public void onFail(Object fail) {
						Toast.makeText(RegistActivity.this, "注册失败" + (String)fail, Toast.LENGTH_LONG).show();
					}
				});
			}
		});
		
		btnBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
	}

	private void initViews() {
		etRegistEmail = (EditText) findViewById(R.id.et_regist_email);
		ivCode = (ImageView) findViewById(R.id.iv_regist_codes);
		etRegistUser = (EditText) findViewById(R.id.et_regist_user);
		etRegistCode = (EditText) findViewById(R.id.et_regist_codes);
		etPwdFirst = (EditText) findViewById(R.id.et_password_first);
		ETPwdSecond = (EditText) findViewById(R.id.et_password_second);
		btnRegist = (Button) findViewById(R.id.btn_regist);
		btnBack = (ImageButton) findViewById(R.id.ib_regist_back);
	}

}

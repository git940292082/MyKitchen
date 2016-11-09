package sc.my.kitchen.activity;

import java.util.Calendar;

import sc.my.kitchen.app.KitchenApplication;
import sc.my.kitchen.entity.AlarmTime;
import sc.my.kitchen.util.AlarmReceiver;
import sc.my.kitchen.util.AppManager;
import sc.my.mykitchen.R;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddRemindActivity extends Activity {
	
	private EditText etHour;
	private EditText etMinute;
	private Button btnAddOk;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_remind);
		
		AppManager.getAppManager().addActivity(this);
		
		initViews();
		
		setListeners();
		
	}

	private void setListeners() {
		btnAddOk.setOnClickListener(new OnClickListener() {
			
			int hour = 0;
			int minute = 0;
			
			@Override
			public void onClick(View v) {
				
				if ("".equals(etMinute.getText().toString()) || etMinute.getText().toString() == null) {
					Toast.makeText(AddRemindActivity.this, "请输入输入分钟时间", Toast.LENGTH_LONG).show();
					return;
				}
				
				if ("".equals(etHour.getText().toString()) || etHour.getText().toString() == null) {
					hour =0;
				}
				else {
					hour = Integer.parseInt(etHour.getText().toString());
				}
				
				minute = Integer.parseInt(etMinute.getText().toString());
				int time = (hour * 60 + minute) * 60 ;
				AlarmTime alarmTime = new AlarmTime();
				alarmTime.setHour(hour);
				alarmTime.setMinute(minute);
				KitchenApplication.addTime(alarmTime);
				Toast.makeText(AddRemindActivity.this, "时间已添加", Toast.LENGTH_LONG).show();
				
				Intent intent =new Intent(AddRemindActivity.this, AlarmReceiver.class);
				intent.setAction("alarm");
				PendingIntent sender=
				PendingIntent.getBroadcast(AddRemindActivity.this,0,intent, 0);

				Calendar calendar=Calendar.getInstance();
				calendar.setTimeInMillis(System.currentTimeMillis());
				//TODO
				calendar.add(Calendar.SECOND, 15);

				AlarmManager alarm=(AlarmManager)getSystemService(ALARM_SERVICE);
				alarm.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
				
				finish();
			}
		});
	}

	private void initViews() {
		etHour = (EditText) findViewById(R.id.et_remind_hour);
		etMinute = (EditText) findViewById(R.id.et_remind_minute);
		btnAddOk = (Button) findViewById(R.id.btn_add_remind_ok);
	}


}

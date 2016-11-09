package sc.my.kitchen.activity;

import java.util.List;

import sc.my.kitchen.adapter.RemindAdapter;
import sc.my.kitchen.app.KitchenApplication;
import sc.my.kitchen.entity.AlarmTime;
import sc.my.kitchen.util.AppManager;
import sc.my.mykitchen.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;

public class RemindActivity extends Activity {

	private ImageButton btnAddRemind;
	private ListView lvRemind;
	private RemindAdapter adapter;
	private List<AlarmTime> times;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_remind);
		AppManager.getAppManager().addActivity(this);
		
		times = KitchenApplication.getTimes();
		
		initViews();
		
		setListeners();
		
		setAdapter();
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		times = KitchenApplication.getTimes();
		adapter.notifyDataSetChanged();
	}

	private void setAdapter() {
		adapter = new RemindAdapter(this, times);
		lvRemind.setAdapter(adapter);
	}

	private void initViews() {
		btnAddRemind = (ImageButton) findViewById(R.id.btn_add_remind);
		lvRemind = (ListView) findViewById(R.id.lv_remind_list);
	}

	private void setListeners() {
		btnAddRemind.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(RemindActivity.this, AddRemindActivity.class);
				startActivity(intent);
			}
		});
	}


}

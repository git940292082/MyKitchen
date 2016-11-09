package sc.my.kitchen.adapter;

import java.util.List;

import sc.my.kitchen.app.KitchenApplication;
import sc.my.kitchen.entity.AlarmTime;
import sc.my.kitchen.util.AlarmReceiver;
import sc.my.mykitchen.R;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class RemindAdapter extends MyAdapter<AlarmTime>{

	public RemindAdapter(Context context, List<AlarmTime> data) {
		super(context, data);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		final AlarmTime time = getData().get(position);
		ViewHolder holder = null;
		
		if (convertView == null) {
			convertView = getLayoutInflater().inflate(R.layout.list_remind_item, null);
			holder = new ViewHolder();
			holder.tvTime = (TextView) convertView.findViewById(R.id.tv_remind_time);
			holder.btnDeleteTime = (Button) convertView.findViewById(R.id.btn_delete_remind);
			convertView.setTag(holder);
		}
		holder = (ViewHolder) convertView.getTag();
		holder.tvTime.setText(time.getHour() + "小时" + time.getMinute() + "分钟");
		holder.btnDeleteTime.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				KitchenApplication.deleteTime(time);
				RemindAdapter.this.notifyDataSetChanged();
				Intent intent = new Intent(getContext(),AlarmReceiver.class);
	            PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
	            //获取闹钟管理器
	            AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
	            alarmManager.cancel(pendingIntent);
	            
			}
		});
	
		return convertView;
	}

	class ViewHolder{
		TextView tvTime;
		Button btnDeleteTime;
	}
	
	
	
}

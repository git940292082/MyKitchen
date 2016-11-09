package sc.my.kitchen.util;

import sc.my.kitchen.activity.CallActivity;
import android.content.BroadcastReceiver;  
import android.content.Context;  
import android.content.Intent;  
import android.widget.Toast;  

public class AlarmReceiver extends BroadcastReceiver {  
	public void onReceive(Context context, Intent intent) {  
		if(intent.getAction().equals("alarm")){
			
			Intent intent2 = new Intent(context, CallActivity.class);
			intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intent2);
			
			Toast.makeText(context, "时间到了哟~", Toast.LENGTH_LONG).show();
		}
	}
	
}  


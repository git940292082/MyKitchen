package sc.my.kitchen.activity;



import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import sc.my.kitchen.util.AppManager;
import sc.my.kitchen.util.VibrateBar;
import sc.my.mykitchen.R;
import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class CallActivity extends Activity implements OnClickListener{
	
	private Button bt;
	private TextView tvCallTime;
	private MediaPlayer player;
	private Timer timer;
	private SimpleDateFormat formatter;
	private Date curDate;
	private String date;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_call);
		AppManager.getAppManager().addActivity(this);
		
		bt=(Button) findViewById(R.id.button1);
		getWindow().addFlags(  
		        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |   
		        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |   
		        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);  

		long[] pattern={1000,2000,2000,2000};
		VibrateBar.Vibrate(this, pattern, true);
		bt.setOnClickListener(this);
		
		player = MediaPlayer.create(this, R.raw.cityhunter_nina);
		player.start();
		
	}
	
	@Override
	public void onClick(View v) {
		VibrateBar.Cancel(this);
		player.stop();
		finish();
	}
	

}

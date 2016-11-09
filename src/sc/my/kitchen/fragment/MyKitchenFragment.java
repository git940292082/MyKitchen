package sc.my.kitchen.fragment;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import sc.my.kitchen.activity.MenuSortActivity;
import sc.my.kitchen.activity.SearchResultActivity;
import sc.my.mykitchen.R;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MyKitchenFragment extends Fragment implements OnClickListener{
	
	private Button btnJumpMenuSort;
	private EditText etMyKitchenSearch;
	private ImageButton ibAddMenu;
	private ImageButton ibAddDish;
	private Button btnQuestion;
	private Button btnLeader;
	private TextView tvDate;
	private ViewPager galleryvViewPager;
	private GalleryAdapter galleryAdapter;
	private Timer timer;
	private SimpleDateFormat formatter;
	private Date curDate;
	private String date;
	private Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				
				formatter  = new SimpleDateFormat("yyyy年MM月dd日    HH:mm:ss     ");       
				curDate = new Date(System.currentTimeMillis());//获取当前时间       
				date = formatter.format(curDate);       
				tvDate.setText(date);
				break;

			}
		};
	}; 
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_my_kitchen, null);
		
		
		
		
		initViews(view);
		
		etMyKitchenSearch.clearFocus();
		
		timer =  new Timer();
		timeTask();
		
		setListeners();
		
		return view;
	}
	
	
	private void timeTask(){
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				handler.sendEmptyMessage(1);
			}
		}, 0, 1000);
	}
	
	@Override
	public void onStop() {
		timer.cancel();
		super.onStop();
	}
	
	
	@Override
	public void onResume() {
		super.onResume();
		
		InputStream is1 = getResources().openRawResource(R.drawable.picture1);
		InputStream is2 = getResources().openRawResource(R.drawable.picture2);
		InputStream is3 = getResources().openRawResource(R.drawable.picture3);
		
		Bitmap bitmap1 = BitmapFactory.decodeStream(is1);
		Bitmap bitmap2 = BitmapFactory.decodeStream(is2);
		Bitmap bitmap3 = BitmapFactory.decodeStream(is3);
		
		Bitmap bm1 = narrowBitmap(bitmap1);
		Bitmap bm2 = narrowBitmap(bitmap2);
		Bitmap bm3 = narrowBitmap(bitmap3);
		
		Bitmap[] bitmaps = {bm1,bm2,bm3};
		
		galleryAdapter = new GalleryAdapter(getActivity(), bitmaps);
		
		galleryvViewPager.setAdapter(galleryAdapter);
		
//		galleryAdapter.notifyDataSetChanged();
		
	}
	
	private Bitmap narrowBitmap(Bitmap bm){
		Matrix matrix = new Matrix();
		matrix.postScale(0.5f, 0.5f);
		Bitmap bitmap =  Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(),matrix,true);
		return bitmap;
	}

	private void initViews(View view) {
		btnJumpMenuSort  = (Button) view.findViewById(R.id.ib_my_kitchen_label_menu_sort);
		etMyKitchenSearch = (EditText) view.findViewById(R.id.et_index_my_kitchen_search);
		ibAddMenu = (ImageButton) view.findViewById(R.id.ib_index_my_kitchen_add_menu);
		ibAddDish = (ImageButton) view.findViewById(R.id.ib_index_my_kitchen_add_dish);
		btnQuestion = (Button) view.findViewById(R.id.ib_my_kitchen_label_question);
		btnLeader = (Button) view.findViewById(R.id.ib_my_kitchen_label_leader_board);
		tvDate = (TextView) view.findViewById(R.id.tv_my_kitchen_date);
		galleryvViewPager = (ViewPager) view.findViewById(R.id.vp_my_kitchen_gallery);
	}
	
	private void setListeners(){
		btnJumpMenuSort.setOnClickListener(this);
		ibAddMenu.setOnClickListener(this);
		etMyKitchenSearch.setOnClickListener(this);
		btnQuestion.setOnClickListener(this);
		btnLeader.setOnClickListener(this);
		ibAddDish.setOnClickListener(this);
	}

	
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ib_my_kitchen_label_menu_sort:
			Intent intent = new Intent(getActivity(), MenuSortActivity.class);
			startActivity(intent);
			break;
			
		case R.id.et_index_my_kitchen_search:
			Intent intent2 = new Intent(getActivity(), SearchResultActivity.class);
			startActivity(intent2);
			break;
			
		case R.id.ib_my_kitchen_label_question:
			
			Toast.makeText(getActivity(), "改功能尚未实现，敬请期待~~", Toast.LENGTH_SHORT).show();
			break;
			
		case R.id.ib_index_my_kitchen_add_menu:
			Toast.makeText(getActivity(), "改功能尚未实现，敬请期待~~", Toast.LENGTH_SHORT).show();
			break;
			
		case R.id.ib_my_kitchen_label_leader_board:
			Toast.makeText(getActivity(), "改功能尚未实现，敬请期待~~", Toast.LENGTH_SHORT).show();
			break;
		}
	}	
	
	class GalleryAdapter extends PagerAdapter{

		private Context context;
		private Bitmap[] bitmaps;
		
		public GalleryAdapter(Context context, Bitmap[] bitmaps) {
			super();
			this.context = context;
			this.bitmaps = bitmaps;
		}

		@Override
		public int getCount() {
			return bitmaps.length;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}
		
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			ImageView imageView = new ImageView(getActivity());
	        imageView.setImageBitmap(bitmaps[position]);
	        ((ViewPager)container).addView(imageView, 0);
	        return imageView;
		}
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			((ViewPager)container).removeView((ImageView)object);
		}
		
	}
	
}

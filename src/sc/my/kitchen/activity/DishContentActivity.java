package sc.my.kitchen.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import sc.my.kitchen.adapter.DishContentAdapter;
import sc.my.kitchen.entity.DishByCId;
import sc.my.kitchen.entity.StepByCId;
import sc.my.kitchen.model.CartModel;
import sc.my.kitchen.model.CollectionModel;
import sc.my.kitchen.util.AppManager;
import sc.my.kitchen.util.ImageLoader;
import sc.my.kitchen.util.QQUtils;
import sc.my.mykitchen.R;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

public class DishContentActivity extends Activity {

	private ImageView ivDish;
	private ImageButton ibShareToQQ;
	private TextView tvName;
	private TextView tvTags;
	private TextView tvImtro;
	private TextView tvIngredients;
	private TextView tvBurden;
	private ListView lvBehavior;
	private Button btnAddToCollection;
	private Button btnAddToBasket;
	private DishByCId dish;
	private List<StepByCId> steps;
	private ImageLoader imageLoader;
	private DishContentAdapter adapter;
	private CollectionModel model;
	private CartModel cartModel;
	private Tencent mTencent;
	private final String APP_ID = "1105680491";
	private ScrollView scrollView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dish_content);
		AppManager.getAppManager().addActivity(this);

		initViews();
		
		scrollView.smoothScrollTo(0, 20);

		mTencent = Tencent.createInstance(APP_ID, DishContentActivity.this);
		model = new CollectionModel();
		cartModel = new CartModel();

		imageLoader = new ImageLoader(this, lvBehavior);

		dish = (DishByCId) getIntent().getSerializableExtra("dish_content");
		Log.i("sc", "DishContentActivity --> dish " + dish.toString());

		steps = dish.getSteps();
		Log.i("sc", "DishContentActivity --> steps " + steps.toString());

		setViews();

		setListeners();

	}

	private void initViews() {
		ivDish = (ImageView) findViewById(R.id.iv_dish_coontent);
		tvName = (TextView) findViewById(R.id.tv_dish_content_name);
		tvTags = (TextView) findViewById(R.id.tv_dish_content_tags);
		tvImtro = (TextView) findViewById(R.id.tv_dish_content_imtro);
		tvIngredients = (TextView) findViewById(R.id.tv_dish_content_ingredients);
		tvBurden = (TextView) findViewById(R.id.tv_dish_content_burden);
		lvBehavior = (ListView) findViewById(R.id.lv_dish_content_behavior);
		btnAddToCollection = (Button) findViewById(R.id.btn_add_to_collection);
		btnAddToBasket = (Button) findViewById(R.id.btn_add_to_basket);
		ibShareToQQ = (ImageButton) findViewById(R.id.ib_share_qq);
		scrollView = (ScrollView) findViewById(R.id.sv_dish_content);
	}

	private void setViews() {
		imageLoader.displayImage(ivDish, dish.getAlbum());
		tvName.setText(dish.getTitle());
		tvTags.setText("   "+dish.getTags());
		tvImtro.setText(dish.getImtro());
		tvIngredients.setText("   "+dish.getIngredients());
		tvBurden.setText("   "+dish.getBurden());

		adapter = new DishContentAdapter(DishContentActivity.this, steps);
		lvBehavior.setAdapter(adapter);
		setListViewHeightBasedOnChildren(lvBehavior);
	}

	public static void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}

	private void setListeners() {
		btnAddToCollection.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean collectResult = model.addDish(dish);
				if (collectResult == true) {
					Toast.makeText(DishContentActivity.this, "成功添加到我的收藏~",
							Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(DishContentActivity.this, "添加到收藏失败，已存在该菜式~",
							Toast.LENGTH_LONG).show();
				}
			}
		});

		btnAddToBasket.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean cartResult = cartModel.addDishToCart(dish);
				if (cartResult == true) {
					Toast.makeText(DishContentActivity.this, "成功添加到菜篮子~",
							Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(DishContentActivity.this,
							"添加到菜篮子失败，已存在该菜式~", Toast.LENGTH_LONG).show();
				}
			}
		});

		//分享整个页面到QQ
		ibShareToQQ.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				try {
					Intent share = new Intent(Intent.ACTION_SEND);
					ComponentName component = new ComponentName(
							"com.tencent.mobileqq",
							"com.tencent.mobileqq.activity.JumpActivity");
					share.setComponent(component);

					String fileName =  System.currentTimeMillis() + ".jpg";
					
					Bitmap bm = getBitmapByView(scrollView);
					saveToSD(bm,Environment.getExternalStorageDirectory()
							.getPath() + "/temp_image" ,fileName);
					File file = new File(Environment.getExternalStorageDirectory()
							+ "/temp_image" + fileName);
					
					share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
					share.setType("*/*");
					startActivity(Intent.createChooser(share, "发送"));
				} catch (IOException e) {
					e.printStackTrace();
				}			
			}
		});

	}

	private class BaseUIListener implements IUiListener {

		@Override
		public void onCancel() {
			QQUtils.toastMessage(DishContentActivity.this, "onCancel: ");
		}

		@Override
		public void onComplete(Object arg0) {
			QQUtils.toastMessage(DishContentActivity.this, "分享成功");
		}

		@Override
		public void onError(UiError arg0) {
			QQUtils.toastMessage(DishContentActivity.this, "onError: "
					+ arg0.errorDetail);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		BaseUIListener listener = new BaseUIListener();
	}
	
	
	public static Bitmap getBitmapByView(ScrollView scrollView) {
        int h = 0;
        Bitmap bitmap = null;
        // 获取listView实际高度
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();
            scrollView.getChildAt(i).setBackgroundResource(R.drawable.bg_scrollview_shot);
        }
        Log.d("sc", "实际高度:" + h);
        Log.d("sc", " 高度:" + scrollView.getHeight());
        // 创建对应大小的bitmap
        bitmap = Bitmap.createBitmap(scrollView.getWidth(), h,
                Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        scrollView.draw(canvas);
        // 测试输出
        FileOutputStream out = null;
        try {
            out = new FileOutputStream("/sdcard/screen_test.png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            if (null != out) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                out.flush();
                out.close();
            }
        } catch (IOException e) {
            // TODO: handle exception
        }
        return bitmap;
    }
	
	private void saveToSD(Bitmap bmp, String dirName,String fileName) throws IOException {
	    // 判断sd卡是否存在
	    if (Environment.getExternalStorageState().equals(
	            Environment.MEDIA_MOUNTED)) {
	    	File dir = new File(dirName);
	    	// 判断文件夹是否存在，不存在则创建
	    	if(!dir.exists()){
	    		dir.mkdir();
	    	}
	    	
	        File file = new File(dirName + fileName);
	        // 判断文件是否存在，不存在则创建
	        if (!file.exists()) {
	            file.createNewFile();
	        }
	 
	        FileOutputStream fos = null;
	        try {
	            fos = new FileOutputStream(file);
	            if (fos != null) {
	                // 第一参数是图片格式，第二个是图片质量，第三个是输出流
	                bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
	                // 用完关闭
	                fos.flush();
	                fos.close();
	            }
	        } catch (FileNotFoundException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	    }
	}

}

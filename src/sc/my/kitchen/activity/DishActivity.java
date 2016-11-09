package sc.my.kitchen.activity;

import java.util.List;

import sc.my.kitchen.adapter.DishByCIdAdapter;
import sc.my.kitchen.entity.DishByCId;
import sc.my.kitchen.model.MenuModel;
import sc.my.kitchen.util.AppManager;
import sc.my.kitchen.util.DialogUtils;
import sc.my.kitchen.util.DishByCIdCallBack;
import sc.my.mykitchen.R;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DishActivity extends Activity {

	private TextView tvName;
	private ListView lvDish;
	private MenuModel model;
	private List<DishByCId> dishes;
	private DishByCIdAdapter adapter;
	private String id;
	private String name;
	private String parentId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dish);
		AppManager.getAppManager().addActivity(this);
		
		//初始化控件
		initviews();
		
		setListeners();
		
		id = getIntent().getStringExtra("id");
		name = getIntent().getStringExtra("menu_name");
		tvName.setText(name);
		parentId = getIntent().getStringExtra("parentid");
		
		model = new MenuModel();
		model.searchDishByCId(id, 0, 10, new DishByCIdCallBack() {
			
			@Override
			public void onDishByCIdLoaded(List<DishByCId> dishes) {
				DishActivity.this.dishes = dishes;
				setAdapter();
			}
		});
		
	}

	private void setListeners() {
		lvDish.setOnScrollListener(new OnScrollListener() {
			boolean isBottom = false;
			boolean isRequesting = false;
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				switch (scrollState) {
				case OnScrollListener.SCROLL_STATE_IDLE:
					
					if (isBottom && !isRequesting) {
						isRequesting = true;
						model.searchDishByCId(id, DishActivity.this.dishes.size(), 10, new DishByCIdCallBack() {
							
							@Override
							public void onDishByCIdLoaded(List<DishByCId> dishes) {
								if (dishes.isEmpty() || dishes == null) {
									Toast.makeText(DishActivity.this, "没有更多数据了", Toast.LENGTH_LONG).show();
									isRequesting = false;
									return;
								}
								else {
									DishActivity.this.dishes.addAll(dishes);
									adapter.notifyDataSetChanged();
									isRequesting = false;
								}
							}
						});
					}
					
					break;

				case OnScrollListener.SCROLL_STATE_FLING:
					break;
				case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
					break;
				}
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				if(firstVisibleItem + visibleItemCount == totalItemCount){
					isBottom = true;
				}else{
					isBottom = false;
				}
			}
		});
		
		lvDish.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Dialog dialog = DialogUtils.createLoadingDialog(DishActivity.this, "正在加载中");
				dialog.show();	
				DishByCId dish = DishActivity.this.dishes.get(position);
				Intent intent = new Intent(DishActivity.this, DishContentActivity.class);
				intent.putExtra("dish_content", dish);
				for(int i = 0; i < dish.getSteps().size(); i++){
					Log.i("sc", "DishActivity" + dish.getSteps().get(i).getStep());
				}
				startActivity(intent);
			}
			
		});
		
	}

	private void initviews() {
		tvName = (TextView) findViewById(R.id.tv_dish_title_bar);
		lvDish = (ListView) findViewById(R.id.lv_dish);
	}

	private void setAdapter(){
		adapter = new DishByCIdAdapter(this, dishes, lvDish);
		lvDish.setAdapter(adapter);
	}
	
}

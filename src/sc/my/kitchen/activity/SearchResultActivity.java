package sc.my.kitchen.activity;

import java.util.ArrayList;
import java.util.List;

import sc.my.kitchen.adapter.DishByCIdAdapter;
import sc.my.kitchen.entity.DishByCId;
import sc.my.kitchen.model.MenuModel;
import sc.my.kitchen.util.AppManager;
import sc.my.kitchen.util.DialogUtils;
import sc.my.kitchen.util.DishByNameCallBack;
import sc.my.mykitchen.R;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class SearchResultActivity extends Activity implements OnClickListener{

	private ImageButton ibBack;
	private ImageButton ibSearch;
	private EditText etSearch;
	private TextView tvEmptyList;
	private ListView lvSearchResult;
	private MenuModel model;
	private DishByCIdAdapter adapter;
	private List<DishByCId> dishes;
	private ProgressDialog progressDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_result);
		AppManager.getAppManager().addActivity(this);
		
		model = new MenuModel();
		dishes = new ArrayList<DishByCId>();
		
		initViews();
		
		setListeners();
		
		lvSearchResult.setEmptyView(tvEmptyList);
		
	}

	private void initViews(){
		ibBack = (ImageButton) findViewById(R.id.ib_search_result_back);
		ibSearch = (ImageButton) findViewById(R.id.ib_search);
		etSearch = (EditText) findViewById(R.id.et_search);
		lvSearchResult = (ListView) findViewById(R.id.lv_search_result);
		tvEmptyList = (TextView) findViewById(R.id.tv_empty_search);
	}
	
	private void setListeners(){
		ibBack.setOnClickListener(this);
		ibSearch.setOnClickListener(this);
		lvSearchResult.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				DishByCId dish = SearchResultActivity.this.dishes.get(position);
				Intent intent = new Intent(SearchResultActivity.this, DishContentActivity.class);
				intent.putExtra("dish_content", dish);
				startActivity(intent);
			}
		});
		
		lvSearchResult.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				boolean isBottom = false;
				boolean isRequesting = false;
				
				switch (scrollState) {
				case OnScrollListener.SCROLL_STATE_IDLE:
					isRequesting = true;
					String menu = etSearch.getText().toString();
					model.searchDishByName(menu, 10, dishes.size(), new DishByNameCallBack() {
						
						@Override
						public void onDishByNameLoaded(List<DishByCId> dish) {
							
						}
					});
					
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
				
			}
		});
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ib_search_result_back:
			finish();
			break;

		case R.id.ib_search:
			String menu = etSearch.getText().toString();
			
			Dialog dialog = DialogUtils.createLoadingDialog(SearchResultActivity.this, "正在加载中");
			dialog.show();	
			
			model.searchDishByName(menu, 10, 0, new DishByNameCallBack() {
				
				@Override
				public void onDishByNameLoaded(List<DishByCId> dish) {
					
					SearchResultActivity.this.dishes = dish;
					if (dish == null) {
						lvSearchResult.setVisibility(View.INVISIBLE);
					}   
					else {
						progressDialog.dismiss();
						lvSearchResult.setVisibility(View.VISIBLE);
						adapter = new DishByCIdAdapter(SearchResultActivity.this, dish,lvSearchResult);
						lvSearchResult.setAdapter(adapter);
					}
				
				}
			});
			break;

		}
	}
	
}

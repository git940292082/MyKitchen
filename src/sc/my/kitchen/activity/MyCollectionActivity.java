package sc.my.kitchen.activity;

import java.util.List;

import sc.my.kitchen.adapter.DishItemAdapter;
import sc.my.kitchen.app.KitchenApplication;
import sc.my.kitchen.entity.DishByCId;
import sc.my.kitchen.model.CollectionModel;
import sc.my.kitchen.util.AppManager;
import sc.my.mykitchen.R;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class MyCollectionActivity extends Activity {

	private List<DishByCId> dishes;
	private ListView lvMyCollection;
	private DishItemAdapter collectionAdapter;
	private CollectionModel model;
	private TextView tvEmptyCollection;
	private DishByCId dish;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_collection);
		AppManager.getAppManager().addActivity(this);
		
		dishes = KitchenApplication.getCollection().getDishes();
		
		initView();
		
		setAdapter();
		
		setListeners();
		
		lvMyCollection.setEmptyView(tvEmptyCollection);
		
	}

	private void initView() {
		lvMyCollection = (ListView) findViewById(R.id.lv_my_collection);
		tvEmptyCollection = (TextView) findViewById(R.id.tv_empty_collection);
	}

	private void setAdapter() {
		collectionAdapter = new DishItemAdapter(this, dishes, lvMyCollection);
		lvMyCollection.setAdapter(collectionAdapter);
	}

	private void setListeners() {
		lvMyCollection.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				 Log.e("sc", "onItemClick position=" + position);
					
//				 dish = MyCollectionActivity.this.dishes.get(position);
//				 
//				 Intent intent = new Intent(MyCollectionActivity.this, DishContentActivity.class);
//				 intent.putExtra("dish_content_collection", dish);
//				 for(int i = 0; i < dish.getSteps().size(); i++){
//						Log.i("sc", "MyCollectionActivity" + dish.getSteps().get(i).getStep());
//					}
//				 startActivity(intent);
				return false;
			}
		});
	}
	
}

package sc.my.kitchen.adapter;

import java.util.List;

import sc.my.kitchen.entity.DishByCId;
import sc.my.kitchen.model.CollectionModel;
import sc.my.kitchen.ui.SlideView;
import sc.my.kitchen.ui.SlideView.OnSlideListener;
import sc.my.kitchen.util.ImageLoader;
import sc.my.mykitchen.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class DishItemAdapter extends BaseAdapter 
		implements OnSlideListener{

	private Context context;
	private List<DishByCId> dishes;
	private LayoutInflater inflater;
	private ImageLoader imageLoader;
	private SlideView mLastSlideViewWithStatusOn;
	private CollectionModel model;
	
	
	public DishItemAdapter(Context context, List<DishByCId> dishes,ListView listView) {
		super();
		this.context = context;
		this.dishes = dishes;
		this.inflater = LayoutInflater.from(context);
		this.imageLoader = new ImageLoader(context, listView);
		model = new CollectionModel();
	}

	@Override
	public int getCount() {
		return dishes.size();
	}

	@Override
	public Object getItem(int position) {
		return dishes.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		DishByCId dish = dishes.get(position);
		ViewHolder holder = null;
		SlideView slideView = (SlideView) convertView;
		
		if (slideView == null) {
			View view = inflater.inflate(R.layout.list_dish_item, null);
			
			slideView = new SlideView(context);
			slideView.setContentView(view);
			
			holder = new ViewHolder(slideView);
			slideView.setOnSlideListener(this);
			slideView.setTag(holder);
		}else {
			holder = (ViewHolder) slideView.getTag();
		}
		
		dish.slideView = slideView;
		dish.slideView.shrink();
		
		holder.tvDishName.setText(dish.getTitle());
		holder.tvIngredients.setText(dish.getIngredients());
		
		imageLoader.displayImage(holder.ivDish, dish.getAlbum());
		
		holder.deleteHolder.setOnClickListener(new DeleteListener(position));
		
		return slideView;
	}

	class ViewHolder{
		ImageView ivDish;
		TextView tvDishName;
		TextView tvIngredients;
		ViewGroup deleteHolder;
		
		ViewHolder(View view) {
			ivDish = (ImageView) view.findViewById(R.id.iv_dish);
			tvDishName = (TextView) view.findViewById(R.id.tv_dish_name);
			tvIngredients = (TextView) view.findViewById(R.id.tv_dish_ingredients);
			deleteHolder = (ViewGroup) view.findViewById(R.id.holder);
		}
	}

	@Override
	public void onSlide(View view, int status) {
		 if (mLastSlideViewWithStatusOn != null && mLastSlideViewWithStatusOn != view) {
             mLastSlideViewWithStatusOn.shrink();
         }

         if (status == SLIDE_STATUS_ON) {
             mLastSlideViewWithStatusOn = (SlideView) view;
         }
	}

	
	class DeleteListener implements OnClickListener{
		private int position;

		public DeleteListener(int position) {
			super();
			this.position = position;
		}

		@Override
		public void onClick(View v) {
			DishByCId d = dishes.get(position);
			model.deleteDish(d.getId());
			DishItemAdapter.this.notifyDataSetChanged();
		}
	}
	
}

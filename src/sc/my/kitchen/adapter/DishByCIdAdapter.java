package sc.my.kitchen.adapter;

import java.util.List;

import sc.my.kitchen.entity.DishByCId;
import sc.my.kitchen.util.ImageLoader;
import sc.my.mykitchen.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class DishByCIdAdapter extends BaseAdapter{

	private Context context;
	private List<DishByCId> dishes;
	private LayoutInflater inflater;
	private ImageLoader imageLoader;
	
	

	public DishByCIdAdapter(Context context, List<DishByCId> dishes, ListView listView) {
		super();
		this.context = context;
		this.dishes = dishes;
		this.inflater = LayoutInflater.from(context);
		this.imageLoader = new ImageLoader(context, listView);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		DishByCId dish = dishes.get(position);
		ViewHolder holder = null;
		
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.list_dish_item, null);
			holder = new ViewHolder();
			holder.ivDish = (ImageView) convertView.findViewById(R.id.iv_dish);
			holder.tvDishName = (TextView) convertView.findViewById(R.id.tv_dish_name);
			holder.tvIngredients = (TextView) convertView.findViewById(R.id.tv_dish_ingredients);
			convertView.setTag(holder);
		}
		
		holder = (ViewHolder) convertView.getTag();
		holder.tvDishName.setText(dish.getTitle());
		holder.tvIngredients.setText(dish.getIngredients());
		
		imageLoader.displayImage(holder.ivDish, dish.getAlbum());
		
		return convertView;
	}

	class ViewHolder{
		ImageView ivDish;
		TextView tvDishName;
		TextView tvIngredients;
	}

	@Override
	public int getCount() {
		return dishes.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}
	
}

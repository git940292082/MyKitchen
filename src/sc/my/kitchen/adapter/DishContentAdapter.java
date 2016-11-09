package sc.my.kitchen.adapter;

import java.util.List;

import sc.my.kitchen.entity.StepByCId;
import sc.my.kitchen.util.BitmapCache;
import sc.my.mykitchen.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.Volley;

public class DishContentAdapter extends BaseAdapter{

	private Context context;
	private List<StepByCId> steps;
	private LayoutInflater inflater;
	private ImageLoader imageLoader;
	private RequestQueue mQueue;
	private ImageListener listener;
	
	
	public DishContentAdapter(Context context, List<StepByCId> steps) {
		super();
		this.context = context;
		this.steps = steps;
		this.inflater = LayoutInflater.from(context);
		this.mQueue = Volley.newRequestQueue(context);
		this.imageLoader = new ImageLoader(mQueue, new BitmapCache());
	}

	@Override
	public int getCount() {
		return steps.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		StepByCId step = steps.get(position);
		ViewHolder holder = null;
		
		if (convertView ==null) {
			convertView  = inflater.inflate(R.layout.list_dish_content_item, null);
			holder = new ViewHolder();
			holder.ivBehavior = (ImageView) convertView.findViewById(R.id.iv_behavior);
			holder.tvBehavior = (TextView) convertView.findViewById(R.id.tv_behavior);
			convertView.setTag(holder);
		}
		holder = (ViewHolder) convertView.getTag();
		
		listener = ImageLoader.getImageListener(holder.ivBehavior,
					R.drawable.ic_launcher, R.drawable.ic_launcher);
		imageLoader.get(step.getImg(), listener);
		
		holder.tvBehavior.setText(step.getStep());
		
		return convertView;
	}

	class ViewHolder{
		ImageView ivBehavior;
		TextView tvBehavior;
	}
			
}

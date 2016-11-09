package sc.my.kitchen.adapter;

import java.util.List;

import sc.my.kitchen.entity.MenuSort;
import sc.my.mykitchen.R;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuSortListAdapter extends MyAdapter<MenuSort>{

	public MenuSortListAdapter(Context context, List<MenuSort> data) {
		super(context, data);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		MenuSort sort = getData().get(position);
		ViewHolder holder = null;
		
		if (convertView == null) {
			convertView = getLayoutInflater().inflate(R.layout.list_menu_sort_item, null);
			holder = new ViewHolder();
			holder.tvName = (TextView) convertView.findViewById(R.id.tv_menu_sort_name);
			holder.ivArrow = (ImageView) convertView.findViewById(R.id.iv_menu_sort_arrow);
			convertView.setTag(holder);
		}
		
		holder = (ViewHolder) convertView.getTag();
		holder.tvName.setText(sort.getName());
		holder.ivArrow.setImageResource(R.drawable.image_view_menu_sort_arrow);
		
		
		return convertView;
	}

	class ViewHolder{
		TextView tvName;
		ImageView ivArrow;
	}
	
}

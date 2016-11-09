package sc.my.kitchen.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import sc.my.kitchen.app.KitchenApplication;
import sc.my.kitchen.entity.DishByCId;
import sc.my.mykitchen.R;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ExpandableListView;
import android.widget.TextView;


public class ShoppingCartFragment extends Fragment{
	
	private ExpandableListView lvCart;
	private List<DishByCId> dishes;
	private CartBaseExpandableListAdapter adapter;
	private List<String> groupTitle = new ArrayList<String>();
	private Map<Integer, List<String>> childMap = new HashMap<Integer, List<String>>();
	private List<String> childList;
	private Button btnDelete;
	private CheckBox cbSelect;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_shopping_cart, null);
		
		
		cbSelect = (CheckBox) view.findViewById(R.id.cb_shopping_cart_select_all);
		btnDelete = (Button) view.findViewById(R.id.btn_shopping_cart_delete);
		
		lvCart = (ExpandableListView) view.findViewById(R.id.lv_shopping_cart);
		
		setListeners();
		
		return view;
	}
	
	
	@Override
	public void onResume() {
		super.onResume();
		
		dishes = KitchenApplication.getApp().getCart().getDishes();
		for(int i = 0; i < dishes.size(); i++){
			groupTitle.add(dishes.get(i).getTitle());
		}
		
		for(int i = 0; i < dishes.size(); i++){
			
			String[] a = dishes.get(i).getIngredients().split(";");
			childList = new ArrayList<String>();
			for(int j = 0;  j < a.length; j++){
				childList.add(a[j]);
			}
			childMap.put(i, childList);
			Log.i("sc", "childList" + childList.toString());
			
		}

		
		adapter = new CartBaseExpandableListAdapter(groupTitle, childMap, getActivity());
		lvCart.setAdapter(adapter);
		adapter.refresh();
	}
	
	private void setListeners() {
		cbSelect.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					for(int i = 0; i < dishes.size(); i++){
						CartBaseExpandableListAdapter.isSelected.put(i, true);
					}
				}
				else {
					for(int i = 0; i < dishes.size(); i++){
						CartBaseExpandableListAdapter.isSelected.put(i, false);
					}
				}
				adapter.refresh();
			}
		});
		
		btnDelete.setOnClickListener(new OnClickListener() {
			
			
			@Override
			public void onClick(View v) {
				
				Log.i("sc", "OnClick");
				
				Set<Integer> set = CartBaseExpandableListAdapter.isSelected.keySet();
				Log.i("sc", "Set--> " + set.toString() );
				for(int x = 0; x < set.size(); x++){
					
					boolean checkState =  CartBaseExpandableListAdapter.isSelected.get(x);
					Log.i("sc", "checkState --> " + checkState);
					
					if (checkState) {
						Log.i("sc", "id --> "+ dishes.get(x).getId() );
						KitchenApplication.getApp().deleteDishInCart(dishes.get(0).getId());
					}
				}
				groupTitle.clear();
				childMap.clear();
				
				dishes = KitchenApplication.getApp().getCart().getDishes();
				for(int i = 0; i < dishes.size(); i++){
					groupTitle.add(dishes.get(i).getTitle());
				}
				
				for(int i = 0; i < dishes.size(); i++){
					
					String[] a = dishes.get(i).getIngredients().split(";");
					childList = new ArrayList<String>();
					for(int j = 0;  j < a.length; j++){
						childList.add(a[j]);
					}
					childMap.put(i, childList);
					Log.i("sc", "childList" + childList.toString());
					
				}
				adapter.refresh();
			}
		});
		
	}

	public static class CartBaseExpandableListAdapter extends BaseExpandableListAdapter{

		private List<String> groupTitle;
		private Map<Integer, List<String>> childMap;
		private Context context;
		private static  HashMap<Integer, Boolean> isSelected = new HashMap<Integer, Boolean>();
		private Handler handler;
		
		public CartBaseExpandableListAdapter(List<String> groupTitle,
				Map<Integer, List<String>> childMap, Context context) {
			super();
			this.groupTitle = groupTitle;
			this.childMap = childMap;
			this.context = context;
			init();
			handler = new Handler(){
				@Override
				public void handleMessage(Message msg) {
					notifyDataSetChanged();
					super.handleMessage(msg);
				}
			};
		}
		
		
		public void refresh(){
			handler.sendMessage(new Message());
		}

		private void init() {
			isSelected = new HashMap<Integer, Boolean>();
			for(int i =0; i < isSelected.size(); i++){
				isSelected.put(i,false);
			}
		}

		@Override
		public int getGroupCount() {
			return groupTitle.size();
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			return childMap.get(groupPosition).size();
		}

		@Override
		public Object getGroup(int groupPosition) {
			return groupTitle.get(groupPosition);
		}

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			Log.i("sc", "getChild" + childMap.get(groupPosition).get(childPosition));
			return childMap.get(groupPosition).get(childPosition);
		}

		@Override
		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			
			GroupHolder groupHolder = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(R.layout.list_cart_group_item, null);
				groupHolder = new GroupHolder();
				groupHolder.tvMenu = (TextView) convertView.findViewById(R.id.tv_cart_group_menu);
				groupHolder.cbSelect = (CheckBox) convertView.findViewById(R.id.cb_cart_group_select);
				convertView.setTag(groupHolder);
			}
			else {
				groupHolder = (GroupHolder) convertView.getTag();
			}
			
			
			groupHolder.tvMenu.setText(groupTitle.get(groupPosition));
			if (isSelected.size() != 0) {
				groupHolder.cbSelect.setChecked(isSelected.get(groupPosition));
			}
			
			
			return convertView;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			
			ChildHolder childHolder = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(R.layout.list_cart_child_item, null);
				childHolder = new ChildHolder();
				childHolder.tvIngredients = (TextView) convertView.findViewById(R.id.tv_cart_child_ingredients);
				convertView.setTag(childHolder);
			}
			else {
				childHolder = (ChildHolder) convertView.getTag();
			}
			
			childHolder.tvIngredients.setText(childMap.get(groupPosition).get(childPosition));
			
			return convertView;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}

		private class GroupHolder{
			TextView tvMenu;
			CheckBox cbSelect;
		}
		
		private class ChildHolder{
			TextView tvIngredients;
		}
		
	}
	
}

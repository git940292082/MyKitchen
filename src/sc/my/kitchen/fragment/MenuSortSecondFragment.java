package sc.my.kitchen.fragment;

import java.util.List;

import sc.my.kitchen.entity.MenuSort;
import sc.my.kitchen.model.MenuModel;
import sc.my.kitchen.util.MenuSortCallback;
import sc.my.mykitchen.R;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MenuSortSecondFragment extends Fragment implements View.OnClickListener{
	
	//创建model类，调用业务层方法
		private MenuModel model;
		/**
		 * 回调接口，将List集合传到Activity
		 */
		CallBackSecond callBackSecond;
		/**
		 * 其他菜品
		 */
		private Button btnOtherDishes;
		/**
		 * 人群
		 */
		private Button btnCrowd;
		/**
		 * 疾病
		 */
		private Button btnDisease;
		/**
		 * 畜肉类
		 */
		private Button btnMeat;
		/**
		 * 禽蛋类
		 */
		private Button btnPoultryAndEggs;
		/**
		 * 水产类
		 */
		private Button btnFisheries;
		/**
		 * 蔬菜类
		 */
		private Button btnVegetables;
		/**
		 * 水果类
		 */
		private Button btnFruits;
		/**
		 * 米面豆乳类
		 */
		private Button btnRice;
		/**
		 * 日常
		 */
		private Button btnDaily;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i("sc", "MenuSortSecondFragment");
		
		View view = inflater.inflate(R.layout.fragment_menu_sort_second, null);
		
		//初始化控件
		initViews(view);
		
		setListeners();
		
		model = new MenuModel();
		
		return view;
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		callBackSecond = (CallBackSecond) getActivity();
	}
	
	private void initViews(View view) {
		btnOtherDishes = (Button) view.findViewById(R.id.ib_other_dishes);
		btnCrowd = (Button) view.findViewById(R.id.ib_crowd);
		btnDisease = (Button) view.findViewById(R.id.ib_disease);
		btnMeat = (Button) view.findViewById(R.id.ib_meat);
		btnPoultryAndEggs = (Button) view.findViewById(R.id.ib_poultry_and_eggs);
		
		btnFisheries = (Button) view.findViewById(R.id.ib_fisheries);
		btnVegetables = (Button) view.findViewById(R.id.ib_vegetables);
		btnFruits = (Button) view.findViewById(R.id.ib_fruits);
		btnRice = (Button) view.findViewById(R.id.ib_rice_soymilk_category);
		btnDaily = (Button) view.findViewById(R.id.ib_daily);
	}
	
	private void setListeners() {
		btnOtherDishes.setOnClickListener(this);
		btnCrowd.setOnClickListener(this);
		btnDisease.setOnClickListener(this);
		btnMeat.setOnClickListener(this);
		btnPoultryAndEggs.setOnClickListener(this);
		btnFisheries.setOnClickListener(this);
		btnVegetables.setOnClickListener(this);
		btnFruits.setOnClickListener(this);
		btnRice.setOnClickListener(this);
		btnDaily.setOnClickListener(this);
	}
	
	public interface CallBackSecond{
		public void sendMessageSecond(List<MenuSort> sorts);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		//当点击的是其他菜品
		case R.id.ib_other_dishes:
			model.searchSortList(10011, new MenuSortCallback() {
				
				@Override
				public void onLoadMenuSort(List<MenuSort> sorts) {
					Log.i("sc", "MenuSortFirstFragment --> 10011" + sorts.toString());
					callBackSecond.sendMessageSecond(sorts);
				}
			});
			break;
			
		//当点击的是人群
		case R.id.ib_crowd:
			model.searchSortList(10012, new MenuSortCallback() {
				
				@Override
				public void onLoadMenuSort(List<MenuSort> sorts) {
					Log.i("sc", "MenuSortFirstFragment --> 10012" + sorts.toString());
					callBackSecond.sendMessageSecond(sorts);
				}
			});
			
			break;
			
		//当点击的是疾病
		case R.id.ib_disease:
			model.searchSortList(10013, new MenuSortCallback() {
				
				@Override
				public void onLoadMenuSort(List<MenuSort> sorts) {
					Log.i("sc", "MenuSortFirstFragment --> 10013" + sorts.toString());
					callBackSecond.sendMessageSecond(sorts);
				}
			});
			break;
			
		//当点击的是畜肉类	
		case R.id.ib_meat:
			model.searchSortList(10014, new MenuSortCallback() {
				
				@Override
				public void onLoadMenuSort(List<MenuSort> sorts) {
					Log.i("sc", "MenuSortFirstFragment --> 10014" + sorts.toString());
					callBackSecond.sendMessageSecond(sorts);
				}
			});
			break;
			
		//当点击的是禽蛋类	
		case R.id.ib_poultry_and_eggs:
			model.searchSortList(10015, new MenuSortCallback() {
				
				@Override
				public void onLoadMenuSort(List<MenuSort> sorts) {
					Log.i("sc", "MenuSortFirstFragment --> 10015" + sorts.toString());
					callBackSecond.sendMessageSecond(sorts);
				}
			});
			break;
			
		//当点击的是水产类	
		case R.id.ib_fisheries:
			model.searchSortList(10016, new MenuSortCallback() {
				
				@Override
				public void onLoadMenuSort(List<MenuSort> sorts) {
					Log.i("sc", "MenuSortFirstFragment --> 10016" + sorts.toString());
					callBackSecond.sendMessageSecond(sorts);
				}
			});
			break;
			
		//当点击的是蔬菜类	
		case R.id.ib_vegetables:
			model.searchSortList(10017, new MenuSortCallback() {
				
				@Override
				public void onLoadMenuSort(List<MenuSort> sorts) {
					Log.i("sc", "MenuSortFirstFragment --> 10017" + sorts.toString());
					callBackSecond.sendMessageSecond(sorts);
				}
			});
			break;
			
		//当点击的是水果类
		case R.id.ib_fruits:
			model.searchSortList(10018, new MenuSortCallback() {
				
				@Override
				public void onLoadMenuSort(List<MenuSort> sorts) {
					Log.i("sc", "MenuSortFirstFragment --> 10018" + sorts.toString());
					callBackSecond.sendMessageSecond(sorts);
				}
			});
			break;
			
		//当点击的是米面豆乳类
		case R.id.ib_rice_soymilk_category:
			model.searchSortList(10019, new MenuSortCallback() {
				
				@Override
				public void onLoadMenuSort(List<MenuSort> sorts) {
					Log.i("sc", "MenuSortFirstFragment --> 10019" + sorts.toString());
					callBackSecond.sendMessageSecond(sorts);
				}
			});
			break;
			
		//当点击的是日常
		case R.id.ib_daily:
			model.searchSortList(10020, new MenuSortCallback() {
				
				@Override
				public void onLoadMenuSort(List<MenuSort> sorts) {
					Log.i("sc", "MenuSortFirstFragment --> 10020" + sorts.toString());
					callBackSecond.sendMessageSecond(sorts);
				}
			});
			break;	
		
		}
	}
	
}

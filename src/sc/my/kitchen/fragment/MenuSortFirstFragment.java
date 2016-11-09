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

public class MenuSortFirstFragment extends Fragment implements View.OnClickListener{
	
	//创建model类，调用业务层方法
	private MenuModel model;
	/**
	 * 回调接口，将List集合传到Activity
	 */
	CallBackFirst callBackFirst;
	/**
	 * 菜式菜品
	 */
	private Button btnCuisineDishes;
	/**
	 * 菜系
	 */
	private Button btnDishes;
	/**
	 * 时令食材
	 */
	private Button btnIngredients;
	/**
	 * 功效
	 */
	private Button btnEffect;
	/**
	 * 场景
	 */
	private Button btnScenes;
	/**
	 * 工艺口味
	 */
	private Button btnProcessFlavors;
	/**
	 * 菜肴
	 */
	private Button btnDish;
	/**
	 * 主食
	 */
	private Button btnStapleFood;
	/**
	 * 西点
	 */
	private Button btnPastry;
	/**
	 * 汤羹饮品
	 */
	private Button btnSoupsDrinks;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i("sc", "MenuSortFirstFragment");
		
		View view = inflater.inflate(R.layout.fragment_menu_sort_first, null);
		
		
		
		//初始化控件
		initViews(view);
		
		setListeners();
		
		model = new MenuModel();
		
		
		return view;
	}

	private void initViews(View view) {
		btnCuisineDishes = (Button) view.findViewById(R.id.ib_cuisine_dishes);
		btnDishes = (Button) view.findViewById(R.id.ib_dishes);
		btnIngredients = (Button) view.findViewById(R.id.ib_ingredients);
		btnEffect = (Button) view.findViewById(R.id.ib_effect);
		btnScenes = (Button) view.findViewById(R.id.ib_scenes);
		
		btnProcessFlavors = (Button) view.findViewById(R.id.ib_process_flavors);
		btnDish = (Button) view.findViewById(R.id.ib_dish);
		btnStapleFood = (Button) view.findViewById(R.id.ib_staple_food);
		btnPastry = (Button) view.findViewById(R.id.ib_pastry);
		btnSoupsDrinks = (Button) view.findViewById(R.id.ib_soups_drinks);
	}
	
	private void setListeners() {
		btnCuisineDishes.setOnClickListener(this);
		btnDishes.setOnClickListener(this);
		btnIngredients.setOnClickListener(this);
		btnEffect.setOnClickListener(this);
		btnScenes.setOnClickListener(this);
		btnProcessFlavors.setOnClickListener(this);
		btnDish.setOnClickListener(this);
		btnStapleFood.setOnClickListener(this);
		btnPastry.setOnClickListener(this);
		btnSoupsDrinks.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		//当点击的是菜式菜品
		case R.id.ib_cuisine_dishes:
			model.searchSortList(10001, new MenuSortCallback() {
				
				@Override
				public void onLoadMenuSort(List<MenuSort> sorts) {
					Log.i("sc", "MenuSortFirstFragment --> 10001" + sorts.toString());
					callBackFirst.sendMessageFirst(sorts);
				}
			});
			break;
			
		//当点击的是菜系
		case R.id.ib_dishes:
			model.searchSortList(10002, new MenuSortCallback() {
				
				@Override
				public void onLoadMenuSort(List<MenuSort> sorts) {
					Log.i("sc", "MenuSortFirstFragment --> 10002" + sorts.toString());
					callBackFirst.sendMessageFirst(sorts);
				}
			});
			
			break;
			
		//当点击的是时令食材
		case R.id.ib_ingredients:
			model.searchSortList(10003, new MenuSortCallback() {
				
				@Override
				public void onLoadMenuSort(List<MenuSort> sorts) {
					Log.i("sc", "MenuSortFirstFragment --> 10003" + sorts.toString());
					callBackFirst.sendMessageFirst(sorts);
				}
			});
			break;
			
		//当点击的是功效	
		case R.id.ib_effect:
			model.searchSortList(10004, new MenuSortCallback() {
				
				@Override
				public void onLoadMenuSort(List<MenuSort> sorts) {
					Log.i("sc", "MenuSortFirstFragment --> 10004" + sorts.toString());
					callBackFirst.sendMessageFirst(sorts);
				}
			});
			break;
			
		//当点击的是场景	
		case R.id.ib_scenes:
			model.searchSortList(10005, new MenuSortCallback() {
				
				@Override
				public void onLoadMenuSort(List<MenuSort> sorts) {
					Log.i("sc", "MenuSortFirstFragment --> 10005" + sorts.toString());
					callBackFirst.sendMessageFirst(sorts);
				}
			});
			break;
			
		//当点击的是工艺口味	
		case R.id.ib_process_flavors:
			model.searchSortList(10006, new MenuSortCallback() {
				
				@Override
				public void onLoadMenuSort(List<MenuSort> sorts) {
					Log.i("sc", "MenuSortFirstFragment --> 10006" + sorts.toString());
					callBackFirst.sendMessageFirst(sorts);
				}
			});
			break;
			
		//当点击的是菜肴	
		case R.id.ib_dish:
			model.searchSortList(10007, new MenuSortCallback() {
				
				@Override
				public void onLoadMenuSort(List<MenuSort> sorts) {
					Log.i("sc", "MenuSortFirstFragment --> 10007" + sorts.toString());
					callBackFirst.sendMessageFirst(sorts);
				}
			});
			break;
			
		//当点击的是主食
		case R.id.ib_staple_food:
			model.searchSortList(10008, new MenuSortCallback() {
				
				@Override
				public void onLoadMenuSort(List<MenuSort> sorts) {
					Log.i("sc", "MenuSortFirstFragment --> 10008" + sorts.toString());
					callBackFirst.sendMessageFirst(sorts);
				}
			});
			break;
			
		//当点击的是西点
		case R.id.ib_pastry:
			model.searchSortList(10009, new MenuSortCallback() {
				
				@Override
				public void onLoadMenuSort(List<MenuSort> sorts) {
					Log.i("sc", "MenuSortFirstFragment --> 10009" + sorts.toString());
					callBackFirst.sendMessageFirst(sorts);
				}
			});
			break;
			
		//当点击的是汤羹饮品
		case R.id.ib_soups_drinks:
			model.searchSortList(10010, new MenuSortCallback() {
				
				@Override
				public void onLoadMenuSort(List<MenuSort> sorts) {
					Log.i("sc", "MenuSortFirstFragment --> 10010" + sorts.toString());
					callBackFirst.sendMessageFirst(sorts);
				}
			});
			break;	
		
		}
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		callBackFirst = (CallBackFirst) getActivity();
	}
	
	public interface CallBackFirst{
		public void sendMessageFirst(List<MenuSort> sorts);
	}

	
	
}

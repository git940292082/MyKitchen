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

public class MenuSortThirdFragment extends Fragment implements View.OnClickListener{
	
	//创建model类，调用业务层方法
	private MenuModel model;
	/**
	 * 回调接口，将List集合传到Activity
	 */
	CallBackThird callBackThird;
	/**
	 * 节日
	 */
	private Button btnFestival;
	/**
	 * 节气
	 */
	private Button btnFeasts;
	/**
	 * 基本工艺
	 */
	private Button btnBasicProcess;
	/**
	 * 其他工艺
	 */
	private Button btnotherArt;
	/**
	 * 基本口味
	 */
	private Button btnBasicTastes;
	/**
	 * 多元口味
	 */
	private Button btnMultipleFlavors;
	/**
	 * 水果味
	 */
	private Button btnFruity;
	/**
	 * 调味料
	 */
	private Button btnSeasoning;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i("sc", "MenuSortThirdFragment");
		
		View view = inflater.inflate(R.layout.fragment_menu_sort_third, null);
		
		
		//初始化控件
		initViews(view);
				
		setListeners();
				
		model = new MenuModel();
		
		
		return view;
	}
	
	private void initViews(View view) {
		btnFestival = (Button) view.findViewById(R.id.ib_festival);
		btnFeasts = (Button) view.findViewById(R.id.ib_feasts);
		btnBasicProcess = (Button) view.findViewById(R.id.ib_basic_process);
		btnotherArt = (Button) view.findViewById(R.id.ib_other_art);
		btnBasicTastes = (Button) view.findViewById(R.id.ib_basic_tastes);
		
		btnMultipleFlavors = (Button) view.findViewById(R.id.ib_multiple_flavors);
		btnFruity = (Button) view.findViewById(R.id.ib_fruity);
		btnSeasoning = (Button) view.findViewById(R.id.ib_seasoning);
	}
	
	private void setListeners() {
		btnFestival.setOnClickListener(this);
		btnFeasts.setOnClickListener(this);
		btnBasicProcess.setOnClickListener(this);
		btnotherArt.setOnClickListener(this);
		btnBasicTastes.setOnClickListener(this);
		btnMultipleFlavors.setOnClickListener(this);
		btnFruity.setOnClickListener(this);
		btnSeasoning.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		//当点击的是节日
		case R.id.ib_festival:
			model.searchSortList(10021, new MenuSortCallback() {
				
				@Override
				public void onLoadMenuSort(List<MenuSort> sorts) {
					Log.i("sc", "MenuSortFirstFragment --> 10021" + sorts.toString());
					callBackThird.sendMessageThird(sorts);
				}
			});
			break;
			
		//当点击的是节气
		case R.id.ib_feasts:
			model.searchSortList(10022, new MenuSortCallback() {
				
				@Override
				public void onLoadMenuSort(List<MenuSort> sorts) {
					Log.i("sc", "MenuSortFirstFragment --> 10022" + sorts.toString());
					callBackThird.sendMessageThird(sorts);
				}
			});
			
			break;
			
		//当点击的是基本工艺
		case R.id.ib_basic_process:
			model.searchSortList(10023, new MenuSortCallback() {
				
				@Override
				public void onLoadMenuSort(List<MenuSort> sorts) {
					Log.i("sc", "MenuSortFirstFragment --> 10023" + sorts.toString());
					callBackThird.sendMessageThird(sorts);
				}
			});
			break;
			
		//当点击的是其他工艺
		case R.id.ib_other_art:
			model.searchSortList(10024, new MenuSortCallback() {
				
				@Override
				public void onLoadMenuSort(List<MenuSort> sorts) {
					Log.i("sc", "MenuSortFirstFragment --> 10024" + sorts.toString());
					callBackThird.sendMessageThird(sorts);
				}
			});
			break;
			
		//当点击的是基本口味	
		case R.id.ib_basic_tastes:
			model.searchSortList(10025, new MenuSortCallback() {
				
				@Override
				public void onLoadMenuSort(List<MenuSort> sorts) {
					Log.i("sc", "MenuSortFirstFragment --> 10025" + sorts.toString());
					callBackThird.sendMessageThird(sorts);
				}
			});
			break;
			
		//当点击的是多元口味	
		case R.id.ib_multiple_flavors:
			model.searchSortList(10026, new MenuSortCallback() {
				
				@Override
				public void onLoadMenuSort(List<MenuSort> sorts) {
					Log.i("sc", "MenuSortFirstFragment --> 10026" + sorts.toString());
					callBackThird.sendMessageThird(sorts);
				}
			});
			break;
			
		//当点击的是水果味	
		case R.id.ib_fruity:
			model.searchSortList(10027, new MenuSortCallback() {
				
				@Override
				public void onLoadMenuSort(List<MenuSort> sorts) {
					Log.i("sc", "MenuSortFirstFragment --> 10027" + sorts.toString());
					callBackThird.sendMessageThird(sorts);
				}
			});
			break;
			
		//当点击的是调味料
		case R.id.ib_seasoning:
			model.searchSortList(10028, new MenuSortCallback() {
				
				@Override
				public void onLoadMenuSort(List<MenuSort> sorts) {
					Log.i("sc", "MenuSortFirstFragment --> 10028" + sorts.toString());
					callBackThird.sendMessageThird(sorts);
				}
			});
			break;
		
		}
	}
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		callBackThird = (CallBackThird) getActivity();
	}
	
	public interface CallBackThird{
		public void sendMessageThird(List<MenuSort> sorts);
	}

	
	
}

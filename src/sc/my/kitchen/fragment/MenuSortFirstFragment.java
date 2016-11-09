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
	
	//����model�࣬����ҵ��㷽��
	private MenuModel model;
	/**
	 * �ص��ӿڣ���List���ϴ���Activity
	 */
	CallBackFirst callBackFirst;
	/**
	 * ��ʽ��Ʒ
	 */
	private Button btnCuisineDishes;
	/**
	 * ��ϵ
	 */
	private Button btnDishes;
	/**
	 * ʱ��ʳ��
	 */
	private Button btnIngredients;
	/**
	 * ��Ч
	 */
	private Button btnEffect;
	/**
	 * ����
	 */
	private Button btnScenes;
	/**
	 * ���տ�ζ
	 */
	private Button btnProcessFlavors;
	/**
	 * ����
	 */
	private Button btnDish;
	/**
	 * ��ʳ
	 */
	private Button btnStapleFood;
	/**
	 * ����
	 */
	private Button btnPastry;
	/**
	 * ������Ʒ
	 */
	private Button btnSoupsDrinks;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i("sc", "MenuSortFirstFragment");
		
		View view = inflater.inflate(R.layout.fragment_menu_sort_first, null);
		
		
		
		//��ʼ���ؼ�
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
		//��������ǲ�ʽ��Ʒ
		case R.id.ib_cuisine_dishes:
			model.searchSortList(10001, new MenuSortCallback() {
				
				@Override
				public void onLoadMenuSort(List<MenuSort> sorts) {
					Log.i("sc", "MenuSortFirstFragment --> 10001" + sorts.toString());
					callBackFirst.sendMessageFirst(sorts);
				}
			});
			break;
			
		//��������ǲ�ϵ
		case R.id.ib_dishes:
			model.searchSortList(10002, new MenuSortCallback() {
				
				@Override
				public void onLoadMenuSort(List<MenuSort> sorts) {
					Log.i("sc", "MenuSortFirstFragment --> 10002" + sorts.toString());
					callBackFirst.sendMessageFirst(sorts);
				}
			});
			
			break;
			
		//���������ʱ��ʳ��
		case R.id.ib_ingredients:
			model.searchSortList(10003, new MenuSortCallback() {
				
				@Override
				public void onLoadMenuSort(List<MenuSort> sorts) {
					Log.i("sc", "MenuSortFirstFragment --> 10003" + sorts.toString());
					callBackFirst.sendMessageFirst(sorts);
				}
			});
			break;
			
		//��������ǹ�Ч	
		case R.id.ib_effect:
			model.searchSortList(10004, new MenuSortCallback() {
				
				@Override
				public void onLoadMenuSort(List<MenuSort> sorts) {
					Log.i("sc", "MenuSortFirstFragment --> 10004" + sorts.toString());
					callBackFirst.sendMessageFirst(sorts);
				}
			});
			break;
			
		//��������ǳ���	
		case R.id.ib_scenes:
			model.searchSortList(10005, new MenuSortCallback() {
				
				@Override
				public void onLoadMenuSort(List<MenuSort> sorts) {
					Log.i("sc", "MenuSortFirstFragment --> 10005" + sorts.toString());
					callBackFirst.sendMessageFirst(sorts);
				}
			});
			break;
			
		//��������ǹ��տ�ζ	
		case R.id.ib_process_flavors:
			model.searchSortList(10006, new MenuSortCallback() {
				
				@Override
				public void onLoadMenuSort(List<MenuSort> sorts) {
					Log.i("sc", "MenuSortFirstFragment --> 10006" + sorts.toString());
					callBackFirst.sendMessageFirst(sorts);
				}
			});
			break;
			
		//��������ǲ���	
		case R.id.ib_dish:
			model.searchSortList(10007, new MenuSortCallback() {
				
				@Override
				public void onLoadMenuSort(List<MenuSort> sorts) {
					Log.i("sc", "MenuSortFirstFragment --> 10007" + sorts.toString());
					callBackFirst.sendMessageFirst(sorts);
				}
			});
			break;
			
		//�����������ʳ
		case R.id.ib_staple_food:
			model.searchSortList(10008, new MenuSortCallback() {
				
				@Override
				public void onLoadMenuSort(List<MenuSort> sorts) {
					Log.i("sc", "MenuSortFirstFragment --> 10008" + sorts.toString());
					callBackFirst.sendMessageFirst(sorts);
				}
			});
			break;
			
		//�������������
		case R.id.ib_pastry:
			model.searchSortList(10009, new MenuSortCallback() {
				
				@Override
				public void onLoadMenuSort(List<MenuSort> sorts) {
					Log.i("sc", "MenuSortFirstFragment --> 10009" + sorts.toString());
					callBackFirst.sendMessageFirst(sorts);
				}
			});
			break;
			
		//���������������Ʒ
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

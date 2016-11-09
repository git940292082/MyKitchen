package sc.my.kitchen.activity;

import java.util.ArrayList;
import java.util.List;

import sc.my.kitchen.adapter.MenuSortListAdapter;
import sc.my.kitchen.entity.MenuSort;
import sc.my.kitchen.fragment.MenuSortFirstFragment;
import sc.my.kitchen.fragment.MenuSortFirstFragment.CallBackFirst;
import sc.my.kitchen.fragment.MenuSortSecondFragment;
import sc.my.kitchen.fragment.MenuSortSecondFragment.CallBackSecond;
import sc.my.kitchen.fragment.MenuSortThirdFragment;
import sc.my.kitchen.fragment.MenuSortThirdFragment.CallBackThird;
import sc.my.kitchen.util.AppManager;
import sc.my.mykitchen.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;



public class MenuSortActivity extends FragmentActivity 
			implements CallBackFirst,CallBackSecond,CallBackThird{

	private ViewPager vpMenuSort;
	private RadioGroup rgMenuSort;
	private RadioButton rbFirst;
	private RadioButton rbSecond;
	private RadioButton rbThird;
	/**
	 * fragment集合
	 */
	private List<Fragment> fragments;
	/**
	 * 菜谱列表 
	 */
	private ListView lvMenuSort;
	/**
	 * 菜单列表的adapter
	 */
	private MenuSortListAdapter adapter;
	/**
	 * 接收菜谱分类的集合
	 */
	private List<MenuSort> sorts;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_sort);
		AppManager.getAppManager().addActivity(this);
		
		//初始化集合
		sorts = new ArrayList<MenuSort>();
		
		//初始化控件
		initViews();
		
		//初始化viewPager的适配器
		setPagerAdapter();
		
		//设置监听器
		setListeners();
		
	}


	private void setListeners() {
		vpMenuSort.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				switch (position) {
				case 0:
					rbFirst.setChecked(true);
					break;

				case 1:
					rbSecond.setChecked(true);
					break;
					
				case 2:
					rbThird.setChecked(true);
					break;
				}
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
		
		rgMenuSort.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.rb_my_kitchen:
					vpMenuSort.setCurrentItem(0);
					break;

				case R.id.rb_my_food_basket:
					vpMenuSort.setCurrentItem(1);
					break;
					
				case R.id.rb_my_info:
					vpMenuSort.setCurrentItem(2);
					break;
				}
			}
		});
		
		lvMenuSort.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				MenuSort sort = sorts.get(position);
				Intent intent = new Intent(MenuSortActivity.this, DishActivity.class);
				intent.putExtra("id", sort.getId());
				intent.putExtra("menu_name", sort.getName());
				intent.putExtra("parentid", sort.getParentId());
				startActivity(intent);
			}
		});
		
	}


	private void setPagerAdapter() {
		fragments = new ArrayList<Fragment>();
		fragments.add(new MenuSortFirstFragment());
		Log.i("sc", "new MenuSortFirstFragment()");
		fragments.add(new MenuSortSecondFragment());
		fragments.add(new MenuSortThirdFragment());
		PagerAdapter adapter = new MenuSortAdapter(getSupportFragmentManager());
		vpMenuSort.setAdapter(adapter);
	}


	private void initViews() {
		vpMenuSort = (ViewPager) findViewById(R.id.vp_menu_sort);
		rgMenuSort = (RadioGroup) findViewById(R.id.rg_menu_sort_tab);
		rbFirst = (RadioButton) findViewById(R.id.rb_first_tab);
		rbSecond = (RadioButton) findViewById(R.id.rb_second_tab);
		rbThird = (RadioButton) findViewById(R.id.rb_third_tab);
		
		lvMenuSort = (ListView) findViewById(R.id.lv_menu_sort);
		
	}

	class MenuSortAdapter extends FragmentPagerAdapter{

		public MenuSortAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int arg0) {
			return fragments.get(arg0);
		}

		@Override
		public int getCount() {
			return fragments.size();
		}
		
	}

	@Override
	public void sendMessageFirst(List<MenuSort> sorts) {
		Log.i("sc", "MenuSortActivity$sendMessageFirst--> " + sorts.toString() + "");
		this.sorts = sorts;
		adapter = new MenuSortListAdapter(MenuSortActivity.this, sorts);
		lvMenuSort.setAdapter(adapter);
	}


	@Override
	public void sendMessageSecond(List<MenuSort> sorts) {
		Log.i("sc", "MenuSortActivity$sendMessageSecond--> " + sorts.toString() + "");
		this.sorts = sorts;
		adapter = new MenuSortListAdapter(MenuSortActivity.this, sorts);
		lvMenuSort.setAdapter(adapter);
	}


	@Override
	public void sendMessageThird(List<MenuSort> sorts) {
		Log.i("sc", "MenuSortActivity$sendMessageThird--> " + sorts.toString() + "");
		this.sorts = sorts;
		adapter = new MenuSortListAdapter(MenuSortActivity.this, sorts);
		lvMenuSort.setAdapter(adapter);
	}
	
}

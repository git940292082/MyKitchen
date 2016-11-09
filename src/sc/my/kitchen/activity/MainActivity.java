package sc.my.kitchen.activity;

import java.util.ArrayList;
import java.util.List;

import sc.my.kitchen.fragment.MyFragment;
import sc.my.kitchen.fragment.MyKitchenFragment;
import sc.my.kitchen.fragment.ShoppingCartFragment;
import sc.my.kitchen.util.AppManager;
import sc.my.mykitchen.R;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class MainActivity extends FragmentActivity {

	private ViewPager vpIndex;
	private RadioGroup rgIndexTab;
	private RadioButton rbMyKitchen;
	private RadioButton rbMyBasket;
	private RadioButton rbMyInfo;
	private List<Fragment> fragments;
	public static final String EXIST = "exist";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		AppManager.getAppManager().addActivity(this);
		
		//��ʼ���ؼ�
		initViews();
		
		
		//��ʼ��viewPager��������
		setPagerAdapter();
		
		//���ü�����
		setListeners();
		
		
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
	    if (intent != null) {//�ж�����Activity������Activityʱ��������intent�Ƿ�Ϊ��
	        //��ȡintent�ж�ӦTag�Ĳ���ֵ
	        boolean isExist = intent.getBooleanExtra(EXIST, false);
	        //���Ϊ�����˳���Activity
	        if (isExist) {
	            this.finish();
	        }
	    }
	}

	private void initViews(){
		vpIndex = (ViewPager) findViewById(R.id.vp_index);
		rgIndexTab = (RadioGroup) findViewById(R.id.rg_index_tab);
		rbMyKitchen = (RadioButton) findViewById(R.id.rb_my_kitchen);
		rbMyBasket = (RadioButton) findViewById(R.id.rb_my_food_basket);
		rbMyInfo = (RadioButton) findViewById(R.id.rb_my_info);
	}
	
	private void setPagerAdapter(){
		fragments = new ArrayList<Fragment>();
		fragments.add(new MyKitchenFragment());
		fragments.add(new ShoppingCartFragment());
		fragments.add(new MyFragment());
		PagerAdapter adapter = new MainPagerAdapter(getSupportFragmentManager());
		vpIndex.setAdapter(adapter);
	}
	
	private void setListeners(){
		rgIndexTab.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.rb_my_kitchen://ѡ�����ҵĳ���
					vpIndex.setCurrentItem(0);
					break;

				case R.id.rb_my_food_basket://ѡ�����ҵĹ��ﳵ
					vpIndex.setCurrentItem(1);
					break;
					
				case R.id.rb_my_info://ѡ�����ҵ���Ϣ
					vpIndex.setCurrentItem(2);
					break;
				}
			}
		});
		
		vpIndex.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				
				switch (position) {
				case 0:
					rbMyKitchen.setChecked(true);
					break;

				case 1:
					rbMyBasket.setChecked(true);
					break;
					
				case 2:
					rbMyInfo.setChecked(true);
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
		
	}
	
	/**
	 * viewpager���Զ���adapter��
	 * @author Sheng
	 *
	 */
	class MainPagerAdapter extends FragmentPagerAdapter{

		public MainPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return fragments.get(position);
		}

		@Override
		public int getCount() {
			return fragments.size();
		}
		
		
	}

}

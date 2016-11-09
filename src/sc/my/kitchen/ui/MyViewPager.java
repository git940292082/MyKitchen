package sc.my.kitchen.ui;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

public class MyViewPager extends ViewPager{


	public MyViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean canScroll(View arg0, boolean arg1, int arg2, int arg3,
			int arg4) {
		 if (arg0 != this && arg0 instanceof ViewPager) {
	            return true;
	        }
		return super.canScroll(arg0, arg1, arg2, arg3, arg4);
	}
	
}

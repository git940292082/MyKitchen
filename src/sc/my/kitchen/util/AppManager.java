package sc.my.kitchen.util;

import java.util.Stack;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;

public class AppManager {

	 private static Stack<Activity> activityStack;      
	    private static AppManager instance;      
	    private PendingIntent restartIntent;      
	      
	    private AppManager() {      
	    }      
	      
	    /**   
	     * ��һʵ��   
	     */      
	    public static AppManager getAppManager() {      
	        if (instance == null) {      
	            instance = new AppManager();      
	        }      
	        return instance;      
	    }      
	      
	    /**   
	     * ����Activity����ջ   
	     */      
	    public void addActivity(Activity activity) {      
	        if (activityStack == null) {      
	            activityStack = new Stack<Activity>();      
	        }      
	        activityStack.add(activity);      
	    }      
	      
	    /**   
	     * ��ȡ��ǰActivity����ջ�����һ��ѹ��ģ�   
	     */      
	    public Activity currentActivity() {      
	        Activity activity = activityStack.lastElement();      
	        return activity;      
	    }      
	      
	    /**   
	     * ������ǰActivity����ջ�����һ��ѹ��ģ�   
	     */      
	    public void finishActivity() {      
	        Activity activity = activityStack.lastElement();      
	        finishActivity(activity);      
	    }      
	      
	    /**   
	     * ����ָ����Activity   
	     */      
	    public void finishActivity(Activity activity) {      
	        if (activity != null) {      
	            activityStack.remove(activity);      
	            activity.finish();      
	            activity = null;      
	        }      
	    }      
	      
	    /**   
	     * ����ָ��������Activity   
	     */      
	    public void finishActivity(Class<?> cls) {      
	        for (Activity activity : activityStack) {      
	            if (activity.getClass().equals(cls)) {      
	                finishActivity(activity);      
	            }      
	        }      
	    }      
	      
	    /**   
	     * ��������Activity   
	     */      
	    public void finishAllActivity() {      
	        for (int i = 0, size = activityStack.size(); i < size; i++) {      
	            if (null != activityStack.get(i)) {      
	                activityStack.get(i).finish();      
	            }      
	        }      
	        activityStack.clear();      
	    }      
	      
	    /**   
	     * �˳�Ӧ�ó���   
	     */      
	    public void exitApp(Context context) {      
	        try {                       
	            finishAllActivity();      
	            System.exit(0);    
	            android.os.Process.killProcess(android.os.Process.myPid());    
	        } catch (Exception e) {      
	        }      
	    }      
	
}
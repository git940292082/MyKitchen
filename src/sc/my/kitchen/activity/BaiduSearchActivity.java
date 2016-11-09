package sc.my.kitchen.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.OverlayManager;
import com.baidu.mapapi.overlayutil.PoiOverlay;
import com.baidu.mapapi.overlayutil.WalkingRouteOverlay;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.baidu.mapapi.utils.CoordinateConverter;
import com.baidu.navisdk.adapter.BNOuterLogUtil;
import com.baidu.navisdk.adapter.BNOuterTTSPlayerCallback;
import com.baidu.navisdk.adapter.BNRoutePlanNode;
import com.baidu.navisdk.adapter.BNaviSettingManager;
import com.baidu.navisdk.adapter.BNRoutePlanNode.CoordinateType;
import com.baidu.navisdk.adapter.BaiduNaviManager;
import com.baidu.navisdk.adapter.BaiduNaviManager.NaviInitListener;
import com.baidu.navisdk.adapter.BaiduNaviManager.RoutePlanListener;

import sc.my.kitchen.util.ToastUtil;
import sc.my.mykitchen.R;
import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


public class BaiduSearchActivity extends Activity implements OnGetPoiSearchResultListener{


	private MapView mMapView = null;
	private BaiduMap mBaiduMap;

	private Button mCompleteButton;
	private Button mRequestLocation;
	private TextView mCancelButton;
	private ListView mListView;

	// 搜索周边相关
	private PoiSearch mPoiSearch = null;

	/**
	 * 定位SDK的核心类
	 */
	public LocationClient mLocationClient = null;

	/**
	 * 当前标志
	 */

	private LocationClientOption option;

	private Marker mCurrentMarker;
	// 定位图标描述

	public BDLocationListener myListener = new MyLocationListener();

	private List<PoiInfo> dataList;
	private ListAdapter adapter;

	private int locType;
	private double longitude;// 精度
	private double latitude;// 维度
	private float radius;// 定位精度半径，单位是米
	private String addrStr;// 反地理编码
	private String province;// 省份信息
	private String city;// 城市信息
	private String district;// 区县信息
	private float direction;// 手机方向信息

	private int checkPosition;
	private EditText etSearchContent;

	private PoiInfo currentInfo;
	private PoiInfo ad;
	private RoutePlanSearch planSearch = null;


	int nodeIndex = -1;//节点索引,供浏览节点时使用
	RouteLine route = null;
	OverlayManager routeOverlay = null;
	boolean useDefaultIcon = false;

	private final static String authBaseArr[] =
		{ Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION };


	private String sdCardPath = null;
	private static final String APP_FOLDER_NAME = "MyKitchen";
	private final static int authBaseRequestCode = 1;
	private final static int authComRequestCode = 2;
	private boolean hasInitSuccess = false;
	private boolean hasRequestComAuth = false;
	private final static String authComArr[] = { Manifest.permission.READ_PHONE_STATE };
	public static List<Activity> activityList = new LinkedList<Activity>();
	public static final String ROUTE_PLAN_NODE = "routePlanNode";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// 注意该方法要再setContentView方法之前实现
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_baidu_search);
		
		activityList.add(this);
		
		BNOuterLogUtil.setLogSwitcher(true);
		
		planSearch = RoutePlanSearch.newInstance();

		initView();
		initEvent();
		initLocation();

		if (initDirs()) {
			initNavi();
		}

	}



	private void initView(){
		etSearchContent = (EditText) findViewById(R.id.et_search_content);
		dataList = new ArrayList<PoiInfo>();
		mMapView = (MapView) findViewById(R.id.bmapView);
		mCompleteButton = (Button) findViewById(R.id.chat_publish_complete_publish);
		mRequestLocation = (Button) findViewById(R.id.request);
		mListView = (ListView) findViewById(R.id.lv_location_nearby);
		mCancelButton = (TextView) findViewById(R.id.chat_publish_complete_cancle);
		checkPosition=0;
		adapter = new ListAdapter(0);
		mListView.setAdapter(adapter);
	}

	/**
	 * 事件初始化 
	 */
	private void initEvent(){

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				checkPosition = position;
				adapter.setCheckposition(position);
				//			    PoiInfo ad = (PoiInfo) adapter.getItem(position);
				ad = dataList.get(position);
				MapStatusUpdate  u = MapStatusUpdateFactory.newLatLng(ad.location);
				mBaiduMap.animateMapStatus(u);
				mBaiduMap.clear();
				mCurrentMarker.setPosition(ad.location);

				PlanNode startNode = PlanNode.withLocation(currentInfo.location);
				PlanNode endNode = PlanNode.withLocation(ad.location);

				planSearch.walkingSearch(new WalkingRoutePlanOption()
						.from(startNode)
						.to(endNode));

				adapter.notifyDataSetChanged();
				
				//TODO

				if (BaiduNaviManager.isNaviInited()) {
					routeplanToNavi(CoordinateType.GCJ02);
				}

			}
		});

		mRequestLocation.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ToastUtil.show(getApplicationContext(), "正在定位。。。");
				initLocation();
			}
		});

		mCompleteButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						Looper.prepare(); 
						searchNeayBy();
						Looper.loop();
					}
				}).start();
				/**
				 * 点击完成按钮之后隐藏软键盘
				 */
				InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				im.hideSoftInputFromWindow(mCompleteButton.getWindowToken(), 0);
				ToastUtil.show(getApplicationContext(), "你现在的位置是: " + dataList.get(checkPosition).name+" 地址是："+dataList.get(checkPosition).address);
			}
		});


		planSearch.setOnGetRoutePlanResultListener(new OnGetRoutePlanResultListener() {

			@Override
			public void onGetWalkingRouteResult(WalkingRouteResult result) {
				if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
					Toast.makeText(BaiduSearchActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
				}
				if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
					//起终点或途经点地址有岐义，通过以下接口获取建议查询信息
					//result.getSuggestAddrInfo()
					return;
				}
				if (result.error == SearchResult.ERRORNO.NO_ERROR) {
					nodeIndex = -1;
					route = result.getRouteLines().get(0);
					WalkingRouteOverlay overlay = new MyWalkingRouteOverlay(mBaiduMap);
					mBaiduMap.setOnMarkerClickListener(overlay);
					routeOverlay = overlay;
					overlay.setData(result.getRouteLines().get(0));
					overlay.addToMap();
					overlay.zoomToSpan();
				}
			}

			@Override
			public void onGetTransitRouteResult(TransitRouteResult arg0) {

			}

			@Override
			public void onGetDrivingRouteResult(DrivingRouteResult arg0) {

			}
		});

		mCancelButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

	}

	/**
	 * 定位
	 */
	private void initLocation(){
		//重新设置
		checkPosition = 0;
		adapter.setCheckposition(0);

		mBaiduMap = mMapView.getMap();
		mBaiduMap.clear();
		// 开启定位图层
		mBaiduMap.setMyLocationEnabled(true);
		mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(17).build()));   // 设置级别

		// 定位初始化
		mLocationClient = new LocationClient(getApplicationContext()); // 声明LocationClient类
		mLocationClient.registerLocationListener(myListener);// 注册定位监听接口

		/**
		 * 设置定位参数
		 */
		option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// 设置定位模式
		//		option.setScanSpan(5000);// 设置发起定位请求的间隔时间,ms
		option.setNeedDeviceDirect(true);// 设置返回结果包含手机的方向
		option.setOpenGps(true);
		option.setAddrType("all");// 返回的定位结果包含地址信息
		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
		option.setIsNeedAddress(true);// 返回的定位结果包含地址信息
		mLocationClient.setLocOption(option);
		mLocationClient.start(); // 调用此方法开始定位
	}


	/**
	 * 定位SDK监听函数
	 * 
	 * @author 
	 * 
	 */
	public class MyLocationListener implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null || mMapView == null) {
				return;
			}

			locType = location.getLocType();

			longitude = location.getLongitude();
			latitude = location.getLatitude();
			if (location.hasRadius()) {// 判断是否有定位精度半径
				radius = location.getRadius();
			}

			if (locType == BDLocation.TypeNetWorkLocation) {
				addrStr = location.getAddrStr();// 获取反地理编码(文字描述的地址)
			}

			direction = location.getDirection();// 获取手机方向，【0~360°】,手机上面正面朝北为0°
			province = location.getProvince();// 省份
			city = location.getCity();// 城市
			district = location.getDistrict();// 区县

			LatLng ll = new LatLng(location.getLatitude(),location.getLongitude());

			//将当前位置加入List里面
			currentInfo = new PoiInfo();
			currentInfo.address = location.getAddrStr();
			currentInfo.city = location.getCity();
			currentInfo.location = ll;
			currentInfo.name = location.getAddrStr();
			dataList.add(currentInfo);
			adapter.notifyDataSetChanged();


			// 构造定位数据
			MyLocationData locData = new MyLocationData.Builder()
					.accuracy(location.getRadius())
					// 此处设置开发者获取到的方向信息，顺时针0-360
					.direction(100).latitude(location.getLatitude())
					.longitude(location.getLongitude()).build();
			mBaiduMap.setMyLocationData(locData);

			//画标志
			CoordinateConverter converter = new CoordinateConverter();
			converter.coord(ll);
			converter.from(CoordinateConverter.CoordType.COMMON);
			LatLng convertLatLng = converter.convert();

			OverlayOptions ooA = new MarkerOptions().position(ll).icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_marka));
			mCurrentMarker = (Marker) mBaiduMap.addOverlay(ooA);


			MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(convertLatLng, 17.0f);
			mBaiduMap.animateMapStatus(u);

			//画当前定位标志
			MapStatusUpdate uc = MapStatusUpdateFactory.newLatLng(ll);
			mBaiduMap.animateMapStatus(uc);

			mMapView.showZoomControls(false);

		}

		public void onReceivePoi(BDLocation poiLocation) {
			if (poiLocation == null) {
				return;
			}
		}
	}

	/**
	 * 搜索周边
	 */
	private void searchNeayBy(){
		// POI初始化搜索模块，注册搜索事件监听
		mPoiSearch = PoiSearch.newInstance();
		mPoiSearch.setOnGetPoiSearchResultListener(this);
		PoiNearbySearchOption poiNearbySearchOption = new PoiNearbySearchOption();

		String keyword = etSearchContent.getText().toString();
		poiNearbySearchOption.keyword(keyword);
		poiNearbySearchOption.location(new LatLng(latitude, longitude));
		poiNearbySearchOption.radius(2000);  // 检索半径，单位是米
		poiNearbySearchOption.pageCapacity(20);  // 默认每页10条
		mPoiSearch.searchNearby(poiNearbySearchOption);  // 发起附近检索请求
	}

	Handler handler = new Handler(){
		@Override  
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:
				adapter.notifyDataSetChanged();
				break;

			default:
				break;
			}
		}
	};

	/* 
	 * 接受周边地理位置结果
	 */
	@Override
	public void onGetPoiResult(PoiResult result) {
		// 获取POI检索结果
		if (result == null || result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {// 没有找到检索结果
			Toast.makeText(BaiduSearchActivity.this, "未找到结果",Toast.LENGTH_LONG).show();
			return;
		}

		if (result.error == SearchResult.ERRORNO.NO_ERROR) {// 检索结果正常返回
			//			mBaiduMap.clear();
			if(result != null){
				if(result.getAllPoi()!= null && result.getAllPoi().size()>0){
					dataList.clear();
					dataList.addAll(result.getAllPoi());
					//					adapter.notifyDataSetChanged();
					Message msg = new Message();
					msg.what = 0;
					handler.sendMessage(msg);
				}
			}
		}
	}

	@Override
	public void onGetPoiDetailResult(PoiDetailResult result) {

	}

	/**
	 * 用于显示poi的overly
	 * @author Administrator
	 *
	 */
	private class MyPoiOverlay extends PoiOverlay {

		public MyPoiOverlay(BaiduMap baiduMap) {
			super(baiduMap);
		}

		@Override
		public boolean onPoiClick(int index) {
			super.onPoiClick(index);
			PoiInfo poiInfo = getPoiResult().getAllPoi().get(index);
			mPoiSearch.searchPoiDetail(new PoiDetailSearchOption()
					.poiUid(poiInfo.uid));
			return true;
		}
	}


	class ListAdapter extends BaseAdapter{

		private int checkPosition;

		public ListAdapter(int checkPosition){
			this.checkPosition = checkPosition;
		}

		public void setCheckposition(int checkPosition){
			this.checkPosition = checkPosition;
		}

		@Override
		public int getCount() {
			return dataList.size();
		}

		@Override
		public Object getItem(int position) {
			return dataList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if(convertView == null){
				holder = new ViewHolder();
				convertView = LayoutInflater.from(BaiduSearchActivity.this).inflate(R.layout.list_item, null);

				holder.textView = (TextView) convertView.findViewById(R.id.text_name);
				holder.textAddress = (TextView) convertView.findViewById(R.id.text_address);
				holder.imageLl = (ImageView) convertView.findViewById(R.id.image);
				convertView.setTag(holder);

			}else{
				holder = (ViewHolder)convertView.getTag();
			}

			holder.textView.setText(dataList.get(position).name);
			holder.textAddress.setText(dataList.get(position).address);
			if(checkPosition == position){
				holder.imageLl.setVisibility(View.VISIBLE);
			}else{
				holder.imageLl.setVisibility(View.GONE);
			}



			return convertView;
		}

	}

	class ViewHolder{
		TextView textView;
		TextView textAddress;
		ImageView imageLl;
	}

	@Override
	protected void onDestroy() {
		// 退出时销毁定位
		if (mLocationClient != null) {
			mLocationClient.stop();
		}

		// 关闭定位图层
		mBaiduMap.setMyLocationEnabled(false);

		super.onDestroy();
		// 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
		mMapView.onDestroy();
		//		mPoiSearch.destroy();
	}

	@Override
	protected void onResume() {
		mMapView.onResume();
		super.onResume();
		// 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理

	}

	@Override
	protected void onPause() {
		mMapView.onPause();
		super.onPause();
		// 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理

	}

	private class MyWalkingRouteOverlay extends WalkingRouteOverlay {

		public MyWalkingRouteOverlay(BaiduMap baiduMap) {
			super(baiduMap);
		}

		@Override
		public BitmapDescriptor getStartMarker() {
			if (useDefaultIcon) {
				return BitmapDescriptorFactory.fromResource(R.drawable.icon_st);
			}
			return null;
		}

		@Override
		public BitmapDescriptor getTerminalMarker() {
			if (useDefaultIcon) {
				return BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
			}
			return null;
		}
	}

	/**
	 * 切换路线图标，刷新地图使其生效
	 * 注意： 起终点图标使用中心对齐.
	 */
	public void changeRouteIcon(View v) {
		if (routeOverlay == null) {
			return;
		}
		if (useDefaultIcon) {
			((Button) v).setText("自定义起终点图标");
			Toast.makeText(this,
					"将使用系统起终点图标",
					Toast.LENGTH_SHORT).show();

		} else {
			((Button) v).setText("系统起终点图标");
			Toast.makeText(this,
					"将使用自定义起终点图标",
					Toast.LENGTH_SHORT).show();

		}
		useDefaultIcon = !useDefaultIcon;
		routeOverlay.removeFromMap();
		routeOverlay.addToMap();
	}


	private boolean hasBasePhoneAuth(){

		PackageManager pm = this.getPackageManager();

		for (String auth : authBaseArr) {
			if (pm.checkPermission(auth, this.getPackageName()) != PackageManager.PERMISSION_GRANTED) {
				return false;
			}
		}
		return true;

	}

	private boolean initDirs(){
		sdCardPath = getSdcardDir();
		if (sdCardPath == null) {
			return false;
		}

		File file = new File(sdCardPath, APP_FOLDER_NAME);
		if (!file.exists()) {
			try {
				file.mkdir();
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	/**
	 * 获得sd卡路径
	 * @return
	 */
	private String getSdcardDir() {
		if (Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {
			return Environment.getExternalStorageDirectory().toString();
		}
		return null;
	}

	/**
	 * 初始化导航
	 */
	private void initNavi(){
		BNOuterTTSPlayerCallback ttCallBack = null;

		//申请权限
		if (android.os.Build.VERSION.SDK_INT >= 23) {
			if (!hasBasePhoneAuth()) {
				this.requestPermissions(authBaseArr, authBaseRequestCode);
				return;
			}
		}

		BaiduNaviManager.getInstance().init(this, sdCardPath, APP_FOLDER_NAME, new NaviInitListener() {
			private String authInfo = null;
			
			@SuppressLint("NewApi")
			@Override
			public void onAuthResult(int status, String msg) {
				if (0 == status) {
					authInfo = "key校验成功";
				}
				else{
					authInfo = "key校验成功" + msg;
				}
			}

			@Override
			public void initSuccess() {
				//引擎初始化成功
				hasInitSuccess = true;
				initSetting();
			}

			@Override
			public void initStart() {
				//引擎初始化开始
			}

			@Override
			public void initFailed() {
				//引擎初始化失败
				Toast.makeText(BaiduSearchActivity.this, "百度引擎初始化失败", Toast.LENGTH_SHORT).show();
			}
		}, null, ttsHandler, ttsPlayStateListener);

	}

	private Handler ttsHandler = new Handler(){
		public void handleMessage(Message msg) {
			int type = msg.what;
			switch (type) {
			case BaiduNaviManager.TTSPlayMsgType.PLAY_START_MSG:

				break;

			case BaiduNaviManager.TTSPlayMsgType.PLAY_END_MSG:

				break;


			}
		};
	};

	private BaiduNaviManager.TTSPlayStateListener ttsPlayStateListener = new BaiduNaviManager.TTSPlayStateListener() {

		@Override
		public void playEnd() {
			// showToastMsg("TTSPlayStateListener : TTS play end");
		}

		@Override
		public void playStart() {
			// showToastMsg("TTSPlayStateListener : TTS play start");
		}
	};

	private CoordinateType mCoordinateType = null;

	private void routeplanToNavi(CoordinateType coType){
		mCoordinateType = coType;
		if (!hasInitSuccess) {

		}

		if (android.os.Build.VERSION.SDK_INT >= 23) {
			// 保证导航功能完备
			if (!hasCompletePhoneAuth()) {
				if (!hasRequestComAuth) {
					hasRequestComAuth = true;
					this.requestPermissions(authComArr, authComRequestCode);
					return;
				} else {
					Toast.makeText(BaiduSearchActivity.this, "没有完备的权限!", Toast.LENGTH_SHORT).show();
				}
			}
		}
		
		LatLng sl = ad.location;
		LatLng el = currentInfo.location;
		
		final BNRoutePlanNode startNaviNode = new BNRoutePlanNode(sl.longitude, sl.latitude, ad.name, null, coType);
		BNRoutePlanNode endNaviNode = new BNRoutePlanNode(el.longitude, el.latitude, currentInfo.name, null, coType);
		
		if (startNaviNode != null && endNaviNode != null) {
	            List<BNRoutePlanNode> list = new ArrayList<BNRoutePlanNode>();
	            list.add(startNaviNode);
	            list.add(endNaviNode);
	            BaiduNaviManager.getInstance().launchNavigator(this, list, 1, true, new RoutePlanListener() {
					
					@Override
					public void onRoutePlanFailed() {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onJumpToNavigator() {
						  for (Activity ac : activityList) {

				                if (ac.getClass().getName().endsWith("BNDemoGuideActivity")) {

				                    return;
				                }
				            }
				            Intent intent = new Intent(BaiduSearchActivity.this, BaiduNaviActivity.class);
				            Bundle bundle = new Bundle();
				            bundle.putSerializable(ROUTE_PLAN_NODE, startNaviNode);
				            intent.putExtras(bundle);
				            startActivity(intent);
						
					}
				});
	        }
		
		
		
	} 
	
	private boolean hasCompletePhoneAuth() {
        // TODO Auto-generated method stub

        PackageManager pm = this.getPackageManager();
        for (String auth : authComArr) {
            if (pm.checkPermission(auth, this.getPackageName()) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
	
	private void initSetting() {
        // BNaviSettingManager.setDayNightMode(BNaviSettingManager.DayNightMode.DAY_NIGHT_MODE_DAY);
        BNaviSettingManager
                .setShowTotalRoadConditionBar(BNaviSettingManager.PreViewRoadCondition.ROAD_CONDITION_BAR_SHOW_ON);
        BNaviSettingManager.setVoiceMode(BNaviSettingManager.VoiceMode.Veteran);
        // BNaviSettingManager.setPowerSaveMode(BNaviSettingManager.PowerSaveMode.DISABLE_MODE);
        BNaviSettingManager.setRealRoadCondition(BNaviSettingManager.RealRoadCondition.NAVI_ITS_ON);
    }
	
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		// TODO Auto-generated method stub
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		
		if (requestCode == authBaseRequestCode) {
            for (int ret : grantResults) {
                if (ret == 0) {
                    continue;
                } else {
                    Toast.makeText(BaiduSearchActivity.this, "缺少导航基本的权限!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            initNavi();
        } else if (requestCode == authComRequestCode) {
            for (int ret : grantResults) {
                if (ret == 0) {
                    continue;
                }
            }
            routeplanToNavi(mCoordinateType);
        }
	}
	
}
	

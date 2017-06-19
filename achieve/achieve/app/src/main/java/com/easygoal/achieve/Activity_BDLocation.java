package com.easygoal.achieve;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;


public class Activity_BDLocation extends AppCompatActivity {
	public LocationClient mLocationClient = null;
    public String loc;
	SharedPreferences sp;
	SharedPreferences.Editor editor;
	TextView tv_bdlocation;
	boolean flag=false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//getWindow().requestFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_bdlocation);
		//EventBus.getDefault().register(getApplicationContext());
		tv_bdlocation=(TextView)findViewById(R.id.tv_bdlocation);

		mLocationClient = new LocationClient(getApplicationContext());
		//声明LocationClient类
		EventBus.getDefault().register(Activity_BDLocation.this);
		//注册监听函数
		BDLocationListener myListener = new MyLocationListener ();
		mLocationClient.registerLocationListener(myListener);
		initLocation();
		mLocationClient.start();
		int a=mLocationClient.requestLocation();
		sp = getApplication().getSharedPreferences("userinfo" , MODE_PRIVATE);
		editor = sp.edit();
		//Toast.makeText(getApplication(), "loc request"+a, Toast.LENGTH_LONG).show();
		//Log.i("sss", sb.toString());


/*
		if (a==1) {
			if (loc!=null) {
				Intent intent = new Intent();
				intent.putExtra("location",loc);
				Toast.makeText(getApplication(), loc, Toast.LENGTH_LONG).show();
				setResult(RESULT_OK, intent);
			}else{
				Toast.makeText(getApplication(), "no loc value", Toast.LENGTH_LONG).show();
			}
		}else{
			    Toast.makeText(getApplication(), "no loc", Toast.LENGTH_LONG).show();
		}*/

     TimerTask tt=new TimerTask() {
			@Override
			public void run() {
				finish();
			}
		};
		Timer timer=new Timer();
		timer.schedule(tt,2000);

	}

    @Subscribe(threadMode = ThreadMode.MAIN)
	public void onEvent(MyEvent myEvent) {
		//loc=myEvent.getMyEvent();
		/*
       if (!TextUtils.isEmpty(myEvent.getMyEvent())) {
		   Toast.makeText(getApplication(), "收到" + myEvent.getMyEvent(), Toast.LENGTH_LONG).show();
		   loc = myEvent.getMyEvent();
		   Intent intent = new Intent();
		   intent.putExtra("location", loc);
		   //Toast.makeText(getApplication(), loc, Toast.LENGTH_LONG).show();
		   setResult(RESULT_OK, intent);
		   editor.putString("address",loc);
		   editor.commit();
		   finish();
	   }else{
		   String oldloc=sp.getString("address","暂无");
		   Intent intent = new Intent();
		   intent.putExtra("location", oldloc);
		   //Toast.makeText(getApplication(), loc, Toast.LENGTH_LONG).show();
		   setResult(RESULT_OK, intent);
		   finish();
	   }
      */


			/*
			new Handler().postDelayed(new Runnable(){
				public void run()
				{
					String oldloc=sp.getString("address","暂无");
					Intent intent = new Intent();
					intent.putExtra("location", oldloc);
					//Toast.makeText(getApplication(), loc, Toast.LENGTH_LONG).show();
					setResult(RESULT_OK, intent);
					finish();
				}
			},100);
            */

		if (!TextUtils.isEmpty(loc)) {

			//Toast.makeText(getApplication(), "收到" + loc, Toast.LENGTH_LONG).show();
			//loc = myEvent.getMyEvent();
			//tv_bdlocation.setText(loc);
			Intent intent = new Intent();
			intent.putExtra("location","目前地址:"+loc);
			//Toast.makeText(getApplication(), loc, Toast.LENGTH_LONG).show();
			//Toast.makeText(getApplication(), "取得地址"+loc, Toast.LENGTH_SHORT).show();
			setResult(RESULT_OK, intent);
			editor.putString("address", loc);
			editor.commit();
			flag=true;
			TimerTask tt=new TimerTask() {
				@Override
				public void run() {
					finish();
				}
			};
			Timer timer=new Timer();
			timer.schedule(tt,80);
			/*
			new Handler().postDelayed(new Runnable(){
				public void run()
				{

				}
			},100);*/
		}else{
			//Toast.makeText(getApplication(), "赞未收到地址", Toast.LENGTH_SHORT).show();
			//tv_bdlocation.setText("请稍候");
			String oldloc=sp.getString("address","暂无");
			Intent intent = new Intent();
			intent.putExtra("location","上次地址"+oldloc);
			//Toast.makeText(getApplication(), loc, Toast.LENGTH_LONG).show();
			setResult(RESULT_OK, intent);
			TimerTask tt= new TimerTask() {
				@Override
				public void run() {
					finish();
				}
			};
			Timer timer=new Timer();
			timer.schedule(tt,50);

		}

	};


/*
	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onEventMainThread(MyEvent myEvent) {
		Log.e("MainThread",  myEvent.getMyEvent());
	}

	@Subscribe(threadMode = ThreadMode.BACKGROUND)
	public void onEventBackgroundThread(MyEvent myEvent) {
		Log.e("BackgroundThread",  myEvent.getMyEvent());
	}

	@Subscribe(threadMode = ThreadMode.ASYNC)
	public void onMessageEventAsync(MyEvent myEvent) {
		Log.e("Async", myEvent.getMyEvent());
	}
*/

	private void initLocation(){
		LocationClientOption option = new LocationClientOption();

		option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
		//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备

		option.setCoorType("bd09ll");
		//可选，默认gcj02，设置返回的定位结果坐标系

		int span=5000;
		option.setScanSpan(span);
		//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的

		option.setIsNeedAddress(true);
		//可选，设置是否需要地址信息，默认不需要

		option.setOpenGps(true);
		//可选，默认false,设置是否使用gps

		option.setLocationNotify(true);
		//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果

		option.setIsNeedLocationDescribe(true);
		//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”

		option.setIsNeedLocationPoiList(true);
		//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到

		option.setIgnoreKillProcess(false);
		//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死

		option.SetIgnoreCacheException(false);
		//可选，默认false，设置是否收集CRASH信息，默认收集

		option.setEnableSimulateGps(false);
		//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要

		mLocationClient.setLocOption(option);

	}


	class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {

			//获取定位结果
			final StringBuffer sb = new StringBuffer(256);
			if (location.getTime() == null) {
				Log.i("ttt", sb.toString());
			} else {
				sb.append("time : ");
				sb.append(location.getTime());    //获取定位时间

				sb.append("\nerror code : ");
				sb.append(location.getLocType());    //获取类型类型

				sb.append("\nlatitude : ");
				sb.append(location.getLatitude());    //获取纬度信息

				sb.append("\nlontitude : ");
				sb.append(location.getLongitude());    //获取经度信息

				sb.append("\nradius : ");
				sb.append(location.getRadius());    //获取定位精准度

				if (location.getLocType() == BDLocation.TypeGpsLocation) {

					// GPS定位结果
					sb.append("\nspeed : ");
					sb.append(location.getSpeed());    // 单位：公里每小时

					sb.append("\nsatellite : ");
					sb.append(location.getSatelliteNumber());    //获取卫星数

					sb.append("\nheight : ");
					sb.append(location.getAltitude());    //获取海拔高度信息，单位米

					sb.append("\ndirection : ");
					sb.append(location.getDirection());    //获取方向信息，单位度

					sb.append("\naddr : ");
					sb.append(location.getAddrStr());    //获取地址信息

					sb.append("\ndescribe : ");
					sb.append("gps定位成功");

				} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {

					// 网络定位结果
					sb.append("\naddr : ");
					sb.append(location.getAddrStr());    //获取地址信息

					sb.append("\noperationers : ");
					sb.append(location.getOperators());    //获取运营商信息

					sb.append("\ndescribe : ");
					sb.append("网络定位成功");

				} else if (location.getLocType() == BDLocation.TypeOffLineLocation) {

					// 离线定位结果
					sb.append("\ndescribe : ");
					sb.append("离线定位成功，离线定位结果也是有效的");

				} else if (location.getLocType() == BDLocation.TypeServerError) {

					sb.append("\ndescribe : ");
					sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");

				} else if (location.getLocType() == BDLocation.TypeNetWorkException) {

					sb.append("\ndescribe : ");
					sb.append("网络不同导致定位失败，请检查网络是否通畅");

				} else if (location.getLocType() == BDLocation.TypeCriteriaException) {

					sb.append("\ndescribe : ");
					sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");

				}

				sb.append("\nlocationdescribe : ");
				sb.append(location.getLocationDescribe());    //位置语义化信息
            /*
            List<Poi> list = location.getPoiList();    // POI数据
            if (list != null) {
                sb.append("\npoilist size = : ");
                sb.append(list.size());
                for (Poi p : list) {
                    sb.append("\npoi= : ");
                    sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                }
            }
            */
				//
				loc=location.getAddrStr();
				Log.i("sss", sb.toString());
				//Intent intent = new Intent();
				//intent.putExtra("location",loc);
				//Toast.makeText(getApplication(), loc, Toast.LENGTH_LONG).show();
				//setResult(RESULT_OK, intent);
				//Toast.makeText(getApplication(), "sb"+loc, Toast.LENGTH_LONG).show();
				editor.putString("address", loc);
				editor.commit();
				EventBus.getDefault().post(new MyEvent(loc));
				//Toast.makeText(getApplication(), "已取得地址"+loc, Toast.LENGTH_SHORT).show();

			}
		}
	}
	

	@Override
	protected void onDestroy() {
		super.onDestroy();
		//EventBus.getDefault().unregister(getApplicationContext());
		EventBus.getDefault().unregister(Activity_BDLocation.this);
	}
}

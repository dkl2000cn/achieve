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
		//����LocationClient��
		EventBus.getDefault().register(Activity_BDLocation.this);
		//ע���������
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
		   Toast.makeText(getApplication(), "�յ�" + myEvent.getMyEvent(), Toast.LENGTH_LONG).show();
		   loc = myEvent.getMyEvent();
		   Intent intent = new Intent();
		   intent.putExtra("location", loc);
		   //Toast.makeText(getApplication(), loc, Toast.LENGTH_LONG).show();
		   setResult(RESULT_OK, intent);
		   editor.putString("address",loc);
		   editor.commit();
		   finish();
	   }else{
		   String oldloc=sp.getString("address","����");
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
					String oldloc=sp.getString("address","����");
					Intent intent = new Intent();
					intent.putExtra("location", oldloc);
					//Toast.makeText(getApplication(), loc, Toast.LENGTH_LONG).show();
					setResult(RESULT_OK, intent);
					finish();
				}
			},100);
            */

		if (!TextUtils.isEmpty(loc)) {

			//Toast.makeText(getApplication(), "�յ�" + loc, Toast.LENGTH_LONG).show();
			//loc = myEvent.getMyEvent();
			//tv_bdlocation.setText(loc);
			Intent intent = new Intent();
			intent.putExtra("location","Ŀǰ��ַ:"+loc);
			//Toast.makeText(getApplication(), loc, Toast.LENGTH_LONG).show();
			//Toast.makeText(getApplication(), "ȡ�õ�ַ"+loc, Toast.LENGTH_SHORT).show();
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
			//Toast.makeText(getApplication(), "��δ�յ���ַ", Toast.LENGTH_SHORT).show();
			//tv_bdlocation.setText("���Ժ�");
			String oldloc=sp.getString("address","����");
			Intent intent = new Intent();
			intent.putExtra("location","�ϴε�ַ"+oldloc);
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
		//��ѡ��Ĭ�ϸ߾��ȣ����ö�λģʽ���߾��ȣ��͹��ģ����豸

		option.setCoorType("bd09ll");
		//��ѡ��Ĭ��gcj02�����÷��صĶ�λ�������ϵ

		int span=5000;
		option.setScanSpan(span);
		//��ѡ��Ĭ��0��������λһ�Σ����÷���λ����ļ����Ҫ���ڵ���1000ms������Ч��

		option.setIsNeedAddress(true);
		//��ѡ�������Ƿ���Ҫ��ַ��Ϣ��Ĭ�ϲ���Ҫ

		option.setOpenGps(true);
		//��ѡ��Ĭ��false,�����Ƿ�ʹ��gps

		option.setLocationNotify(true);
		//��ѡ��Ĭ��false�������Ƿ�GPS��Чʱ����1S/1��Ƶ�����GPS���

		option.setIsNeedLocationDescribe(true);
		//��ѡ��Ĭ��false�������Ƿ���Ҫλ�����廯�����������BDLocation.getLocationDescribe��õ�����������ڡ��ڱ����찲�Ÿ�����

		option.setIsNeedLocationPoiList(true);
		//��ѡ��Ĭ��false�������Ƿ���ҪPOI�����������BDLocation.getPoiList��õ�

		option.setIgnoreKillProcess(false);
		//��ѡ��Ĭ��true����λSDK�ڲ���һ��SERVICE�����ŵ��˶������̣������Ƿ���stop��ʱ��ɱ��������̣�Ĭ�ϲ�ɱ��

		option.SetIgnoreCacheException(false);
		//��ѡ��Ĭ��false�������Ƿ��ռ�CRASH��Ϣ��Ĭ���ռ�

		option.setEnableSimulateGps(false);
		//��ѡ��Ĭ��false�������Ƿ���Ҫ����GPS��������Ĭ����Ҫ

		mLocationClient.setLocOption(option);

	}


	class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {

			//��ȡ��λ���
			final StringBuffer sb = new StringBuffer(256);
			if (location.getTime() == null) {
				Log.i("ttt", sb.toString());
			} else {
				sb.append("time : ");
				sb.append(location.getTime());    //��ȡ��λʱ��

				sb.append("\nerror code : ");
				sb.append(location.getLocType());    //��ȡ��������

				sb.append("\nlatitude : ");
				sb.append(location.getLatitude());    //��ȡγ����Ϣ

				sb.append("\nlontitude : ");
				sb.append(location.getLongitude());    //��ȡ������Ϣ

				sb.append("\nradius : ");
				sb.append(location.getRadius());    //��ȡ��λ��׼��

				if (location.getLocType() == BDLocation.TypeGpsLocation) {

					// GPS��λ���
					sb.append("\nspeed : ");
					sb.append(location.getSpeed());    // ��λ������ÿСʱ

					sb.append("\nsatellite : ");
					sb.append(location.getSatelliteNumber());    //��ȡ������

					sb.append("\nheight : ");
					sb.append(location.getAltitude());    //��ȡ���θ߶���Ϣ����λ��

					sb.append("\ndirection : ");
					sb.append(location.getDirection());    //��ȡ������Ϣ����λ��

					sb.append("\naddr : ");
					sb.append(location.getAddrStr());    //��ȡ��ַ��Ϣ

					sb.append("\ndescribe : ");
					sb.append("gps��λ�ɹ�");

				} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {

					// ���綨λ���
					sb.append("\naddr : ");
					sb.append(location.getAddrStr());    //��ȡ��ַ��Ϣ

					sb.append("\noperationers : ");
					sb.append(location.getOperators());    //��ȡ��Ӫ����Ϣ

					sb.append("\ndescribe : ");
					sb.append("���綨λ�ɹ�");

				} else if (location.getLocType() == BDLocation.TypeOffLineLocation) {

					// ���߶�λ���
					sb.append("\ndescribe : ");
					sb.append("���߶�λ�ɹ������߶�λ���Ҳ����Ч��");

				} else if (location.getLocType() == BDLocation.TypeServerError) {

					sb.append("\ndescribe : ");
					sb.append("��������綨λʧ�ܣ����Է���IMEI�źʹ��嶨λʱ�䵽loc-bugs@baidu.com��������׷��ԭ��");

				} else if (location.getLocType() == BDLocation.TypeNetWorkException) {

					sb.append("\ndescribe : ");
					sb.append("���粻ͬ���¶�λʧ�ܣ����������Ƿ�ͨ��");

				} else if (location.getLocType() == BDLocation.TypeCriteriaException) {

					sb.append("\ndescribe : ");
					sb.append("�޷���ȡ��Ч��λ���ݵ��¶�λʧ�ܣ�һ���������ֻ���ԭ�򣬴��ڷ���ģʽ��һ���������ֽ�����������������ֻ�");

				}

				sb.append("\nlocationdescribe : ");
				sb.append(location.getLocationDescribe());    //λ�����廯��Ϣ
            /*
            List<Poi> list = location.getPoiList();    // POI����
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
				//Toast.makeText(getApplication(), "��ȡ�õ�ַ"+loc, Toast.LENGTH_SHORT).show();

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

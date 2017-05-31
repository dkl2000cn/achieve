package com.easygoal.achieve;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
 

public class ShowLocation extends Activity {
    String provider;//λ���ṩ��
    LocationManager locationManager;//λ�÷���
    Location location;
    //private Button btn_show;
    //private TextView tv_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.location_show);
        //init();//�����ؼ�
    
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);//���λ�÷���
        //locationManager.setTestProviderEnabled(LocationManager.NETWORK_PROVIDER,true); 
        //provider = judgeProvider(locationManager);
        
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 0, mLocationListener01);
        //locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3000, 0, mLocationListener01);
        LocationUtil lu =new LocationUtil(getApplication());
        lu.startMonitor();
       
        Toast.makeText(getApplicationContext(), "lu location"+ lu.getLocation().getLatitude(), Toast.LENGTH_LONG).show();
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        //criteria.setCostAllowed(false);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        provider = locationManager.getBestProvider(criteria, true);
       
        Toast.makeText(getApplicationContext(), "provider"+provider.toString(), Toast.LENGTH_LONG).show();
        if (provider != null) {//��λ���ṩ�������
            //Ϊ��ѹ��getLastKnownLocation�����ľ���
        	/*
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            */
        	String[] providers={LocationManager.NETWORK_PROVIDER,LocationManager.GPS_PROVIDER};
        	//for (int i=0;i<providers.length;i++){
        		//location = locationManager.getLastKnownLocation(providers[i]);
        	
        		location = locationManager.getLastKnownLocation(provider);
        		
        	//}
            //location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            //location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            //Criteria criteria=new Criteria();
            //criteria.
            //location = locationManager.getBestProvider(criteria, enabledOnly);
            if (location != null) {
            	Log.d("provider","location"+location.toString());
      		    Toast.makeText(getApplicationContext(),"location"+location.toString(), Toast.LENGTH_SHORT).show();
                getLocation(location);//�õ���ǰ��γ�Ȳ������߳�ȥ����������
                
            } else {
                //tv_show.setText("��ʱ�޷���õ�ǰλ��");
            	Toast.makeText(getApplicationContext(), "��ʱ�޷���õ�ǰλ��", Toast.LENGTH_SHORT).show();
            }
        }else{//������λ���ṩ�������
        	Toast.makeText(getApplicationContext(), "������λ���ṩ�������", Toast.LENGTH_SHORT).show();
        }
    }
    /*
    private void init() {
        btn_show = (Button) findViewById(R.id.btn_show_location);
        tv_show = (TextView) findViewById(R.id.tv_location_show);
    }
  */
    /**
     * �õ���ǰ��γ�Ȳ������߳�ȥ����������
     */
    public void getLocation(Location location) {
        String latitude = location.getLatitude()+"";
        String longitude = location.getLongitude()+"";
        String url = "http://api.map.baidu.com/geocoder/v2/?ak=yU9UPWM2MXfNWDtgHsoI3HIAgz15d4Kx&callback=renderReverse&location="+latitude+","+longitude+"&output=json&pois=0";
        //String url = "http://api.map.baidu.com/geocoder/v2/?ak=pPGNKs75nVZPloDFuppTLFO3WXebPgXg&callback=renderReverse&location="+latitude+","+longitude+"&output=json&pois=0";
        new MyAsyncTask(url).execute();
    }

    /**
     * �ж��Ƿ��п��õ������ṩ��
     * @return �����ڷ���null
     */
    private String judgeProvider(LocationManager locationManager) {
        List<String> prodiverlist = locationManager.getProviders(true);
        Toast.makeText(getApplicationContext(), prodiverlist.toString(), Toast.LENGTH_SHORT).show();
        if(prodiverlist.contains(LocationManager.NETWORK_PROVIDER)){
        	Toast.makeText(getApplicationContext(), "network", Toast.LENGTH_SHORT).show();
            return LocationManager.NETWORK_PROVIDER;
        }else if(prodiverlist.contains(LocationManager.GPS_PROVIDER)) {
            return LocationManager.GPS_PROVIDER;
        }else{
            Toast.makeText(ShowLocation.this,"û�п��õ�λ���ṩ��",Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    class MyAsyncTask extends AsyncTask<Void,Void,Void>{
        String url = null;//Ҫ�������ַ
        String str = null;//���������ص�����
        String address = null;
        public MyAsyncTask(String url){
            this.url = url;
        }
        @Override
        protected Void doInBackground(Void... params) {
            str = GetHttpConnectionData.getData(url);
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                str = str.replace("renderReverse&&renderReverse","");
                str = str.replace("(","");
                str = str.replace(")","");
                JSONObject jsonObject = new JSONObject(str);
                JSONObject address = jsonObject.getJSONObject("result");
                String city = address.getString("formatted_address");
                String district = address.getString("sematic_description");
                //tv_show.setText("��ǰλ�ã�"+city+district);
                Toast.makeText(getApplicationContext(), city+district, Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            super.onPostExecute(aVoid);
        }
    }
    
    private void updateView(Location location) {  
        if (location != null) {  
            StringBuffer sb = new StringBuffer();  
            sb.append("λ����Ϣ��\n");  
            sb.append("���ȣ�" + location.getLongitude() + ", γ�ȣ�"  
                    + location.getLatitude());  
            //textView.setText(sb.toString()); 
            Toast.makeText(ShowLocation.this,sb.toString(),Toast.LENGTH_SHORT).show();
        } else {  
            //textView.setText("");  
            Toast.makeText(ShowLocation.this,"û�п��õ�λ���ṩ��",Toast.LENGTH_SHORT).show();
        }  
    }  
    
    
    private Location updateToNewLocation(Location location) {
    	  System.out.println("--------zhixing--2--------");
    	   String latLongString;
    	   double lat = 0;
    	   double lng=0;
    	  
    	   if (location != null) {
    	    lat = location.getLatitude();
    	    lng = location.getLongitude();
    	    latLongString = "γ��:" + lat + "\n����:" + lng;
    	    System.out.println("���ȣ�"+lng+"γ�ȣ�"+lat);
    	   } else {
    	    latLongString = "�޷���ȡ������Ϣ�����Ժ�...";
    	   }
    	   if(lat!=0){
    	    System.out.println("--------������Ϣ----------"+ String.valueOf(lat));
    	   }
    	  
    	   Toast.makeText(getApplicationContext(), latLongString, Toast.LENGTH_SHORT).show();
    	 
    	   return location;
    	  
    	 }
    
    public final LocationListener mLocationListener01 = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
        	ShowLocation.this.location=location;
        	double lat = 0;
      	    double lng=0;
      	    String latLongString;
        	if (location != null) {
        	    lat = location.getLatitude();
        	    lng = location.getLongitude();
        	    latLongString = "γ��:" + lat + "\n����:" + lng;
        	    System.out.println("���ȣ�"+lng+"γ�ȣ�"+lat);
        	   } else {
        	    latLongString = "�޷���ȡ������Ϣ�����Ժ�...";
        	   }
        	   if(lat!=0){
        	    System.out.println("--------������Ϣ----------"+ String.valueOf(lat));
        	   }
        }
   
        @Override
        public void onProviderDisabled(String provider) {
            updateToNewLocation(null);
        }
        @Override
        public void onProviderEnabled(String provider) {
        	updateView(locationManager  
                    .getLastKnownLocation(provider)); 
        	
        }
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}
};
    public static class GetHttpConnectionData {

        String str = null;//��·�������ص�����
        public static String getData(String url){//url��·�������ַ
            URL u = null;
            try {
                u = new URL(url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            HttpURLConnection hc = null;
            InputStream inputStream = null;
            StringBuffer sb = null;
            BufferedReader br = null;
            try {
                hc = (HttpURLConnection) u.openConnection();
                hc.setRequestMethod("GET");
                inputStream = hc.getInputStream();
                sb = new StringBuffer();
                br = new BufferedReader(new InputStreamReader(inputStream));
                String len = null;
                while ((len=br.readLine())!=null){
                    sb.append(len);
                }
                return sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                if(inputStream!=null){
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }
    }     
    
}
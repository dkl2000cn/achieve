package com.easygoal.achieve;



import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.util.TextUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;
import org.xmlpull.v1.XmlPullParser;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.Volley;
import com.baidu.autoupdatesdk.BDAutoUpdateSDK;
import com.baidu.autoupdatesdk.UICheckUpdateCallback;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.jakewharton.disklrucache.DiskLruCache;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
//import com.umeng.analytics.MobclickAgent;
//import com.umeng.message.PushAgent;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import io.rong.imkit.*;
import io.rong.imkit.MainActivity;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;


public class MainActivity_1 extends MPAppCompatActivity  {

	static FragmentManager fm;
	Fragment from_fg;
	TabHost bottomTab=null;
	ActionBar actionBar;
	Toolbar toolbar ;
	Timer timer = new Timer();
	MyEvent myevent;
	Handler handler = null;
	int selTabID=0;
	int index=0;
	int CONN_FLAG=-1;
	ConnMySQL conMysql;
	SlidingMenu sm;
	CustomDrawerLayout mDrawerLayout;
	ConnectivityManager con;
	int[] draw ;
	int[] id ;
	String[] btabTag;
	String Tags="MainActivity_1";
	//Intent musicintent;
	//MusicService.MusicBinder musicServiceBinder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if (savedInstanceState != null) {

			FragmentManager manager = getFragmentManager();
			manager.popBackStackImmediate(null, 1);
		}
		super.onCreate(savedInstanceState);
		//PushAgent.getInstance(getApplicationContext()).onAppStart();
		supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		conMysql = new ConnMySQL(getApplicationContext());
		//ServiceUtils.playMusicService(getApplicationContext());

		login();
		initToolbar();
		initTabhost();
		Log.d(Tags,"initTabhost done");
		intervalUpdate();
		Log.d(Tags,"interval update done");

		/*
		try {
			InputStream is=getAssets().open("update.xml");
			List<UpdateInfo> list = XmlUtils.Pull2Xml(is);
			Toast.makeText(getApplicationContext(),list.toString(),Toast.LENGTH_SHORT).show();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		//requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);



        /*
		musicintent = new Intent("com.angel.Android.MUSIC");
		startService(musicintent);
         */
		con = (ConnectivityManager)getSystemService(Activity.CONNECTIVITY_SERVICE);
	}
	//requestPermission(permissionlist,0x00099);
	//Intent intent=new Intent(MainActivity_1.this,Activity_BDLocation.class);
	//startService(intent);



	//Intent intent=new Intent(MainActivity_1.this,Activity_ForeCast.class);
	//startActivity(intent);




	// getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.customtitle);
		/*
		sm =  new SlidingMenu(this);
	    // 设置可以左右滑动的菜???
	    sm.setMode(SlidingMenu.LEFT);
	    // 设置滑动阴影的宽???
	   //sm.setShadowWidthRes(R.dimen.shadow_width);
	    // 设置滑动菜单阴影的图像资???
	    //sm.setShadowDrawable(150);
	    // 设置滑动菜单视图的宽???
	    sm.setBehindWidth(600);
	    sm.showMenu();
	    //sm.showContent();
	  // sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
	    // 设置渐入渐出效果的???
	    sm.setFadeDegree(0.35f);
	    // 设置触摸屏幕的模???,这里设置为全???
	    sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
	    // 设置下方视图的在滚动时的缩放比例
	    sm.setBehindScrollScale(0.0f);
	    sm.setMenu(R.layout.slidingmenu);
		sm.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);

		  delegate = AppCompatDelegate.create(this, this);
		   //we need to call the onCreate() of the AppCompatDelegate
		      delegate.onCreate(savedInstanceState);
		     //we use the delegate to inflate the layout
		     delegate.setContentView(R.layout.activity_main);
		     //Finally, let's add the Toolbar
		    */



         /*
		TaskData.homepage_countupdate();
		Toast.makeText(getApplicationContext(), "首页已更新", Toast.LENGTH_SHORT).show();
		TaskData.adapterUpdate();
		Toast.makeText(getApplicationContext(), "初始更新", Toast.LENGTH_SHORT).show();
		*/



	/*
	public static Fragment showFrag(Fragment from_fg,Fragment[] frag,int i){
		  FragmentTransaction ft = fm.beginTransaction();
          ft.setTransition(ft.TRANSIT_NONE);
	       if (from_fg==null){
	    	   ft.add(R.id.main_layout, frag[i]).commit();

	       }else{
	    	   if (!frag[i].isAdded()){

	    		// ft.hide(from_fg);

	    		 ft.hide(from_fg).add(R.id.main_layout,frag[i]).commit();

	    	   }
	    	   else{
	    		   ft.hide(from_fg).show(frag[i]).commit();

	    	   }
	         };
	       from_fg=frag[i];

		   return from_fg;
	 }
	*/

	private void initTabhost(){
		bottomTab=(TabHost)findViewById(android.R.id.tabhost);
		bottomTab.setup();
		draw = new int[]{R.drawable.selector_bottomtab1,R.drawable.selector_bottomtab2,R.drawable.selector_bottomtab3,R.drawable.selector_bottomtab4};

		id = new int[]{R.id.tab1,R.id.tab2,R.id.tab3,R.id.tab4};
		btabTag=new String[]{"今日","任务","进度","绩效"};
		TabWidget tabWidget = (TabWidget) findViewById(android.R.id.tabs);
		for (int i = 0; i < 4; i++) {
			TabSpec tabSpec = bottomTab.newTabSpec(String.valueOf(i));
			tabSpec.setIndicator(getTabItemView(i));
			tabSpec.setContent(getTabConTent(i));
			bottomTab.addTab(tabSpec);
			Log.d(Tags,"tab created tabspec "+i);
		}
		int initTabcolor=getResources().getColor(R.color.mediumorchid);;
		ImageView[] iv = new ImageView[4];
		final TextView[] tv = new TextView[4];
		for (int i=0; i<bottomTab.getTabWidget().getChildCount(); i++){                         //循环每个tabView
			View view = tabWidget.getChildAt(i);                                 //获取tabView
			//view.setContentDescription(Integer.toString(i+1));
			//view.getLayoutParams().height = (int) (view.getLayoutParams().height / 1.2);
			view.setBackgroundResource(R.drawable.topborder);
			iv[i] = (ImageView) bottomTab.getTabWidget().getChildAt(i).findViewById(R.id.myimage);
			tv[i] = (TextView) bottomTab.getTabWidget().getChildAt(i).findViewById(R.id.mytext);
			//tv.setTextColor(R.drawable.selector_bottomtab1_textcolor);
			//tv.setText("XXXX");
			//iv.setImageDrawable(getResources().getDrawable(R.drawable.icon));

			tv[i].setTextSize(16);;
			switch (i) {
				case 0:{
					// view.setBackgroundResource(R.drawable.menu_1_selector);
					iv[0].setImageDrawable(getResources().getDrawable(R.drawable.selector_bottomtab1));
					tv[0].setTextColor(initTabcolor);

					break;
				}
				case 1:{
					//  view.setBackgroundResource(R.drawable.menu_2_selector);
					iv[1].setImageDrawable(getResources().getDrawable(R.drawable.selector_bottomtab2));
					tv[1].setTextColor(Color.GRAY);
					break;
				}
				case 2:{
					// view.setBackgroundResource(R.drawable.menu_3_selector);
					iv[2].setImageDrawable(getResources().getDrawable(R.drawable.selector_bottomtab3));
					tv[2].setTextColor(Color.GRAY);
					break;
				}
				case 3:{
					iv[3].setImageDrawable(getResources().getDrawable(R.drawable.selector_bottomtab4));
					tv[3].setTextColor(Color.GRAY);

					break;
				}
			}
		}

		FragmentTransaction ft =TaskData.fm.beginTransaction();
		ft.setTransition(FragmentTransaction.TRANSIT_NONE);
		Fragment fragment_hometab=new FragmentHomeTab();
		Fragment fragment_tasktab=new FragmentTaskTab();
		Fragment fragment_taskscoretab=new FragmentTaskScoreTab();
		Fragment fragment_taskrecordtab=new FragmentTaskRecordTab();
		final Fragment[] frag={fragment_hometab,fragment_tasktab,fragment_taskrecordtab,fragment_taskscoretab};
		from_fg=null;

		ft.add(R.id.main_layout, frag[0]).commit();
		Log.d(Tags,"tabhost init set tab0");
		from_fg = frag[0];
		Log.d(TaskData.t_tags,""+(new Date().getTime()-TaskData.t_start)+"ms");
		bottomTab.setOnTabChangedListener(new TabHost.OnTabChangeListener()
		{

			int tabcolor=getResources().getColor(R.color.mediumorchid);;
			int oldtabcolor=getResources().getColor(R.color.darkgrey);;
			//final Fragment from_fg = frag[0]

			@Override
			public void onTabChanged(String s)
			{  Log.d(Tags,"TabHost currentTab "+bottomTab.getCurrentTab());
				//bottomTab.getc.getCurrentTabTag()
				int selTab =bottomTab.getCurrentTab();
				//TextView tv1 = (TextView) bottomTab.getTabWidget().getChildAt(selTab).findViewById(R.id.mytext);

				for (int i=0;i<=3;i++){

					if(i==selTab){
						//  ((View) bottomTab.get.getTag(i)).setBackgroundColor(Color.RED);
						tv[i].setTextColor(tabcolor);
						Log.d(Tags,"TabHost selTabID find "+i);

					} else{
						tv[i].setTextColor(oldtabcolor);
						//bottomTab.getCurrentTabView().setBackgroundColor(Color.WHITE);
					}
				};
				Log.d(Tags,"TabHost selTabID "+selTab );

				switch (selTab){
					case 0: //Log.d(selTab+" tab selected","frag0"+"from:"+from_fg.toString());
						// ft.replace(R.id.tab1,frag[selabID]);
						showFrag(TaskData.fm,from_fg,frag,0);
						from_fg=frag[0];
						break;
					// ft.hide(frag[0]).show(frag[1]);
					//4ft.show(frag[0]).hide(frag[1]);
					// Log.d("tab selected","change to tab1");
					// ft.addToBackStack(null);
					// ft.commit();

					case 1: //Log.d(selTab+"tab selected","frag1 "+from_fg.toString());
						showFrag(TaskData.fm,from_fg,frag,1);
						from_fg=frag[1];
						break;
					case 2: Log.d(Tags,selTab+"tab selected fragmenttab2 new");

						//ft.replace(R.id.main_layout,fragmentOpenTasks);
						//Log.d("tab selected","tab3");
						// ft.addToBackStack(null);
						// ft.commit();
						showFrag(TaskData.fm,from_fg,frag,2);
						from_fg=frag[2];
						break;
					case 3: Log.d(Tags,selTab+"tab selected fragmenttab3 new");

						//ft.replace(R.id.main_layout,fragmentOpenTasks);
						//Log.d("tab selected","tab4");
						// ft.addToBackStack(null);
						// ft.commit();
						showFrag(TaskData.fm,from_fg,frag,3);
						from_fg=frag[3];
						break;
          	/*case 4: Log.d(selTabID+"tab selected","fragmenttab5 new");

			      //ft.replace(R.id.main_layout,fragmentOpenTasks);
              //Log.d("tab selected","tab4");
             // ft.addToBackStack(null);
              // ft.commit();
    	     //showFrag(from_fg,frag,3);
    	     from_fg=showFrag(from_fg,frag,4);
    	     break;*/
					default:break;
				}
			}
		});

	}

	public View getTabItemView(int index){
		final LayoutInflater layoutInflater = LayoutInflater.from(this);
		View view = layoutInflater.inflate(R.layout.mytab_item, null);

		ImageView imageView = (ImageView) view.findViewById(R.id.myimage);
		imageView.setImageResource(draw[index]);
		//imageView.setPadding(10, 5, 0, 0);

		TextView textView = (TextView) view.findViewById(R.id.mytext);
		textView.setText(btabTag[index]);
		//textView.setPadding(0, -5, 0, 0);
		// textView.setBackgroundResource(R.drawable.selector_bottomtab);
		//view.setBackgroundResource(R.drawable.selector_bottomtab);
		return view;
	}
	private int getTabConTent(int index){
		return id[index];
	}

	private void initToolbar(){
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		//toolbar.setTitle("Title");
		//toolbar.setSubtitle("SubTitle");
		//toolbar.setLogo(R.drawable.ic_launcher);
		setSupportActionBar(toolbar);
		toolbar.setNavigationIcon(R.drawable.addgoal);

		//Button mLvDrawer = (Button) findViewById(R.id.bt_drawer);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		//getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener(){

			@Override
			public boolean onMenuItemClick(MenuItem menuItem) {
				// TODO Auto-generated method stub
				String msg = "";

				switch (menuItem.getItemId()) {
					case R.id.action_sharepic:

						File sdFileDir=Environment.getExternalStorageDirectory();
						SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm");
						Date curDate = new Date(System.currentTimeMillis());//获取当前时间
						String curTime1 = formatter.format(curDate);
						//String fn=	FileUtils.getDiskCacheDirPath(getApplicationContext())+"[目标达]"+curTime1+".png";
						String fn=	Environment.getExternalStorageDirectory()+"/DCIM/"+"[目标达]"+curTime1+".png";
						File file1=new File(fn);
						System.out.println(file1.getPath());
						System.out.println(file1.getAbsolutePath());
						System.out.println(file1.toString());
						try {
							if (!file1.exists()) {
								file1.createNewFile();
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						//ScreenShot screenshot = new ScreenShot();
						// BitmapDrawable bd= new BitmapDrawable(screenshot.takeScreenShot(MainActivity_1.this));
						Intent intent = new Intent(Intent.ACTION_SEND);
						intent.setType("image/*");

						String a=ScreenShot.shoot(MainActivity_1.this,file1);
						Log.d("screenshot","shoot"+a);

						Uri u=Uri.fromFile(file1);
						//Log.d("screenshot",file1.toString()+file1.getTotalSpace()+u.toString());
						//Log.d("screenshot",FileUtils.getDiskCacheDirPath(getApplicationContext())+"Bitmap/"+saveFileName);
						intent.putExtra(Intent.EXTRA_STREAM, u);
						intent.putExtra(Intent.EXTRA_SUBJECT, "任务图片分享");
						intent.putExtra(Intent.EXTRA_TEXT, "screen");
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						startActivity(Intent.createChooser(intent, "任务图片分享"));
						/*
						if (file1 != null && file1.exists()) {

							u = Uri.fromFile(file1);
							screenshot.shoot(MainActivity_1.this,file1);
							Log.d("screenshot",file1.toString()+file1.getTotalSpace()+u.toString());
							intent.putExtra(Intent.EXTRA_STREAM, u);
							intent.putExtra(Intent.EXTRA_SUBJECT, "任务图片分享");
							intent.putExtra(Intent.EXTRA_TEXT, "screen");
							intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							startActivity(Intent.createChooser(intent, "任务图片分享"));
						} else{
							Log.d("screenshot",file1.toString()+"not exist");
							Toast.makeText(getApplicationContext(), "文件不存在",Toast.LENGTH_SHORT).show();
						}*/

						//intent.putExtra("BITMAP", screenshot.takeScreenShot(MainActivity_1.this));

						break;
					case R.id.action_addtask:
						DialogFragment_Task dialogfrag_task=new DialogFragment_Task();
						showDialog(fm,dialogfrag_task);
						msg="add goal";
						//Intent intent0 = new Intent();
						//intent0.setClass(MainActivity_1.this, Activity_AddTask.class);
						//startActivity(intent0);
						break;
					case R.id.action_search:
						Intent intent1 = new Intent();
						intent1.setClass(MainActivity_1.this, Activity_Search.class);
						startActivity(intent1);
						break;
					//DialogFragment_Search dg_search=new DialogFragment_Search();
					//TaskTool.showDialog(dg_search);
					case R.id.action_help:

						break;
					case R.id.action_feeback:
						   DialogFragment_Feedback dg_feedback=new DialogFragment_Feedback();
						   showDialog(fm,dg_feedback);
						//RongIM.getInstance().startGroupChat(getApplicationContext(), TaskData.usergroup, TaskData.usergroup);
						/*
						HashMap<String, Boolean> hashMap = new HashMap<>();
						//会话类型 以及是否聚合显示
						hashMap.put(Conversation.ConversationType.PRIVATE.getName(),false);
						hashMap.put(Conversation.ConversationType.PUSH_SERVICE.getName(),true);
						hashMap.put(Conversation.ConversationType.SYSTEM.getName(),true);
						RongIM.getInstance().startConversationList(MainActivity_1.this,hashMap);

						if (TaskData.user.equals("15802131279")) {
							if (RongIM.getInstance() != null) {
								RongIM.getInstance().startPrivateChat(MainActivity_1.this, "13917981639","和"+"13917981639"+"谈话");

								//RongIM.getInstance().startPrivateChat(MainActivity_1.this, "13917981639", "title");
								//RongIM.getInstance().startConversationList(MainActivity_1.this);
								//RongIM.getInstance().startSubConversationList(MainActivity_1.this, Conversation.ConversationType.GROUP);
							}
						}

						if (TaskData.user.equals("13917981639")) {
							RongIM.getInstance().startPrivateChat(MainActivity_1.this, "15802131279", "和"+"15802131279"+"谈话");

							//RongIM.getInstance().startConversationList(MainActivity_1.this);
							//RongIM.getInstance().startSubConversationList(MainActivity_1.this, Conversation.ConversationType.GROUP);
						}*/

						break;
					case R.id.action_rating:
						DialogFragment_Rating dg_rating=new DialogFragment_Rating();
						showDialog(fm,dg_rating);
						break;
					case R.id.action_about:
						//showDialog(fm,new DialogFragment_QR());

						DialogFragment_About dg_about=new DialogFragment_About();
						showDialog(fm,dg_about);
						break;

					case R.id.action_version:
						checkVersion();
						break;
		           /*
			      case R.id.action_settings6:
			    	     DialogFragment_Preferences dg_preferences=new DialogFragment_Preferences();
	                     TaskTool.showDialog(dg_preferences);
	                     break;
			      case R.id.action_settings7:

						 Editor alarmEditor=alarmSettingSP.edit();
						 alarmEditor.putBoolean("alarmclock", true);
						 alarmEditor.putBoolean("vibration", false);
						 alarmEditor.putBoolean("alarmring", false);
						 alarmEditor.putBoolean("notification", false);
					      DialogFragment_AlarmSetting dg_alarmsetting=new DialogFragment_AlarmSetting();
					     TaskTool.showDialog(dg_alarmsetting);
				      break;
				  */
					default:break;
				}

				if(!msg.equals("")) {
					//Toast.makeText(MainActivity_1.this, msg, Toast.LENGTH_SHORT).show();
				}
				return false;
			}

		});

		mDrawerLayout = (CustomDrawerLayout) findViewById(R.id.main_drawer);
		ListView mDrawerList = (ListView) findViewById(R.id.left_drawer);
		Log.d(Tags,"mDrawerlist "+mDrawerList.toString());
		ArrayList<DrawerListItems> menuLists = new ArrayList<DrawerListItems>();
		Resources res = this.getResources();

		DrawerListItems mItem1 = new DrawerListItems();

		mItem1.setImage(res.getDrawable(R.drawable.setting32px));
		mItem1.setTitle("账号中心");
		menuLists.add(mItem1);

		DrawerListItems mItem2 = new DrawerListItems();
		mItem2.setImage(res.getDrawable(R.drawable.refresh_32px));
		mItem2.setTitle("数据同步");
		menuLists.add(mItem2);
		  /*
		  DrawerListItems mItem3 = new DrawerListItems();
		  mItem3.setImage(res.getDrawable(R.drawable.share_32px));
		  mItem3.setTitle("截图分享");
		  menuLists.add(mItem3);

		  DrawerListItems mItem4 = new DrawerListItems();
		  mItem4.setImage(res.getDrawable(R.drawable.export_32px));
		  mItem4.setTitle("数据导出");
		  menuLists.add(mItem4);
         */

		DrawerListItems mItem10 = new DrawerListItems();
		mItem10.setImage(res.getDrawable(R.drawable.team_36px));
		mItem10.setTitle("任务共享(VIP)");
		menuLists.add(mItem10);


		DrawerListItems mItem11 = new DrawerListItems();
		mItem11.setImage(res.getDrawable(R.drawable.report_32px));
		mItem11.setTitle("周期报表(VIP)");
		menuLists.add(mItem11);

		DrawerListItems mItem4 = new DrawerListItems();
		mItem4.setImage(res.getDrawable(R.drawable.preferences_32px));
		mItem4.setTitle("使用设置");
		menuLists.add(mItem4);

		DrawerListItems mItem5 = new DrawerListItems();
		mItem5.setImage(res.getDrawable(R.drawable.alarm_32px));
		mItem5.setTitle("提醒方式");
		menuLists.add(mItem5);
        /*
		DrawerListItems mItem6 = new DrawerListItems();
		mItem6.setImage(res.getDrawable(R.drawable.feedback_32px));
		mItem6.setTitle("问题反馈");
		menuLists.add(mItem6);

		DrawerListItems mItem7 = new DrawerListItems();
		mItem7.setImage(res.getDrawable(R.drawable.rating_32px));
		mItem7.setTitle("满意评价");
		menuLists.add(mItem7);

		DrawerListItems mItem8 = new DrawerListItems();
		mItem8.setImage(res.getDrawable(R.drawable.about_32px));
		mItem8.setTitle("关于");
		menuLists.add(mItem8);
*/
		DrawerListItems mItem9 = new DrawerListItems();
		mItem9.setImage(res.getDrawable(R.drawable.logout_32px));
		mItem9.setTitle("注销");
		menuLists.add(mItem9);

		DrawerListItems mItem13 = new DrawerListItems();
		mItem13.setImage(res.getDrawable(R.drawable.exit_32px));
		mItem13.setTitle("退出");
		menuLists.add(mItem13);



		Log.d(Tags,"mDrawerlist"+menuLists.toString());
		DrawerListAdapter DrawerListAdapter = new DrawerListAdapter(menuLists,this);

		mDrawerList.setAdapter(DrawerListAdapter);
		// mDrawerLayout.openDrawer(mDrawerList);

		// ActionBar actionBar = getActionBar();
		//actionBar.setDisplayHomeAsUpEnabled(true);
		//getActionBar().setHomeButtonEnabled(true);


		myevent = new MyEvent("我是从网络下载的文本");

		mDrawerList.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				// TODO Auto-generated method stub
				switch (position){

					case 0:
						// EventBus.getDefault().post(new MyEvent("welcome"));
					/*
					if(getConnCode()>0){
					         SharedPreferences sp=getApplication().getSharedPreferences("userinfo",Context.MODE_PRIVATE);
			  	             String userPhone = sp.getString("userPhone", "");
			  	             Map param=new HashMap<String,String>();
			  	             param.put("phoneNo",userPhone);
			  	             UserLoginRequestPost("UserServlet", param);
					     }else{
					    	 DialogFragment_UserSetting dg_usersetting=new DialogFragment_UserSetting(getApplication());
						     TaskTool.showDialog(dg_usersetting);
					     }*/
						DialogFragment_UserSetting dg_usersetting=new DialogFragment_UserSetting(getApplication());
						    showDialog(fm,dg_usersetting);

						break;
					case 1:conSyn();
						break;
					case 2:ChangeToTeam();
						break;
					case 3:StartExportReport();
						break;
					case 4: DialogFragment_Preferences dg_preferences=new DialogFragment_Preferences();
						     showDialog(fm,dg_preferences);
						break;
					case 5:
						     DialogFragment_AlarmSetting dg_alarmsetting=new DialogFragment_AlarmSetting();
						    showDialog(fm,dg_alarmsetting);
						break;
					case 6:Logout();
						break;
					case 7:Exit();
						break;

					default:break;


				}
			}

		});

		ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open, R.string.close) {
			// 被打???的时???
			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				//getActionBar().setTitle("open"); // 设置actionBar的文???
				invalidateOptionsMenu(); // Call onPrepareOptionsMenu()
			}

			// 被关闭的时???
			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
				// getActionBar().setTitle("close");
				invalidateOptionsMenu();// 重新绘制actionBar上边的菜单项
			}
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);
		mDrawerToggle.syncState();
	}

	private void intervalUpdate(){
		TimerTask task = new TimerTask() {

			@Override
			public void run() {
				// ???要做的事:发???消???
				Message message = new Message();
				message.what = 1;
				handler.sendMessage(message);
			}
			// 1s后执行task,经过1s再次执行
		};
		handler = new Handler() {
			public void handleMessage(Message msg) {
				if (msg.what == 1) {
					if (TaskData.adapter_reminder!=null&&TaskData.adapter_reminder.getCursor().getCount()>0){
						TaskData.adapter_reminder.getCursor().requery();
						TaskData.adapter_reminder.notifyDataSetChanged();
						TaskData.homepage_countupdate();
					}
					//TaskData.adapterUpdate();
					//TaskData.adapterUpdate();
					//if (TaskData.adapter_reminder!=null){
					//TaskData.adapter_reminder.notifyDataSetChanged();;
					//}
					// TODO Auto-generated method stub
					// TaskData.cursor_todaytasks.requery();
					//TaskData.adapter_todaytasks.notifyDataSetChanged();
				   	/*  TaskData.cursor_todaytasks = TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+TaskData.TdDB.TASK_STATUS+"=? and "+
			                   TaskData.TdDB.TASK_DEADLINEDATE+"=? and "+
			                   TaskData.TdDB.TASK_DEADLINETIMEDATA+">?",
			                   new String[]{"open",
			                   new SimpleDateFormat ("yy-MM-dd").format(new Date()),
			           		   String.valueOf(new Date().getTime()/(1000*60))});

				       TaskData.adapter_todaytasks = new mcAdapter_tasks( TaskData.todaycontext,R.layout.lv_todaytasks,TaskData.cursor_todaytasks);
				       Log.d("adapter_todaytasks",TaskData.adapter_todaytasks.toString() );
				       TaskData.adapter_todaytasks.notifyDataSetChanged();
				       TaskData.lv_todaytasks.setAdapter(TaskData.adapter_todaytasks);
				       */
					//Toast.makeText(getApplicationContext(), "update!!!"+TaskData.cursor_todaytasks.getCount(), Toast.LENGTH_SHORT).show();;

					super.handleMessage(msg);
				};
			};
		};
		timer.schedule(task, 5000, 60000);

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_menu, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.


		switch (item.getItemId()) {
			case android.R.id.home:
				final Builder builder=new AlertDialog.Builder(this);
				builder.setTitle("信息");
				builder.setMessage("真要离开吗？");
				builder.setPositiveButton("确认", new OnClickListener(){

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						//TextView show=(TextView)findViewById(R.layout.tv_alertdialog_pos);
						//show.setText("");
						finish();
					}
				} );
				builder.setNegativeButton("取消", new OnClickListener(){

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub

					}
				});
				builder.create().show();
				//finish();
				return true;

		}
		return super.onOptionsItemSelected(item);
	}



	public void ExitApp()
	{    long exitTime = 0;
		if ((System.currentTimeMillis() - exitTime) > 2000)
		{
			Toast.makeText(getApplication(), "再按一次退出目标达软件", Toast.LENGTH_SHORT).show();
			exitTime = System.currentTimeMillis();
		} else
		{
			this.finish();
		}
	}



	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);

		if(newConfig.orientation==Configuration.ORIENTATION_LANDSCAPE) {
			//横向

		} else {
			//竖向
			setContentView(R.layout.activity_main);
		}
	}


	public void ChangeToTeam() {
		ConnectivityManager con = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
		boolean wifi = con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
		boolean internet = con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
		if (wifi | internet) {
			if (TaskData.privilege>=TaskData.vipentryright) {
				conSyn();
				//DialogFragment_Group dg_group = new DialogFragment_Group();
				//TaskTool.showDialog(dg_group);

				Intent intent1 = new Intent();
				intent1.setClass(MainActivity_1.this, Activity_Team.class);
				startActivity(intent1);
			}else{
				Toast.makeText(MainActivity_1.this, "请先申请高级用户", Toast.LENGTH_SHORT).show();
				showDialog(fm,new DialogFragment_ApplyMembership());
			}
		}else{
			Toast.makeText(getApplicationContext(),
					"亲，网络连了么？", Toast.LENGTH_LONG)
					.show();
		}
	}

	public void StartExportReport() {
		ConnectivityManager con = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
		boolean wifi = con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
		boolean internet = con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
		if (wifi | internet) {
			if (TaskData.privilege>=TaskData.vipentryright) {
				DialogFragment_Report dg_report=new DialogFragment_Report();
				showDialog(fm,dg_report);
			}else{
				Toast.makeText(MainActivity_1.this, "请先申请高级用户", Toast.LENGTH_SHORT).show();
				showDialog(fm,new DialogFragment_ApplyMembership());
			}

		}else{
			Toast.makeText(getApplicationContext(),
					"亲，网络连了么？", Toast.LENGTH_LONG)
					.show();
		}
	}

	public void checkVersion() {
		ConnectivityManager con = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
		boolean wifi = con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
		boolean internet = con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
		if (wifi | internet) {
			UpdateManager mUpdateManger = new UpdateManager(MainActivity_1.this);// 注意此处不能传入getApplicationContext();会报错，因为只有是一个Activity才可以添加窗???
			mUpdateManger.findVersion();
		}else{
			Toast.makeText(getApplicationContext(),
					"亲，网络连了么？", Toast.LENGTH_LONG)
					.show();
		}
	}

	public void conSyn(){
		ConnectivityManager con = (ConnectivityManager)getSystemService(Activity.CONNECTIVITY_SERVICE);
		boolean wifi=con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
		boolean internet=con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
		if(wifi|internet){

			SharedPreferences sp = this.getSharedPreferences("userinfo" , MODE_PRIVATE);
			String phoneNo=TaskData.user;
			String username=sp.getString("username", "");
			if (!TextUtils.isEmpty(TaskData.user)&&TaskTool.isMobileNO11(TaskData.user)) {
				final Map params = new HashMap<String, String>();
				params.put("username", username);
				params.put("phoneNo", phoneNo);
				conMysql.SynLoginRequestPost("UserServlet", params);

				TaskData.adapterUpdate();

			}else{
				Toast.makeText(getApplicationContext(),
						"请先登录或注册", Toast.LENGTH_SHORT)
						.show();
				TaskData.user=null;
				Intent intent =new Intent(MainActivity_1.this,LoginActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				finish();
			}
		}else{
			Toast.makeText(getApplicationContext(),
					"亲，网络连了么？", Toast.LENGTH_LONG)
					.show();
		}

	}
/*
 public void initData(){
		ConnectivityManager con = (ConnectivityManager)getSystemService(Activity.CONNECTIVITY_SERVICE);
		boolean wifi=con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
		boolean internet=con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
		if(wifi|internet){

			SharedPreferences sp = this.getSharedPreferences("userinfo" , MODE_PRIVATE);
			String phoneNo=TaskData.user;
			String username=sp.getString("username", "");
			if (!TextUtils.isEmpty(TaskData.user)&&TaskTool.isMobileNO11(TaskData.user)) {
				// conSyn_db();

				Cursor cursor_rating = DataSupport.findBySQL("select * from userratingbean where username="+"'"+TaskData.user+"'");
				Cursor cursor_reminder = DataSupport.findBySQL("select * from reminder where username="+"'"+TaskData.user+"'");
				Cursor cursor_memo= DataSupport.findBySQL("select * from memo where username="+"'"+TaskData.user+"'");

				final ConnMySQL conn = new ConnMySQL(getApplication());
				boolean flag = false;
				Cursor cursor_taskrec = TaskData.db_TdDB.rawQuery("select * from " + TaskData.TdDB.TABLE_NAME_TaskRecord + " where " + TaskData.TdDB.TASK_USER + "=?", new String[]{TaskData.user});
				conn.InitDataGsonArrayRequestPostSuccess("TaskrecServlet", cursor_taskrec);
				TaskData.cursor_alltasks=TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain +" where "+TaskData.TdDB.TASK_USER+"=?", new String[]{TaskData.user});
				conn.InitDataGsonArrayRequestPostSuccess("TaskmainServlet",TaskData.cursor_alltasks);

				conn.InitDataGsonArrayRequestPostSuccess("MemoServlet",cursor_memo);
				//conn.InitDataGsonArrayRequestPostSuccess("ReminderServlet",cursor_reminder);
				conn.InitDataGsonArrayRequestPostSuccess("RatingServlet",cursor_rating);

			}
		}
	};*/

	public void BaiduCheck() {
		BDAutoUpdateSDK.uiUpdateAction(getApplicationContext(), new MyUICheckUpdateCallback());

	}

	class MyUICheckUpdateCallback implements UICheckUpdateCallback {
		@Override
		public void onNoUpdateFound() {
			//Toast.makeText(getApplication(), "没有更新", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onCheckComplete() {
			//Toast.makeText(getApplication(), "检查结束", Toast.LENGTH_SHORT).show();
			if (TaskTool.getConnCode(getApplicationContext())>2) {
				BDAutoUpdateSDK.silenceUpdateAction(getApplicationContext());
			}
		}
	}

	public void login() {
		//TaskData.alarmReceiverlist=new ArrayList<AlarmReceiverBean>() ;
		//TaskTool.checkTaskCycle();
		//TaskTool.updateOverdueCycleTask();
		//if (Build.VERSION.SDK_INT>=15) {

		if (TaskTool.getConnCode(getApplicationContext())>0) {
			BaiduCheck();
		}
		//}
		TaskData.fm=getFragmentManager();
		final SharedPreferences spp = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
		Editor sppEditor = spp.edit();
		if (spp != null) {
			TaskData.usermodel = spp.getInt("model", 0);
			TaskData.dataperiod = spp.getInt("dataperiod", 1);
			TaskData.preweight = spp.getInt("preweight", 100);
			TaskData.prehour = spp.getInt("prehour", 8);
			TaskData.alarmclock = spp.getBoolean("alarmclock", true);
			TaskData.vibration = spp.getBoolean("vibration", true);
			TaskData.alarmring = spp.getBoolean("alarmring", true);
			TaskData.notification = spp.getBoolean("notification", true);
			TaskData.alarmfreq = spp.getInt("alarmfreq", 0);
			TaskData.alarminterval = spp.getInt("alarminterval", 5);
			TaskData.alarmadvance = spp.getInt("alarmadvance", 0);
			TaskData.endhour = spp.getInt("endhour", 23);
			TaskData.endmin = spp.getInt("endmin", 59);
			TaskData.durationcheck = spp.getBoolean("durationcheck", false);
			TaskData.completionAnimation = spp.getBoolean("completionAnimation", true);
			TaskData.completionSound= spp.getBoolean("completionSound", true);
			TaskData.username = spp.getString("username", "");
			TaskData.usernickname = spp.getString("nickname", "");
			TaskData.userRongtoken = spp.getString("Rongtoken", "");
			TaskData.headpicuri = spp.getString("headpicuri", "");
			TaskData.usergroup = spp.getString("organization", "");
			TaskData.privilege = spp.getInt("userprivilege", 0);

		} else {
			sppEditor.putBoolean("alarmclock", true);
			sppEditor.putBoolean("vibration", true);
			sppEditor.putBoolean("alarmring", true);
			sppEditor.putBoolean("notification", true);
			sppEditor.putInt("alarmfreq", 0);
			sppEditor.putInt("alarminterval", 5);
			sppEditor.putInt("alarmadvance", 0);
			sppEditor.commit();
			TaskData.usermodel = 0;
			TaskData.dataperiod = 1;
			TaskData.preweight = 100;
			TaskData.prehour = 8;
			TaskData.alarmclock = true;
			TaskData.vibration = true;
			TaskData.alarmring = true;
			TaskData.notification = true;
			TaskData.alarmfreq = 0;
			TaskData.alarminterval = 5;
			TaskData.alarmadvance = 0;
			TaskData.endhour = 23;
			TaskData.endmin = 59;
			TaskData.userRongtoken = "";
			TaskData.durationcheck = false;
			TaskData.completionAnimation=true;
			TaskData.completionSound=true;
			TaskData.privilege = 0;
		}
		//if (TaskData.privilege>0) {
		if (TaskData.initflag == 2&& TaskData.privilege>0) {
			Toast.makeText(getApplicationContext(),Hello.sayHello(),Toast.LENGTH_SHORT).show();
			conMysql.initData();
			TaskData.initflag = 1;
			Log.d(Tags + "|login|", "initData");
		}

		TaskData.adapterInit();

		TaskData.tag_taskhistory = "(" + TaskTool.getCurTime() + " " + TaskData.usernickname + ")" + "\n";
		TaskData.tag_tasklesson = "(" + TaskTool.getCurTime() + " " + TaskData.usernickname + ")";

		if (TaskData.adapter_todaytasks != null) {
			TaskData.adapter_todaytasks.getCursor().requery();
			TaskData.adapter_todaytasks.notifyDataSetChanged();
			Log.d(Tags + "|Adapter update|", "todaytasks done");
		}
		if (TaskData.mcAdapter_taskrecord != null) {
			TaskData.mcAdapter_taskrecord.getCursor().requery();
			TaskData.mcAdapter_taskrecord.notifyDataSetChanged();
			Log.d(Tags + "|Adapter update|", "taskrecord done");
		}
		if (TaskData.adapter_opentasks != null) {
			TaskData.adapter_opentasks.getCursor().requery();
			TaskData.adapter_opentasks.notifyDataSetChanged();

		}

		if (TaskTool.checkTaskCycle().size() > 0){
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					Log.d(Tags + "|heckTaskCycle|", "start");
					TaskTool.updateOverdueCycleTask();
					TaskData.adapterUpdate();
					TaskData.homepage_countupdate();
					Log.d(Tags + "|heckTaskCycle|", "end");
					Toast.makeText(getApplicationContext(), "周期任务已刷新", Toast.LENGTH_SHORT).show();
				}
			}, 1000);
		}else {
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					TaskData.adapterUpdate();
					TaskData.homepage_countupdate();
					Log.d(Tags + "|heckTaskCycle|", "end");
					//Toast.makeText(getApplicationContext(), "任务已更新", Toast.LENGTH_SHORT).show();
				}
			}, 1000);
		}

		TaskTool.RegisterAllReminders(getApplication());
		Log.d(TaskData.t_tags,"login end"+(new Date().getTime()-TaskData.t_start)+"ms");

		//Toast.makeText(getApplication()," "+TaskData.privilege+" "+TaskData.entryright, Toast.LENGTH_SHORT).show();
		//Log.d(TaskData.t_tags,""+(new Date().getTime()-TaskData.t_start)+"ms");
	}

	long waitTime = 2000;
	long touchTime = 0;
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(event.getAction() == KeyEvent.ACTION_DOWN && KeyEvent.KEYCODE_BACK == keyCode) {
			long currentTime = System.currentTimeMillis();
			if((currentTime-touchTime)>=waitTime) {
				//让Toast的显示时间和等待时间相同
				Toast.makeText(this, "再按一次退出目标达软件", (int)waitTime).show();
				touchTime = currentTime;
			}else {
				finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}


	public void Logout(){
		SharedPreferences sp = getApplication().getSharedPreferences("userinfo", MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.putString("username","");
		editor.putString("headpicuri","");
		editor.putString("organization","");
		editor.putString("phoneNo","");
		editor.putString("nickname","");
		editor.putString("usertype","");
		editor.putString("userid", "");
		editor.putString("lastlogintime","");
		editor.putInt("logintimes",0);
		editor.putInt("userprivilege",0);
		editor.commit();
		TaskData.username=null;
		TaskData.user=null;
		TaskData.usernickname=null;
		TaskData.userRongtoken=null;
		TaskData.headpicuri=null;
		TaskData.usergroup =null;
		TaskData.privilege=0;
		TaskData.connRongflag=false;
		Intent intent =new Intent(MainActivity_1.this,LoginActivity.class);
		startActivity(intent);
	}

	public  Fragment showFrag(FragmentManager fm,Fragment from_fg,Fragment[] frag,int i){
		if (fm==null){
			fm=getFragmentManager();
		}
		if (fm!=null) {
			FragmentTransaction ft = fm.beginTransaction();
			ft.setTransition(FragmentTransaction.TRANSIT_NONE);
			if (from_fg == null) {
				ft.add(R.id.main_layout, frag[i]).commit();

			} else {
				if (!frag[i].isAdded()) {

					// ft.hide(from_fg);

					ft.hide(from_fg).add(R.id.main_layout, frag[i]).commit();

				} else {
					ft.hide(from_fg).show(frag[i]).commit();

				}
			}
			;
		}

		from_fg = frag[i];
		return from_fg;
	}

	public void showDialog(FragmentManager fm,DialogFragment dialogFragment) {
		if (fm==null){
			fm=getFragmentManager();
		}
		if (fm!=null&&dialogFragment!= null) {
			FragmentTransaction ft = fm.beginTransaction();
			//ft.setTransition(FragmentTransaction.TRANSIT_NONE);
			//  dialogFragment = new Fragment_Search();
			//dialogFragment.setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
			ft.setTransition(FragmentTransaction.TRANSIT_NONE).commit();
			dialogFragment.show(fm, "dialog");
		}
	}


	public void Exit(){
		final Builder builder=new AlertDialog.Builder(this);
		builder.setTitle("信息");
		builder.setMessage("真要离开吗？");
		builder.setPositiveButton("确认", new OnClickListener(){

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				//TextView show=(TextView)findViewById(R.layout.tv_alertdialog_pos);
				//show.setText("");
				finish();
			}
		} );
		builder.setNegativeButton("取消", new OnClickListener(){

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub

			}
		});
		builder.create().show();
	} ;


	public void onResume() {
		super.onResume();
		//MobclickAgent.onResume(this);



	}
	public void onPause() {
		super.onPause();
		//MobclickAgent.onPause(this);
	}


	@Subscribe
	public void onEvent(UserResponseEvent userevent){

		TaskData.userResponseJsonArrayStr=userevent.getUserResponseEvent();
		Log.d(Tags,"receive data"+TaskData.userResponseJsonArrayStr.toString());

		if (CONN_FLAG==0){

			DialogFragment_UserSetting dg_usersetting=new DialogFragment_UserSetting(getApplication());
			showDialog(fm,dg_usersetting);
		}
	}


	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		bottomTab.setCurrentTab(selTabID);
		super.onRestoreInstanceState(savedInstanceState);
	}
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		//super.onSaveInstanceState(outState);
		outState.putInt("index", index);
	}

	@Override
	protected void onStart() {
		super.onStart();

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		timer.cancel();
		// EventBus.getDefault().unregister(getApplication());
		//LocalBroadcastManager lbm=LocalBroadcastManager.getInstance(getApplication());
		if (TaskData.privilege>0) {
			conMysql.quitConSyn_db();
			Log.d(Tags,"quit ");
		}
		Log.d(Tags,"unregit alarm "+TaskData.bcreceiverlist.size());
		if (TaskData.bcreceiverlist!=null && TaskData.bcreceiverlist.size()>0){
			for (int i=0;i<TaskData.bcreceiverlist.size();i++){
				Log.d(Tags,"existing alarm No."+i+TaskData.bcreceiverlist.get(i).toString());
				try{
					getApplication().unregisterReceiver(TaskData.bcreceiverlist.get(i));
					Log.d(Tags,"unregit alarm No."+i+TaskData.bcreceiverlist.get(i).toString());
				}catch (IllegalArgumentException e) {
					if (e.getMessage().contains("Receiver not registered")) {
						// Ignore this exception. This is exactly what is desired
						Log.d(Tags,"unregit alarm No."+i+" error Receiver not registered");

					} else {
						// unexpected, re-throw

						try {
							throw e;
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							Log.d(Tags,"unregit alarm No."+i+" error"+e1.toString());
							e1.printStackTrace();
						}
					}
				}
			}
		}

		TaskData.bcreceiverlist.clear();;
		TaskData.alarmpilist.clear();;
		TaskData.alarmsourceidlist.clear();

		TaskData.subItemlist.clear();;
		TaskData.subdellist.clear();

		//stopService(musicintent);

		super.onDestroy();
        /*
		if(TaskData.db_TdDB!=null){ 
			TaskData.db.close(); 
		}; 
	    if(TaskData.db!=null){ 
	    	TaskData.db.close(); 
		} 
		*/
	}

}

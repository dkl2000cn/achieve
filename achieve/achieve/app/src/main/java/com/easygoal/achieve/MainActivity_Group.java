package com.easygoal.achieve;


import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.view.ActionMode;
import android.support.v7.view.ActionMode.Callback;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;




public class MainActivity_Group extends AppCompatActivity implements AppCompatCallback {

	public RadioGroup radiogroup;
	public RadioButton radiobutton;
	public ListView lv_taskscore;
	public static FragmentManager fm;
	public static Fragment from_fg;
	ConnectivityManager con;
	TabHost bottomTab=null;
	ActionBar actionBar;
	Toolbar toolbar ;
	Timer timer = new Timer();
	MyEvent myevent;
	Handler handler = null;
	int selTabID=0;
	int index=0;
	static int CONN_FLAG=-1;
	// 实例化滑动菜单对???
	SlidingMenu sm;
	CustomDrawerLayout mDrawerLayout;

	int[] draw = new int[]{R.drawable.selector_bottomtab1,R.drawable.selector_bottomtab2,R.drawable.selector_bottomtab3,R.drawable.selector_bottomtab4};
	// String[] str = new String[]{"第一???","第二???","第三???","???4???"};
	int[] id = new int[]{R.id.tab1,R.id.tab2,R.id.tab3,R.id.tab4};
	final String[] btabTag={"今日","任务","进度","绩效"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if(savedInstanceState!=null){

			FragmentManager manager = getFragmentManager();
			manager.popBackStackImmediate(null, 1);
		}
		super.onCreate(savedInstanceState);

		login();
		EventBus.getDefault().register(MainActivity_Group.this);
		//requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_main);

		Fragment fragment_hometab=new FragmentHomeTab();
		Fragment fragment_tasktab=new FragmentTaskTab();
		Fragment fragment_taskscoretab=new FragmentTaskScoreTab();
		Fragment fragment_taskrecordtab=new FragmentTaskRecordTab();
		final Fragment[] frag={fragment_hometab,fragment_tasktab,fragment_taskrecordtab,fragment_taskscoretab};
		from_fg=null;
		fm =getFragmentManager();

		TaskData.fm=fm;

		//ListView lv1=(ListView)findViewById(R.id.listView1);
		//ListviewAdapter lvadapter = new ListviewAdapter(getApplicationContext());
		//lv1.setAdapter(lvadapter);
		//Log.d("lv adapter",lvadapter.toString());
		bottomTab=(TabHost)findViewById(android.R.id.tabhost);
		bottomTab.setup();
		/*final String[] btabTag={"home","task","record","score","log"};
		TabSpec bTab1 = bottomTab.newTabSpec(btabTag[0]).setIndicator("", getResources().getDrawable(R.drawable.home)).setContent(R.id.tab1);
		//Fragment fg_btab1=new Fragment();
		bottomTab.addTab(bTab1);
		//ft.replace(R.id.tab1,frag[0]).commit();
		Log.d("Tabhost", "new TabSpec1");
		TabSpec bTab2 = bottomTab.newTabSpec(btabTag[1]).setIndicator("",getResources().getDrawable(R.drawable.task)).setContent(R.id.tab2);
		bottomTab.addTab(bTab2);
		//ft.hide(frag[0]).replace(R.id.tab2,frag[1]).commit();
		Log.d("Tabhost", "new TabSpec2");
		TabSpec bTab3 = bottomTab.newTabSpec(btabTag[2]).setIndicator("",getResources().getDrawable(R.drawable.record)).setContent(R.id.tab3);
		Log.d("Tabhost", "new TabSpec3");
		bottomTab.addTab(bTab3);
		TabSpec bTab4 = bottomTab.newTabSpec(btabTag[3]).setIndicator("",getResources().getDrawable(R.drawable.score)).setContent(R.id.tab4);
		Log.d("Tabhost", "new TabSpec4");
		bottomTab.addTab(bTab4);*/




		TabWidget tabWidget = (TabWidget) findViewById(android.R.id.tabs);
		for (int i = 0; i < 4; i++) {
			TabSpec tabSpec = bottomTab.newTabSpec(i + "");
			tabSpec.setIndicator(getTabItemView(i));
			tabSpec.setContent(getTabConTent(i));
			bottomTab.addTab(tabSpec);
			Log.d("tab created","tabspec "+i);
		}
		int initTabcolor=getResources().getColor(R.color.mediumorchid);;
		ImageView[] iv = new ImageView[4];
		final TextView[] tv = new TextView[4];
		for (int i=0; i<bottomTab.getTabWidget().getChildCount(); i++){                         //循环每个tabView
			View view = tabWidget.getChildAt(i);                                 //获取tabView???
			//view.setContentDescription(Integer.toString(i+1));
			//view.getLayoutParams().height = (int) (view.getLayoutParams().height / 1.2);
			view.setBackgroundColor(R.drawable.topborder);
			iv[i] = (ImageView) bottomTab.getTabWidget().getChildAt(i).findViewById(R.id.myimage);
			tv[i] = (TextView) bottomTab.getTabWidget().getChildAt(i).findViewById(R.id.mytext);
			//tv.setTextColor(R.drawable.selector_bottomtab1_textcolor);
			//tv.setText("XXXX");
			//iv.setImageDrawable(getResources().getDrawable(R.drawable.icon));
			Log.d("tv"+i,"tv"+ tv.toString());
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
					Log.d("iv3", "got it");
					break;
				}
			}
		}


		FragmentTransaction ft =fm.beginTransaction();
		ft.add(R.id.main_layout, frag[0]).commit();
		Log.d("tab init","set frag0");
		from_fg = frag[0];

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
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		//toolbar.setTitle("Title");
		//toolbar.setSubtitle("SubTitle");
		//toolbar.setLogo(R.drawable.ic_launcher);
		setSupportActionBar(toolbar);
		toolbar.setNavigationIcon(R.drawable.addgoal);
		mDrawerLayout = (CustomDrawerLayout) findViewById(R.id.main_drawer);
		//Button mLvDrawer = (Button) findViewById(R.id.bt_drawer);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);



		toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener(){

			@Override
			public boolean onMenuItemClick(MenuItem menuItem) {
				// TODO Auto-generated method stub

				switch (menuItem.getItemId()) {
					case R.id.action_settings1:

						File sdFileDir=Environment.getExternalStorageDirectory();
						SimpleDateFormat formatter = new SimpleDateFormat ("yyMMddHHmm");
						Date curDate = new Date(System.currentTimeMillis());//获取当前时间
						String curTime = formatter.format(curDate);
						File file1=new File(sdFileDir.getAbsoluteFile()+"/DCIM/"+"[目标达]"+curTime+".png");
						System.out.println(file1.getPath());
						System.out.println(file1.getAbsolutePath());
						System.out.println(file1.toString());
						try {
							file1.createNewFile();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						ScreenShot screenshot = new ScreenShot();
						// BitmapDrawable bd= new BitmapDrawable(screenshot.takeScreenShot(MainActivity_1.this));
						screenshot.shoot(MainActivity_Group.this,file1);
						Intent intent = new Intent(Intent.ACTION_SEND);
						intent.setType("image/*");
						Uri u;
						if (file1 != null && file1.exists()) {

							u = Uri.fromFile(file1);
							Log.d("screenshot",file1.toString()+file1.getTotalSpace()+u.toString());
							intent.putExtra(Intent.EXTRA_STREAM, u);
						} else{
							Log.d("screenshot",file1.toString()+"not exist");
						}
						//intent.putExtra("BITMAP", screenshot.takeScreenShot(MainActivity_1.this));
						intent.putExtra(Intent.EXTRA_SUBJECT, "screenshot");
						intent.putExtra(Intent.EXTRA_TEXT, "screen");
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						startActivity(Intent.createChooser(intent, "screenshot"));

						break;
					case R.id.action_settings4:
						DialogFragment_Task dialogfrag_task=new DialogFragment_Task();
						TaskTool.showDialog(dialogfrag_task);

						break;
					default:break;
				}
				return false;
			}
		});


		ListView mDrawerList = (ListView) findViewById(R.id.left_drawer);
		Log.d("mDrawerlist",mDrawerList.toString());
		ArrayList<DrawerListItems> menuLists = new ArrayList<DrawerListItems>();
		DrawerListItems mItem1 = new DrawerListItems();
		Resources res = this.getResources();
		mItem1.setImage(res.getDrawable(R.drawable.setting32px));
		mItem1.setTitle("账号设置");
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
		DrawerListItems mItem4 = new DrawerListItems();
		mItem4.setImage(res.getDrawable(R.drawable.refresh_32px));
		mItem4.setTitle("用户设置");
		menuLists.add(mItem4);

		DrawerListItems mItem5 = new DrawerListItems();
		mItem5.setImage(res.getDrawable(R.drawable.alarm_32px));
		mItem5.setTitle("提醒方式");
		menuLists.add(mItem5);

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

		DrawerListItems mItem9 = new DrawerListItems();
		mItem9.setImage(res.getDrawable(R.drawable.exit_32px));
		mItem9.setTitle("退出");
		menuLists.add(mItem9);

		// 初始化???配???
		Log.d("mDrawerlist",menuLists.toString());
		DrawerListAdapter DrawerListAdapter = new DrawerListAdapter(menuLists,this);

		mDrawerList.setAdapter(DrawerListAdapter);
		// mDrawerLayout.openDrawer(mDrawerList);

		// ActionBar actionBar = getActionBar();
		//actionBar.setDisplayHomeAsUpEnabled(true);
		//getActionBar().setHomeButtonEnabled(true);

		//fragment_tasklog=new FragmentTaskLog();

		myevent = new MyEvent("我是从网络下载的文本");

		mDrawerList.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				// TODO Auto-generated method stub
				switch (position){


				/*
				case 2:  File sdFileDir=Environment.getExternalStorageDirectory();
				   Log.d("sdDir", sdFileDir.getAbsolutePath());
				   Log.d("sdDir", sdFileDir.getPath());
				   File file1=new File(sdFileDir.getAbsoluteFile()+"/DCIM/aaa.bmp");
					System.out.println(file1.getPath());
					System.out.println(file1.getAbsolutePath());
					System.out.println(file1.toString());
						try {
							file1.createNewFile();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						 Log.d("file1", "read"+file1.canRead());
						 Log.d("file1", "write"+file1.canWrite());
						 Log.d("file1", ""+file1.exists());
						 Log.d("file1", ""+file1.getUsableSpace());

			       ScreenShot screenshot = new ScreenShot();
			       BitmapDrawable bd= new BitmapDrawable(screenshot.takeScreenShot(MainActivity_1.this));
			       screenshot.shoot(MainActivity_1.this,file1);
			       Intent intent = new Intent(Intent.ACTION_SEND);
			       intent.setType("image/*");
			       Uri u;
			       if (file1 != null && file1.exists()) {
			    	   Log.d("file1", file1.toString()+file1.getTotalSpace());

				        u = Uri.fromFile(file1);
				        Log.d("uri", u.toString());
				       intent.putExtra(Intent.EXTRA_STREAM, u);
				      } else{
				    	  Log.d("file1", "not exist");
				      }
			       //intent.putExtra("BITMAP", screenshot.takeScreenShot(MainActivity_1.this));
			       intent.putExtra(Intent.EXTRA_SUBJECT, "fddff");
				     intent.putExtra(Intent.EXTRA_TEXT, "ddd");
				     intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				     startActivity(Intent.createChooser(intent, "love you"));
				      break;
				case 3:  ExportToExcel outputexcel = new ExportToExcel(TaskData.TdDB.TABLE_NAME_TaskMain,TaskData.cursor_finishedtasks);
			    Log.d("download ok","ok");
			    File outfile=outputexcel.writeExcel("love");
			    Intent intent2 = new Intent(Intent.ACTION_SEND);
			       intent2.setType("text/*");
			       Uri u2;
			       if (outfile != null && outfile.exists()) {
			    	   Log.d("file1", outfile.toString()+outfile.getTotalSpace());

				        u = Uri.fromFile(outfile);
				        Log.d("uri", u.toString());
				       intent2.putExtra(Intent.EXTRA_STREAM, u);
				      } else{
				    	  Log.d("file1", "not exist");
				      }
			       //intent.putExtra("BITMAP", screenshot.takeScreenShot(MainActivity_1.this));
			       intent2.putExtra(Intent.EXTRA_SUBJECT, "fddff");
				     intent2.putExtra(Intent.EXTRA_TEXT, "ddd");
				     intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				     startActivity(Intent.createChooser(intent2, "love you!!!"));
				     break;
				  */
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
						     showDialog(dg_usersetting);
					     }*/
						DialogFragment_UserSetting dg_usersetting=new DialogFragment_UserSetting(getApplication());
						TaskTool.showDialog(dg_usersetting);

						break;
					case 1:conSyn();
						break;
					case 2:DialogFragment_Preferences dg_preferences=new DialogFragment_Preferences();
						TaskTool.showDialog(dg_preferences);
						break;
					case 3:
						final SharedPreferences alarmSettingSP=getApplication().getSharedPreferences("alarmsetting",Context.MODE_WORLD_READABLE+Context.MODE_WORLD_WRITEABLE);
						Editor alarmEditor=alarmSettingSP.edit();
						alarmEditor.putBoolean("alarmclock", true);
						alarmEditor.putBoolean("vibration", false);
						alarmEditor.putBoolean("alarmring", false);
						alarmEditor.putBoolean("notification", false);
						DialogFragment_AlarmSetting dg_alarmsetting=new DialogFragment_AlarmSetting();
						TaskTool.showDialog(dg_alarmsetting);
						break;
					case 4:DialogFragment_Feedback dg_feedback=new DialogFragment_Feedback();
						TaskTool.showDialog(dg_feedback);
						break;
					case 5:DialogFragment_Rating dg_rating=new DialogFragment_Rating();
						TaskTool.showDialog(dg_rating);
						break;
					case 6:DialogFragment_About dg_about=new DialogFragment_About();
						TaskTool.showDialog(dg_about);
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




		bottomTab.setOnTabChangedListener(new TabHost.OnTabChangeListener()
		{
			View selview;
			int tabcolor=getResources().getColor(R.color.mediumorchid);;
			int oldtabcolor=getResources().getColor(R.color.darkgrey);;
			//final Fragment from_fg = frag[0]




			@Override
			public void onTabChanged(String s)
			{  Log.d("TabHost currentTab", bottomTab.getCurrentTabTag());
				int selTab = Integer.parseInt(bottomTab.getCurrentTabTag());
				//TextView tv1 = (TextView) bottomTab.getTabWidget().getChildAt(selTab).findViewById(R.id.mytext);

				for (int i=0;i<=3;i++){

					if(i==selTab){
						//  ((View) bottomTab.get.getTag(i)).setBackgroundColor(Color.RED);
						tv[i].setTextColor(tabcolor);
						Log.d("TabHost selTabID", "find"+i);

					} else{
						tv[i].setTextColor(oldtabcolor);
						//bottomTab.getCurrentTabView().setBackgroundColor(Color.WHITE);
					}
				};
				Log.d("TabHost selTabID", String.valueOf(selTabID));

				switch (selTab){
					case 0: //Log.d(selTab+" tab selected","frag0"+"from:"+from_fg.toString());
						// ft.replace(R.id.tab1,frag[selabID]);
						from_fg=showFrag(from_fg,frag,0);
						from_fg=frag[0];
						break;
					// ft.hide(frag[0]).show(frag[1]);
					//4ft.show(frag[0]).hide(frag[1]);
					// Log.d("tab selected","change to tab1");
					// ft.addToBackStack(null);
					// ft.commit();

					case 1: //Log.d(selTab+"tab selected","frag1 "+from_fg.toString());
						from_fg=showFrag(from_fg,frag,1);
						from_fg=frag[1];
						break;
					case 2: Log.d(selTab+"tab selected","fragmenttab2 new");

						//ft.replace(R.id.main_layout,fragmentOpenTasks);
						//Log.d("tab selected","tab3");
						// ft.addToBackStack(null);
						// ft.commit();
						showFrag(from_fg,frag,2);

						from_fg=frag[2];
						break;
					case 3: Log.d(selTab+"tab selected","fragmenttab3 new");

						//ft.replace(R.id.main_layout,fragmentOpenTasks);
						//Log.d("tab selected","tab4");
						// ft.addToBackStack(null);
						// ft.commit();
						showFrag(from_fg,frag,3);

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
						//TaskData.cursor_reminder.requery();
						//TaskData.adapter_reminder.notifyDataSetChanged();
					}

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
		timer.schedule(task, 10000, 60000);

	}

	long waitTime = 2000;
	long touchTime = 0;
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(event.getAction() == KeyEvent.ACTION_DOWN && KeyEvent.KEYCODE_BACK == keyCode) {
			long currentTime = System.currentTimeMillis();
			if((currentTime-touchTime)>=waitTime) {
				//让Toast的显示时间和等待时间相同
				Toast.makeText(this, "再按???次???出目标达软件", (int)waitTime).show();
				touchTime = currentTime;
			}else {
				finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}



	public static Fragment showFrag(Fragment from_fg,Fragment[] frag,int i){
		FragmentTransaction ft = fm.beginTransaction();

		if (from_fg==null){
			ft.add(R.id.main_layout, frag[i]).commit();
			Log.d("tab selected","add frag"+i);
		}else{
			if (!frag[i].isAdded()){
				Log.d("show frag", "frag"+i+"not added");
				// ft.hide(from_fg);
				Log.d("show frag", "hide from_fg");
				ft.hide(from_fg).add(R.id.main_layout,frag[i]).commit();
				Log.d("show frag", "add frag"+i);
			}
			else{
				ft.hide(from_fg).show(frag[i]).commit();
				Log.d("show frag", "show frag aaa");
			}
		};
		from_fg=frag[i];
		Log.d("change frag_fg", "frag i");
		return from_fg;
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


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_menu, menu);
		SubMenu menu_setting = (SubMenu)findViewById(R.id.action_settings2);
		//SubMenu menu_addgoal=menu.addSubMenu("新建");
		//menu_addgoal.setHeaderIcon(R.drawable.addgoal);
		//SubMenu menu_setting=menu.addSubMenu("设置");
		//menu_setting.setHeaderIcon(R.drawable.setting);

		//MenuItem menuItem_share=menu_setting.add("分享hh");
		//MenuItem menuItem_download=menu_setting.add("下载jj");
		//MenuItem menuItem_help=menu_setting.add("帮助ll");
		//MenuItem menuItem_feedback=menu_setting.add("反馈kk");
		//MenuItem menuItem_about=menu_setting.add("关于ll");
	  /*   menu.add(Menu.NONE, Menu.FIRST + 1, 5, "删除").setIcon(

	    	        android.R.drawable.ic_menu_delete);

	    	        // setIcon()方法为菜单设置图标，这里使用的是系统自带的图标，同学们留意一???,???

	    	        // android.R???头的资源是系统提供的，我们自己提供的资源是以R???头的

	    	        menu.add(Menu.NONE, Menu.FIRST + 2, 2, "保存").setIcon(

	    	        android.R.drawable.ic_menu_edit);

	    	        menu.add(Menu.NONE, Menu.FIRST + 3, 6, "帮助").setIcon(

	    	        android.R.drawable.ic_menu_help);

	    	        menu.add(Menu.NONE, Menu.FIRST + 4, 1, "添加").setIcon(

	    	        android.R.drawable.ic_menu_add);

	    	        menu.add(Menu.NONE, Menu.FIRST + 5, 4, "详细").setIcon(

	    	        android.R.drawable.ic_menu_info_details);

	    	        menu.add(Menu.NONE, Menu.FIRST + 6, 3, "发???").setIcon(

	    	        android.R.drawable.ic_menu_send);
	    	        */
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



		/*int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);*/
		}
		return super.onOptionsItemSelected(item);
	}
	public void onOptionMenuClosed(Menu menu){
		//Toast.makeText(this, "选项菜单关闭???", Toast.LENGTH_LONG).show();
	}


	public void conSyn(){
		ConnectivityManager con = (ConnectivityManager)getSystemService(Activity.CONNECTIVITY_SERVICE);
		boolean wifi=con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
		boolean internet=con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
		if(wifi|internet){
			conSyn_db();
			//clear_rating();
			Toast.makeText(getApplication(), "数据同步已 完成", Toast.LENGTH_SHORT).show();
		}else{
			Toast.makeText(getApplicationContext(),
					"亲，网络连了么？", Toast.LENGTH_LONG)
					.show();
		}

	}

	private void GsonObjectRequestPostSuccess(String servletName,Cursor cursor){
		// String url = "http://route.showapi.com/213-3";
		//String url = "http://192.168.1.105:8080/EasyTest/TaskmainServlet";
		//Map<String, String> params = new HashMap<String, String>();
		//params.put("taskname", "value1");
		//params.put("deadline", "value2");
		String url = "http://192.168.1.105:8080/EasyTest/"+servletName;
		//String url="http://www.weather.com.cn/data/cityinfo/101210101.html";
		RequestQueue requestQueue=Volley.newRequestQueue(getApplication());
		if (cursor!=null&&cursor.getCount()>0){
			Cursor ca = cursor;


			mJsonObjectRequest jsonObjectRequest;

			//jolist = new JSONArray();
			ca.moveToFirst();
			int t=0;
			do{
				Map<String, String> params = new HashMap<String, String>();
				for (int i=0;i<ca.getColumnCount();i++){


					params.put(ca.getColumnName(i), ca.getString(i));


				};
				// jolist.add(jo);
				JsonObject jostring=new Gson().toJsonTree(params).getAsJsonObject();
				Log.d("JsonObject"+t, jostring.toString());
				t++;
				jsonObjectRequest = new mJsonObjectRequest(url,jostring,new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						// responseText.setText(error.getMessage());
					}
				});

				requestQueue.add(jsonObjectRequest);

			} while(ca.moveToNext());
			//JSONObject jsonObject = new JSONObject(params);

		}

	}

	private void DelByGsonArrayRequestPost(String servletName,String tbname,int delId){
		// String url = "http://route.showapi.com/213-3";
		//String url = "http://192.168.1.105:8080/EasyTest/TaskmainServlet";
		//Map<String, String> params = new HashMap<String, String>();
		//params.put("taskname", "value1");
		//params.put("deadline", "value2");
		String url = "http://192.168.1.105:8080/EasyTest/"+servletName;
		//String url="http://www.weather.com.cn/data/cityinfo/101210101.html";

		RequestQueue requestQueue=Volley.newRequestQueue(getApplication());
		Cursor ca=TaskData.db_TdDB.rawQuery("select * from "+tbname+" where "+TaskData.TdDB.TASK_ID+"="+delId, null);
		if (ca!=null&&ca.getCount()>0){

			mGsonArrayRequest DelGsonArrayRequest;
			JsonArray jolist=new JsonArray();
			//jolist = new JSONArray();
			ca.moveToFirst();

			int t=0;
			do{
				Map<String, String> params = new HashMap<String, String>();
				for (int i=0;i<ca.getColumnCount();i++){


					params.put(ca.getColumnName(i), ca.getString(i));


				};

				JsonObject jostring=new Gson().toJsonTree(params).getAsJsonObject();
				// Log.d("JsonObject"+t, jostring.toString());
				t++;
				jolist.add(jostring);

			} while(ca.moveToNext());
			//JSONObject jsonObject = new JSONObject(params);
			Log.d("JsonArray"+t, jolist.toString());

			DelGsonArrayRequest = new mGsonArrayRequest(url,jolist,"3",new Response.Listener<JSONArray>() {
				@Override
				public void onResponse(JSONArray response) {
					if(response != null){
						Log.d("json back", "ddd "+response.toString());
						GsonArray2Sqlite(response,TaskData.TdDB.TABLE_NAME_TaskMain,"_id");
						Toast.makeText(getApplicationContext(), "back ok",Toast.LENGTH_SHORT).show();
						TaskData.adapterUpdate();
					}else{
						Toast.makeText(getApplicationContext(), "no data",Toast.LENGTH_SHORT).show();
					}

				}
			},new Response.ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError error) {
					// responseText.setText(error.getMessage());
				}
			});

			requestQueue.add(DelGsonArrayRequest);
			requestQueue.start();
		}

	}



	private void GsonArrayRequestPostSuccess(final String servletName,Cursor cursor){
		// String url = "http://route.showapi.com/213-3";
		//String url = "http://192.168.1.105:8080/EasyTest/TaskmainServlet";
		//Map<String, String> params = new HashMap<String, String>();
		//params.put("taskname", "value1");
		//params.put("deadline", "value2");
		String url = "http://192.168.1.105:8080/EasyTest/"+servletName;
		//String url="http://www.weather.com.cn/data/cityinfo/101210101.html";
		RequestQueue requestQueue=Volley.newRequestQueue(getApplication());
		if (cursor!=null&&cursor.getCount()>0){
			Cursor ca = cursor;


			mGsonArrayRequest gsonArrayRequest;
			JsonArray jolist=new JsonArray();
			//jolist = new JSONArray();
			ca.moveToFirst();
			int t=0;
			do{
				Map<String, String> params = new HashMap<String, String>();
				for (int i=0;i<ca.getColumnCount();i++){


					params.put(ca.getColumnName(i), ca.getString(i));


				};

				JsonObject jostring=new Gson().toJsonTree(params).getAsJsonObject();
				// Log.d("JsonObject"+t, jostring.toString());
				t++;
				jolist.add(jostring);

			} while(ca.moveToNext());
			//JSONObject jsonObject = new JSONObject(params);
			Log.d("JsonArray"+t, jolist.toString());

			gsonArrayRequest = new mGsonArrayRequest(url,jolist,"1",new Response.Listener<JSONArray>() {
				@Override
				public void onResponse(JSONArray response) {
					if(response != null){
						Log.d("json back", response.toString());
						if (servletName=="TaskmainServlet"){
							GsonArray2Sqlite(response,TaskData.TdDB.TABLE_NAME_TaskMain,"_id");
							//Toast.makeText(getApplicationContext(), "back ok",Toast.LENGTH_SHORT).show();
							Log.d("GsonArray2Sqlite", "taskmain ok");
							TaskData.adapterUpdate();
						}
						if (servletName=="TaskrecServlet"){
							GsonArray2Sqlite(response,TaskData.TdDB.TABLE_NAME_TaskRecord,"id");
							//Toast.makeText(getApplicationContext(), "back ok",Toast.LENGTH_SHORT).show();
							Log.d("GsonArray2Sqlite", "taskrec ok");
						}
						if (servletName=="MemoServlet"){
							Memo newMemo = new Memo();
							GsonArray2LitePal(response,"id","memo",Memo.class);
							//Toast.makeText(getApplicationContext(), "back ok",Toast.LENGTH_SHORT).show();
							Log.d("GsonArray2LitePal", "memo ok");

						}
						if (servletName=="ReminderServlet"){
							//Reminder newReminder=new Reiminder();
							GsonArray2LitePal(response,"id","reminder",Reminder.class);
							//Toast.makeText(getApplicationContext(), "back ok",Toast.LENGTH_SHORT).show();
							Log.d("GsonArray2LitePal", "reminder ok");

						}
						if (servletName=="RatingServlet"){
							UserRatingBean newRating=new UserRatingBean();
							GsonArray2LitePal(response,"id","userratingbean",UserRatingBean.class);
							//Toast.makeText(getApplicationContext(), "back ok",Toast.LENGTH_SHORT).show();
							Log.d("GsonArray2LitePal", "userratingbean ok");

						}
						TaskData.adapterUpdate();
					}else{
						Toast.makeText(getApplicationContext(), "no data",Toast.LENGTH_SHORT).show();
					}

				}
			},new Response.ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError error) {
					// responseText.setText(error.getMessage());
				}
			});

			requestQueue.add(gsonArrayRequest);
			requestQueue.start();
		}

	}

	void GsonArray2Sqlite(JSONArray ja,String tbname,String index){
		Cursor cursor=null;
		ContentValues cv;
		JSONObject jo;
		try{
		  for (int i=0;i<ja.length();i++){
			try {
				jo = ja.getJSONObject(i);
				Iterator<String> it=jo.keys();
				//String indexkey = (String) it.next();
				String indexvalue=jo.getString(index);

				cv=new ContentValues();
				//it.next();
				do{
					String key = (String) it.next();
					Object u=jo.get(key);
					//Log.d(key, u.toString());
					if (!key.equals("_id")){
						cv.put(key, u.toString());
					}
				}while(it.hasNext());
				cursor = TaskData.db_TdDB.rawQuery("select * from "+tbname+" where "+index+"="+"'"+indexvalue+"'", null);
				if (cursor.getCount()==0){
					Log.d("|insert backdata|", "diff:"+i);
					long insertcode=TaskData.db_TdDB.insert(tbname, null, cv);
					Log.d("|insert backdata|", "ok"+insertcode);
				}else{
					if (cursor.getCount()>0){
						cursor.moveToFirst();
						String whereAs=index+"=?";
						String[] whereValue={cursor.getString(cursor.getColumnIndex(index))};

						int updatecode = TaskData.db_TdDB.update(tbname, cv, whereAs, whereValue);
						Log.d("|udpate backdata|", "ok:"+i+" "+updatecode);
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	}
	    }catch(Exception e){
			e.printStackTrace();
	    }finally {
		   if (cursor!=null) {
			cursor.close();
		}
	   }
	}

	void GsonArray2LitePal(JSONArray ja,String rowId,String tbname,Class classname){
		Cursor cursor=null;
		ContentValues cv;
		JSONObject jo;
		try{
		      for (int i=0;i<ja.length();i++) {
			       cv = new ContentValues();
			     try {
				    jo = ja.getJSONObject(i);
				    Iterator<String> it = jo.keys();

				     String rowIdvalue = jo.get(rowId).toString();

				    cv.put(rowId, rowIdvalue);
				    it.next();

				  do {
					  String key = (String) it.next();
					  Object u = jo.get(key);
					  cv.put(key, u.toString());

				  } while (it.hasNext());
				  //Cursor cursor = DataSupport.findBySQL("select * from "+tbname+" where "+"username="+"'"+TaskTool.getPhoneNumber(getApplicationContext())+"'"+" and "+rowId+"="+"'"+rowIdvalue+"';");
				   cursor = DataSupport.findBySQL("select * from " + tbname + " where " + rowId + "=" + "'" + rowIdvalue + "';");
				if (cursor!=null&&cursor.getCount() == 0) {
					String root = getApplication().getFilesDir().getAbsolutePath();

					//String path ="data/data/com.example.easygoal/"+tbname;
					//String path=root.replace("files", "databases")+File.separator+tbname;
					String path = root.replace("files", tbname);

					//SQLiteDatabase db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
					//SQLiteDatabase db=this.openOrCreateDatabase("Memo.db", Context.MODE_PRIVATE, null);
					TaskData.db.insert(tbname, null, cv);
					//DataSupport.insert(classname,cv);

				  } else {
					/*
				   cursor.moveToFirst();
				   String whereAs=rowId+"=?";
				   String[] whereValue={cursor.getString(cursor.getColumnIndex(rowId))};
				   DataSupport.update(classname, cv, Long.parseLong(rowIdvalue));
				   Log.d("udpate backdata", "ok");
				   */
			    	}
			   } catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			 }
		  }
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			if (cursor!=null) {
				cursor.close();
			}
		}
	}



	private void jsonArrayRequestPostSuccess(String servletName,Cursor cursor){
		final JSONArray jolist = new JSONArray();

		String url = "http://192.168.1.105:8080/EasyTest/"+servletName;
		//String url="http://www.weather.com.cn/data/cityinfo/101210101.html";
		RequestQueue requestQueue=Volley.newRequestQueue(getApplication());
		if (cursor!=null&&cursor.getCount()>0){
			Cursor ca = cursor;

			ca.moveToFirst();
			int t=0;
			Gson gson=new Gson();
			List<List> lll=new ArrayList<List>();
			do{

				ArrayList<Map<String,String>> arraylist = new ArrayList<Map<String,String>>();
				for (int i=0;i<ca.getColumnCount();i++){
					Map<String, String> newmap =  new HashMap<String,String>();
					newmap.put(ca.getColumnName(i), ca.getString(i));
					arraylist.add(newmap);
				}
				lll.add(arraylist);
				//jolist.put(jo.toJson(arraylist));
				//Log.d("jolist"+t,jolist.toString());
				t++;
			} while(ca.moveToNext());
			//jolist=gson.toJsonTree(lll,new TypeToken<List<List>>(){}.getType()).getAsJsonArray();
			Log.d("jolist",jolist.toString());
		}


		Response.Listener rl=new Response.Listener<JSONArray>() {
			@Override
			public void onResponse(JSONArray response) {
				if(response != null){

					//Toast.makeText(getApplicationContext(), response.toString(),Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(getApplicationContext(), "no data",Toast.LENGTH_SHORT).show();
				}

			}
		};



		Response.ErrorListener rel=new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				//Log.i("res errorlistener",error.getMessage());
			}
		};

		myJsonArrayRequest jr = new myJsonArrayRequest(url,jolist,rl, rel);
		requestQueue.add(jr);
		requestQueue.start();

	}

	private void jsonArrayRequestPostSuccess1(String servletName,Cursor cursor){
		final JSONArray jolist = new JSONArray();

		String url = "http://192.168.1.105:8080/EasyTest/"+servletName;
		//String url="http://www.weather.com.cn/data/cityinfo/101210101.html";
		RequestQueue requestQueue=Volley.newRequestQueue(getApplication());
		if (cursor!=null&&cursor.getCount()>0){
			Cursor ca = cursor;

			ca.moveToFirst();
			int t=0;
			do{
				JSONObject jo=new JSONObject();
				for (int i=0;i<ca.getColumnCount();i++){
					try {
						jo.put(ca.getColumnName(i), ca.getString(i));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				jolist.put(jo);
				Log.d("jolist"+t,jolist.toString());
				t++;
			} while(ca.moveToNext());
		}


		Response.Listener rl=new Response.Listener<JSONArray>() {
			@Override
			public void onResponse(JSONArray response) {
				if(response != null){

					//Toast.makeText(getApplicationContext(), response.toString(),Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(getApplicationContext(), "no data",Toast.LENGTH_SHORT).show();
				}

			}
		};

		Response.ErrorListener rel=new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				//Log.i("res errorlistener",error.getMessage());
			}
		};

		myJsonArrayRequest jr = new myJsonArrayRequest(url,jolist,rl, rel);
		requestQueue.add(jr);
		requestQueue.start();

	}

	Cursor cursor_rating = DataSupport.findBySQL("select * from userratingbean");
	Cursor cursor_reminder = DataSupport.findBySQL("select * from reminder");
	Cursor cursor_memo= DataSupport.findBySQL("select * from memo");
	public void conSyn_db(){
		//TaskData.cursor_alltasks.requery();
		//Log.d("cursor alltasks", ""+TaskData.cursor_alltasks.getCount());
		//GsonArrayRequestPostSuccess("TaskmainServlet",TaskData.cursor_alltasks);
		//Log.d("cursor taskrecord", ""+TaskData.cursor_taskrecord.getCount());
		TaskData.cursor_taskrecord=TaskData.db_TdDB.query(TaskData.TdDB.TABLE_NAME_TaskRecord, null, null, null, null, null, null);
		GsonArrayRequestPostSuccess("TaskrecServlet",TaskData.cursor_taskrecord);
		//Cursor cursor_memo= DataSupport.findBySQL("select * from memo");
		//Log.d("cursor memo", ""+cursor_memo.getCount());
		//GsonArrayRequestPostSuccess("MemoServlet",cursor_memo);
		//Cursor cursor_reminder = DataSupport.findBySQL("select id,sourceid,name,content,createdtime,deadlinetime,advance,frequency,alarminterval,piaction from reminder;");
		//Log.d("cursor reminder", ""+cursor_reminder.getCount());
		//GsonArrayRequestPostSuccess("ReminderServlet",cursor_reminder);
		//Log.d("cursor rating", ""+cursor_rating.getCount());
		//Cursor cursor_rating = DataSupport.findBySQL("select * from userratingbean");
		//GsonArrayRequestPostSuccess("RatingServlet",cursor_rating);
		//jsonArrayRequestPostSuccess("TaskrecServlet",TaskData.cursor_taskrecord);
		//Log.d("cursor alltasks", ""+TaskData.cursor_alltasks.getCount());
		//jsonArrayRequestPostSuccess("MemoServlet",cursor_memo);
		//Log.d("cursor reminder", ""+cursor_reminder.getCount());
		//jsonArrayRequestPostSuccess("ReminderServlet",cursor_reminder);
		//Log.d("cursor rating", ""+cursor_rating.getCount());
		//jsonArrayRequestPostSuccess("RatingServlet",cursor_rating);
		/*
		JSONArray jolist = null;

		String url = "http://192.168.1.105:8080/EasyTest/TaskmainServlet";
		//String url="http://www.weather.com.cn/data/cityinfo/101210101.html";
		RequestQueue requestQueue=Volley.newRequestQueue(getApplication());
	  if (TaskData.cursor_alltasks!=null&&TaskData.cursor_alltasks.getCount()>0){
		Cursor ca = TaskData.cursor_alltasks;
		jolist = new JSONArray();
		ca.moveToFirst();
		int t=0;
		do{
			JSONObject jo=new JSONObject();
		    for (int i=0;i<ca.getColumnCount();i++){
			    try {
					jo.put(ca.getColumnName(i), ca.getString(i));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		   jolist.put(jo);
		   try {
			Log.d("jolist"+t,jolist.get(t).toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   t++;
		} while(ca.moveToNext());
	  }


		Response.Listener rl=new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
            	if(response != null){

                    Toast.makeText(getApplicationContext(), response.toString(),Toast.LENGTH_SHORT).show();
                }else{
                	 Toast.makeText(getApplicationContext(), "no data",Toast.LENGTH_SHORT).show();
                }

            }
        };

       Response.ErrorListener rel=new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("res errorlistener",error.getMessage());
            }
        };
		mJsonArrayRequest jr = new mJsonArrayRequest(url,jolist,rl, rel);

		requestQueue.add(jr);
		requestQueue.start();


	 if (TaskData.cursor_alltasks!=null&&TaskData.cursor_alltasks.getCount()>0){
		Cursor ca = TaskData.cursor_alltasks;

		jolist = new JSONArray();
		ca.moveToFirst();
		do{
			JSONObject jo=new JSONObject();
		    for (int i=0;i<ca.getColumnCount();i++){
			    jo.put(ca.getColumnName(i), ca.getString(i));
		    }
		   // jolist.add(jo);
		} while(ca.moveToNext());
		    //将用户名和密码放入HashMap???
		    Log.d("json data", jolist.toString());



		    class MemoThread implements Runnable
		    {

		        @Override
		        public void run() {
		            // TODO Auto-generated method stub
		        	String uri = "http://192.168.1.105:8080/EasyTest/TaskmainServlet";
		    		//String uri2 = "http://www.163.com/";
		    	    HttpPost request = new HttpPost(uri);
		    	    //HttpGet request2 = new HttpGet(uri);
		    	    Log.i("create request",request.toString());


	        StringEntity se = null;
			try {
				se = new StringEntity(jolist.toString().trim(),"utf8");
				se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
				 request.setEntity(se);
				 Log.i("set entity",jolist.toString());
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

	        // 发???请???
			HttpResponse  httpResponse;
			BasicHttpParams httpParams = new BasicHttpParams();
			httpParams.setParameter("charset", "UTF-8");
	        HttpConnectionParams.setConnectionTimeout(httpParams, 10*1000);
	        HttpConnectionParams.setSoTimeout(httpParams, 20*1000);
			HttpClient httpClient = new DefaultHttpClient(httpParams);
			//httpClient.getConnectionManager().closeExpiredConnections();;
			String retSrc=null;
				try {
					httpResponse = httpClient.execute(request);             // read response entity              // do something!!!
					Log.i("httpresponse",httpResponse.toString());

					if(httpResponse.getStatusLine().getStatusCode()==200)
        	        {

        	        	 // 得到应答的字符串，这也是?????? JSON 格式保存的数???
        				try {
        					retSrc = EntityUtils.toString(httpResponse.getEntity());

        					Log.i("retSrc",retSrc);

        				} catch (ParseException e1) {
        					// TODO Auto-generated catch block
        					e1.printStackTrace();
        				} catch (IOException e1) {
        					// TODO Auto-generated catch block
        					e1.printStackTrace();
        				}
        	          }else{
  						//loginresult="void";
  					  }

				    } catch (Exception e) {
						e.printStackTrace();
						throw new RuntimeException(e);
					} finally {
							request.abort();
							Log.i("request",request.toString());
					}
	           }

		   }
		   MemoThread memothread=new MemoThread();
	        Thread thread_memo = new Thread(memothread);
	    	thread_memo.start();
	    	Log.i("conSyn","task finished");
	   }else{
		   Log.i("conSyn","no task ");
	   }
	  */
	}

	/*
	public void conSyn_taskrec(){
		final JSONArray jolist;
		Cursor ca=TaskData.db_TdDB.query(TaskData.TdDB.TABLE_NAME_TaskRecord, null, null, null, null, null, null);
	 if (ca!=null&&ca.getCount()>0){

		jolist = new JSONArray();
		ca.moveToFirst();
		do{
			JSONObject jo=new JSONObject();
		    for (int i=0;i<ca.getColumnCount();i++){
			    jo.put(ca.getColumnName(i), ca.getString(i));
		    }
		    jolist..add(jo);
		} while(ca.moveToNext());
		    //将用户名和密码放入HashMap???
		    Log.d("json data", jolist.toString());
		    class MemoThread implements Runnable
		    {

		        @Override
		        public void run() {
		            // TODO Auto-generated method stub
		        	String uri = "http://192.168.1.105:8080/EasyTest/TaskrecServlet";
		    		//String uri2 = "http://www.163.com/";
		    	    HttpPost request = new HttpPost(uri);
		    	    //HttpGet request2 = new HttpGet(uri);
		    	    Log.i("create request",request.toString());


	        StringEntity se = null;
			try {
				se = new StringEntity(jolist.toString().trim(),"utf8");
				se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
				 request.setEntity(se);
				 Log.i("set entity",jolist.toString());
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

	        // 发???请???
			HttpResponse  httpResponse;
			BasicHttpParams httpParams = new BasicHttpParams();
			httpParams.setParameter("charset", "UTF-8");
	        HttpConnectionParams.setConnectionTimeout(httpParams, 10*1000);
	        HttpConnectionParams.setSoTimeout(httpParams, 20*1000);
			HttpClient httpClient = new DefaultHttpClient(httpParams);
			//httpClient.getConnectionManager().closeExpiredConnections();;
			String retSrc=null;
				try {
					httpResponse = httpClient.execute(request);             // read response entity              // do something!!!
					Log.i("httpresponse",httpResponse.toString());

					if(httpResponse.getStatusLine().getStatusCode()==200)
        	        {

        	        	 // 得到应答的字符串，这也是?????? JSON 格式保存的数???
        				try {
        					retSrc = EntityUtils.toString(httpResponse.getEntity());

        					Log.i("retSrc",retSrc);

        				} catch (ParseException e1) {
        					// TODO Auto-generated catch block
        					e1.printStackTrace();
        				} catch (IOException e1) {
        					// TODO Auto-generated catch block
        					e1.printStackTrace();
        				}
        	          }else{
  						//loginresult="void";
  					  }

				    } catch (Exception e) {
						e.printStackTrace();
						throw new RuntimeException(e);
					} finally {
							request.abort();
							Log.i("request",request.toString());
					}
	           }

		   }
		   MemoThread memothread=new MemoThread();
	        Thread thread_memo = new Thread(memothread);
	    	thread_memo.start();
	    	Log.i("conSyn","rec finished");
	   }else{
		   Log.i("conSyn","no rec ");
	   }
   }

	public void clear_rating(){
		int del = DataSupport.deleteAll(UserRatingBean.class);
		Cursor ca = DataSupport.findBySQL("select * from userratingbean");
		 Log.i("del",""+del);
	}
	public void conSyn_rating(){
		final JSONArray jolist;
		Cursor ca = DataSupport.findBySQL("select * from userratingbean");
	 if (ca!=null&&ca.getCount()>0){
		jolist = new JSONArray();
		ca.moveToFirst();
		do{
			JSONObject jo=new JSONObject();
		    for (int i=0;i<ca.getColumnCount();i++){
			    jo.put(ca.getColumnName(i), ca.getString(i));
			    Log.d(ca.getColumnName(i), ca.getString(i));
		    }
		    //jolist.add(jo);
		} while(ca.moveToNext());
		    //将用户名和密码放入HashMap???
		    Log.d("json data", jolist.toString());
		    class MemoThread implements Runnable
		    {

		        @Override
		        public void run() {
		            // TODO Auto-generated method stub
		        	String uri = "http://192.168.1.105:8080/EasyTest/RatingServlet";
		    		//String uri2 = "http://www.163.com/";
		    	    HttpPost request = new HttpPost(uri);
		    	    //HttpGet request2 = new HttpGet(uri);
		    	    Log.i("create request",request.toString());


	        StringEntity se = null;
			try {
				se = new StringEntity(jolist.toString().trim(),"utf8");
				se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
				 request.setEntity(se);
				 Log.i("set entity",jolist.toString());
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

	        // 发???请???
			HttpResponse  httpResponse;
			BasicHttpParams httpParams = new BasicHttpParams();
			httpParams.setParameter("charset", "UTF-8");
	        HttpConnectionParams.setConnectionTimeout(httpParams, 10*1000);
	        HttpConnectionParams.setSoTimeout(httpParams, 20*1000);
			HttpClient httpClient = new DefaultHttpClient(httpParams);
			//httpClient.getConnectionManager().closeExpiredConnections();;
			String retSrc=null;
				try {
					httpResponse = httpClient.execute(request);             // read response entity              // do something!!!
					Log.i("httpresponse",httpResponse.toString());

					if(httpResponse.getStatusLine().getStatusCode()==200)
        	        {

        	        	 // 得到应答的字符串，这也是?????? JSON 格式保存的数???
        				try {
        					retSrc = EntityUtils.toString(httpResponse.getEntity());

        					Log.i("retSrc",retSrc);

        				} catch (ParseException e1) {
        					// TODO Auto-generated catch block
        					e1.printStackTrace();
        				} catch (IOException e1) {
        					// TODO Auto-generated catch block
        					e1.printStackTrace();
        				}
        	          }else{
  						//loginresult="void";
  					  }

				    } catch (Exception e) {
						e.printStackTrace();
						throw new RuntimeException(e);
					} finally {
							request.abort();
							Log.i("request",request.toString());
					}
	           }

		   }
		   MemoThread mthread=new MemoThread();
	        Thread thread_m = new Thread(mthread);
	    	thread_m.start();
	    	Log.i("conSyn","rating finished");
	   }else{
		   Log.i("conSyn","no rating ");
	   }
   }

	public void conSyn_Reminder(){
		final JSONArray jolist;
		Cursor ca = DataSupport.findBySQL("select * from reminder");
	 if (ca!=null&&ca.getCount()>0){
		jolist = new JSONArray();
		ca.moveToFirst();
		do{
			JSONObject jo=new JSONObject();
		    for (int i=0;i<ca.getColumnCount();i++){
			    jo.put(ca.getColumnName(i), ca.getString(i));
			    //Log.d(ca.getColumnName(i), ca.getString(i));
		    }
		    //jolist.add(jo);
		} while(ca.moveToNext());
		    //将用户名和密码放入HashMap???
		    Log.d("json data", jolist.toString());
		    class MemoThread implements Runnable
		    {

		        @Override
		        public void run() {
		            // TODO Auto-generated method stub
		        	String uri = "http://192.168.1.105:8080/EasyTest/ReminderServlet";
		    		//String uri2 = "http://www.163.com/";
		    	    HttpPost request = new HttpPost(uri);
		    	    //HttpGet request2 = new HttpGet(uri);
		    	    Log.i("create request",request.toString());


	        StringEntity se = null;
			try {
				se = new StringEntity(jolist.toString().trim(),"utf8");
				se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

				request.setEntity(se);
				 Log.i("set entity",jolist.toString());
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

	        // 发???请???
			HttpResponse  httpResponse;
			BasicHttpParams httpParams = new BasicHttpParams();
			httpParams.setParameter("charset", "UTF-8");
	        HttpConnectionParams.setConnectionTimeout(httpParams, 10*1000);
	        HttpConnectionParams.setSoTimeout(httpParams, 20*1000);
			HttpClient httpClient = new DefaultHttpClient(httpParams);
			//httpClient.getConnectionManager().closeExpiredConnections();;
			String retSrc=null;
				try {
					httpResponse = httpClient.execute(request);             // read response entity              // do something!!!
					Log.i("httpresponse",httpResponse.toString());

					if(httpResponse.getStatusLine().getStatusCode()==200)
        	        {

        	        	 // 得到应答的字符串，这也是?????? JSON 格式保存的数???
        				try {
        					retSrc = EntityUtils.toString(httpResponse.getEntity());

        					Log.i("retSrc",retSrc);

        				} catch (ParseException e1) {
        					// TODO Auto-generated catch block
        					e1.printStackTrace();
        				} catch (IOException e1) {
        					// TODO Auto-generated catch block
        					e1.printStackTrace();
        				}
        	          }else{
  						//loginresult="void";
  					  }

				    } catch (Exception e) {
						e.printStackTrace();
						throw new RuntimeException(e);
					} finally {
							request.abort();
							Log.i("request",request.toString());
					}
	           }

		   }
		   MemoThread mthread=new MemoThread();
	        Thread thread_m = new Thread(mthread);
	    	thread_m.start();
	    	Log.i("conSyn","reminder finished");
	   }else{
		   Log.i("conSyn","no reminder ");
	   }
   }
	public void conSyn_memo(){
		final JSONArray jolist;
		Cursor ca = DataSupport.findBySQL("select * from memo");
	 if (ca!=null&&ca.getCount()>0){
		jolist = new JSONArray();
		ca.moveToFirst();
		do{
			JSONObject jo=new JSONObject();
		    for (int i=0;i<ca.getColumnCount();i++){
			    jo.put(ca.getColumnName(i), ca.getString(i));
			    //Log.d(ca.getColumnName(i), ca.getString(i));
		    }
		    //jolist.add(jo);
		} while(ca.moveToNext());
		    //将用户名和密码放入HashMap???
		    Log.d("json data", jolist.toString());
		    class MemoThread implements Runnable
		    {

		        @Override
		        public void run() {
		            // TODO Auto-generated method stub
		        	String uri = "http://192.168.1.105:8080/EasyTest/MemoServlet";
		    		//String uri2 = "http://www.163.com/";
		    	    HttpPost request = new HttpPost(uri);
		    	    //HttpGet request2 = new HttpGet(uri);
		    	    Log.i("create request",request.toString());


	        StringEntity se = null;
			try {
				se = new StringEntity(jolist.toString().trim(),"utf8");

				se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
				request.setEntity(se);
				 Log.i("set entity",jolist.toString());
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

	        // 发???请???
			HttpResponse  httpResponse;
			BasicHttpParams httpParams = new BasicHttpParams();
			httpParams.setParameter("charset", "UTF-8");
	        HttpConnectionParams.setConnectionTimeout(httpParams, 10*1000);
	        HttpConnectionParams.setSoTimeout(httpParams, 20*1000);
			HttpClient httpClient = new DefaultHttpClient(httpParams);
			//httpClient.getConnectionManager().closeExpiredConnections();;
			String retSrc=null;
				try {
					httpResponse = httpClient.execute(request);             // read response entity              // do something!!!
					Log.i("httpresponse",httpResponse.toString());

					if(httpResponse.getStatusLine().getStatusCode()==200)
        	        {

        	        	 // 得到应答的字符串，这也是?????? JSON 格式保存的数???
        				try {
        					retSrc = EntityUtils.toString(httpResponse.getEntity());

        					Log.i("retSrc",retSrc);

        				} catch (ParseException e1) {
        					// TODO Auto-generated catch block
        					e1.printStackTrace();
        				} catch (IOException e1) {
        					// TODO Auto-generated catch block
        					e1.printStackTrace();
        				}
        	          }else{
  						//loginresult="void";
  					  }

				    } catch (Exception e) {
						e.printStackTrace();
						throw new RuntimeException(e);
					} finally {
							request.abort();
							Log.i("request",request.toString());
					}
	           }

		   }
		   MemoThread mthread=new MemoThread();
	        Thread thread_m = new Thread(mthread);
	    	thread_m.start();
	    	Log.i("conSyn","memo finished");
	   }else{
		   Log.i("conSyn","no memo ");
	   }
   }
   */

	public void UserLoginRequestPost(String servletName,Map params){

		// String url = "http://route.showapi.com/213-3";
		//String url = "http://192.168.1.100:8080/EasyTest/TaskmainServlet";
		//Map<String, String> params = new HashMap<String, String>();
		//params.put("taskname", "value1");
		//params.put("deadline", "value2");
		String url = "http://192.168.1.105:8080/EasyTest/"+servletName;
		//String url="http://www.weather.com.cn/data/cityinfo/101210101.html";
		RequestQueue requestQueue=Volley.newRequestQueue(getApplication());

		JsonObject jostring=new Gson().toJsonTree(params).getAsJsonObject();
		JsonArray jolist=new JsonArray();
		jolist.add(jostring);

		mGsonArrayRequest gsonObjectRequest = new mGsonArrayRequest(url,jolist,"5",new Response.Listener<JSONArray>() {

			@Override
			public void onResponse(JSONArray response) {
				if(response != null){
					//Log.d("json back", "ddd "+response.toString());

					//Toast.makeText(getActivity(), "back ok",Toast.LENGTH_SHORT).show();
					//TaskData.adapterUpdate();
					Log.d("json back", response.toString());
					EventBus.getDefault().post(new UserResponseEvent(response.toString()));
					Log.d("json back", response.toString());
				}else{
					Toast.makeText(getApplication(), "no data",Toast.LENGTH_SHORT).show();
				}

			}
		},new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				// responseText.setText(error.getMessage());
			}
		});

		requestQueue.add(gsonObjectRequest);
		requestQueue.start();

		return ;
	}

	public void UpdateUserRequestPost(String servletName,Map params){

		// String url = "http://route.showapi.com/213-3";
		//String url = "http://192.168.1.100:8080/EasyTest/TaskmainServlet";
		//Map<String, String> params = new HashMap<String, String>();
		//params.put("taskname", "value1");
		//params.put("deadline", "value2");
		String url = "http://192.168.1.105:8080/EasyTest/"+servletName;
		//String url="http://www.weather.com.cn/data/cityinfo/101210101.html";
		RequestQueue requestQueue=Volley.newRequestQueue(getApplication());

		JsonObject jostring=new Gson().toJsonTree(params).getAsJsonObject();
		JsonArray jolist=new JsonArray();
		jolist.add(jostring);

		mGsonArrayRequest gsonObjectRequest = new mGsonArrayRequest(url,jolist,"6",new Response.Listener<JSONArray>() {

			@Override
			public void onResponse(JSONArray response) {
				if(response != null){
					//Log.d("json back", "ddd "+response.toString());

					//Toast.makeText(getActivity(), "back ok",Toast.LENGTH_SHORT).show();
					//TaskData.adapterUpdate();
					Log.d("json back", response.toString());
					EventBus.getDefault().post(new UserResponseEvent(response.toString()));
					Log.d("json back", response.toString());
				}else{
					Toast.makeText(getApplication(), "no data",Toast.LENGTH_SHORT).show();
				}

			}
		},new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				// responseText.setText(error.getMessage());
			}
		});

		requestQueue.add(gsonObjectRequest);
		requestQueue.start();

		return ;
	}

	public int getConnCode(){

		ConnectivityManager con = (ConnectivityManager)getSystemService(Activity.CONNECTIVITY_SERVICE);  ;
		boolean wifi=con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
		boolean internet=con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();

		if (wifi==true&& internet==false){
			return 1;
		}
		if (wifi==false&& internet==true){
			return 2;
		}
		if (wifi==true&& internet==true){
			return 3;
		}
		return 0;
	}

	public void login(){

		String PREFS_NAME = "easygoal";
		String FIRST_RUN = "firstrun";
		CONN_FLAG=getConnCode();
		//SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		//boolean first = settings.getBoolean(FIRST_RUN, true);
		//Log.d("conn status", ""+CONN_FLAG);
		//if (first) {
		//Toast.makeText(this, "The Application is first run",
		//                Toast.LENGTH_SHORT).show();

		SharedPreferences sp = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
		String userPhone = TaskData.user;
		if(CONN_FLAG>0){
			if (sp!=null){
				Map params=new HashMap<String,String>();
				params.put("phoneNo",sp.getString("phoneNo",""));
				params.put("nickname",sp.getString("nickname",""));
				params.put("lastlogintime",sp.getString("lastlogintime",""));
				params.put("logintimes",sp.getInt("logintimes",0));
				UpdateUserRequestPost("UserServlet",params);
				params.put("phoneNo",userPhone);
				UserLoginRequestPost("UserServlet",params);
			}
		}else{
			int times=0;
			Editor logineditor = sp.edit(); //获取编辑???
			String lastlogintime=new Timestamp(new Date().getTime()).toString();
			logineditor.putString("phoneNo",userPhone);
			logineditor.putString("lastlogintime",lastlogintime);
			times++;
			logineditor.putInt("logintimes",times);
			logineditor.commit();
		}

		final SharedPreferences spp=getSharedPreferences("preferences",Context.MODE_WORLD_READABLE+Context.MODE_WORLD_WRITEABLE);
		if (spp!=null){
			TaskData.usermodel = spp.getInt("model", 0);
			TaskData.dataperiod = spp.getInt("dataperiod", 1);
			TaskData.preweight = spp.getInt("preweight", 80);
		}else{
			TaskData.usermodel = 0;
			TaskData.dataperiod = 1;
			TaskData.preweight = 80;
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

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		timer.cancel();
		// EventBus.getDefault().unregister(getApplication());
		EventBus.getDefault().unregister(MainActivity_Group.this);
		super.onDestroy();
	}

	@Override
	public ActionMode onWindowStartingSupportActionMode(Callback arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	/*
	@Subscribe
	 public void onEvent(MyEvent mevent){

	        Toast.makeText(getApplicationContext(), "eventbus"+mevent.getMyEvent(), Toast.LENGTH_SHORT).show();
	    }
	    */

	@Subscribe
	public void onEvent(UserResponseEvent userevent){

		TaskData.userResponseJsonArrayStr=userevent.getUserResponseEvent();
		Log.d("receive data", TaskData.userResponseJsonArrayStr.toString());

		if (CONN_FLAG==0){

			DialogFragment_UserSetting dg_usersetting=new DialogFragment_UserSetting(getApplication());
			TaskTool.showDialog(dg_usersetting);
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
		// TODO Auto-generated method stub
		super.onStart();


		//IntentFilter filter=new IntentFilter();
		//filter.addAction("short");
		//AlarmReciever reciever = new AlarmReciever();
		//getApplication().registerReceiver(reciever,filter);
		//Toast.makeText(getApplication(), "registered"+filter+reciever, Toast.LENGTH_LONG).show();

	}
}

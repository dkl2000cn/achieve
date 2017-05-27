package com.easygoal.achieve;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import org.litepal.crud.DataSupport;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import net.sf.json.JSONArray;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;

public class Activity_Team extends AppCompatActivity {

	FragmentManager fm;
	Fragment from_fg;
	TabHost bottomTab = null;
	Handler handler = null;
	int selTabID = 0;
	int index = 0;
	int[] draw;
	int[] id;
	ImageView[] iv = new ImageView[4];
	TextView[] tv = new TextView[4];
	String[] btabTag;
	Cursor c;
	View oldview = null;
	View newview = null;
	Toolbar toolbar;
	AlertDialog alog3;
	final String Tags = "|Activity_Team|";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		login();
		initView();
		initToolbar();
		setTabhost();
		initUserData();
		initTabhost();
		//initRong();

	}
	private void initView() {
		supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_team);
		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		//supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
		//ActionBar actionBar = getActionBar();
		//actionBar.setDisplayHomeAsUpEnabled(true);
		//actionBar.setHomeAsUpIndicator(R.drawable.back_48px);
	}
	private void initRong(){
		//if (TaskData.connRongflag==false){
		 int k=-1;
		  for (int i=0;i<TaskData.userinfolist_usergroup.size();i++){
		      if (TaskData.userinfolist_usergroup.get(i).get("userId").equals(TaskData.user)) {
				  k = i;
			  }
		  }

			final Map params=new HashMap();
			params.put("userId",TaskData.user);
			params.put("name",TaskData.usernickname);
			params.put("portraitUri",TaskData.userinfolist_usergroup.get(k).get("headpicuri"));

			if (TaskData.userRongtoken!=null&&!TextUtils.isEmpty(TaskData.userRongtoken)){
				//refreshTeam();
				//refreshTeamPic();

				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						RongUtils.connRong(getApplicationContext(),TaskData.userRongtoken,params);
						TaskData.connRongflag=true;
						refreshTeamPic();
					}
				},3000);
				/*
				final Map tokenMap=new HashMap();
				tokenMap.put("phoneNo",TaskData.user);
				tokenMap.put("RongToken",TaskData.userRongtoken);

				new Thread(new Runnable() {
					@Override
					public void run() {
						HttpUtils.RequestPost("UserServlet",tokenMap,"6");
						Log.d(Tags, TaskData.userRongtoken+" "+HttpUtils.RequestPost("UserServlet",tokenMap,"6"));
					}
				}).start();*/
				Toast.makeText(getApplicationContext(),"Rongtoken:"+TaskData.userRongtoken, Toast.LENGTH_SHORT).show();
			}else{
				//initChat();
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						refreshTeam();
						refreshTeamPic();
					}
				},3000);

			}

	}

	void setUserInfoProvider(){
		/**
		 * 设置用户信息的提供者，供 RongIM 调用获取用户名称和头像信息。
		 *
		 * @param userInfoProvider 用户信息提供者。
		 * @param isCacheUserInfo  设置是否由 IMKit 来缓存用户信息。<br>
		 *                         如果 App 提供的 UserInfoProvider
		 *                         每次都需要通过网络请求用户数据，而不是将用户数据缓存到本地内存，会影响用户信息的加载速度；<br>
		 *                         此时最好将本参数设置为 true，由 IMKit 将用户信息缓存到本地内存中。
		 * @see UserInfoProvider
		 */
		RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {

			@Override
			public UserInfo getUserInfo(String s) {
				return null;
			}
		}, false);

	}
 /*
	private void refreshUserInfo(UserInfo userInfo) {
		if (userInfo == null||mRrongIMClient == null) {
			throw new IllegalArgumentException();
		}

		if(RongContext.getInstance()!=null){
			RongContext.getInstance().getUserInfoCache().put(userInfo.getUserId(),userInfo);
		}
	}*/

	private void refreshTeamPic(){
		if (TaskData.userinfolist_usergroup!=null) {
			for (int i=0;i<TaskData.userinfolist_usergroup.size();i++){
				final Map params=new HashMap();
				params.put("userId",TaskData.userinfolist_usergroup.get(i).get("phoneNo"));
				params.put("name",TaskData.userinfolist_usergroup.get(i).get("nicename"));
				params.put("portraitUri",TaskData.userinfolist_usergroup.get(i).get("headpicuri"));

				RongIM.getInstance().refreshUserInfoCache(new UserInfo(
						TaskData.userinfolist_usergroup.get(i).get("phoneNo").toString(),
						TaskData.userinfolist_usergroup.get(i).get("nickname").toString(),
						Uri.parse(TaskData.userinfolist_usergroup.get(i).get("headpicuri").toString())
				));
				Log.e(Tags, "——onSuccess—-" + "refreshed");
			}
		}else{
			Toast.makeText(getApplicationContext(),"尚未收到info", Toast.LENGTH_SHORT).show();
		}
	}

	private void initChat(){

		final Map params=new HashMap();
		params.put("userId",TaskData.user);
		params.put("name",TaskData.usernickname);
		params.put("portraitUri",TaskData.headpicuri);
		final Handler handler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				if (msg.what==1){
					TaskData.userRongtoken=msg.getData().get("token").toString();
					SharedPreferences sp=getSharedPreferences("userinfo",Context.MODE_PRIVATE);
					SharedPreferences.Editor editor= sp.edit();
					editor.putString("Rongtoken",TaskData.userRongtoken);
					RongUtils.connRong(getApplicationContext(),TaskData.userRongtoken,params);
					Log.d(Tags, "Rongtoken" + TaskData.userRongtoken);
					TaskData.connRongflag=true;
					final Map tokenMap=new HashMap();
					tokenMap.put("username",TaskData.username);
					tokenMap.put("phoneNo",TaskData.user);
					tokenMap.put("RongToken",TaskData.userRongtoken);
					new Thread(new Runnable() {
						@Override
						public void run() {
							String token=HttpUtils.RequestPost("UserServlet",tokenMap,"6");
							Log.d(Tags, token+" "+token);
						}
					}).start();


				}

			}
		};

		new Thread(new Runnable() {
			@Override
			public void run() {

				String token = HttpUtils.TokenRequestPost(params,"utf-8");
				Bundle bundle=new Bundle();
				bundle.putString("token",token);
				Log.d(Tags, "token" + token);
				Message msg=new Message();
				msg.what=1;
				msg.setData(bundle);
				handler.dispatchMessage(msg);

			}
		}).start();
	};

	public void login() {
		TaskData.fm=getFragmentManager();
	}

	private void initToolbar() {
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		//toolbar.setTitle("Title");
		//toolbar.setSubtitle("SubTitle");
		//toolbar.setLogo(R.drawable.ic_launcher);
		setSupportActionBar(toolbar);
		toolbar.setNavigationIcon(R.drawable.arrowback_white32px);
		//mDrawerLayout = (CustomDrawerLayout) findViewById(R.id.main_drawer);
		//Button mLvDrawer = (Button) findViewById(R.id.bt_drawer);
		//getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		//getSupportActionBar().setHomeButtonEnabled(true);
        //<meta-data android:name="android.support.PARENT_ACTIVITY"
		//android:value=".MainActivity_1"></meta-data>
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent1 = new Intent();
				intent1.setClass(Activity_Team.this,MainActivity_1.class);
				intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent1);
				finish();
			}
		});


		toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem menuItem) {
				// TODO Auto-generated method stub
				String msg = "";
				String curTime;
				switch (menuItem.getItemId()) {

					case R.id.action_addteamtask:
						DialogFragment_AddTeamTask dg_addteamtask = new DialogFragment_AddTeamTask();
						TaskTool.showDialog(dg_addteamtask);
						break;
					case R.id.action_sharepic:
						File sdFileDir = Environment.getExternalStorageDirectory();
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
						Date curDate = new Date(System.currentTimeMillis());//?????????
						String curTime1 = formatter.format(curDate);
						//File file1=new File(sdFileDir.getAbsoluteFile()+"/DCIM/"+"[????]"+curTime1+".png");
						File file1 = new File(sdFileDir.getAbsoluteFile() + "/DCIM/" + "[目标达]" + curTime1 + ".png");
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

						ScreenShot screenshot = new ScreenShot();
						// BitmapDrawable bd= new BitmapDrawable(screenshot.takeScreenShot(MainActivity_1.this));
						Intent intent = new Intent(Intent.ACTION_SEND);
						intent.setType("image/*");
						Uri u;
						if (file1 != null && file1.exists()) {

							u = Uri.fromFile(file1);
							screenshot.shoot(Activity_Team.this, file1);
							Log.d("screenshot", file1.toString() + file1.getTotalSpace() + u.toString());
							intent.putExtra(Intent.EXTRA_STREAM, u);
							intent.putExtra(Intent.EXTRA_SUBJECT, "任务图片分享");
							intent.putExtra(Intent.EXTRA_TEXT, "任务图片分享");
							intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							startActivity(Intent.createChooser(intent, "任务图片分享"));
						} else {
							Log.d("screenshot", file1.toString() + "not exist");
							Toast.makeText(getApplicationContext(), "文件不存在", Toast.LENGTH_SHORT).show();
						}
						break;
					case R.id.action_search:
						Intent intent1 = new Intent();
						intent1.setClass(Activity_Team.this, SearchActivity.class);
						startActivity(intent1);

					case R.id.action_feeback:
						DialogFragment_Feedback dg_feedback = new DialogFragment_Feedback();
						TaskTool.showDialog(dg_feedback);
						break;

					case R.id.action_aboutTeam:
						DialogFragment_About dg_about = new DialogFragment_About();
						TaskTool.showDialog(dg_about);
						break;
					/*
					case R.id.action_createteam:
						Intent intent0=new Intent(Activity_Team.this,Activity_ChangeTeam.class);
						intent0.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent0);
						finish();
						break;
					*/
					case R.id.action_jointeam:
						Intent intent0 = new Intent(Activity_Team.this, Activity_ChangeTeam.class);
						intent0.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent0);
						finish();
						//DialogFragment_UserSetting dg_usersetting=new DialogFragment_UserSetting(getApplication());
						//TaskTool.showDialog(dg_usersetting);
						//UpdateManager mUpdateManger = new UpdateManager(MainActivity_1.this);// ????????????getApplicationContext();???????????????Activity????????????
						//mUpdateManger.findVersion();
						break;
					case R.id.action_teamchat:
						//RongUtils.connRong(getApplicationContext());
						//initChat(getApplicationContext());

		/*
		for (int i=0;i<TaskData.userinfolist_usergroup.size();i++){
			list_teamuser.add(TaskData.userinfolist_usergroup.get(i).get("username").toString());
		}
						List list_teamuser = new ArrayList();
						list_teamuser.add("15802131279");
						list_teamuser.add("13917981639");
						ArrayList<String> userIds = new ArrayList<String>();
						userIds.add("101");//增加 userId。
						userIds.add("102");//增加 userId。
						userIds.add("103");//增加 userId。

						RongIM.getInstance().addMemberToDiscussion("9527", userIds, new RongIMClient.OperationCallback() {

							@Override
							public void onSuccess() {

							}

							@Override
							public void onError(RongIMClient.ErrorCode errorCode) {

							}
						});

						if (RongIM.getInstance() != null){
							//RongIM.getInstance().getConversationList(Conversation.ConversationType.PRIVATE);
							/*
							RongIM.getInstance().createDiscussionChat(getApplicationContext(), list_teamuser, "目标团队",
									new RongIMClient.CreateDiscussionCallback() {
										@Override
										public void onSuccess(String s) {
											//getIntent().getData().getQueryParameter("title");
											Toast.makeText(getApplicationContext(), "创建讨论组" + s, Toast.LENGTH_SHORT).show();
											Log.d(Tags, "创建讨论组" + s);
										}

										@Override
										public void onError(RongIMClient.ErrorCode errorCode) {
											Toast.makeText(getApplicationContext(), "创建失败"+errorCode.toString(), Toast.LENGTH_SHORT).show();
											Log.d(Tags, "创建讨论组" + errorCode.toString());
										}
									}
							);*/
						//refreshTeamPic();
						HashMap<String, Boolean> hashMap = new HashMap<>();
						//会话类型 以及是否聚合显示
						hashMap.put(Conversation.ConversationType.PRIVATE.getName(), false);
						hashMap.put(Conversation.ConversationType.PUSH_SERVICE.getName(), true);
						hashMap.put(Conversation.ConversationType.SYSTEM.getName(), true);
						RongIM.getInstance().startConversationList(Activity_Team.this, hashMap);
						//RongIM.getInstance().startSubConversationList(Activity_Team.this, Conversation.ConversationType.GROUP);
						//DialogFragment_UserSetting dg_usersetting=new DialogFragment_UserSetting(getApplication());
						//TaskTool.showDialog(dg_usersetting);
						//UpdateManager mUpdateManger = new UpdateManager(MainActivity_1.this);// ????????????getApplicationContext();???????????????Activity????????????
						//mUpdateManger.findVersion();
						break;

					default:break;

				}

				if (!msg.equals("")) {
					//Toast.makeText(MainActivity_1.this, msg, Toast.LENGTH_SHORT).show();
				}
				return false;
			}

		});

	}


	private void refreshTeam(){
		if (TaskData.userinfolist_usergroup!=null) {
			for (int i = 0; i < TaskData.userinfolist_usergroup.size(); i++) {
				if (TaskData.userinfolist_usergroup.get(i).get("RongToken")!=null) {
					String token = TaskData.userinfolist_usergroup.get(i).get("RongToken").toString();
					final Map params = new HashMap();
					params.put("userId", TaskData.userinfolist_usergroup.get(i).get("phoneNo"));
					params.put("name", TaskData.userinfolist_usergroup.get(i).get("nickname"));
					params.put("portraitUri", TaskData.userinfolist_usergroup.get(i).get("headpicuri"));
					Log.d("refreshTeam","token:"+token+" headpicuri:"+TaskData.userinfolist_usergroup.get(i).get("headpicuri"));
					RongUtils.connRong(getApplication(), token, params);
				}
			}
		}
	}

	private void setTabhost() {
		bottomTab = (TabHost) findViewById(android.R.id.tabhost);
		bottomTab.setup();
		draw = new int[]{R.drawable.selector_bottomtab1, R.drawable.selector_bottomtab2, R.drawable.selector_bottomtab3, R.drawable.selector_bottomtab4};

		id = new int[]{R.id.tab1, R.id.tab2, R.id.tab3, R.id.tab4};
		btabTag = new String[]{"我的团队","团队任务", "团队进度", "团队绩效"};
		TabWidget tabWidget = (TabWidget) findViewById(android.R.id.tabs);
		for (int i = 0; i < 4; i++) {
			TabHost.TabSpec tabSpec = bottomTab.newTabSpec(String.valueOf(i));
			tabSpec.setIndicator(getTabItemView(i));
			tabSpec.setContent(getTabConTent(i));
			bottomTab.addTab(tabSpec);
			Log.d(Tags, "tab created tabspec " + i);
		}
		int initTabcolor = getResources().getColor(R.color.mediumorchid);
		for (int i = 0; i < bottomTab.getTabWidget().getChildCount(); i++) {                         //??????tabView
			View view = tabWidget.getChildAt(i);                                 //???tabView
			//view.setContentDescription(Integer.toString(i+1));
			//view.getLayoutParams().height = (int) (view.getLayoutParams().height / 1.2);
			view.setBackgroundResource(R.drawable.topborder);
			iv[i] = (ImageView) bottomTab.getTabWidget().getChildAt(i).findViewById(R.id.myimage);
			tv[i] = (TextView) bottomTab.getTabWidget().getChildAt(i).findViewById(R.id.mytext);
			//tv.setTextColor(R.drawable.selector_bottomtab1_textcolor);
			//tv.setText("XXXX");
			//iv.setImageDrawable(getResources().getDrawable(R.drawable.icon));

			tv[i].setTextSize(16);
			;
			switch (i) {
				case 0: {
					// view.setBackgroundResource(R.drawable.menu_1_selector);
					iv[0].setImageDrawable(getResources().getDrawable(R.drawable.selector_bottomtab1));
					tv[0].setTextColor(initTabcolor);

					break;
				}
				case 1: {
					//  view.setBackgroundResource(R.drawable.menu_2_selector);
					iv[1].setImageDrawable(getResources().getDrawable(R.drawable.selector_bottomtab2));
					tv[1].setTextColor(Color.GRAY);
					break;
				}
				case 2: {
					// view.setBackgroundResource(R.drawable.menu_3_selector);
					iv[2].setImageDrawable(getResources().getDrawable(R.drawable.selector_bottomtab3));
					tv[2].setTextColor(Color.GRAY);
					break;
				}
				case 3: {
					// view.setBackgroundResource(R.drawable.menu_3_selector);
					iv[3].setImageDrawable(getResources().getDrawable(R.drawable.selector_bottomtab4));
					tv[3].setTextColor(Color.GRAY);
					break;
				}
				default:
					break;
			}
		}
	}
	  private void initUserData() {
		  	String usergroup;
	      	final SharedPreferences spp=getSharedPreferences("userinfo",Context.MODE_PRIVATE);
	      	SharedPreferences.Editor sppEditor = spp.edit();
		if (spp!=null) {
			usergroup = spp.getString("organization", "").trim();
			if (!TextUtils.isEmpty(usergroup)) {
				TaskData.usergroup = spp.getString("organization", "");
			} else {
				//Toast.makeText(getApplicationContext(), "?????????", Toast.LENGTH_SHORT).show();
			}
		}
	  }


	private void initTabhost() {

		fm=getFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		ft.setTransition(FragmentTransaction.TRANSIT_NONE);
		Fragment fg_createteam = new Fragment_CreateTeam();
		Fragment fg_teamtask = new Fragment_TeamTaskTab();
		Fragment fg_teamscoretab = new Fragment_TeamScoreTab();
		Fragment fg_teamrecordtab =new Fragment_TeamTaskRecordTab();
		final Fragment[] frag = {fg_createteam,fg_teamtask, fg_teamrecordtab, fg_teamscoretab};
		from_fg=null;
		TaskData.fm=fm;
		ft.add(R.id.main_layout,frag[0]).commit();

		Log.d(Tags,"tabhost init set tab0");
		from_fg=frag[0];
		Log.d(TaskData.t_tags,""+(new Date().getTime() -TaskData.t_start)+"ms");

		bottomTab.setOnTabChangedListener(new TabHost.OnTabChangeListener()

		{
			int tabcolor = getResources().getColor(R.color.mediumorchid);;
			int oldtabcolor = getResources().getColor(R.color.darkgrey);;

			@Override
			public void onTabChanged (String s)
			{
				Log.d(Tags, "TabHost currentTab " + bottomTab.getCurrentTab());
				int selTab = bottomTab.getCurrentTab();

				for (int i = 0; i <4; i++) {

					if (i == selTab) {
						//  ((View) bottomTab.get.getTag(i)).setBackgroundColor(Color.RED);
						tv[i].setTextColor(tabcolor);
						Log.d(Tags, "TabHost selTabID find " + i);

					} else {
						tv[i].setTextColor(oldtabcolor);
						//bottomTab.getCurrentTabView().setBackgroundColor(Color.WHITE);
					}
				}
				;
				Log.d(Tags, "TabHost selTabID " + selTab);

				switch (selTab) {
					case 0: //Log.d(selTab+" tab selected","frag0"+"from:"+from_fg.toString());
						// ft.replace(R.id.tab1,frag[selabID]);
						from_fg = TaskTool.showFrag(TaskData.fm, from_fg, frag, 0);
						from_fg = frag[0];
						break;

					case 1: //Log.d(selTab+"tab selected","frag1 "+from_fg.toString());
						from_fg = TaskTool.showFrag(TaskData.fm, from_fg, frag, 1);
						from_fg = frag[1];
						break;
					case 2:
						Log.d(Tags, selTab + "tab selected fragmenttab2 new");

						TaskTool.showFrag(TaskData.fm, from_fg, frag, 2);
						from_fg = frag[2];
						break;
					case 3:
						Log.d(Tags, selTab + "tab selected fragmenttab3 new");

						TaskTool.showFrag(TaskData.fm, from_fg, frag, 3);
						from_fg = frag[3];
						break;

					default:
						break;
				}
			}
		});
	}


	public View getTabItemView(int index){
		final LayoutInflater layoutInflater = LayoutInflater.from(this);
		View view = layoutInflater.inflate(R.layout.mytab_item, null);
		ImageView imageView = (ImageView) view.findViewById(R.id.myimage);
		imageView.setImageResource(draw[index]);
		TextView textView = (TextView) view.findViewById(R.id.mytext);
		textView.setText(btabTag[index]);
		return view;
	}

	private void joinTeam(final Context context){
		final EditText editorg = new EditText(context);

			if (alog3==null){
				alog3=new AlertDialog.Builder(context)
						.setTitle("新团队")
						//.setIcon(android.R.drawable.ic_dialog_info)
						.setView(editorg)
						.setPositiveButton("确认", new DialogInterface.OnClickListener(){


							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								// TODO Auto-generated method stub
									/*
									ContentValues cv = new ContentValues();
									cv.put("organization", editorg.getText().toString());
									DataSupport.update(UserBean.class, cv, 1);
									*/
								SharedPreferences sp= context.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
								final String username =  sp.getString("username", "");
								Map params=new HashMap<String,String>();
								params.put("username",username);
								params.put("phoneNo",TaskData.user);
								params.put("organization",editorg.getText().toString().trim());
								TeamTaskUtils.updateUserGroup(context,params);
								//TeamTaskUtils.queryUserGroup(getActivity(),editorg.getText().toString().trim());
								Toast.makeText(context, "修改团队成功", Toast.LENGTH_SHORT).show();
					       		    /*
					       	        sfeditor.putString("username", params.get("username").toString());
				                    sfeditor.putString("phoneNo",params.get("phoneNo").toString());
				                    sfeditor.commit();
				                    */
								alog3.dismiss();
								//DialogFragment_UserSetting dg_usersetting=new DialogFragment_UserSetting(getActivity());
								//ghjghTaskTool.showDialog(dg_usersetting);
							}

						})
						.setNegativeButton("取消",  new DialogInterface.OnClickListener(){


							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								// TODO Auto-generated method stub
								alog3.dismiss();
							}

						})
						.show();
			}else{
				alog3.show();
			}
	};

	private int getTabConTent(int index){
		return id[index];
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {  
        // Inflate the menu; this adds items to the action bar if it is present.  
        getMenuInflater().inflate(R.menu.main_menugroup, menu);  
        return true;  
    };

	@Override
	protected void onDestroy() {
		super.onDestroy();
		//RongIM.getInstance().disconnect();

	}
}

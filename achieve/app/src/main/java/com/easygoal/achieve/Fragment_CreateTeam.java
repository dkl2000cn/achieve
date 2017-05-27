package com.easygoal.achieve;


import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.StringSignature;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.sf.json.JSONArray;

import org.json.JSONException;
import org.litepal.crud.DataSupport;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;

import static com.easygoal.achieve.R.id.tv1;
import static com.easygoal.achieve.R.id.tv2;
import static com.easygoal.achieve.R.id.tv3;

public class Fragment_CreateTeam extends Fragment {

	Cursor c;
	View oldview=null;
	View newview=null;
    AlertDialog alog3;
	EditText et_addteam;
	TextView tv_teamname;
	TextView tv_teamMemberCount;
	ListView lv_teammember;
	mBaseAdapter_TeamMember mBaseAdapter_teammember;
	final String Tags="Fragment_CreateTeam";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		final View v=inflater.inflate(R.layout.fg_createteam, container, false);

		lv_teammember=(ListView)v.findViewById(R.id.lv_teammember);

		Button btn_update=(Button)v.findViewById(R.id.btn_update);
		Button btn_add= (Button) v.findViewById(R.id.btn_add);
		Button btn_delete=(Button)v.findViewById(R.id.btn_delete);
		Button btn_refreshteam= (Button) v.findViewById(R.id.btn_refreshteam);
		Button btn_chat= (Button) v.findViewById(R.id.btn_chat);
		Button btn_call= (Button) v.findViewById(R.id.btn_call);
        Button btn_addmember= (Button) v.findViewById(R.id.btn_addmember);
		final EditText et_addteam= (EditText) v.findViewById(R.id.et_teamname);
		tv_teamname= (TextView) v.findViewById(R.id.tv_teamname);
		tv_teamMemberCount= (TextView) v.findViewById(R.id.tv_teamMemberCount);

		final Spinner spin_team = (Spinner)v.findViewById(R.id.spin_team);
		/*
		final List<InputValueSet> list_team=new ArrayList<InputValueSet>();
		list_team.add(new InputValueSet(1,"A"));
		list_team.add(new InputValueSet(2,"B"));
		list_team.add(new InputValueSet(3,"C"));
		// list_imp.add(new InputValueSet(4,"至关重要"));
		mArrayAdapter adapt_spinteam = new mArrayAdapter(getActivity(), R.layout.myspinner_item, list_team);
		list_team.add(new InputValueSet(list_team.size(),TaskData.usergroup));
		*/
		// adap_spin4_taskimportance.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		//spin_team.setAdapter(adapt_spinteam);
		tv_teamMemberCount.setText("点击刷新");
		refreshTeamList();
		et_addteam.setText(TaskData.usergroup);
		/*
		tv_teamname.setText(""+TaskData.usergroup);
		//TeamTaskUtils.updateUserGroup(getActivity(),TaskData.usergroup);
		TeamTaskUtils.queryUserGroup(getActivity(),TaskData.usergroup);
		TeamTaskUtils.QueryTeam(getActivity(),TaskData.usergroup);
		if (TaskData.userinfolist_usergroup!=null) {
			tv_teamMemberCount.setText("团队共 " + TaskData.userinfolist_usergroup.size() + " 人");
		}
		//Toast.makeText(getActivity(),""+TaskData.userlist_usergroup.size(),Toast.LENGTH_SHORT).show();
		if (TaskData.userinfolist_usergroup!=null&&TaskData.userinfolist_usergroup.size()>0) {
			mBaseAdapter_TeamMember mBaseAdapter_teammember = new mBaseAdapter_TeamMember(getActivity(), TaskData.userinfolist_usergroup);
			lv_teammember.setAdapter(mBaseAdapter_teammember);
			//Toast.makeText(getActivity(),"show"+TaskData.userinfolist_usergroup.size(),Toast.LENGTH_SHORT).show();
			TeamTaskUtils.QueryTeam(getActivity(),TaskData.usergroup);
			tv_teamMemberCount.setText("团队共 "+TaskData.userinfolist_usergroup.size()+" 人");
		}else{
			Toast.makeText(getActivity(),"请刷新团队信息",Toast.LENGTH_SHORT).show();
		}
		TaskData.adapterUpdate();
		*/

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshTeamList();
				initRong();
				Toast.makeText(getActivity(), "团队成员已刷新",Toast.LENGTH_SHORT).show();
				//refreshTeamPic();
            }
        },800);




		tv_teamMemberCount.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				refreshTeamList();
				/*
				String teamname=et_addteam.getText().toString().trim();
				if (!TextUtils.isEmpty(teamname)){
					TaskData.usergroup=teamname;
                    //TeamTaskUtils.updateUserGroup(getActivity(),TaskData.usergroup);
					TeamTaskUtils.queryUserGroup(getActivity(),TaskData.usergroup);
					//Toast.makeText(getActivity(),""+TaskData.userlist_usergroup.size(),Toast.LENGTH_SHORT).show();
					if (TaskData.userinfolist_usergroup!=null&&TaskData.userinfolist_usergroup.size()>0) {
						mBaseAdapter_TeamMember mBaseAdapter_teammember = new mBaseAdapter_TeamMember(getActivity(), TaskData.userinfolist_usergroup);
						lv_teammember.setAdapter(mBaseAdapter_teammember);
						//Toast.makeText(getActivity(),"show"+TaskData.userinfolist_usergroup.size(),Toast.LENGTH_SHORT).show();
						tv_teamMemberCount.setText("团队共 "+TaskData.userinfolist_usergroup.size()+" 人");
					}else{
						Toast.makeText(getActivity(),"请刷新团队信息",Toast.LENGTH_SHORT).show();
					}

					tv_teamname.setText(""+TaskData.usergroup);
					TaskData.adapterUpdate();
				}*/
			}
		});

        btn_addmember.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View view) {
				Intent intent1 = new Intent();
				intent1.setClass(getActivity(), Activity_AddTeamMember.class);
				startActivity(intent1);
				getActivity().finish();
				//joinTeam(getActivity());
			}
        });
       /*
		View popupView = getActivity().getLayoutInflater().inflate(R.layout.popupwin_team, null);

		final PopupWindow mPopupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
		mPopupWindow.setTouchable(true);
		mPopupWindow.setOutsideTouchable(true);
		mPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
       */
		/*
		popupView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				switch (v){
					case R.id.btn_call:
								if (TaskData.selMemberID!=null){
								Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+TaskData.selMemberID));
								startActivity(intent);
							}else{
								Toast.makeText(getActivity(), "请先选定成员", Toast.LENGTH_LONG).show();
							}
						  b
				}
			}
		});*/


		lv_teammember.setOnItemClickListener(new OnItemClickListener(){
			int newPos=0;
			int oldPos=0;

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub


					try{
						TaskData.selMemberID=String.valueOf(TaskData.userinfolist_usergroup.get(position).get("phoneNo").toString());
						TaskData.selMemberName=String.valueOf(TaskData.userinfolist_usergroup.get(position).get("nickname").toString());
						//mPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
						showPopupWindow(getActivity(),view);

					}catch(Exception e){
						Log.d(Tags, e.toString());
						e.printStackTrace();
					}

					if (view!=newview){
						oldview=newview;
						newview=view;
						newview.setBackgroundColor(getResources().getColor(R.color.gray));
						if (oldview!=null){
							oldview.setBackgroundColor(getResources().getColor(R.color.mTextColor2));
						}
					}

                  /*
                  for(int i=0;i<parent.getCount();i++){
                      View v=parent.getChildAt(i);
                      if (position == i) {
                     	 int horizontal = 1;
 						//v.requestFocus();
                          v.setBackgroundColor( getResources().getColor(R.color.gray));
                      } else {
                     	 v.setBackgroundColor(getResources().getColor(R.color.white));
                      }


                  }*/
			}
		});
		btn_chat.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				/*
				if (TaskData.selMemberID!=null){
					 RongIM.getInstance().startPrivateChat(getActivity(),TaskData.selMemberID,"和"+TaskData.selMemberName+"谈话");
				}else{
					 Toast.makeText(getActivity(), "请先选定成员", Toast.LENGTH_LONG).show();
				}*/
				//mPopupWindow.showAsDropDown(v);
			}
		});

		btn_call.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (TaskData.selMemberID!=null){
					Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+TaskData.selMemberID));
					startActivity(intent);
				}else{
					Toast.makeText(getActivity(), "请先选定成员", Toast.LENGTH_LONG).show();
				}
			}
		});




		final SwipeRefreshLayout swipeRefreshLayout=(SwipeRefreshLayout)v.findViewById(R.id.swipe_ly);
		swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_green_dark,
				android.R.color.holo_orange_light, android.R.color.holo_red_light);

		swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

			@Override
			public void onRefresh() {
				refreshTeamList();
				swipeRefreshLayout.setRefreshing(false);
				/*
				if (mBaseAdapter_teammember!=null) {
					refreshTeamList();
					//mBaseAdapter_teammember.notifyDataSetChanged();
					//Toast.makeText(getActivity(),"loading",Toast.LENGTH_SHORT).show();
					swipeRefreshLayout.setRefreshing(false);
				}else{
					swipeRefreshLayout.setRefreshing(false);
				}*/
			}
		});

		lv_teammember.setOnScrollListener(new ListView.OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if (scrollState ==view.SCROLL_INDICATOR_BOTTOM) {
					swipeRefreshLayout.setRefreshing(false);
					// 此处在现实项目中，请换成网络请求数据代码，sendRequest .....
					Toast.makeText(getActivity(),"已到最后页",Toast.LENGTH_SHORT).show();
				}
				if (scrollState ==view.SCROLL_INDICATOR_TOP) {
					swipeRefreshLayout.setRefreshing(false);
					// 此处在现实项目中，请换成网络请求数据代码，sendRequest .....
					//Toast.makeText(getActivity(),"已到最上页",Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				//lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
			}

		});

		return v;
	}


	private void initRong(){
		//if (TaskData.connRongflag==false){
        /*
		int k=-1;
		for (int i=0;i<TaskData.userinfolist_usergroup.size();i++) {
			if (TaskData.userinfolist_usergroup.get(i).get("userId").equals(TaskData.user)) {
				k = i;
			}
		}*/
			final Map params=new HashMap();
			params.put("userId",TaskData.user);
			params.put("name",TaskData.usernickname);
			params.put("portraitUri",TaskData.headpicuri);
		if (TaskData.userRongtoken!=null&&!TextUtils.isEmpty(TaskData.userRongtoken)){
			RongUtils.connRong(getActivity(),TaskData.userRongtoken,params);
			//TaskData.connRongflag=true;
			//Toast.makeText(getApplicationContext(),"Rongtoken:"+TaskData.userRongtoken, Toast.LENGTH_SHORT).show();
			//refreshTeamPic();
			//setUserInfoProvider();
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					//RongUtils.connRong(getActivity(),TaskData.userRongtoken,params);
					refreshTeamPic();
				}
			},4000);
		}else{
			initChat(getActivity());
			//refreshTeamPic();
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					//RongUtils.connRong(getActivity(),TaskData.userRongtoken,params);
					refreshTeamPic();
				}
			},4000);
			//refreshTeam();
			//setUserInfoProvider();
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
			String userJAStr;
			@Override
			public UserInfo getUserInfo(final String userId) {

				UserInfo userInfo=new UserInfo(
						userId,
						("nickname").toString(),
						Uri.parse("")
				);

				return  userInfo;//根据 userId 去你的用户系统里查询对应的用户信息返回给融云 SDK。
			}

		}, false);
	}

	private void initChat(final Context context){

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
					SharedPreferences sp=context.getSharedPreferences("userinfo",Context.MODE_PRIVATE);
					SharedPreferences.Editor editor= sp.edit();
					editor.putString("Rongtoken",TaskData.userRongtoken);
					RongUtils.connRong(context,TaskData.userRongtoken,params);
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
					RongUtils.connRong(getActivity(), token, params);
				}
			}
		}
	}

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
				Log.d("refreshTeam",TaskData.userinfolist_usergroup.get(i).get("headpicuri").toString());
			}
		}
	}

	private void refreshTeamList() {

        TeamTaskUtils.queryUserGroup(getActivity(), TaskData.usergroup);
        TeamTaskUtils.QueryTeam(getActivity(), TaskData.usergroup);
        //Toast.makeText(getActivity(),""+TaskData.userlist_usergroup.size(),Toast.LENGTH_SHORT).show();
        if (TaskData.userinfolist_usergroup != null && TaskData.userinfolist_usergroup.size() > 0) {

            mBaseAdapter_TeamMember mBaseAdapter_teammember = new mBaseAdapter_TeamMember(getActivity(), TaskData.userinfolist_usergroup);
            lv_teammember.setAdapter(mBaseAdapter_teammember);
            //Toast.makeText(getActivity(),"show"+TaskData.userinfolist_usergroup.size(),Toast.LENGTH_SHORT).show();
            //TeamTaskUtils.QueryTeam(getActivity(),TaskData.usergroup);
            tv_teamMemberCount.setText("团队共 " + TaskData.userinfolist_usergroup.size() + " 人");

        } else {
            tv_teamMemberCount.setText("点击刷新");
            //Toast.makeText(getActivity(),"请刷新团队信息",Toast.LENGTH_SHORT).show();
        }
        tv_teamname.setText("" + TaskData.usergroup);

		TaskData.adapterUpdate();
	};

    private void joinTeam(final Context context){
        final EditText editNewMember = new EditText(context);

        if (alog3==null){
            alog3=new AlertDialog.Builder(context)
                    .setTitle("增加新成员用户名或手机号")
                    //.setIcon(android.R.drawable.ic_dialog_info)
                    .setView(editNewMember)
                    .setPositiveButton("确认", new DialogInterface.OnClickListener(){


                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
							// TODO Auto-generated method stub
									/*
									ContentValues cv = new ContentValues();
									cv.put("organization", editorg.getText().toString());
									DataSupport.update(UserBean.class, cv, 1);
									*/

							Map params = new HashMap<String, String>();
							String et_newmember = editNewMember.getText().toString().trim();
							if (!TextUtils.isEmpty(et_newmember)){
								   if (TaskTool.isMobileNO(et_newmember)) {
									   params.put("phoneNo", et_newmember);
								   }else {
									   params.put("username", et_newmember);
								   }
								addNewTeamMember(context,"UserServlet",params);
								tv_teamMemberCount.setText("点击刷新");
								//refreshTeamList();
								//Toast.makeText(context, TaskData.usergroup+"增加新成员成功", Toast.LENGTH_SHORT).show();
								alog3.dismiss();
						     }else {
								   Toast.makeText(context,"输入不能为空", Toast.LENGTH_SHORT).show();
							}
                            //params.put("phoneNo",editNewMember.getText().toString().trim());
                            //TeamTaskUtils.updateUserGroup(context,params);
                            //TeamTaskUtils.queryUserGroup(getActivity(),editorg.getText().toString().trim());
							//TaskData.usergroup=editorg.getText().toString().trim();
							//Toast.makeText(context, "团队修改为["+ TaskData.usergroup+"]", Toast.LENGTH_SHORT).show();

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

	void addNewTeamMember(final Context context, final String servletName, final Map params){

		final String Tags="addNewTeamMember";
		String url = TaskData.hostname+servletName;
		RequestQueue requestQueue= Volley.newRequestQueue(context);
		JsonArray jolist=new JsonArray();
		JsonObject jostring=new Gson().toJsonTree(params).getAsJsonObject();
		Log.d(Tags, jostring.toString());
		jolist.add(jostring);

		mGsonArrayRequest gsonArrayRequest = new mGsonArrayRequest(url, jolist, "7", new Response.Listener<org.json.JSONArray>() {
			@Override
			public void onResponse(org.json.JSONArray response) {
				//tv_teamname.setText(""+response.toString());
				if (response != null){
					if (!response.toString().contains("username")) {
						//tv_teamname.setText("团队不存在");
						Toast.makeText(context, "此成员不存在", Toast.LENGTH_SHORT).show();
					}else{
						//JSONArray ja = ConvertStr2JSONArray(response.toString());
						//TeamTaskUtils.updateUserGroup(context,params);
						//Map params=new HashMap<String,String>();
						//params.put("username","denton");
						//params.put("phoneNo","15802131279");
						try {
							String username = response.getJSONObject(0).get("username").toString();
							params.put("username",username);
							params.put("organization",TaskData.usergroup);
							new RetrofitUtils(context).UpdateUserRequestPost("UserServlet",params);
							refreshTeamList();
							Toast.makeText(context, "增加成员"+username+"成功", Toast.LENGTH_SHORT).show();
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				} else {
					//Toast.makeText(getApplicationContext(), "无数据", Toast.LENGTH_SHORT).show();
					//Toast.makeText(getApplicationContext(), "无数据", Toast.LENGTH_SHORT).show();
					Toast.makeText(context, "无数据", Toast.LENGTH_SHORT).show();
					Log.d(Tags, "无数据");
				}

			}

		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				// responseText.setText(error.getMessage());
				new ConnMySQL(context).connFailureNoWarning( error);
			}
		});
		requestQueue.add(gsonArrayRequest);
		// requestQueue.start();
	}

	public void call(){
		if (TaskData.selMemberID!=null&&TaskTool.isMobileNO11(TaskData.selMemberID)){
			Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+TaskData.selMemberID));
			startActivity(intent);

		}else{
			Toast.makeText(getActivity(), "请先选定成员", Toast.LENGTH_SHORT).show();
		}
	}

	public void chat() {
		if (TaskData.selMemberID != null) {
			RongIM.getInstance().startPrivateChat(getActivity(), TaskData.selMemberID, "和" + TaskData.selMemberName + "谈话");
		} else {
			Toast.makeText(getActivity(), "请先选定成员", Toast.LENGTH_SHORT).show();
		}
	}

	//public void mail(String mailaddress ){
	public void mail( ){
		DialogFragment_Mail dialogFragment_mail=new DialogFragment_Mail();
		TaskTool.showDialog(dialogFragment_mail);

	}

	public void sms( ){
		if (TaskData.selMemberID!=null){
			DialogFragment_SMS dialogFragment_sms=new DialogFragment_SMS();
			TaskTool.showDialog(dialogFragment_sms);

		}else{
			Toast.makeText(getActivity(), "请先选定成员",Toast.LENGTH_SHORT).show();
		}
	}

	class ItemClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case (R.id.btn_call): call();break;
				case (R.id.btn_mail): mail();break;
				case (R.id.btn_chat): chat();break;
				case (R.id.btn_sms): sms();break;
				default:break;
			}
		}
	}

	public  void  FastSendSMS(Context context,String phoneNo,String smsMsg){
		SmsManager manager = SmsManager.getDefault();
		ArrayList<String> list = manager.divideMessage("[目标达]您的密码为:"+smsMsg);  //因为??条短信有字数限制，因此要将长短信拆分
		for(String text:list){
			manager.sendTextMessage(phoneNo, null, text, null, null);
		}
		//Toast.makeText(context, "发???完??", Toast.LENGTH_SHORT).show();
	}

	private void showPopupWindow(Context context,View rootview) {
		//设置contentView
		View popupView = LayoutInflater.from(context).inflate(R.layout.popupwin_team, null);
		final PopupWindow mPopupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
		mPopupWindow.setTouchable(true);
		mPopupWindow.setFocusable(false);
		mPopupWindow.setOutsideTouchable(true);
		mPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));

		mPopupWindow.setContentView(popupView);
		//设置各个控件的点击响应
		Button btn_chat = (Button)popupView.findViewById(R.id.btn_chat);
		Button btn_call = (Button)popupView.findViewById(R.id.btn_call);
		Button btn_sms = (Button)popupView.findViewById(R.id.btn_sms);
		Button btn_mail = (Button)popupView.findViewById(R.id.btn_mail);
		ItemClickListener itemlistener = new ItemClickListener();
		btn_chat.setOnClickListener(itemlistener);
		btn_call.setOnClickListener(itemlistener);
		btn_sms.setOnClickListener(itemlistener);
		btn_mail.setOnClickListener(itemlistener);

		//显示PopupWindow
		//View rootview = LayoutInflater.from(MainActivity.this).inflate(R.layout.main, null);
		mPopupWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);

	}

	@Override
   public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		SimpleDateFormat formatter = new SimpleDateFormat ("yyyy年MM月dd日 HH:mm:ss ");
		Date curDate = new Date(System.currentTimeMillis());//获取当前时间
		String str = formatter.format(curDate);
    }

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (TaskData.userinfolist_usergroup!=null&&TaskData.userinfolist_usergroup.size()>0) {
			TaskData.userinfolist_usergroup.clear();
		}
	}
}

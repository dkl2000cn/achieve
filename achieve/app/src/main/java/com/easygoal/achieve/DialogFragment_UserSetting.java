package com.easygoal.achieve;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

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
import com.squareup.picasso.Picasso;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;

import static android.app.Activity.RESULT_OK;

@SuppressLint("ValidFragment")
public class DialogFragment_UserSetting extends DialogFragment {

	private Context context;
	String result;
	ImageView iv_userpic;
	TextView tv_userId;
	TextView tv_phoneNo;
	TextView tv_username;
	TextView tv_usernickname;
	TextView tv_usertype;
	TextView tv_usercredit;
	TextView tv_organization;
	TextView tv_usergrade;
	TextView tv_userlogintimes;
	TextView tv_userlastlogintime;
	TextView tv_online;
	AlertDialog alog1;
	AlertDialog alog2;
	AlertDialog alog3;
	Handler handler;
	public static  int RESULT_PIC=1;

	public DialogFragment_UserSetting(Context context) {
		// TODO Auto-generated constructor stub
		this.context=context;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v=inflater.inflate(R.layout.activity_profile, container);
		Button btn_closewin = (Button) v.findViewById(R.id.btn_closewin);
		Button btn_editpwd = (Button) v.findViewById(R.id.btn_editpwd);
		Button btn_editorg = (Button) v.findViewById(R.id.btn_editorg);
		Button btn_editnickname = (Button) v.findViewById(R.id.btn_editnickname);
		Button btn_applymembership = (Button) v.findViewById(R.id.btn_applymembership);
		//Button btn_editpwd = (Button) v.findViewById(R.id.btn_editpwd);
		iv_userpic=(ImageView)v.findViewById(R.id.iv_userpic);
		tv_phoneNo=(TextView)v.findViewById(R.id.tv_phoneNo);
		tv_userId=(TextView)v.findViewById(R.id.tv_userId);
		tv_username=(TextView)v.findViewById(R.id.tv_username);
		tv_usernickname=(TextView)v.findViewById(R.id.tv_usernickname);
		tv_usertype=(TextView)v.findViewById(R.id.tv_usertype);
		tv_organization=(TextView)v.findViewById(R.id.tv_organization);
		tv_usercredit=(TextView)v.findViewById(R.id.tv_usercredit);
		tv_usergrade=(TextView)v.findViewById(R.id.tv_usergrade);
		tv_userlogintimes=(TextView)v.findViewById(R.id.tv_userlogintimes);
		tv_userlastlogintime=(TextView)v.findViewById(R.id.tv_userlastlogintime);
		tv_online = (TextView)v.findViewById(R.id.tv_online);


		// ??????
		SharedPreferences sharedpreference = context.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
		final Editor sfeditor = sharedpreference.edit();
		ConnectivityManager con = (ConnectivityManager)context.getSystemService(Activity.CONNECTIVITY_SERVICE);  ;
		boolean wifi=con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
		boolean internet=con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();


		String userPhone = sharedpreference.getString("phoneNo", "");
		String userId = sharedpreference.getString("userId", "");
		final String username = sharedpreference.getString("username", "");
		String nickname = sharedpreference.getString("nickname", "");
		String usertype = sharedpreference.getString("usertype", "");
		String organization = sharedpreference.getString("organization", "");
		String lastlogintime= sharedpreference.getString("lastlogintime", "");
		int logintimes= sharedpreference.getInt("logintimes", 0);
		tv_userId.setText(userId);
		tv_username.setText(username);
		tv_usernickname.setText(nickname);
		tv_phoneNo.setText(userPhone);
		tv_usertype.setText(usertype);
		tv_organization.setText(organization);
		tv_userlastlogintime.setText(TimeData.TimeStamp2TimeStrYY(lastlogintime));
		tv_userlogintimes.setText(""+logintimes);
		tv_online.setText(TaskData.onlinestatus);
		//final Map userMap;



		if (TaskData.headpicuri==null) {
			//iv_userpic.setLayoutParams(new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(100, 100)));
			final SharedPreferences sp = getActivity().getSharedPreferences("userinfo", Context.MODE_PRIVATE);
			TaskData.headpicuri= sp.getString("headpicuri", "");

		}else{
			//Toast.makeText(context,TaskData.headpicuri,Toast.LENGTH_SHORT).show();
		}

		if (TaskData.headpicuri!=null&& !TextUtils.isEmpty(TaskData.headpicuri)){
			//Toast.makeText(context,"头像uri"+TaskData.headpicuri,Toast.LENGTH_SHORT).show();
			//iv_userpic.setImageURI(Uri.parse(TaskData.headpicuri));
			//iv_userpic.setLayoutParams(new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(100, 100)));

			Glide.with(getActivity())
					.load(TaskData.headpicuri)
					.placeholder(R.drawable.defaultheadpic_100px)
					.signature(new StringSignature(UUID.randomUUID().toString()))
					//.diskCacheStrategy(DiskCacheStrategy.ALL)
					//.centerCrop()

					.bitmapTransform(new CircleTransform(context))
					.dontAnimate()
					.into(iv_userpic);


		}else{
			//iv_userpic.setLayoutParams(new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(100, 100)));
			/*
			Glide.with(getActivity())
					.load(R.drawable.user)
					//.signature(new StringSignature(UUID.randomUUID().toString()))
					.diskCacheStrategy(DiskCacheStrategy.ALL)
					//.centerCrop()
					.dontAnimate()
					.into(iv_userpic);
			*/
			Glide.with(getActivity())
					.load(R.drawable.defaultheadpic_100px)
					.signature(new StringSignature(UUID.randomUUID().toString()))
					//.centerCrop()
					.bitmapTransform(new CircleTransform(context))
					.dontAnimate()
					.into(iv_userpic);
			Toast.makeText(context,"请点击选择头像",Toast.LENGTH_SHORT).show();
		}

        /*
		handler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);

                    String headpicuri=msg.getData().getString("response");
					//Log.d("get userpic",msg.getData().getString("response"));
					//JSONObject resp = new JSONObject(msg.getData().getString("response"));
					//Bitmap bm = (Bitmap) resp.get("headpic");
					//iv_userpic.setImageBitmap(bm);
					//bm.recycle();
					Toast.makeText(context,"头像uri"+headpicuri,Toast.LENGTH_SHORT).show();
					Glide.with(getActivity())
							.load(headpicuri)
							//.diskCacheStrategy(DiskCacheStrategy.ALL)
							//.centerCrop()
							.dontAnimate()
							.into(iv_userpic);

				//Map userMap= JSONUtils.getMapFromJson(msg.getData().getString("response"));
				//iv_userpic.setImageBitmap(userMap.get("headpic").);
				//Toast.makeText(context,msg.getData().getString("response"),Toast.LENGTH_SHORT).show();
			}
		};*/


	         /*
		   if(wifi|internet){

			   Map params=new HashMap<String,String>();
       		   params.put("phoneNo",getPhoneNumber());
               UserLoginRequestPost("UserServlet",params);

			    tv_online.setText(TaskData.onlinestatus);


		   }else{
			    tv_online.setText("(???????)");
		   }



			    String userinfo=TaskData.userResponseJsonArrayStr;
		     if (userinfo!=null&&!userinfo.trim().isEmpty()){
			     try {
			     	JSONArray joa=new JSONArray(userinfo);
			    	Log.d("jsonarray", joa.toString());
				    for (int i=0;i<joa.length();i++){
					JSONObject jo=joa.getJSONObject(i);
					Iterator<String> it = jo.keys();
					do{
						String key=(String)it.next();
						Log.d("jo key", key);

						if ((String)jo.get(key)!=null){
						Log.d("jo value", jo.get(key).toString());
						}

					}while(it.hasNext());
					if (jo.getString("userId")!=null){
				      tv_userId.setText(jo.getString("userId"));
				      sfeditor.putString("userId",jo.getString("userId"));
					}
					if (jo.getString("phoneNo")!=null){
					  tv_phoneNo.setText(jo.getString("phoneNo"));
					  //sfeditor.putString("userId",jo.getString("userId"));
						}
					if (jo.getString("username")!=null){
				      tv_username.setText(jo.getString("username"));
				      sfeditor.putString("usernane",jo.getString("username"));
					}
					if (jo.get("lastlogintime")!=null){
					     tv_userlastlogintime.setText((String)jo.get("lastlogintime"));
					     sfeditor.putString("lastlogintime",jo.getString("lastlogintime"));
					 }
					if (jo.get("logintimes")!=null){
					    tv_userlogintimes.setText((String)jo.get("logintimes"));
					    sfeditor.putInt("logintimes",jo.getInt("logintimes"));
						}

					if (jo.getString("usertype")!=null){
				       tv_usertype.setText(jo.getString("usertype"));
				       sfeditor.putString("usertype",jo.getString("usertype"));
					}

					if (jo.get("usercredit")!=null){
				     tv_usercredit.setText(jo.get("usercredit").toString());
				      sfeditor.putString("usercredit",jo.getString("usercredit"));
					}
					if (jo.getString("nickname")!=null){
					       tv_usernickname.setText(jo.getString("nickname"));
					       sfeditor.putString("nickname",jo.getString("nickname"));
						}


				   }
			   } catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		      	}

			      sfeditor.commit();
			      //Toast.makeText(getActivity(), "????????????", Toast.LENGTH_SHORT).show();

		      }else{
			        Toast.makeText(getActivity(), "δ???????", Toast.LENGTH_SHORT).show();
		           //Toast.makeText(getActivity(), "??????????????", Toast.LENGTH_LONG).show();

	        	 String userPhone = sharedpreference.getString("phoneNo", "");
	        	 String userId = sharedpreference.getString("userId", "");
	        	 String username = sharedpreference.getString("username", "");
	        	 String nickname = sharedpreference.getString("nickname", "");
	        	 String usertype = sharedpreference.getString("usertype", "");
	        	 String lastlogintime= sharedpreference.getString("lastlogintime", "");
	             int logintimes= sharedpreference.getInt("logintimes", 1);
			     tv_userId.setText(userId);
			     tv_username.setText(username);
			     tv_usernickname.setText(nickname);
			     tv_phoneNo.setText(userPhone);
			     tv_usertype.setText(usertype);
		         tv_userlastlogintime.setText(lastlogintime);
		         tv_userlogintimes.setText(""+logintimes);

		         //Toast.makeText(getActivity(), "???????", Toast.LENGTH_SHORT).show();
		      }
		  */

	    /*
		void GsonArray2Sqlite(JSONArray ja,String tbname){

		 for (int i=0;i<ja.length();i++){
			JSONObject jo;
			try {
				jo = ja.getJSONObject(i);
				Iterator<String> it=jo.keys();
				String rowId=it.next();
				 ContentValues cv=new ContentValues();
				do{
				 String key = (String) it.next();
				 Object u=jo.get(key);

				 cv.put(key, u.toString());
				}while(it.hasNext());
				 Cursor cursor = TaskData.db_TdDB.rawQuery("select * from "+tbname+" where "+"_id"+"="+rowId, null);
				 if (cursor.getCount()==0){
				   TaskData.db_TdDB.insert(tbname, null, cv);
				   Log.d("insert backdata", "ok");
				 }else{
				   cursor.moveToFirst();
				   String whereAs=TaskData.TdDB.TASK_ID+"=?";
				   String[] whereValue={cursor.getString(cursor.getColumnIndex(TaskData.TdDB.TASK_ID))};
				   TaskData.db_dDB.update(tbname, cv, whereAs, whereValue);
				   Log.d("udpate backdata", "ok");
				 }
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		 }
		}

	    Cursor c=DataSupport.findBySQL("select * from userbean");
	    if (c!=null&&c.getCount()>0){
		    c.moveToFirst();
		   	do {

		    		   tv_userId.setText(c.getString(c.getColumnIndex("userid")));
		    		   tv_username.setText(c.getString(c.getColumnIndex("username")));
		    		   tv_usernickname.setText(c.getString(c.getColumnIndex("usernickname")));
		    		   tv_usertype.setText(c.getString(c.getColumnIndex("usertype")));
		    		   tv_userscore.setText(c.getString(c.getColumnIndex("userscore")));
		    		   tv_usergrade.setText(c.getString(c.getColumnIndex("usergrade")));
		    		   tv_userlogintimes.setText(c.getString(c.getColumnIndex("userlogintimes")));
		    		   tv_userlastlogintime.setText(c.getString(c.getColumnIndex("userlastlogintime")));

		      }while(c.moveToNext());


			}

	     */
		btn_closewin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// ???????
				dismiss();
			}
		});

		iv_userpic.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
                /* 开启Pictures画面Type设定为image */
				intent.setAction(Intent.ACTION_PICK);
				intent.setType("image/*");
                /* 使用Intent.ACTION_GET_CONTENT这个Action */

                /* 取得相片后返回本画面 */
				startActivityForResult(intent, RESULT_PIC);
			}
		});


		btn_editpwd.setOnClickListener(new OnClickListener(){
			EditText editpwd = new EditText(getActivity());
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (alog2==null){
					alog2=new AlertDialog.Builder(getActivity())
							.setTitle("新密码")
							//.setIcon(android.R.drawable.ic_dialog_info)
							.setView(editpwd)
							.setPositiveButton("确认", new DialogInterface.OnClickListener(){


								@Override
								public void onClick(DialogInterface arg0, int arg1) {
									// TODO Auto-generated method stub
									ContentValues cv = new ContentValues();
									cv.put("password", editpwd.getText().toString());
									DataSupport.update(UserBean.class, cv, 1);

									Map params=new HashMap<String,String>();
									params.put("username",username);
									params.put("phoneNo",TaskData.user);
									params.put("password",editpwd.getText().toString());
									new ConnMySQL(getActivity()).UpdateUserRequestPost("UserServlet",params);
									Toast.makeText(context, "密码修改成功", Toast.LENGTH_SHORT).show();
					       		/*
					       	    sfeditor.putString("username", params.get("username").toString());
				                sfeditor.putString("phoneNo",params.get("phoneNo").toString());
				                sfeditor.commit();
				                */
									dismiss();
									alog2.dismiss();
									//DialogFragment_UserSetting dg_usersetting=new DialogFragment_UserSetting(getActivity());
									//ghjghTaskTool.showDialog(dg_usersetting);
								}

							})
							.setNegativeButton("取消",  new DialogInterface.OnClickListener(){


								@Override
								public void onClick(DialogInterface arg0, int arg1) {
									// TODO Auto-generated method stub
									alog2.dismiss();
								}

							})
							.show();
				}else{
					alog2.show();
				}


			}

		});

		btn_editnickname.setOnClickListener(new OnClickListener(){
			EditText et_nickname = new EditText(getActivity());
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (alog1==null){
					alog1=new AlertDialog.Builder(getActivity())
							.setTitle("新昵称")
							//.setIcon(android.R.drawable.ic_dialog_info)
							.setView(et_nickname)
							.setPositiveButton("确认", new DialogInterface.OnClickListener(){


								@Override
								public void onClick(DialogInterface arg0, int arg1) {
									// TODO Auto-generated method stub
									ContentValues cv = new ContentValues();
									cv.put("nickname", et_nickname.getText().toString());
									DataSupport.update(UserBean.class, cv, 1);
									Toast.makeText(context, "修改昵称成功", Toast.LENGTH_SHORT).show();
									Map params=new HashMap<String,String>();
									params.put("username",username);
									params.put("phoneNo",TaskData.user);
									params.put("nickname",et_nickname.getText().toString());
									new ConnMySQL(getActivity()).UpdateUserRequestPost("UserServlet",params);

									//String lastlogintime=new Timestamp(new Date().getTime()).toString();

									sfeditor.putString("nickname", params.get("nickname").toString());
									sfeditor.commit();

									dismiss();
									alog1.dismiss();
									//DialogFragment_UserSetting dg_usersetting=new DialogFragment_UserSetting(getActivity());
									//TaskTool.showDialog(dg_usersetting);
								}

							})
							.setNegativeButton("取消",  new DialogInterface.OnClickListener(){


								@Override
								public void onClick(DialogInterface arg0, int arg1) {
									// TODO Auto-generated method stub
									alog1.dismiss();
								}

							})
							.show();
				}else{
					alog1.show();
				}
			}

		});

		btn_editorg.setOnClickListener(new OnClickListener(){
			EditText editorg = new EditText(getActivity());
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (alog3==null){
					alog3=new AlertDialog.Builder(getActivity())
							.setTitle("修改团队")
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

									Map params=new HashMap<String,String>();
									params.put("username",username);
									params.put("phoneNo",TaskData.user);
									params.put("organization",editorg.getText().toString().trim());
									TeamTaskUtils.updateUserGroup(getActivity(),params);
									//TeamTaskUtils.queryUserGroup(getActivity(),editorg.getText().toString().trim());
									TaskData.usergroup=editorg.getText().toString().trim();
									sfeditor.putString("organization", TaskData.usergroup);
									sfeditor.commit();
									Toast.makeText(context, "修改团队成功", Toast.LENGTH_SHORT).show();
					       		    /*
					       	        sfeditor.putString("username", params.get("username").toString());
				                    sfeditor.putString("phoneNo",params.get("phoneNo").toString());
				                    sfeditor.commit();
				                    */
									dismiss();
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


			}

		});

		btn_applymembership.setOnClickListener(new OnClickListener(){
			EditText et_membershippwd = new EditText(getActivity());
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (TaskData.privilege>=TaskData.vipentryright) {
					Toast.makeText(getActivity(), "亲，您已升过VIP了", Toast.LENGTH_SHORT).show();
				}else{
					TaskTool.showDialog(new DialogFragment_ApplyMembership());
				}

			/*
  			 alog3=new AlertDialog.Builder(getActivity())
  		  	 	.setTitle("??????")
  		  	 	//.setIcon(android.R.drawable.ic_dialog_info)
  		  	 	.setView(et_membershippwd)
  		  	 	.setPositiveButton("???", new DialogInterface.OnClickListener(){


  					@Override
  					public void onClick(DialogInterface arg0, int arg1) {
  						// TODO Auto-generated method stub
  						ContentValues cv = new ContentValues();
  						cv.put("password", et_membershippwd.getText().toString());
  						DataSupport.update(UserBean.class, cv, 1);
  					    Toast.makeText(context, "??????", Toast.LENGTH_SHORT).show();
  					     Map params=new HashMap<String,String>();
 			       		params.put("phoneNo",TaskData.user);
 			       		params.put("password",et_membershippwd.getText().toString());
 			       		UpdateUserRequestPost("UserServlet",params);


  					}

  		  	 	  })
  		  	 	.setNegativeButton("???",  new DialogInterface.OnClickListener(){


  					@Override
  					public void onClick(DialogInterface arg0, int arg1) {
  						// TODO Auto-generated method stub
  						alog3.dismiss();
  					}
  		  	 		
  		  	 	  })
  		  	 	.show();
  		  	 	*/
			}

		});

		return v;
	}

	@Override
     public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode!=RESULT_OK) {
			//Toast.makeText(getActivity(), "" + resultCode, Toast.LENGTH_SHORT).show();
			return;
		}
		if (requestCode == RESULT_PIC) {

			if (data!=null){
				if(data.getData()!= null){
					//use bundle to get data
					final Uri uri = data.getData();
                    if (!TextUtils.isEmpty(uri.toString())) {
						Glide.with(getActivity())
								.load(uri.toString())
								.placeholder(R.drawable.defaultheadpic_100px)
								//.diskCacheStrategy(DiskCacheStrategy.ALL)
								//.centerCrop()
								.dontAnimate()
								.into(iv_userpic);
					}

					//ContentResolver resolver = getActivity().getContentResolver();
					//Uri originalUri = data.getData();
					// 将图片内容解析成字节数组
					//byte[] mContent = new byte[0];
					try {
						//mContent = readStream(resolver.openInputStream(Uri.parse(originalUri.toString())));
						//Bitmap pic = getPicFromBytes(mContent,getOpts());
						//Log.d("userpic original size",""+pic.getByteCount());
						//iv_userpic.setImageBitmap(pic);

                        //HttpUtils.ImageRequestPost("UserServlet",params,"6");


						new Thread(new Runnable() {
							@Override
							public void run() {
								String uploadPath=HttpUtils.uploadFile(getActivity(),"UploadServlet",uri.toString(),TaskData.user+".jpg");
								Log.d("userpic upload",uploadPath);

								String headpicuri=TaskData.hostname+"headpic"+TaskData.user+".jpg";

									TaskData.headpicuri=headpicuri;

								final Map params=new HashMap<String,String>();
								params.put("username",TaskData.username);
								params.put("phoneNo",TaskData.user);
								params.put("headpicuri",headpicuri);
								String WebimgUri=HttpUtils.ImageRequestPost("UserServlet",params,"6");
								Log.d("userpic upload",WebimgUri);
								RongUtils.refreshTeamPic(TaskData.user,TaskData.usernickname,headpicuri);
								//TaskData.profileUpdateflag=1;

							}
						}).start();

					} catch (Exception e) {
						e.printStackTrace();
					}
					// 将字节数组转换为ImageView可调用的Bitmap对象

					TaskData.profileUpdateflag=1;
					if (TaskData.profileUpdateflag==1){
						Glide.get(getActivity()).clearMemory();
						new Thread(new Runnable() {
							@Override
							public void run() {
								Glide.get(getActivity()).clearDiskCache();
							}
						}).start();

						TaskData.profileUpdateflag=0;
                /*
				for (int i=0;i<TaskData.userinfolist_usergroup.size();i++) {
					Glide.with(getActivity())
							.load(TaskData.userinfolist_usergroup.get(i).get("headpicuri").toString())
							.signature(new StringSignature(UUID.randomUUID().toString()));
							//.diskCacheStrategy(DiskCacheStrategy.ALL);
					Log.d(Tags, "缓存图片"+i);
				}*/
					}
				} else{
					Toast.makeText(getActivity(), "文件没找到", Toast.LENGTH_SHORT).show();
					//Bitmap photoBmp = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),uri);
					//iv_memoaddpic.setImageURI(uri);
					//Log.d("uri ", photoBmp.toString());
				}
			}else{
				Toast.makeText(getActivity(), "未回应", Toast.LENGTH_SHORT).show();
				//Bitmap photoBmp = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),uri);
				//iv_memoaddpic.setImageURI(uri);
				//Log.d("uri ", photoBmp.toString());
			}
			/*
			Uri uri = data.getData();
			Log.e("userpic", uri.toString());
			ContentResolver cr = getActivity().getContentResolver();
			Bitmap bitmap = null;
			try {
				bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
				iv_userpic.setImageBitmap(bitmap);
				Log.e("userpic", "get bitmap");
			} catch (FileNotFoundException e) {
				Log.e("userpic", "file not found");
				e.printStackTrace();
			}*/
		}

	}

	public byte[] getImgByte(Bitmap bitmap)
   {
		      ByteArrayOutputStream baos = new ByteArrayOutputStream();
		      //Bitmap bitmap = ((BitmapDrawable) getResources().getDrawable(id)).getBitmap();
		      bitmap.compress(Bitmap.CompressFormat.PNG, 50, baos);
		      return baos.toByteArray();
		 }

	BitmapFactory.Options getOpts(){
		BitmapFactory.Options opt=new BitmapFactory.Options();
		opt.inSampleSize=4;
		opt.inDensity=50;
		return opt;
	}

	public static Bitmap getPicFromBytes(byte[] bytes, BitmapFactory.Options opts) {

		if (bytes != null)
			if (opts != null)
				return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, opts);
			else
				return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
		return null;
	}

	public static byte[] readStream(InputStream inStream) throws Exception {
		byte[] buffer = new byte[1024];
		int len = -1;
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		byte[] data = outStream.toByteArray();
		outStream.close();
		inStream.close();
		return data;


	}
/*
	private Bitmap getBitmapFromUri(Uri uri)
	{
		try
		{
			// 读取uri所在的图片
			Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
			return bitmap;
		}
		catch (Exception e)
		{
			Log.e("[Android]", e.getMessage());
			Log.e("[Android]", "目录为：" + uri);
			e.printStackTrace();
			return null;
		}
	}*/

	public void UpdateUserRequestPost(String servletName,Map params){

		// String url = "http://route.showapi.com/213-3";
		//String url = "http://192.168.1.100:8080/EasyTest/TaskmainServlet";
		//Map<String, String> params = new HashMap<String, String>();
		//params.put("taskname", "value1");
		//params.put("deadline", "value2");
		String url = TaskData.hostname+servletName;
		//String url="http://www.weather.com.cn/data/cityinfo/101210101.html";
		RequestQueue requestQueue=Volley.newRequestQueue(getActivity());

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
					Toast.makeText(getActivity(), "no data",Toast.LENGTH_SHORT).show();
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



	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

		DisplayMetrics dm = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics( dm );
		getDialog().getWindow().setLayout( dm.widthPixels, getDialog().getWindow().getAttributes().height );
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		//EventBus.getDefault().unregister(getActivity());

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//EventBus.getDefault().register(getActivity());
	}
}

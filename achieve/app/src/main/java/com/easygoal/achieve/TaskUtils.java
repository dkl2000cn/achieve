package com.easygoal.achieve;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Acer on 2017/4/28.
 */

public class TaskUtils {

    private Context context;
    private String tabId;
    String Tags="|TaskUtils|";
    public TaskUtils(Context context,String tabId) {
        this.context=context;
        this.tabId=tabId;
    }

    public void showPopupWindow(Context context, View rootview) {
        //设置contentView
        View popupView = LayoutInflater.from(context).inflate(R.layout.popupwin_tasktoolbox, null);
        final PopupWindow mPopupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setFocusable(false);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(context.getResources(), (Bitmap) null));

        mPopupWindow.setContentView(popupView);

        //设置各个控件的点击响应
        Button btn_view=(Button)popupView.findViewById(R.id.task_view_bt);
        Button btn_addrecord=(Button)popupView.findViewById(R.id.task_addrecord_bt);
        Button btn_taskdelete=(Button)popupView.findViewById(R.id.task_delete_bt);
        Button btn_taskupdate=(Button)popupView.findViewById(R.id.task_update_bt);
        Button btn_taskclear=(Button)popupView.findViewById(R.id.task_clear_bt);
        Button btn_export=(Button)popupView.findViewById(R.id.export_bt);

        TeamItemClickListener teamitemlistener = new TeamItemClickListener();
        btn_view.setOnClickListener(teamitemlistener);
        btn_addrecord.setOnClickListener(teamitemlistener);
        btn_taskclear.setOnClickListener(teamitemlistener);
        btn_taskdelete.setOnClickListener(teamitemlistener);
        btn_taskupdate.setOnClickListener(teamitemlistener);
        btn_export.setOnClickListener(teamitemlistener);

        //显示PopupWindow
        //View rootview = LayoutInflater.from(MainActivity.this).inflate(R.layout.main, null);
        mPopupWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);

    }

    class TeamItemClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case (R.id.task_view_bt): view();break;
                case (R.id.task_addrecord_bt): addrecord();break;
                case (R.id.task_update_bt): update();break;
                case (R.id.task_delete_bt): delete();break;
                case (R.id.task_clear_bt): clear();break;
                case (R.id.export_bt): export();break;
                default:break;
            }
        }
    }

    void view(){
        DialogFragment_TaskDetail dg_taskdetail=new DialogFragment_TaskDetail();
        TaskTool.showDialog(dg_taskdetail);
    }



    void addrecord(){

        if (TaskData.selTaskID!=null){
            DialogFragment_Comment dialogfrag_comment=new DialogFragment_Comment();
            TaskTool.showDialog(dialogfrag_comment);
            //Log.d(Tags,"dialogfrag"+dialogfrag_comment.toString());
        }else{
            //Toast.makeText(context, "请先选定任务", Toast.LENGTH_LONG).show();
        }

    };
    void update(){
        TaskData.adapterUpdate();
        Toast.makeText(context, "更新完成",Toast.LENGTH_SHORT).show();
    }
    void deleteTeamTask(){
        if (TaskData.selTaskID!=null) {

            Cursor c = TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+TaskData.TdDB.TASK_ID+"="+TaskData.selTaskID,null);
            if (c!=null&&c.getCount()>0){
                c.moveToFirst();
            };

							/*TaskData.cursor_opentasks.moveToPosition(TaskData.cursor_opentasks.getPosition());
							int p=TaskData.cursor_opentasks.getInt(0);
							Log.d("delete row", String.valueOf(p));
							String where=TaskData.TdDB.TASK_ID+"=?";
							String[] whereArgs={Integer.toString(p)};
							TaskData.db_TdDB.delete(TaskData.TdDB.TABLE_NAME_TaskMain,where, whereArgs);
							Log.d("delete row", "delete OK");
							 Log.d("cursor",String.valueOf(TaskData.cursor_opentasks.getCount())+"position"+String.valueOf(TaskData.cursor_opentasks.getPosition()));
							 */

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("确认");
                builder.setMessage("真要删除任务T" + TaskData.selTaskID + "吗？");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // TODO Auto-generated method stub
                        //TextView show=(TextView)findViewById(R.layout.tv_alertdialog_pos);
                        //show.setText("");
                        if (TaskData.privilege > TaskData.entryright) {
                            new ConnMySQL(context).DelByGsonArrayRequestPost("TaskmainServlet", TaskData.TdDB.TABLE_NAME_TaskMain, TaskData.selTaskSN);
                            Log.d(Tags, "del by Mysql done");
                        }
                        TaskData.db_TdDB.execSQL("delete from " + TaskData.TdDB.TABLE_NAME_TaskMain + " where " +
                                TaskData.TdDB.TASK_SN + "=" + "'" + TaskData.selTaskSN + "'" + " and " +
                                //TaskData.TdDB.TASK_STATUS+"="+"'open'"+" and "+
                                TaskData.TdDB.TASK_USERGROUP + "=" + "'" + TaskData.usergroup + "';");


                        Cursor cr = DataSupport.findBySQL("select id as _id,sourceId from reminder where sourceId=" + "'" + "T" + TaskData.selTaskID + "';");

                        if (cr.getCount() > 0) {
                            cr.moveToFirst();
                            do {
                                String remindersn = cr.getString(1);
                                Alarm.alarmCancel(context, remindersn);
                                int reminderno = cr.getInt(0);
                                DataSupport.delete(Reminder.class, reminderno);
                                Log.d(Tags, "delete reminder " + reminderno);

                            } while (cr.moveToNext());

                        }

                        TaskData.adapterUpdate();
                        TaskData.selTaskID = null;
                        TaskData.selTaskSN = null;
                        TaskData.selTaskOwner=null;
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub

                    }
                });
                builder.create().show();

        }else{
            Toast.makeText(context, "请先选定任务", Toast.LENGTH_SHORT).show();
        }	;
    }

    void deleteSingleTask(){
        if (TaskData.selTaskID!=null) {

            Cursor c = TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+TaskData.TdDB.TASK_ID+"="+TaskData.selTaskID,null);
            if (c!=null&&c.getCount()>0){
                c.moveToFirst();
            };

							/*TaskData.cursor_opentasks.moveToPosition(TaskData.cursor_opentasks.getPosition());
							int p=TaskData.cursor_opentasks.getInt(0);
							Log.d("delete row", String.valueOf(p));
							String where=TaskData.TdDB.TASK_ID+"=?";
							String[] whereArgs={Integer.toString(p)};
							TaskData.db_TdDB.delete(TaskData.TdDB.TABLE_NAME_TaskMain,where, whereArgs);
							Log.d("delete row", "delete OK");
							 Log.d("cursor",String.valueOf(TaskData.cursor_opentasks.getCount())+"position"+String.valueOf(TaskData.cursor_opentasks.getPosition()));
							 */

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("确认");
                builder.setMessage("真要删除任务T" + TaskData.selTaskID + "吗？");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // TODO Auto-generated method stub
                        //TextView show=(TextView)findViewById(R.layout.tv_alertdialog_pos);
                        //show.setText("");
                        if (TaskData.privilege > TaskData.entryright) {
                            new ConnMySQL(context).DelByGsonArrayRequestPost("TaskmainServlet", TaskData.TdDB.TABLE_NAME_TaskMain, TaskData.selTaskSN);
                            Log.d(Tags, "del by Mysql done");
                        }
                        TaskData.db_TdDB.execSQL("delete from " + TaskData.TdDB.TABLE_NAME_TaskMain + " where " +
                                TaskData.TdDB.TASK_SN + "=" + "'" + TaskData.selTaskSN + "';");


                        Cursor cr = DataSupport.findBySQL("select id as _id,sourceId from reminder where sourceId=" + "'" + "T" + TaskData.selTaskID + "';");

                        if (cr.getCount() > 0) {
                            cr.moveToFirst();
                            do {
                                String remindersn = cr.getString(1);
                                Alarm.alarmCancel(context, remindersn);
                                int reminderno = cr.getInt(0);
                                DataSupport.delete(Reminder.class, reminderno);
                                Log.d(Tags, "delete reminder " + reminderno);

                            } while (cr.moveToNext());

                        }

                        TaskData.adapterUpdate();
                        TaskData.selTaskID = null;
                        TaskData.selTaskSN = null;
                        TaskData.selTaskOwner=null;
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub

                    }
                });
                builder.create().show();

        }else{
            Toast.makeText(context, "请先选定任务", Toast.LENGTH_SHORT).show();
        }	;
    }

    void clear(){
        switch (tabId){
            case "h1": clearTodayOpenTasks();break;
            case "s1": clearSingleOpenTasks();break;
            case "s2": clearSingleAllTasks();break;
            case "s3": clearSingleFinishedTasks();break;
            case "s4": clearSingleCancelledTasks();break;
            case "t1": clearTeamOpenTasks();break;
            case "t2": clearTeamAllTasks();break;
            case "t3": clearTeamFinishedTasks();break;
            case "t4": clearTeamCancelledTasks();break;
            default:break;
        }
    }

    void delete(){
        switch (tabId){
            case "h1": deleteSingleTask();break;
            case "s1": deleteSingleTask();break;
            case "s2": deleteSingleTask();break;
            case "s3": deleteSingleTask();break;
            case "s4": deleteSingleTask();break;
            case "t1": deleteTeamTask();break;
            case "t2": deleteTeamTask();break;
            case "t3": deleteTeamTask();break;
            case "t4": deleteTeamTask();break;
            default:break;
        }
    }

    void export(){
        switch (tabId){
            case "h1": exportTodayTasksList();break;
            case "s1": exportSingleOpenTasksList();break;
            case "s2": exportSingleAllTasksList();break;
            case "s3": exportSingleFinishedTasksList();break;
            case "s4": exportSingleCanclledTasksList();break;
            case "t1": exportTeamOpenTasksList();break;
            case "t2": exportTeamAllTasksList();break;
            case "t3": exportTeamFinishedTasksList();break;
            case "t4": exportTeamCanclledTasksList();break;
            default:break;
        }
    }

    void clearTodayOpenTasks(){

        //new ConnMySQL(context).ClearByGsonArrayRequestPost("TaskmainServlet",TaskData.TdDB.TABLE_NAME_TaskMain);
        //Toast.makeText(context, "已全部清除",Toast.LENGTH_SHORT).show();
        //Log.d("clear by Mysql","done");

        //for (int i=0;i<TaskData.cursor_opentasks.getCount();i++){
        //TaskData.db_TdDB.delete(TaskData.TdDB.TABLE_NAME_TaskMain, null, null);
        //};
						/*
						if (TaskData.cursor_opentasks!=null&&TaskData.cursor_opentasks.getCount()>0){
						TaskData.db_TdDB.delete(TaskData.TdDB.TABLE_NAME_TaskMain, null, null);
					    }
						*/
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle("确认");
        builder.setMessage("真要清除吗？");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                // TODO Auto-generated method stub
                //TextView show=(TextView)findViewById(R.layout.tv_alertdialog_pos);
                //show.setText("");
                TaskData.db_TdDB.execSQL("delete from "+TaskData.TdDB.TABLE_NAME_TaskMain+"  where "+TaskData.TdDB.TASK_USER+"="+"'"+
                                TaskData.TdDB.TASK_STATUS+"=? and "+
                                TaskData.TdDB.TASK_DEADLINETIMEDATA+"<=?",
                                new String[]{TaskData.user,"open",
                                String.valueOf(TimeData.todayEndTimeData())});

                //TaskData.adapter_opentasks.notifyDataSetChanged();

                TaskTool.clearlinkedReminder(context,"T");
									/*
									 Cursor cr=DataSupport.findBySQL("select id as _id from reminder where sourceId like"+"'"+"T%"+"';");

									 if (cr.getCount()>0){
										   cr.moveToFirst();
										   do{
										   int reminderno = cr.getInt(0);
										   DataSupport.delete(Reminder.class, reminderno);
										   Log.d(Tags,"clear reminder"+reminderno);

										   }while(cr.moveToNext());
									   }
									*/
                if (TaskData.privilege>TaskData.entryright){

                    Map params = new HashMap();
                    params.put("username", TaskData.user);
                    params.put(TaskData.TdDB.TASK_STATUS, "finished");

                    new ConnMySQL(context).ClearByGsonArrayRequestPost("TaskmainServlet", params);
                }

                TaskData.adapterUpdate();
                TaskData.selTaskID=null;
                TaskData.selTaskSN=null;
            }
        } );
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

            }
        });
        builder.create().show();
    }

    void clearTeamFinishedTasks(){

        //new ConnMySQL(context).ClearByGsonArrayRequestPost("TaskmainServlet",TaskData.TdDB.TABLE_NAME_TaskMain);
        //Toast.makeText(context, "已全部清除",Toast.LENGTH_SHORT).show();
        //Log.d("clear by Mysql","done");

        //for (int i=0;i<TaskData.cursor_opentasks.getCount();i++){
        //TaskData.db_TdDB.delete(TaskData.TdDB.TABLE_NAME_TaskMain, null, null);
        //};
						/*
						if (TaskData.cursor_opentasks!=null&&TaskData.cursor_opentasks.getCount()>0){
						TaskData.db_TdDB.delete(TaskData.TdDB.TABLE_NAME_TaskMain, null, null);
					    }
						*/
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle("确认");
        builder.setMessage("真要清除吗？");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                // TODO Auto-generated method stub
                //TextView show=(TextView)findViewById(R.layout.tv_alertdialog_pos);
                //show.setText("");
                TaskData.db_TdDB.execSQL("delete from "+TaskData.TdDB.TABLE_NAME_TaskMain+"  where "+TaskData.TdDB.TASK_USERGROUP+"="+"'"+TaskData.usergroup+"' and "+TaskData.TdDB.TASK_STATUS+"="+"'"+"finished"+"';");
                //TaskData.adapter_opentasks.notifyDataSetChanged();

                TaskTool.clearlinkedReminder(context,"T");
									/*
									 Cursor cr=DataSupport.findBySQL("select id as _id from reminder where sourceId like"+"'"+"T%"+"';");

									 if (cr.getCount()>0){
										   cr.moveToFirst();
										   do{
										   int reminderno = cr.getInt(0);
										   DataSupport.delete(Reminder.class, reminderno);
										   Log.d(Tags,"clear reminder"+reminderno);

										   }while(cr.moveToNext());
									   }
									*/
                if (TaskData.privilege>TaskData.entryright){

                    Map params = new HashMap();
                    params.put("username", TaskData.user);
                    params.put(TaskData.TdDB.TASK_STATUS, "finished");

                    new ConnMySQL(context).ClearByGsonArrayRequestPost("TaskmainServlet", params);
                }

                TaskData.adapterUpdate();

                TaskData.selTaskID=null;
                TaskData.selTaskSN=null;
            }
        } );
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

            }
        });
        builder.create().show();
    }

    void clearSingleFinishedTasks(){

        //new ConnMySQL(context).ClearByGsonArrayRequestPost("TaskmainServlet",TaskData.TdDB.TABLE_NAME_TaskMain);
        //Toast.makeText(context, "已全部清除",Toast.LENGTH_SHORT).show();
        //Log.d("clear by Mysql","done");

        //for (int i=0;i<TaskData.cursor_opentasks.getCount();i++){
        //TaskData.db_TdDB.delete(TaskData.TdDB.TABLE_NAME_TaskMain, null, null);
        //};
						/*
						if (TaskData.cursor_opentasks!=null&&TaskData.cursor_opentasks.getCount()>0){
						TaskData.db_TdDB.delete(TaskData.TdDB.TABLE_NAME_TaskMain, null, null);
					    }
						*/
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle("确认");
        builder.setMessage("真要清除吗？");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                // TODO Auto-generated method stub
                //TextView show=(TextView)findViewById(R.layout.tv_alertdialog_pos);
                //show.setText("");
                TaskData.db_TdDB.execSQL("delete from "+TaskData.TdDB.TABLE_NAME_TaskMain+"  where "+TaskData.TdDB.TASK_USER+"="+"'"+TaskData.user+"' and "+TaskData.TdDB.TASK_STATUS+"="+"'"+"finished"+"';");
                //TaskData.adapter_opentasks.notifyDataSetChanged();

                TaskTool.clearlinkedReminder(context,"T");
									/*
									 Cursor cr=DataSupport.findBySQL("select id as _id from reminder where sourceId like"+"'"+"T%"+"';");

									 if (cr.getCount()>0){
										   cr.moveToFirst();
										   do{
										   int reminderno = cr.getInt(0);
										   DataSupport.delete(Reminder.class, reminderno);
										   Log.d(Tags,"clear reminder"+reminderno);

										   }while(cr.moveToNext());
									   }
									*/
                if (TaskData.privilege>TaskData.entryright){

                    Map params = new HashMap();
                    params.put("username", TaskData.user);
                    params.put(TaskData.TdDB.TASK_STATUS, "finished");

                    new ConnMySQL(context).ClearByGsonArrayRequestPost("TaskmainServlet", params);
                }

                TaskData.adapterUpdate();
                TaskData.selTaskID=null;
                TaskData.selTaskSN=null;
            }
        } );
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

            }
        });
        builder.create().show();
    }

    void clearSingleCancelledTasks(){
        //new ConnMySQL(context).ClearByGsonArrayRequestPost("TaskmainServlet",TaskData.TdDB.TABLE_NAME_TaskMain);
        //Toast.makeText(context, "已全部清除",Toast.LENGTH_SHORT).show();
        //Log.d("clear by Mysql","done");

        //for (int i=0;i<TaskData.cursor_opentasks.getCount();i++){
        //TaskData.db_TdDB.delete(TaskData.TdDB.TABLE_NAME_TaskMain, null, null);
        //};
						/*
						if (TaskData.cursor_opentasks!=null&&TaskData.cursor_opentasks.getCount()>0){
						TaskData.db_TdDB.delete(TaskData.TdDB.TABLE_NAME_TaskMain, null, null);
					    }
						*/
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle("确认");
        builder.setMessage("真要清除吗？");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                // TODO Auto-generated method stub
                //TextView show=(TextView)findViewById(R.layout.tv_alertdialog_pos);
                //show.setText("");
                TaskData.db_TdDB.execSQL("delete from "+TaskData.TdDB.TABLE_NAME_TaskMain+"  where "+TaskData.TdDB.TASK_USER+"="+"'"+TaskData.user+"' and "+TaskData.TdDB.TASK_STATUS+"="+"'"+"cancelled"+"';");
                //TaskData.adapter_opentasks.notifyDataSetChanged();

                TaskTool.clearlinkedReminder(context,"T");
									/*
									 Cursor cr=DataSupport.findBySQL("select id as _id from reminder where sourceId like"+"'"+"T%"+"';");

									 if (cr.getCount()>0){
										   cr.moveToFirst();
										   do{
										   int reminderno = cr.getInt(0);
										   DataSupport.delete(Reminder.class, reminderno);
										   Log.d("clear reminder", "clear "+reminderno);

										   }while(cr.moveToNext());
									   }
									*/
                if (TaskData.privilege>TaskData.entryright){

                    Map params = new HashMap();
                    params.put("username", TaskData.user);
                    params.put(TaskData.TdDB.TASK_STATUS, "finished");

                    new ConnMySQL(context).ClearByGsonArrayRequestPost("TaskmainServlet", params);
                }

                TaskData.adapterUpdate();

                TaskData.selTaskID=null;
                TaskData.selTaskSN=null;
            }
        } );
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

            }
        });
        builder.create().show();
    }


    void clearTeamCancelledTasks(){
        //new ConnMySQL(context).ClearByGsonArrayRequestPost("TaskmainServlet",TaskData.TdDB.TABLE_NAME_TaskMain);
        //Toast.makeText(context, "已全部清除",Toast.LENGTH_SHORT).show();
        //Log.d("clear by Mysql","done");

        //for (int i=0;i<TaskData.cursor_opentasks.getCount();i++){
        //TaskData.db_TdDB.delete(TaskData.TdDB.TABLE_NAME_TaskMain, null, null);
        //};
						/*
						if (TaskData.cursor_opentasks!=null&&TaskData.cursor_opentasks.getCount()>0){
						TaskData.db_TdDB.delete(TaskData.TdDB.TABLE_NAME_TaskMain, null, null);
					    }
						*/
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle("确认");
        builder.setMessage("真要清除吗？");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                // TODO Auto-generated method stub
                //TextView show=(TextView)findViewById(R.layout.tv_alertdialog_pos);
                //show.setText("");
                TaskData.db_TdDB.execSQL("delete from "+TaskData.TdDB.TABLE_NAME_TaskMain+"  where "+TaskData.TdDB.TASK_USERGROUP+"="+"'"+TaskData.usergroup+"' and "+TaskData.TdDB.TASK_STATUS+"="+"'"+"cancelled"+"';");
                //TaskData.adapter_opentasks.notifyDataSetChanged();

                TaskTool.clearlinkedReminder(context,"T");
									/*
									 Cursor cr=DataSupport.findBySQL("select id as _id from reminder where sourceId like"+"'"+"T%"+"';");

									 if (cr.getCount()>0){
										   cr.moveToFirst();
										   do{
										   int reminderno = cr.getInt(0);
										   DataSupport.delete(Reminder.class, reminderno);
										   Log.d("clear reminder", "clear "+reminderno);

										   }while(cr.moveToNext());
									   }
									*/
                if (TaskData.privilege>TaskData.entryright){

                    Map params = new HashMap();
                    params.put("username", TaskData.user);
                    params.put(TaskData.TdDB.TASK_STATUS, "finished");

                    new ConnMySQL(context).ClearByGsonArrayRequestPost("TaskmainServlet", params);
                }

                TaskData.adapterUpdate();

                TaskData.selTaskID=null;
                TaskData.selTaskSN=null;
            }
        } );
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

            }
        });
        builder.create().show();
    }

    void clearSingleOpenTasks(){
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle("确认");
        builder.setMessage("真要清除吗？");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                // TODO Auto-generated method stub
                //TextView show=(TextView)findViewById(R.layout.tv_alertdialog_pos);
                //show.setText("");
                TaskData.db_TdDB.execSQL("delete from "+TaskData.TdDB.TABLE_NAME_TaskMain+"  where "+TaskData.TdDB.TASK_USER+"="+"'"+ TaskData.user+"' and "+TaskData.TdDB.TASK_STATUS+"="+"'"+"open"+"';");
                //TaskData.adapter_teamopentasks.notifyDataSetChanged();

                TaskTool.clearlinkedReminder(context,"T");
                if (TaskData.privilege>TaskData.entryright){

                    Map params = new HashMap();
                    params.put("username", TaskData.user);
                    params.put(TaskData.TdDB.TASK_STATUS, "open");

                    new ConnMySQL(context).ClearByGsonArrayRequestPost("TaskmainServlet", params);
                }
								/*
								 Cursor cr=DataSupport.findBySQL("select id as _id from reminder where sourceId like"+"'"+"T%"+"';");

								 if (cr.getCount()>0){
									   cr.moveToFirst();
									   do{
									   int reminderno = cr.getInt(0);
									   DataSupport.delete(Reminder.class, reminderno);
									   Log.d("clear reminder", "clear "+reminderno);
									   String remindersn = cr.getString(cr.getColumnIndex("sourceId"));
									   Alarm.alarmCancel(context, remindersn);
									   }while(cr.moveToNext());
								   }
								*/

                TaskData.adapterUpdate();


                TaskData.selTaskID=null;
                TaskData.selTaskSN=null;
            }
        } );
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

            }
        });
        builder.create().show();
    }

    void clearTeamOpenTasks(){
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle("确认");
        builder.setMessage("真要清除吗？");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                // TODO Auto-generated method stub
                //TextView show=(TextView)findViewById(R.layout.tv_alertdialog_pos);
                //show.setText("");
                TaskData.db_TdDB.execSQL("delete from "+TaskData.TdDB.TABLE_NAME_TaskMain+"  where "+TaskData.TdDB.TASK_USERGROUP+"="+"'"+ TaskData.usergroup+"' and "+TaskData.TdDB.TASK_STATUS+"="+"'"+"open"+"';");
                //TaskData.adapter_teamopentasks.notifyDataSetChanged();

                TaskTool.clearlinkedReminder(context,"T");
                if (TaskData.privilege>TaskData.entryright){

                    Map params = new HashMap();
                    params.put("username", TaskData.user);
                    params.put(TaskData.TdDB.TASK_STATUS, "open");

                    new ConnMySQL(context).ClearByGsonArrayRequestPost("TaskmainServlet", params);
                }
								/*
								 Cursor cr=DataSupport.findBySQL("select id as _id from reminder where sourceId like"+"'"+"T%"+"';");

								 if (cr.getCount()>0){
									   cr.moveToFirst();
									   do{
									   int reminderno = cr.getInt(0);
									   DataSupport.delete(Reminder.class, reminderno);
									   Log.d("clear reminder", "clear "+reminderno);
									   String remindersn = cr.getString(cr.getColumnIndex("sourceId"));
									   Alarm.alarmCancel(context, remindersn);
									   }while(cr.moveToNext());
								   }
								*/

                TaskData.adapterUpdate();

                TaskData.selTaskID=null;
                TaskData.selTaskSN=null;
            }
        } );
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

            }
        });
        builder.create().show();
    }

    void clearSingleAllTasks( ){
        //new ConnMySQL(context).ClearByGsonArrayRequestPost("TaskmainServlet",TaskData.TdDB.TABLE_NAME_TaskMain);
        //Toast.makeText(context, "已全部清除",Toast.LENGTH_SHORT).show();
        //Log.d("clear by Mysql","done");

        //for (int i=0;i<TaskData.cursor_opentasks.getCount();i++){
        //TaskData.db_TdDB.delete(TaskData.TdDB.TABLE_NAME_TaskMain, null, null);
        //};
						/*
						if (TaskData.cursor_opentasks!=null&&TaskData.cursor_opentasks.getCount()>0){
						TaskData.db_TdDB.delete(TaskData.TdDB.TABLE_NAME_TaskMain, null, null);
					    }
						*/
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle("确认");
        builder.setMessage("真要清除吗？");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                // TODO Auto-generated method stub
                //TextView show=(TextView)findViewById(R.layout.tv_alertdialog_pos);
                //show.setText("");
                TaskData.db_TdDB.execSQL("delete from "+TaskData.TdDB.TABLE_NAME_TaskMain+"  where "+TaskData.TdDB.TASK_USER+"="+"'"+TaskData.user+"';");
                //TaskData.adapter_opentasks.notifyDataSetChanged();
                TaskTool.clearlinkedReminder(context,"T");
									 /*
									 Cursor cr=DataSupport.findBySQL("select id as _id from reminder where sourceId like"+"'"+"T%"+"';");

									 if (cr.getCount()>0){
										   cr.moveToFirst();
										   do{
										   int reminderno = cr.getInt(0);
										   DataSupport.delete(Reminder.class, reminderno);
										   Log.d(Tags,"clear reminder"+reminderno);

										   }while(cr.moveToNext());
									   }
									*/
                if (TaskData.privilege>TaskData.entryright){
                    Map params = new HashMap();

                    params.put("username", TaskData.user);
                    new ConnMySQL(context).ClearByGsonArrayRequestPost("TaskmainServlet",params);
                    Log.d(Tags,"clear all taks by mysql");
                }


                TaskData.adapterUpdate();




                TaskData.selTaskID=null;
                TaskData.selTaskSN=null;
            }
        } );
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

            }
        });
        builder.create().show();
    }

    void clearTeamAllTasks( ){
        //new ConnMySQL(context).ClearByGsonArrayRequestPost("TaskmainServlet",TaskData.TdDB.TABLE_NAME_TaskMain);
        //Toast.makeText(context, "已全部清除",Toast.LENGTH_SHORT).show();
        //Log.d("clear by Mysql","done");

        //for (int i=0;i<TaskData.cursor_opentasks.getCount();i++){
        //TaskData.db_TdDB.delete(TaskData.TdDB.TABLE_NAME_TaskMain, null, null);
        //};
						/*
						if (TaskData.cursor_opentasks!=null&&TaskData.cursor_opentasks.getCount()>0){
						TaskData.db_TdDB.delete(TaskData.TdDB.TABLE_NAME_TaskMain, null, null);
					    }
						*/
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle("确认");
        builder.setMessage("真要清除吗？");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                // TODO Auto-generated method stub
                //TextView show=(TextView)findViewById(R.layout.tv_alertdialog_pos);
                //show.setText("");
                TaskData.db_TdDB.execSQL("delete from "+TaskData.TdDB.TABLE_NAME_TaskMain+"  where "+TaskData.TdDB.TASK_USERGROUP+"="+"'"+TaskData.usergroup+"';");
                //TaskData.adapter_opentasks.notifyDataSetChanged();
                TaskTool.clearlinkedReminder(context,"T");
									 /*
									 Cursor cr=DataSupport.findBySQL("select id as _id from reminder where sourceId like"+"'"+"T%"+"';");

									 if (cr.getCount()>0){
										   cr.moveToFirst();
										   do{
										   int reminderno = cr.getInt(0);
										   DataSupport.delete(Reminder.class, reminderno);
										   Log.d(Tags,"clear reminder"+reminderno);

										   }while(cr.moveToNext());
									   }
									*/
                if (TaskData.privilege>TaskData.entryright){
                    Map params = new HashMap();

                    params.put("username", TaskData.user);
                    new ConnMySQL(context).ClearByGsonArrayRequestPost("TaskmainServlet",params);
                    Log.d(Tags,"clear all taks by mysql");
                }


                TaskData.adapterUpdate();

                TaskData.selTaskID=null;
                TaskData.selTaskSN=null;
            }
        } );
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

            }
        });
        builder.create().show();
    }

    void exportTodayTasksList(){
        Cursor c=  TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+
                        TaskData.TdDB.TASK_USER+"=? and "+TaskData.TdDB.TASK_STATUS+"=? and "+
                        TaskData.TdDB.TASK_DEADLINETIMEDATA+"<=?"+
                        " order by "+TaskData.TdDB.TASK_SEQUENCENO+" asc",
                new String[]{TaskData.user,"open",
                        String.valueOf(TimeData.todayEndTimeData())});
        // TODO Auto-generated method stub

        if (c.getCount()>0){

            ExportUtils.exportListToFile(context,c,"今日待办任务清单");

        }else{
            Toast.makeText(context, "记录为空", Toast.LENGTH_SHORT).show();
        }
    }


    void exportTeamOpenTasksList(){
        Cursor c=  TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+
                TaskData.TdDB.TASK_USERGROUP+"="+"'"+TaskData.usergroup+"' and "+TaskData.TdDB.TASK_STATUS+"=?", new String[]{"open"});
        // TODO Auto-generated method stub

        if (c.getCount()>0){

            ExportUtils.exportListToFile(context,c,"团队待办任务清单");

        }else{
            Toast.makeText(context, "记录为空", Toast.LENGTH_SHORT).show();
        }
    }

    void exportSingleOpenTasksList(){
        Cursor c=  TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+
                TaskData.TdDB.TASK_USER+"="+"'"+TaskData.user+"' and "+TaskData.TdDB.TASK_STATUS+"=?", new String[]{"open"});
        // TODO Auto-generated method stub

        if (c.getCount()>0){

            ExportUtils.exportListToFile(context,c,"待办任务清单");

        }else{
            Toast.makeText(context, "记录为空", Toast.LENGTH_SHORT).show();
        }
    }

    void exportTeamAllTasksList(){
        Cursor c=  TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+
                TaskData.TdDB.TASK_USERGROUP+"="+"'"+TaskData.usergroup+"'; ", null);
        // TODO Auto-generated method stub

        if (c.getCount()>0){

            ExportUtils.exportListToFile(context,c,"团队全部任务清单");

        }else{
            Toast.makeText(context, "记录为空", Toast.LENGTH_SHORT).show();
        }
    }

    void exportSingleAllTasksList(){
        Cursor c=  TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+
                TaskData.TdDB.TASK_USER+"="+"'"+TaskData.user+"'; ", null);
        // TODO Auto-generated method stub

        if (c.getCount()>0){

            ExportUtils.exportListToFile(context,c,"全部任务清单");

        }else{
            Toast.makeText(context, "记录为空", Toast.LENGTH_SHORT).show();
        }
    }

    void exportTeamFinishedTasksList(){
        Cursor c=  TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+
                TaskData.TdDB.TASK_USERGROUP+"="+"'"+TaskData.usergroup+"' and "+TaskData.TdDB.TASK_STATUS+"=?", new String[]{"finished"});
        // TODO Auto-generated method stub

        if (c.getCount()>0){

            ExportUtils.exportListToFile(context,c,"团队已完成任务清单");

        }else{
            Toast.makeText(context, "记录为空", Toast.LENGTH_SHORT).show();
        }
    }

    void exportSingleFinishedTasksList(){
        Cursor c=  TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+
                TaskData.TdDB.TASK_USER+"="+"'"+TaskData.user+"' and "+TaskData.TdDB.TASK_STATUS+"=?", new String[]{"finished"});
        // TODO Auto-generated method stub

        if (c.getCount()>0){

            ExportUtils.exportListToFile(context,c,"已完成任务清单");

        }else{
            Toast.makeText(context, "记录为空", Toast.LENGTH_SHORT).show();
        }
    }

    void exportTeamCanclledTasksList(){
        Cursor c=  TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+
                TaskData.TdDB.TASK_USERGROUP+"="+"'"+TaskData.usergroup+"'; ", null);
        // TODO Auto-generated method stub

        if (c.getCount()>0){

            ExportUtils.exportListToFile(context,c,"团队取消任务清单");

        }else{
            Toast.makeText(context, "记录为空", Toast.LENGTH_SHORT).show();
        }
    }

    void exportSingleCanclledTasksList(){
        Cursor c=  TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+
                TaskData.TdDB.TASK_USER+"="+"'"+TaskData.user+"'; ", null);
        // TODO Auto-generated method stub

        if (c.getCount()>0){

            ExportUtils.exportListToFile(context,c,"取消任务清单");

        }else{
            Toast.makeText(context, "记录为空", Toast.LENGTH_SHORT).show();
        }
    }

    void exportList(String status){
        Cursor c=  TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+
                TaskData.TdDB.TASK_STATUS+"=?", new String[]{status});
        // TODO Auto-generated method stub

        if (c.getCount()>0){

            ExportUtils.exportListToFile(context,c,"撤销任务清单");

        }else{
            Toast.makeText(context, "记录为空", Toast.LENGTH_SHORT).show();
        }
    }
    /*
    protected void showDialog(DialogFragment dialogFragment) {

        // Create and show the dialog.
        if(dialogFragment == null)
            //  dialogFragment = new Fragment_Search();
            dialogFragment.setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
        dialogFragment.show(context.getFragmentManager(), "dialog");
    }*/
}

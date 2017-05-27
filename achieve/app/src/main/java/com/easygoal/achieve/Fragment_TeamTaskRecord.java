package com.easygoal.achieve;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Fragment_TeamTaskRecord extends Fragment {

	Cursor c;
	View oldview=null;
	View newview=null;
	Toolbar toolbar;
	final String Tags="Fragment_TeamTaskRecord";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View v=inflater.inflate(R.layout.fg_teamtaskrecord, container, false);

		//Log.d("create database", "start"+TaskData.d.toString());
		final EditText et1=(EditText)v.findViewById(R.id.record_item2_recordTime_et);
		final EditText et3=(EditText)v.findViewById(R.id.record_item4_progress_et);
		final EditText et4=(EditText)v.findViewById(R.id.record_item5_comment_et);


		final ListView lv_taskrecord=(ListView)v.findViewById(R.id.taskrecord_lv);

		//Button btn_confirm=(Button)findViewById(R.id.btn_confirm);
		Button btn_update=(Button)v.findViewById(R.id.btn_update);
		Button btn_addrecord = (Button) v.findViewById(R.id.btn_addrecord);
		Button btn_cancel = (Button) v.findViewById(R.id.btn_cancel);
		Button btn_close = (Button)v.findViewById(R.id.btn_close);
		Button btn_taskdelete=(Button)v.findViewById(R.id.btn_delete);
		Button btn_export=(Button)v.findViewById(R.id.export_bt);

		final Spinner spin_team = (Spinner)v.findViewById(R.id.spin_team);
		//final InputValueSet taskimportance[]={new InputValueSet(1,"不重要"),new InputValueSet(2,"2-一般重要"),new InputValueSet(3,"3-非常重要"),new InputValueSet(4,"4-至关重要")};
		final TextView tv_teamname=(TextView)v.findViewById(R.id.tv_teamname);
		if (TaskData.usergroup!=null) {
			tv_teamname.setText(TaskData.usergroup);
		}

		final List<InputValueSet> list_team=new ArrayList<InputValueSet>();
		list_team.add(new InputValueSet(1,"A"));
		list_team.add(new InputValueSet(2,"B"));
		list_team.add(new InputValueSet(3,"C"));

		mArrayAdapter adapt_spinteam = new mArrayAdapter(getActivity(), R.layout.myspinner_item, list_team);
		spin_team.setAdapter(adapt_spinteam);
		//TaskData.usergroup=list_team.get(0).getValue().toString();;

		lv_taskrecord.setAdapter(groupquery(getActivity()));
		spin_team.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int pos, long id) {
				// TODO Auto-generated method stub
				//usedTimeEachWeek_et.setText(list_timeUnit.get(pos).toString());
				//TaskData.usergroup = list_team.get(pos).getValue().toString();
				lv_taskrecord.setAdapter(groupquery(getActivity()));
				TaskData.adapterUpdate();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stubn /////////////'[]\

			}
		});

		lv_taskrecord.setOnItemClickListener(new OnItemClickListener(){
			int newPos=0;
			int oldPos=0;

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				try {
					TaskData.selTaskID = String.valueOf(TaskData.cursor_group.getString(TaskData.cursor_group.getColumnIndex(TaskData.TdDB.TASK_ID)));
					TaskData.selTaskSN = String.valueOf(TaskData.cursor_group.getString(TaskData.cursor_group.getColumnIndex(TaskData.TdDB.TASK_SN)));
				} catch (Exception e) {
					Log.d(Tags, e.toString());
					e.printStackTrace();
				}

				if (view != newview) {
					oldview = newview;
					newview = view;
					newview.setBackgroundColor(getResources().getColor(R.color.gray));
					if (oldview != null) {
						oldview.setBackgroundColor(getResources().getColor(R.color.mTextColor2));
					}
				}
			}
		});

		btn_addrecord.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				//Log.d("from_fg", from_fg.toString());
				if (TaskData.selTaskID!=null){
					DialogFragment_GroupRecord dialogfrag_grouprecord=new DialogFragment_GroupRecord();
					TaskTool.showDialog(dialogfrag_grouprecord);
				}else{
					Toast.makeText(getActivity(), "请先选定任务", Toast.LENGTH_LONG).show();
				}

				//TaskData.from_fg=showFrag(TaskData.from_fg,R.id.sublayout_task,subfrag_task,3);
				//	Log.d("task tab", "choice3");
			}
		});

		btn_update.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TaskData.adapterUpdate();

			}
		});



		btn_export.setOnClickListener(new OnClickListener(){


			@Override
			public void onClick(View v) {
				Cursor c=TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+TaskData.TdDB.TASK_STATUS+"=?"+" order by "+TaskData.TdDB.TASK_SEQUENCENO+" asc", new String[]{"open"});
				// TODO Auto-generated method stub

				SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd mm:ss");
				String reportname  ="teamtaskslist"+formatter.format(new Date());
				final String[] taskitemlist={
						TaskData.TdDB.TASK_NAME,
						TaskData.TdDB.TASK_ASSESSMENT,
						TaskData.TdDB.TASK_SEQUENCENO,
						TaskData.TdDB.TASK_DEADLINE,
						TaskData.TdDB.TASK_PROGRESS,
						//TaskData.TdDB.TASK_STATUS,
						//TaskData.TdDB.TASK_FINISHED,
						//TaskData.TdDB.TASK_DELAYED
				};
				final String[] titlenamelist={
						"任务","重要紧急","智能排序","期限","完成%"
				};

				if (c.getCount()>0){

					File outfile=new ExportListToExcel(getActivity(),TaskData.TdDB.TABLE_NAME_TaskMain,
							c,
							titlenamelist,
							taskitemlist)
							.writeExcel(reportname);
					Intent intent2 = new Intent(Intent.ACTION_SEND);
					intent2.setType("text/*");

					if (outfile != null && outfile.exists()) {


						Uri u = Uri.fromFile(outfile);

						intent2.putExtra(Intent.EXTRA_STREAM, u);
					} else{

					}
					startActivity(Intent.createChooser(intent2, "opentasks list"));

				}else{
					Toast.makeText(getActivity(), "记录为空", Toast.LENGTH_SHORT).show();
				}
			}
		});

		btn_taskdelete.setOnClickListener(new OnClickListener(){

			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub


				if (TaskData.selTaskID!=null){

					new ConnMySQL(getActivity()).DelByGsonArrayRequestPost("TaskmainServlet",TaskData.TdDB.TABLE_NAME_TaskMain,TaskData.selTaskSN);
					Log.d(Tags,"del by Mysql done");
					TaskData.db_TdDB.execSQL("delete from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+
							TaskData.TdDB.TASK_SN+"="+"'"+TaskData.selTaskSN+"'"+" and "+
							TaskData.TdDB.TASK_STATUS+"="+"'open'"+" and "+
							TaskData.TdDB.TASK_USERGROUP+"="+"'"+TaskData.usergroup+"'"+" and "+
							TaskData.TdDB.TASK_USER+"="+"'"+TaskData.user+"';");
					Cursor cr=DataSupport.findBySQL("select id as _id from reminder where sourceId="+"'"+"T"+TaskData.selTaskID+"';");

					if (cr.getCount()>0){
						cr.moveToFirst();
						do{
							int reminderno = cr.getInt(0);
							DataSupport.delete(Reminder.class, reminderno);
							Log.d(Tags,"delete reminder"+reminderno);

						}while(cr.moveToNext());
					}

					//Toast.makeText(getActivity(), "已删除TaskID"+TaskData.selTaskID,Toast.LENGTH_SHORT).show();
					TaskData.adapterUpdate();
					TaskData.selTaskID=null;
					TaskData.selTaskSN=null;

				}else{
					Toast.makeText(getActivity(), "请先选定任务", Toast.LENGTH_SHORT).show();
				}

			}

		});
		return v;
	}

	@Override
   public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		SimpleDateFormat formatter = new SimpleDateFormat ("yyyy年MM月dd日 HH:mm:ss ");
		Date curDate = new Date(System.currentTimeMillis());//获取当前时间
		String str = formatter.format(curDate);
    }

	mcAdapter_group groupquery(Context context){
		//Log.d("start join query","OK");

		String a = TaskData.TdDB.TABLE_NAME_TaskMain;
		String b = TaskData.TdDB.TABLE_NAME_TaskRecord;
        /*
	    TaskData.cursor_group=TaskData.db_TdDB.rawQuery("select * from "+a+" where "+
				TaskData.TdDB.TASK_USERGROUP+"="+"'"+TaskData.usergroup+"'"+" and "+
				TaskData.TdDB.TASK_USER+"="+"'"+TaskData.user+"'", null);
		*/
		//Toast.makeText(getActivity(),""+TaskData.usergroup+TaskData.user, Toast.LENGTH_SHORT).show();
		TaskData.cursor_group=TaskData.db_TdDB.rawQuery("select * from "+a+" where "+
				TaskData.TdDB.TASK_USERGROUP+"="+"'"+TaskData.usergroup+"'"
				+" and "+TaskData.TdDB.TASK_STATUS+"='open'", null);
		TaskData.mcAdapter_group = new mcAdapter_group(context,TaskData.cursor_group);
		TaskData.mcAdapter_group.notifyDataSetChanged();
		return TaskData.mcAdapter_group;
		// lv_taskrecord.setAdapter(adapter_record;
	}
}

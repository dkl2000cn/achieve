package com.easygoal.achieve;


import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Fragment_TeamMemberScore extends Fragment {
	
	

	 //final ToDoDB TdDB = new ToDoDB(getActivity(), db_name,null, 1);
	    String Tags="Fragment_MemberTaskScore ";
	
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
	            Bundle savedInstanceState) {  
		View v=inflater.inflate(R.layout.fg_teammemberscore, container, false);
		//DatabaseHelper TdDB=new DatabaseHelper(); 

		 final ListView lv_memberscore=(ListView)v.findViewById(R.id.memberscore_lv);
           /*
			final TextView tv_teamname=(TextView)v.findViewById(R.id.tv_teamname);
			if (TaskData.usergroup!=null) {
				tv_teamname.setText(TaskData.usergroup);
			}

			*/
		  //Button btn1=(Button)v.findViewById(R.id.record_confirm_bt);

		  // final TextView tv1=(TextView)findViewById(R.id.textView2);
		 // final TextView tv2=(TextView)findViewById(R.id.textView3);
		 TaskData.cursor_teamscore= TaskData.db_TdDB.rawQuery("select * from "+ TaskData.TdDB.TABLE_NAME_TaskMain +" where "+
				 TaskData.TdDB.TASK_USERGROUP+"=? and "+
				 TaskData.TdDB.TASK_STATUS+"!=?",
				 new String[]{TaskData.usergroup,"cancelled"});

		 TaskData.cursor_finishedteamtasks = TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+
				 " where "+TaskData.TdDB.TASK_USERGROUP+"=? and "+
				 TaskData.TdDB.TASK_STATUS+"=?",
				 new String[]{TaskData.usergroup,"finished"});
		 TaskData.tv_scoreheader_teamaccomplished=(TextView)v.findViewById(R.id.tv_scoreheader_accomplished);
		 TaskData.tv_scoreheader_teamcontribution=(TextView)v.findViewById(R.id.tv_scoreheader_contribution);
		 TaskData.tv_scoreheader_teamachieved=(TextView)v.findViewById(R.id.tv_scoreheader_achieved);
   	 	 TaskData.tv_scoreheader_teamenjoyment=(TextView)v.findViewById(R.id.tv_scoreheader_enjoyment);
		 TaskData.tv_scoreheader_teamexperience=(TextView)v.findViewById(R.id.tv_scoreheader_experience);
			/*
    	 TaskData.update_sum(TaskData.tv_scoreheader_teamaccomplished,TaskData.cursor_finishedteamtasks,TaskData.TdDB.TASK_IMPORTANCE);
			TaskData.update_sum(TaskData.tv_scoreheader_teamachieved,TaskData.cursor_finishedteamtasks,TaskData.TdDB.SUM_ACHIEVED);
			TaskData.update_sum(TaskData.tv_scoreheader_teamenjoyment,TaskData.cursor_finishedteamtasks,TaskData.TdDB.TASK_PASSION );
			TaskData.update_sum(TaskData.tv_scoreheader_teamexperience,TaskData.cursor_finishedteamtasks,TaskData.TdDB.TASK_DIFFICULTY );
          */
			TaskData.update_sum(TaskData.tv_scoreheader_teamaccomplished,TaskData.cursor_finishedteamtasks,TaskData.TdDB.TASK_IMPORTANCE);
			TaskData.update_sum(TaskData.tv_scoreheader_teamcontribution,TaskData.cursor_teamscore,TaskData.TdDB.SUM_CONTRIBUTION);
			TaskData.update_sum(TaskData.tv_scoreheader_teamachieved,TaskData.cursor_teamscore,TaskData.TdDB.SUM_ACHIEVED);
			TaskData.update_sum(TaskData.tv_scoreheader_teamenjoyment,TaskData.cursor_teamscore,TaskData.TdDB.SUM_ENJOYMENT);
			TaskData.update_sum(TaskData.tv_scoreheader_teamexperience,TaskData.cursor_teamscore,TaskData.TdDB.SUM_EXPERIENCE);


		 //SimpleCursorAdapter adapter_score = new SimpleCursorAdapter(getActivity(), R.layout.lv_taskscore, c,itemlist_taskscore,controllist_taskscore, 0);
			if (TaskData.userinfolist_usergroup!=null&&TaskData.userinfolist_usergroup.size()>0) {
				mBaseAdapter_TeamMemberScore mBaseAdapter_teamMemberScore = new mBaseAdapter_TeamMemberScore(getActivity(), TaskData.userinfolist_usergroup);
				lv_memberscore.setAdapter(mBaseAdapter_teamMemberScore);

			}else{
				Toast.makeText(getActivity(),"请刷新团队信息",Toast.LENGTH_SHORT).show();
			}
		 //lv_teamscore.setAdapter(TaskData.mcadapter_teamscore);
  	     //Log.d(Tags,"lv_taskscore show data ok" +lv_teamscore.toString());
			/*
			final Spinner spin_team = (Spinner)v.findViewById(R.id.spin_team);
			final List<InputValueSet> list_team=new ArrayList<InputValueSet>();
			list_team.add(new InputValueSet(1,"A"));
			list_team.add(new InputValueSet(2,"B"));
			list_team.add(new InputValueSet(3,"C"));
			mArrayAdapter adapt_spinteam = new mArrayAdapter(getActivity(), R.layout.myspinner_item, list_team);
			spin_team.setAdapter(adapt_spinteam);
			spin_team.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1, int pos, long id) {
					// TODO Auto-generated method stub
					//usedTimeEachWeek_et.setText(list_timeUnit.get(pos).toString());
					//TaskData.usergroup = list_team.get(pos).getValue().toString();
					TaskData.cursor_teamscore= TaskData.db_TdDB.rawQuery("select * from "+ TaskData.TdDB.TABLE_NAME_TaskMain +" where "+
									TaskData.TdDB.TASK_USERGROUP+"=? and "+
									TaskData.TdDB.TASK_STATUS+"!=?",
							new String[]{TaskData.usergroup,"cancelled"});
					TaskData.mcadapter_teamscore=new mcAdapter_taskscore(getActivity(), TaskData.cursor_teamscore);
					lv_teamscore.setAdapter(TaskData.mcadapter_teamscore);
					TaskData.cursor_finishedteamtasks = TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+
									" where "+TaskData.TdDB.TASK_USERGROUP+"=? and "+
									TaskData.TdDB.TASK_STATUS+"=?",
							new String[]{TaskData.usergroup,"finished"});
					TaskData.update_sum(TaskData.tv_scoreheader_teamaccomplished,TaskData.cursor_finishedteamtasks,TaskData.TdDB.TASK_IMPORTANCE);
					TaskData.update_sum(TaskData.tv_scoreheader_teamachieved,TaskData.cursor_teamscore,TaskData.TdDB.SUM_ACHIEVED);
					TaskData.update_sum(TaskData.tv_scoreheader_teamenjoyment,TaskData.cursor_teamscore,TaskData.TdDB.SUM_ENJOYMENT);
					TaskData.update_sum(TaskData.tv_scoreheader_teamexperience,TaskData.cursor_teamscore,TaskData.TdDB.SUM_EXPERIENCE);
					TaskData.adapterUpdate();
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stubn /////////////'[]\
				}
			});
            */

			return v;
	 }



	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
			  
	}

	  
}

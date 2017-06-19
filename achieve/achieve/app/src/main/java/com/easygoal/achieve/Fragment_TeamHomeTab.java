package com.easygoal.achieve;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RadioButton;

public class Fragment_TeamHomeTab extends Fragment{
	
	int selectedID=0;
    Fragment from_fg;
    String Tags="FragmentHomeTab";
	 //final ToDoDB TdDB = new ToDoDB(getActivity(), db_name,null, 1);
	 
	 
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
	            Bundle savedInstanceState) {  
		View v=inflater.inflate(R.layout.fg_teamhometab, container, false);
		//DatabaseHelper TdDB=new DatabaseHelper(); 
		
		//Log.d("create database", "start"+TaskData.db.toString());
		 
		 Fragment fragment_home = new FragmentHome();
		 Fragment fragment_reminder = new FragmentReminder();
		 Fragment fragment_schedule = new FragmentSchedule();
		 Fragment fragment_memo = new FragmentMemo();
		 //fragment_addComments= new DialogFragment_Comment();
		 final Fragment[] subfrag_hometab={fragment_home,fragment_schedule,fragment_memo,fragment_reminder};
		
		 
		  final RadioButton rb0=(RadioButton)v.findViewById(R.id.radio0);
		  final RadioButton rb1=(RadioButton)v.findViewById(R.id.radio1);
		  final RadioButton rb2=(RadioButton)v.findViewById(R.id.radio2);
		  final RadioButton rb3=(RadioButton)v.findViewById(R.id.radio3);
		  FragmentTransaction ft =getFragmentManager().beginTransaction();
			ft.add(R.id.sublayout_home, subfrag_hometab[0]).commit(); 
	 	   
	 	    from_fg=subfrag_hometab[0];
		  rb0.setOnClickListener(new OnClickListener(){

			  
			@Override
			public void onClick(View v) {
				
				// TODO Auto-generated method stub
				from_fg=showFrag(from_fg,R.id.sublayout_home,subfrag_hometab,0);
				Log.d(Tags,"task tab selected choice0");
			}
		  });
		  rb1.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					
					// TODO Auto-generated method stub
					from_fg=showFrag(from_fg,R.id.sublayout_home,subfrag_hometab,1);
					Log.d(Tags,"task tab selected choice1");
				}
			  });
		  rb2.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					
					// TODO Auto-generated method stub
					from_fg=showFrag(from_fg,R.id.sublayout_home,subfrag_hometab,2);
					Log.d(Tags,"task tab selected choice2");
				}
			  });
		  rb3.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					
					// TODO Auto-generated method stub
					 //Log.d("from_fg", from_fg.toString()); 
					from_fg=showFrag(from_fg,R.id.sublayout_home,subfrag_hometab,3);
					Log.d(Tags,"task tab selected choice3");
					
					
					//from_fg=showFrag(from_fg,R.id.sublayout_home,subfrag_hometab,3);
				//	Log.d("task tab", "choice3");
				}
			  });

		return v;
	 
	 }

	 protected void showDialog(DialogFragment dialogFragment) {  
         
	        // Create and show the dialog.   
	    if(dialogFragment == null)  
	          //  dialogFragment = new Fragment_Search();  
	    	dialogFragment.setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen); 
	        dialogFragment.show(getFragmentManager(), "dialog");  
	    }

	public Fragment showFrag(Fragment from_fg,int viewframe,Fragment[] frag,int i){
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.setTransition(FragmentTransaction.TRANSIT_NONE);
		if (i!=0){
			if (from_fg==null){
				if (!frag[i].isAdded()) {
					ft.add(viewframe, frag[i]).commit();
				}else{
					ft.show(frag[i]).commit();
				}

			}else{
				if (!frag[i].isAdded()){

					// ft.hide(from_fg);

					ft.hide(from_fg).add(viewframe,frag[i]).commit();

				}
				else{
					ft.hide(from_fg).show(frag[i]).commit();

				}
			};
			from_fg=frag[i];
			return from_fg;
		}else{
			if (from_fg==null){
				ft.add(viewframe, frag[i]).commit();

			}else{
				if (!frag[i].isAdded()){
					ft.hide(from_fg).add(viewframe,frag[i]).commit();
				}else{
					if (TaskData.initteamflag==1){
						ft.remove(frag[i]);
						frag[i]= new FragmentHome();
						ft.hide(from_fg).add(viewframe, frag[i]).commit();
						Log.d(Tags,"reload score chart "+i);
						TaskData.initteamflag=0;
					}else{
						ft.hide(from_fg).show(frag[i]).commit();
						Log.d(Tags,"show tab_task "+i);
					}
				}
			}
			from_fg=frag[i];
			return from_fg;
		}

	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
      /*
	   if (TaskData.TdDB==null) {
		   TaskData.TdDB = new ToDoDB(getActivity(), TaskData.db_TdDBname,null, 1);
		   TaskData.db_TdDB = TaskData.TdDB.getWritableDatabase();
		Log.d("create TdDB", TaskData.TdDB.getDatabaseName());
	   }else{
		   Log.d("TdDB open", "go");
	   }	*/	 
		
	    //Log.d("TaskTab", "create");
	}


  
}

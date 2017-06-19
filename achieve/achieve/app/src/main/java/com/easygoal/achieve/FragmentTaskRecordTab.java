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

public class FragmentTaskRecordTab extends Fragment{
	
	int selectedID=0;
	int selRecordTabId=0;
	Fragment from_fg;
	String Tags="FragmentScoreTab";
	 //final ToDoDB TdDB = new ToDoDB(getActivity(), db_name,null, 1);
	 
	 
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
	            Bundle savedInstanceState) {  
		View v=inflater.inflate(R.layout.rfg_bottomtab3_record, container, false);	  
		//DatabaseHelper TdDB=new DatabaseHelper(); 
		
		//Log.d("create database", "start"+TaskData.db.toString());
		 
		Fragment fragment_taskrecord = new FragmentTaskRecord();
		Fragment fragment_taskreview = new FragmentTaskReview();
		Fragment fragment_taskrun = new FragmentTaskRun();
		//Fragment fragment_taskloglist = new FragmentTaskLogList();
			final Fragment[] subfrag_taskrecordTab={fragment_taskrecord,fragment_taskrun,fragment_taskreview };
		
		
		  final RadioButton recordtab_rb0=(RadioButton)v.findViewById(R.id.radio0);
		  final RadioButton recordtab_rb1=(RadioButton)v.findViewById(R.id.radio1);
		  final RadioButton recordtab_rb2=(RadioButton)v.findViewById(R.id.radio2);
			//final RadioButton recordtab_rb3=(RadioButton)v.findViewById(R.id.radio3);

		  FragmentTransaction ft =getFragmentManager().beginTransaction();
			ft.add(R.id.sublayout_record, subfrag_taskrecordTab[0]).commit(); 
	 	  
	 	//  from_fg=showFrag(from_fg,R.id.sublayout_record,subfrag_taskrecordTab,selRecordTabId);
	 	 //  from_fg=showFrag(from_fg,R.id.sublayout_record,subfrag_taskrecordTab,0);
	 	    from_fg=subfrag_taskrecordTab[0];
	 	    
		  recordtab_rb0.setOnClickListener(new OnClickListener(){

			  
			@Override
			public void onClick(View v) {
				
				// TODO Auto-generated method stub
				from_fg=showFrag(from_fg,R.id.sublayout_record,subfrag_taskrecordTab,0);
				int selRecordTabId = 0;
				Log.d(Tags,"task record tab choice0");
			}
		  });
		  recordtab_rb1.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					
					// TODO Auto-generated method stub
					from_fg=showFrag(from_fg,R.id.sublayout_record,subfrag_taskrecordTab,1);
					int selRecordTabId=1;
					Log.d(Tags,"task record tab choice1");
				}
			  });	 
		  recordtab_rb2.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					
					// TODO Auto-generated method stub
					from_fg=showFrag(from_fg,R.id.sublayout_record,subfrag_taskrecordTab,2);
					
					Log.d(Tags,"task record tab choice2");
				}
			  });

			return v;
	 
	 }

	 
	 public Fragment showFrag(Fragment from_fg,int viewframe,Fragment[] frag,int i){
		  FragmentTransaction ft = getFragmentManager().beginTransaction();
		  ft.setTransition(FragmentTransaction.TRANSIT_NONE);
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
		
	}

}

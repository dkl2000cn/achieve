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

public class FragmentTaskTab extends Fragment{
	
	int selectedID=0;
    Fragment from_fg;
	String Tags="FragmentTaskTab";
	 //final ToDoDB TdDB = new ToDoDB(getActivity(), db_name,null, 1);
	 
	 
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
	            Bundle savedInstanceState) {  
		View v=inflater.inflate(R.layout.rfg_bottomtab2_task, container, false);	  
		//DatabaseHelper TdDB=new DatabaseHelper(); 
		
		//Log.d("create database", "start"+TaskData.db.toString());
		 
		 Fragment fragment_alltasks = new FragmentAllTasks();
		 Fragment fragment_opentasks = new FragmentOpenTasks();
		 Fragment fragment_finishedtasks = new FragmentFinishedTasks();
		 Fragment fragment_cancelledtasks = new FragmentCancelledTasks();
		 //fragment_addComments= new DialogFragment_Comment();
		 final Fragment[] subfrag_task={fragment_opentasks,fragment_alltasks,fragment_finishedtasks,fragment_cancelledtasks};
		
		  final RadioButton rb0=(RadioButton)v.findViewById(R.id.radio0);
		  final RadioButton rb1=(RadioButton)v.findViewById(R.id.radio1);
		  final RadioButton rb2=(RadioButton)v.findViewById(R.id.radio2);
		  final RadioButton rb3=(RadioButton)v.findViewById(R.id.radio3);
		  FragmentTransaction ft =getFragmentManager().beginTransaction();
			ft.add(R.id.sublayout_task, subfrag_task[0]).commit(); 
	 	   
	 	    from_fg=subfrag_task[0];
		  rb0.setOnClickListener(new OnClickListener(){

			  
			@Override
			public void onClick(View v) {
				
				// TODO Auto-generated method stub
				from_fg=showFrag(from_fg,R.id.sublayout_task,subfrag_task,0);
				Log.d(Tags,"task tab choice0");
			}
		  });
		  rb1.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					
					// TODO Auto-generated method stub
					from_fg=showFrag(from_fg,R.id.sublayout_task,subfrag_task,1);
					Log.d(Tags,"task tab choice1");
				}
			  });
		  rb2.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					
					// TODO Auto-generated method stub
					from_fg=showFrag(from_fg,R.id.sublayout_task,subfrag_task,2);
					Log.d(Tags,"task tab choice2");
				}
			  });
		  rb3.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					
					// TODO Auto-generated method stub
					 //Log.d("from_fg", from_fg.toString()); 
					from_fg=showFrag(from_fg,R.id.sublayout_task,subfrag_task,3);
					Log.d(Tags,"task tab choice3");
					
					
					//from_fg=showFrag(from_fg,R.id.sublayout_task,subfrag_task,3);
				//	Log.d("task tab", "choice3");
				}
			  });
	
		return v;
	 
	 }

	
	 
	 public Fragment showFrag(Fragment from_fg,int viewframe,Fragment[] frag,int i){
		  FragmentTransaction ft = getFragmentManager().beginTransaction();

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

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

public class FragmentTaskScoreTab extends Fragment{
	
	int selectedID=0;
	int selScoreTabId=0;
	Fragment from_fg;
	String Tags="FragmentTaskScoreTab";
	 //final ToDoDB TdDB = new ToDoDB(getActivity(), db_name,null, 1);
	 
	 
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
	            Bundle savedInstanceState) {  
		View v=inflater.inflate(R.layout.rfg_bottomtab4_scoretab, container, false);	  
		//DatabaseHelper TdDB=new DatabaseHelper(); 
		
		//Log.d("create database", "start"+TaskData.db.toString());
		 
		Fragment fragment_taskrecore = new FragmentTaskScore();
		Fragment fragment_taskreivew = new FragmentScoreRank();
		Fragment fragment_scorechart = new FragmentScoreChart();
		
		
		final Fragment[] subfrag_taskscoreTab={fragment_taskrecore,  fragment_taskreivew,fragment_scorechart};
		
		  final RadioButton scoretab_rb0=(RadioButton)v.findViewById(R.id.radio0);
		  final RadioButton scoretab_rb1=(RadioButton)v.findViewById(R.id.radio1);
		  final RadioButton scoretab_rb2=(RadioButton)v.findViewById(R.id.radio2);
		  
		  FragmentTransaction ft =getFragmentManager().beginTransaction();
			ft.add(R.id.sublayout_score, subfrag_taskscoreTab[0]).commit(); 
	 	    
	 	//  from_fg=showFrag(from_fg,R.id.sublayout_score,subfrag_taskscoreTab,selscoreTabId);
	 	 //  from_fg=showFrag(from_fg,R.id.sublayout_score,subfrag_taskscoreTab,0);
	 	    from_fg=subfrag_taskscoreTab[0];
	 	    //final ViewGroup mContainer = container;
		  scoretab_rb0.setOnClickListener(new OnClickListener(){

			  
			@Override
			public void onClick(View v) {
				
				// TODO Auto-generated method stub
				from_fg=showFrag(from_fg,R.id.sublayout_score,subfrag_taskscoreTab,0);
				
				Log.d(Tags,"task score tab choice0");
			}
		  });
		  scoretab_rb1.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					
					// TODO Auto-generated method stub
					from_fg=showFrag(from_fg,R.id.sublayout_score,subfrag_taskscoreTab,1);
					
					Log.d(Tags,"task score tab choice1");
				}
			  });	 
		  scoretab_rb2.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					
					// TODO Auto-generated method stub
					from_fg=showFrag(from_fg,R.id.sublayout_score,subfrag_taskscoreTab,2);
					
					Log.d(Tags,"task score tab choice2");
				}
			  });	 
		return v;
	 
	 }

 
	 public Fragment showFrag(Fragment from_fg,int viewframe,Fragment[] frag,int i){
		  FragmentTransaction ft = getFragmentManager().beginTransaction();
		  ft.setTransition(FragmentTransaction.TRANSIT_NONE);
		  if (i!=2){
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
			   if (!frag[i].isAdded()) {
				   ft.add(viewframe, frag[i]).commit();
			   }else{
				   ft.show(frag[i]).commit();
			   }
  	    	     
  	       }else{
  	    	  if (!frag[i].isAdded()){
  	    	   ft.hide(from_fg).add(viewframe,frag[i]).commit();
  	    	  }else{
  	    		if (TaskData.updateflag==1){
  	    	       ft.remove(frag[i]);
  	    	       frag[i]=new FragmentScoreChart();
  	    	       ft.hide(from_fg).add(viewframe, frag[i]).commit();
  	    	       Log.d(Tags,"reload score chart "+i);
  	    	       TaskData.updateflag=0;
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
		
	}

}

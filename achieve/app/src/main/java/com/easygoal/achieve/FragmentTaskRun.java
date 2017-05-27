package com.easygoal.achieve;


import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class FragmentTaskRun extends Fragment {
	

	int selectedID=0;
	View oldview=null;
    View newview = null;
    String Tags="FragmentTaskRun";
	 @Override  
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
	            Bundle savedInstanceState) {  
		View v=inflater.inflate(R.layout.subfg_recordtab_toptab3_run, container, false);	  
		//DatabaseHelper TdDB=new DatabaseHelper(); 
		
		 // TaskData.tv_taskruncount=(TextView)v.findViewById(R.id.tv_taskruncount);
		 
		  final ListView lv_taskrun=(ListView)v.findViewById(R.id.taskrun_lv);
		//  TextView textView = ViewFinder.findViewById(R.id.my_textview); 
		
	
		  // final TextView tv1=(TextView)findViewById(R.id.textView2);
		 // final TextView tv2=(TextView)findViewById(R.id.textView3);
		   // final Cursor c = TaskData.db_TdDB.query(TaskData.TdDB.TABLE_NAME_TaskMain, null, null, null, null, null, null);
		  //  Log.d("cursor",String.valueOf(c.getCount())+"position"+String.valueOf(c.getPosition()));
		
		 // TaskData.tv_taskrunc.setText(String.valueOf(TaskData.cursor_taskrun.getCount()));
		
	      //TaskData.cursor_taskrun=TaskData.db_TdDB.rawQuery("select * from "+a+" where "+TaskData.TdDB.TASK_STATUS+"=?", new String[]{"open"});
	      TaskData.cursor_taskrun= TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain +" where "+TaskData.TdDB.TASK_USER+"=?"+" and "+TaskData.TdDB.TASK_STATUS+"=?", new String[]{TaskData.user,"open"});
	      TaskData.adapter_taskrun = new mcAdapter_taskrun(getActivity(),R.layout.lv_taskrun,TaskData.cursor_taskrun);
		 
		  lv_taskrun.setAdapter(TaskData.adapter_taskrun); 
  	      Log.d(Tags,"lv taskrun show data ok "+lv_taskrun.toString());	
		  
		  lv_taskrun.setOnItemClickListener(new OnItemClickListener(){
           
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				
				if (view!=newview){
 					oldview=newview; 
 					newview=view;
 					newview.setBackgroundColor(getResources().getColor(R.color.gray));
 					if (oldview!=null){
 					oldview.setBackgroundColor(getResources().getColor(R.color.mTextColor2));
 					} 
							 			
		         }
			}	
		 });
		  	  
		  	 
		return v;
	 
	 }

	 
	 public Fragment showFrag(Fragment from_fg,int viewframe,Fragment[] frag,int i){
		  FragmentTransaction ft = getFragmentManager().beginTransaction();

	       if (from_fg==null){
	    	   ft.add(viewframe, frag[i]).commit(); 
	    	      
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
		  
	 protected void showDialog(DialogFragment dialogFragment) {  
         
	        // Create and show the dialog.   
	    if(dialogFragment == null)  
	          //  dialogFragment = new Fragment_Search();  
	    	   dialogFragment.setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);  
	           dialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, 0); 
	         dialogFragment.show(getFragmentManager(), "dialog");  
	    }  
	 


	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		 
		
		 /*  if (TaskData.TdDB==null) {
			   TaskData.TdDB = new ToDoDB(getActivity(), TaskData.db_TdDBname,null, 1);
			 //  TaskData.db_TdDB = TaskData.TdDB.getWritableDatabase();
			   TaskData.TdDB.onCreate(TaskData.db_TdDB);
			 
			    //textView = (TextView) findViewById(R.id.text_view); 
				Log.d("task execute", "task execute");
			Log.d("create TdDB", TaskData.db_TdDB.toString());
		   }else{
			   Log.d("TdDB open", "go");
		   }	*/	 
		}

			
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	
		  
	}	  
}

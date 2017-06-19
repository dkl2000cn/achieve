package com.easygoal.achieve;


import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.graphics.Color;
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

public class FragmentTaskReview extends Fragment {
	
	
	
	int selectedID=0;
	View oldview=null;
	View newview = null;
	String Tags="FragmentTaskReview";
	 //final ToDoDB TdDB = new ToDoDB(getActivity(), db_name,null, 1);
	 
	 @Override  
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
	            Bundle savedInstanceState) {  
		View v=inflater.inflate(R.layout.subfg_recordtab_toptab2_review, container, false);	  
		//DatabaseHelper TdDB=new DatabaseHelper(); 
		
		 // TaskData.tv_taskreviewcount=(TextView)v.findViewById(R.id.tv_taskreviewcount);
		 
		  final ListView lv_taskreview=(ListView)v.findViewById(R.id.taskreview_lv);
		//  TextView textView = ViewFinder.findViewById(R.id.my_textview); 
		
		  // final TextView tv1=(TextView)findViewById(R.id.textView2);
		 // final TextView tv2=(TextView)findViewById(R.id.textView3);
		   // final Cursor c = TaskData.db_TdDB.query(TaskData.TdDB.TABLE_NAME_TaskMain, null, null, null, null, null, null);
		  //  Log.d("cursor",String.valueOf(c.getCount())+"position"+String.valueOf(c.getPosition()));
		  
		 // TaskData.tv_taskreviewc.setText(String.valueOf(TaskData.cursor_taskreview.getCount()));
		
	     //TaskData.cursor_taskreview=TaskData.db_TdDB.rawQuery("select * from "+a+" where "+TaskData.TdDB.TASK_STATUS+"=?", new String[]{"finished"});
	       
	       TaskData.cursor_taskreview= TaskData.db_TdDB.rawQuery("select * from "+TaskData.TdDB.TABLE_NAME_TaskMain+" where "+TaskData.TdDB.TASK_USER+"=?"+"and "+TaskData.TdDB.TASK_STATUS+"=?", new String[]{TaskData.user,"finished"});
	       //TaskData.cursor_taskreview.requery();
	       TaskData.adapter_taskreview = new mcAdapter_taskreview(getActivity(),R.layout.lv_taskreview,TaskData.cursor_taskreview);
		
		 lv_taskreview.setAdapter(TaskData.adapter_taskreview); 
  	    Log.d(Tags,"lv taskreview show data ok "+lv_taskreview.toString());
  	      
		 
		  
		  lv_taskreview.setFocusable(true);
		  lv_taskreview.setItemsCanFocus(true);
		  lv_taskreview.setSelected(true);
		 /* final Bundle bundle=null;
		  final Handler hd=new Handler(){
			  public void handleMessage(android.os.Message msg) {

				  super.handleMessage(msg); 
			  if(msg.what==1){
				  Bundle no = msg.getData();
				  selectedID=no.getInt("number");
				  Log.d("click at select", String.valueOf(no.getInt("number")));
			  }
			  } 
		  };
		 
		  final Message msg=null;
		  Thread tr=new Thread(){
			  
			  public void run(){
				  
				  m_listview.setOnItemClickListener(new OnItemClickListener(){
			             int ccc;
						@Override
						public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
							// TODO Auto-generated method stub
			                 ccc = position;
			                 m_listview.setSelection(ccc);
			                 Log.d("Thread click select", String.valueOf(m_listview.getSelectedItemPosition()));
			                 view.setBackgroundColor(Color.RED);
							Log.d("click at", String.valueOf(position)+String.valueOf(ccc));
					     	Log.d("click at select", String.valueOf(m_listview.getSelectedItemPosition()));
					     	msg.what=1;
							 					  
							bundle.putInt("number", ccc);
							  hd.sendMessage(msg);
						
						}
					  });		  
			  }
		  };
		  tr.start();*/
		
		  
		  lv_taskreview.setOnItemClickListener(new OnItemClickListener(){
             int newPos=0;
             int oldPos=0;
              
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

				//Log.d("click at", String.valueOf(position)+String.valueOf(ccc));
		     	//Log.d("click at select", String.valueOf(m_listview.getSelectedItemPosition()));
		     	/* new Handler().postDelayed(new Runnable() {
		             @Override
		             public void run() {
		            	 m_listview.setSelection(ccc);
		            	 Log.d("Thread click select", String.valueOf(m_listview.getSelectedItemPosition()));
		             }
		         }, 200);*/
			
			
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

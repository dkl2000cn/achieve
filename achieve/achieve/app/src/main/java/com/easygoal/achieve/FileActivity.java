package com.easygoal.achieve;

import java.io.File;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class FileActivity extends ListActivity {
	
	 private ArrayList<String> items = null;
	    private ArrayList<String> paths = null;
	    private String rootPath = "/";
	    private TextView mPath;
	    public static String clickedfile;
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.filemain); 
	       // getActionBar().setHomeButtonEnabled(true);
	        mPath = (TextView)findViewById(R.id.mPath);
	        mPath.setTextColor(Color.RED);
	        getFileDir(rootPath);
	        String str = getIntent().getStringExtra("test");
	        //Toast.makeText(getApplication(), str, Toast.LENGTH_LONG).show();        
	    }
	    
	    long waitTime = 2000;    
		 long touchTime = 0;   
		 @Override  
		 public boolean onKeyDown(int keyCode, KeyEvent event) {  
		     if(event.getAction() == KeyEvent.ACTION_DOWN && KeyEvent.KEYCODE_BACK == keyCode) {    
		    	 finish();  
		          
		     }    
		     return super.onKeyDown(keyCode, event);    
		 }
		
	    
	    
	    private void getFileDir(String filePath) {
	        mPath.setText(filePath);
	         
	        items = new ArrayList<String>();
	        paths = new ArrayList<String>();
	        File file = new File(filePath);
	        File[] files = file.listFiles();
	        if(!filePath.equals(rootPath)) {
	            items.add("返回根目录 " + rootPath);
	            paths.add(rootPath);
	            items.add("返回根目录 ../");
	            paths.add(file.getParent());
	        }
	        for(File fileTemp :files) {
	        	if (fileTemp.canRead()){
	            items.add(fileTemp.getName());
	            paths.add(fileTemp.getPath());
	        	}
	        }
	         
	        ArrayAdapter<String> adapter = new ArrayAdapter<String>(FileActivity.this,R.layout.file_now,items);
	        setListAdapter(adapter);
	         
	         
	    }
	    @Override
	    protected void onListItemClick(ListView l, View v, int position, long id) {
	        final File file = new File(paths.get(position));
	        if(file.canRead()) {
	            if(file.isDirectory()) {
	                getFileDir(paths.get(position));
	            }else {
	                new AlertDialog.Builder(this)
	                .setTitle("Message")
	                .setMessage(file.getAbsolutePath()+file.getName())
	                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
	                     
	                    @Override
	                    public void onClick(DialogInterface dialog, int which) {
	                    	clickedfile=file.getAbsolutePath();
	                    	 Intent intent = new Intent();  
	                         intent.putExtra("return", clickedfile);  
	                         setResult(RESULT_OK, intent);  
	                         finish();  

	                    }
	                }).show();
	                 
	            }
	        }else {
	            new AlertDialog.Builder(this)
	            .setTitle("Message")
	            .setMessage("无访问权限")
	            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
	                 
	                @Override
	                public void onClick(DialogInterface dialog, int which) {
	                     
	                }
	            }).show();
	             
	        }
	    }
		
	     
}

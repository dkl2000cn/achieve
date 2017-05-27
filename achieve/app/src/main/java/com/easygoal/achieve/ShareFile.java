package com.easygoal.achieve;

import java.io.File;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

public class ShareFile {
    private Context context;
    public static void ShareFile(Context context,File file,String filename,String subject,String text,String msgname) {
		// TODO Auto-generated constructor stub
		   
		    Intent intent = new Intent(Intent.ACTION_SEND);
		       intent.setType("text/*"); 
		       Uri u;
		       if (file != null && file.exists()) {  
		    	  // Log.d("file1", outfile.toString()+outfile.getTotalSpace());
			        
			        u = Uri.fromFile(file);  
			        Log.d("uri", u.toString());
			       intent.putExtra(Intent.EXTRA_STREAM, u);  
			      } else{
			    	  Log.d("file", "not exist");
			      }
		       //intent.putExtra("BITMAP", screenshot.takeScreenShot(MainActivity_1.this));
		         intent.putExtra(Intent.EXTRA_SUBJECT, "fddff");  
			     intent.putExtra(Intent.EXTRA_TEXT, "ddd");  
			     intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
			     context.startActivity(Intent.createChooser(intent, "love you!!!")); 

	}

}

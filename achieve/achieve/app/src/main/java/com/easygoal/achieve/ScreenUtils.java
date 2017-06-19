package com.easygoal.achieve;


import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class ScreenUtils {

	public static void screenshot(Context context){
		File sdFileDir= Environment.getExternalStorageDirectory();
		SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm");
		Date curDate = new Date(System.currentTimeMillis());//获取当前时间
		String curTime1 = formatter.format(curDate);
		//File file1=new File(sdFileDir.getAbsoluteFile()+"/DCIM/"+"[目标达]"+curTime1+".png");
		File file1=new File(sdFileDir.getAbsoluteFile()+"/DCIM/"+"[目标达]"+curTime1+".png");
		System.out.println(file1.getPath());
		System.out.println(file1.getAbsolutePath());
		System.out.println(file1.toString());
		try {
			if (!file1.exists()) {
				file1.createNewFile();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ScreenShot screenshot = new ScreenShot();
		// BitmapDrawable bd= new BitmapDrawable(screenshot.takeScreenShot(MainActivity_1.this));
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("image/*");
		Uri u;
		if (file1 != null && file1.exists()) {

			u = Uri.fromFile(file1);
			shoot(context,file1);
			Log.d("screenshot",file1.toString()+file1.getTotalSpace()+u.toString());
			intent.putExtra(Intent.EXTRA_STREAM, u);
			intent.putExtra(Intent.EXTRA_SUBJECT, "任务图片分享");
			intent.putExtra(Intent.EXTRA_TEXT, "screen");
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(Intent.createChooser(intent, "任务图片分享"));
		} else{
			Log.d("screenshot",file1.toString()+"not exist");
			//Toast.makeText(getApplicationContext(), "文件不存在",Toast.LENGTH_SHORT).show();
			Toast.makeText(context, "文件不存在",Toast.LENGTH_SHORT).show();
		}
	}

    public static Bitmap takeScreenShot(Context context, final File filePath) {
    	
    	
    	Bitmap bmshot = null;
    	final String Tags="ScreenShot";
		/*
		   class myAsynTask extends AsyncTask<File,Integer,Bitmap>{
			   
			   @Override
		        protected void onPreExecute() {
				  
		        }   
			   
			@Override
			protected Bitmap doInBackground(File... filePath) {
				// TODO Auto-generated method stub
				    View view = activity.getWindow().getDecorView();
			        view.setDrawingCacheEnabled(true);  
			        view.buildDrawingCache();  
			        Bitmap b1 = view.getDrawingCache();  
			   
			        // 获取状态栏高度  
			        //Rect frame = new Rect();  
			        //activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);  
			        //int statusBarHeight = frame.top;  
			   
			        // 获取屏幕长和高  
			        //int width = activity.getWindowManager().getDefaultDisplay().getWidth();  
			        //int height =activity.getWindowManager().getDefaultDisplay().getHeight();  
			        // 去掉标题栏  
			        int width = view.getWidth();  
			        int height =view.getHeight();  
			        //Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height-statusBarHeight);  
			        Bitmap b = Bitmap.createBitmap(b1, 0, 0, width, height); 
			        //Log.d(Tags, "statusheight"+statusBarHeight+"width"+width+"height"+height+"final height"+(height-statusBarHeight));
			        Log.d(Tags, "statusheight"+"width"+width+"height"+height);
			        view.destroyDrawingCache();  
			        
			        FileOutputStream fos = null;  
			        try {  
			            fos = new FileOutputStream(filePath[0]);  
			            if (fos!=null) {  
			                b.compress(Bitmap.CompressFormat.PNG, 100, fos);  
			                fos.flush();  
			                fos.close();  
			                Log.d(Tags,"fos"+fos.toString());
			            }  
			        } catch (FileNotFoundException e) {  
			            // e.printStackTrace();  
			        	Log.d(Tags,"fos error Filenotfound");
			        } catch (IOException e) {  
			            // e.printStackTrace();
			        	Log.d(Tags,"fos error IOException");
			        }  
			        
			        
				return b;
			}
  		
			@Override
				protected void onPostExecute(Bitmap bm) {
				// TODO Auto-generated method stub
					
				if (bm != null) {
					
					return ;
				}
			
				return;
				}

			
    	};
       */
		//WindowManager wmManager=(WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		//View view = wmManager..getDecorView();
		if (scanForActivity(context)!=null){
           Log.d("scan for Activity",""+scanForActivity(context).toString());
		}

		View view =scanForActivity(context).getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap b1 = view.getDrawingCache();
		int width = view.getWidth();
		int height =view.getHeight();
		final Bitmap b = Bitmap.createBitmap(b1, 0, 0, width, height);
		view.destroyDrawingCache();
		ExecutorService exec = Executors.newFixedThreadPool(5);
		Callable callable = null;
		FutureTask ft=new FutureTask(new Callable<String>(){
			public String call(){
				try {
					FileOutputStream fos = null;
					fos = new FileOutputStream(filePath);
					if (fos!=null) {
						b.compress(Bitmap.CompressFormat.PNG, 100, fos);
						fos.flush();
						fos.close();
						Log.d(Tags,"fos"+fos.toString());
					}
				} catch (FileNotFoundException e) {
					// e.printStackTrace();
					Log.d(Tags,"fos error Filenotfound");
				} catch (IOException e) {
					// e.printStackTrace();
					Log.d(Tags,"fos error IOException");
				}

				return "hello";
			}
		});
		//ft.run();
		exec.submit(ft);
		try {
			System.out.println(ft.get().toString());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		/*
        new Handler().post(new Runnable(){

			@Override
			public void run() {
				try {
					FileOutputStream fos = null;
					fos = new FileOutputStream(filePath);
					if (fos!=null) {
						b.compress(Bitmap.CompressFormat.PNG, 100, fos);
						fos.flush();
						fos.close();
						Log.d(Tags,"fos"+fos.toString());
					}
				} catch (FileNotFoundException e) {
					// e.printStackTrace();
					Log.d(Tags,"fos error Filenotfound");
				} catch (IOException e) {
					// e.printStackTrace();
					Log.d(Tags,"fos error IOException");
				}
			}
		});
*/
/*
		try {
			//bmshot = new myAsynTask().execute(filePath).get();
			bmshot=b;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   */
    	
        // View是你需要截图的View  
        /*
    	View view = activity.getWindow().getDecorView();  
        view.setDrawingCacheEnabled(true);  
        view.buildDrawingCache();  
        Bitmap b1 = view.getDrawingCache();  
   
        // 获取状态栏高度  
        Rect frame = new Rect();  
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);  
        int statusBarHeight = frame.top;  
   
        // 获取屏幕长和高  
        int width = activity.getWindowManager().getDefaultDisplay().getWidth();  
        int height = activity.getWindowManager().getDefaultDisplay()  
                .getHeight();  
        // 去掉标题栏  
        Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height  
                - statusBarHeight);  
        view.destroyDrawingCache();  
        Log.d("Bitmap", b.toString());
        return b;  */
    	return b;
    }  
   
   static void savePic(Bitmap b, File filePath) {  
        FileOutputStream fos = null;  
        final String Tags="ScreenShot|savePic|";
        try {  
            fos = new FileOutputStream(filePath);  
            if (null != fos) {  
                b.compress(Bitmap.CompressFormat.PNG, 100, fos);  
                fos.flush();  
                fos.close();  
                Log.d(Tags,"fos"+fos.toString());
            }  
        } catch (FileNotFoundException e) {  
            // e.printStackTrace();  
        	Log.d(Tags,"fos error"+"Filenotfound");
        } catch (IOException e) {  
            // e.printStackTrace();
        	Log.d(Tags,"fos error"+"IOException");
        }  
    }  
   
    public static void shoot(Context context, File filePath) {
    	final String Tags="ScreenShot|shoot|";
        if (filePath == null) {  
        	 Log.d(Tags,"filepath"+"null");
            return;  
        }  
        /*if (!filePath.getParentFile().exists()) {  
            filePath.getParentFile().mkdirs();  
            Log.d("filepath","make dirs");
        }  */
       
        takeScreenShot(context, filePath);
        Log.d(Tags,"begin shoot"+filePath.toString()+filePath.getTotalSpace());
    }

    private static Activity scanForActivity(Context cont) {
        if (cont == null)
            return null;
        else if (cont instanceof Activity)
            return (Activity)cont;
        else if (cont instanceof ContextWrapper)
            return scanForActivity(((ContextWrapper)cont).getBaseContext());

        return null;
    }
    public static Activity getActivity(Context context) {
		//Context context = getContext();
		while (!(context instanceof Activity) && context instanceof ContextWrapper) {
			context = ((ContextWrapper) context).getBaseContext();
		}

		if (context instanceof Activity) {
			return (Activity) context;
		}
       return null;
	}
}
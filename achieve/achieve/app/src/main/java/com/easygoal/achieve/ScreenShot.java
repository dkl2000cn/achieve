package com.easygoal.achieve;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.jakewharton.disklrucache.DiskLruCache;

public class ScreenShot {  
	
    public static String takeScreenShot(final Activity activity, final File filePath)  {
    	
    	
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
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap b1 = view.getDrawingCache();
		int width = view.getWidth();
		int height =view.getHeight();
		final Bitmap b = Bitmap.createBitmap(b1, 0, 0, width, height);
		view.destroyDrawingCache();

		ExecutorService exec = Executors.newFixedThreadPool(2);
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
						if (!b.isRecycled()){
							b.recycle();
						}
						return "true";
					}
				} catch (FileNotFoundException e) {
					// e.printStackTrace();
					Log.d(Tags,"fos error Filenotfound");
				} catch (IOException e) {
					// e.printStackTrace();
					Log.d(Tags,"fos error IOException");
				}finally {
					if (!b.isRecycled()) {
						b.recycle();
					}
				}
				return "false";
			}
		});
		//ft.run();
		exec.submit(ft);
		String result=null;
		try {
			 result = ft.get().toString();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		/*
		try {
			System.out.println(ft.get().toString());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
        new Handler().post(new Runnable(){

			@Override
			public void run() {
				try {
					FileOutputStream fos = null;
					fos = new FileOutputStream(filePath);
					if (fos!=null) {
						b.compress(Bitmap.CompressFormat.PNG, 50, fos);
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
				}finally {
					if (!b.isRecycled()) {
						b.recycle()
					}
				}
			}
		});


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
    	return result;
    }

	public static Bitmap takeScreenShot(final Activity activity) {

		final String Tags="ScreenShot";
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap b1 = view.getDrawingCache();
		int width = view.getWidth();
		int height =view.getHeight();
		final Bitmap b = Bitmap.createBitmap(b1, 0, 0, width, height);
		view.destroyDrawingCache();

		return b;
	}


	static String savePicForFileName(final Activity activity, final Bitmap b, final String saveFileName) {

		final String Tags="ScreenShot|savePicForFileName|";
		//final OutputStream fos = null;
		//DiskLruCache mDiskLruCache =FileUtils.openDishCache(activity.getBaseContext(),
		//		"Bitmap", 1,1*10*1024*1024);
		//String saveFileName="[目标达]" + TaskTool.getCurTime() + ".png";
		//String saveFileNameHashKey=FileUtils.hashKeyForDisk(saveFileName);
		//try {
			//final DiskLruCache.Editor editor = mDiskLruCache.edit(saveFileNameHashKey);

			ExecutorService exec = Executors.newFixedThreadPool(5);
			FutureTask ft=new FutureTask(new Callable<String>(){
				public String call(){
					try {
						DiskLruCache mDiskLruCache =FileUtils.openDishCache(activity.getBaseContext(),
								"Bitmap", 1,1*10*1024*1024);
						//String saveFileName="[目标达]" + TaskTool.getCurTime() + ".png";
						String saveFileNameHashKey=FileUtils.hashKeyForDisk(saveFileName);
						DiskLruCache.Editor editor = mDiskLruCache.edit(saveFileNameHashKey);
						OutputStream fos=editor.newOutputStream(0);
						//fos = new FileOutputStream(filePath);
						if (fos!=null) {
							b.compress(Bitmap.CompressFormat.PNG, 100, fos);
							fos.flush();
							fos.close();
							Log.d(Tags,"fos"+fos.toString());
							return "true";
						}
					} catch (FileNotFoundException e) {
						// e.printStackTrace();
						Log.d(Tags,"fos error Filenotfound");
					} catch (IOException e) {
						// e.printStackTrace();
						Log.d(Tags,"fos error IOException");
					}
					return "false";
				}
			});
			//ft.run();
			exec.submit(ft);
		    //String result;=ft.get().toString();
		    String result=null;
			try {
				result=ft.get().toString();
				System.out.println(ft.get().toString());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		//}
        return result;
	}

	static void compress(Bitmap b, File filePath) {


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
   
    public static String shoot(Activity a, File filePath) {
    	final String Tags="ScreenShot|shoot|";
        if (filePath == null) {  
        	 Log.d(Tags,"filepath"+"null");
            return null;
        }  
        /*if (!filePath.getParentFile().exists()) {  
            filePath.getParentFile().mkdirs();  
            Log.d("filepath","make dirs");
        }  */
		Log.d(Tags,"begin shoot"+filePath.toString()+filePath.getTotalSpace());
        return takeScreenShot(a, filePath);
    }

	public static String shoot(Activity a, String saveFileName) {
		final String Tags="ScreenShot|shoot|";
		/*
		if (filePath == null) {
			Log.d(Tags,"filepath"+"null");
			return;
		}
        if (!filePath.getParentFile().exists()) {
            filePath.getParentFile().mkdirs();
            Log.d("filepath","make dirs");
        }  */

		//takeScreenShot(a, filePath);
        return savePicForFileName(a,takeScreenShot(a),saveFileName);
		//Log.d(Tags,"save Pic done");
	}

}
package com.easygoal.achieve;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

public class AppUtils {

	 private AppUtils()  
	    {  
	        /* cannot be instantiated */  
	        throw new UnsupportedOperationException("cannot be instantiated");  
	  
	    }  
	  
	    /** 
	     * 获取应用程序名称 
	     */  
	    public static String getAppName(Context context)  
	    {  
	        try  
	        {  
	            PackageManager packageManager = context.getPackageManager();  
	            PackageInfo packageInfo = packageManager.getPackageInfo(  
	                    context.getPackageName(), 0);  
	            int labelRes = packageInfo.applicationInfo.labelRes;  
	            return context.getResources().getString(labelRes);  
	        } catch (NameNotFoundException e)  
	        {  
	            e.printStackTrace();  
	        }  
	        return null;  
	    }  
	  
	    /** 
	     * [获取应用程序版本名称信息] 
	     *  
	     * @param context 
	     * @return 当前应用的版本名称 
	     */  
	    public static String getVersionName(Context context)  
	    {  
	        try  
	        {  
	            PackageManager packageManager = context.getPackageManager();  
	            PackageInfo packageInfo = packageManager.getPackageInfo(  
	                    context.getPackageName(), 0);  
	            return packageInfo.versionName;  
	  
	        } catch (NameNotFoundException e)  
	        {  
	            e.printStackTrace();  
	        }  
	        return null;  
	    }


	public static String getVersionCode(Context context)
			throws NameNotFoundException {
		// 获取PackageManager 实例
		PackageManager packageManager = context.getPackageManager();
		// 获得context所属类的包名，0表示获取版本信息
		PackageInfo packageInfo = packageManager.getPackageInfo(
				context.getPackageName(), 0);
		return String.valueOf(packageInfo.versionCode);
	}
}

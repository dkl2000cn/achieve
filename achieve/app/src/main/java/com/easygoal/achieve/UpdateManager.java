package com.easygoal.achieve;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

public class UpdateManager {
	 // 应用程序Context
	 private Context mContext;
	 // 提示消息
	 private String updateMsg = "有最新的软件包，请下载！";
	 // 下载安装包的网络路径
	 private String versionCode;
	 private String versionName;
	 private String apkUrl = TaskData.hostname+"mubiaoda.apk";
	 private Dialog noticeDialog;// 提示有软件更新的对话框
	 private Dialog downloadDialog;// 下载对话框
	 private final String xmlPath = "/DCIM/";// 保存apk的文件夹
	 private final String savePath = "/sdcard/updatedemo/";// 保存apk的文件夹
	 private final String saveFileName = savePath + "UpdateDemoRelease.apk";
	 private final String xmlFileName = xmlPath + "update.xml";
	 // 进度条与通知UI刷新的handler和msg常量 
	 private ProgressBar mProgress;
	 private static final int DOWN_UPDATE = 1;
	 private static final int DOWN_OVER = 2;
	 private int progress;// 当前进度
	 private Thread downLoadThread; // 下载线程
	 private boolean interceptFlag = false;// 用户取消下载
	 // 通知处理刷新界面的handler
	 private Handler mHandler = new Handler() {
	  @SuppressLint("HandlerLeak")
	  @Override
	  public void handleMessage(Message msg) {
	   switch (msg.what) {
	   case DOWN_UPDATE:
	    mProgress.setProgress(progress);
	    break;
	   case DOWN_OVER:
		//unstallApk();
	    installApk();
	    break;
	   }
	   super.handleMessage(msg);
	  }
	 };
	 public UpdateManager(Context context) {
	  this.mContext = context;
		 try {
			 this.versionCode=VersionUtil.getVersionCode(mContext);
			 this.versionName=VersionUtil.getVersionName(mContext);
		 } catch (NameNotFoundException e) {
			 e.printStackTrace();
		 }
	 }
	 // 显示更新程序对话框，供主程序调用
	 public void checkUpdateInfo() {
	  showNoticeDialog();
	 }
	 private void showNoticeDialog() {
	  android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(
	    mContext);// Builder，可以通过此builder设置改变AleartDialog的默认的主题样式及属性相关信息

		 builder.setTitle("软件版本更新"+"("+versionName+")");
		 builder.setMessage(updateMsg);
	     builder.setPositiveButton("下载", new OnClickListener() {
	   @Override
	   public void onClick(DialogInterface dialog, int which) {
	    dialog.dismiss();// 当取消对话框后进行操作一定的代码？取消对话框
	    showDownloadDialog();
	   }
	  });
	  builder.setNegativeButton("以后再说", new OnClickListener() {
	   @Override
	   public void onClick(DialogInterface dialog, int which) {
	    dialog.dismiss();
	   }
	  });
	  noticeDialog = builder.create();
	  noticeDialog.show();
	 }
	 /*
	 public static void inputstreamtofile(InputStream ins,File file) {
		  try {
			
			 if (file==null){ 
		       
		        Log.d("file null","not created");
			 }else{
				  OutputStream os = new FileOutputStream(file);
			 
		   int bytesRead = 0;
		   byte[] buffer = new byte[8192];
		   while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
		    os.write(buffer, 0, bytesRead);
		   }
		   os.close();
		   ins.close();
		   Log.d("file have",""+file.getTotalSpace());
			}
		  } catch (Exception e) {
		   e.printStackTrace();
		  }
		 }*/
		 
	 public void inputstreamtofile(InputStream ins,File file) throws IOException{
		 OutputStream os = new FileOutputStream(file);
		 int bytesRead = 0;
		 byte[] buffer = new byte[8192];
		 while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
		 os.write(buffer, 0, bytesRead);
		 }
		 os.close();
		 ins.close();
		 }
	 /**
		 * 检查是否需要更新程序
		 * 
		 * @throws NameNotFoundException
		 */
	 
	 public static File getFilePath(String filePath,String fileName) {
            File file = null;
            makeRootDirectory(filePath);
          try {
                file = new File(filePath + fileName);
          } catch (Exception e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
           }
         return file;
      }

    public static void makeRootDirectory(String filePath) {
         File file = null;
      try {
            file = new File(filePath);
           if (!file.exists()) {
          file.mkdir();
       }
      } catch (Exception e) {

      }
    }
    
    Handler myHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case 1:
				String newVersionCode1=msg.getData().getString("version");
				String netVersionName1=msg.getData().getString("versionName");
				TaskData.latestVersionName=netVersionName1;
				Toast.makeText(mContext, "版本已更新,需要升级"+TaskData.latestVersionName, Toast.LENGTH_SHORT).show();
				if (TaskData.privilege>TaskData.vipentryright) {
					showNoticeDialog();
				}else {
					Intent intent2 = new Intent();
					intent2.setClass(mContext, Activity_Help.class);
					mContext.startActivity(intent2);
				}

				break;
			case 2:
				 String newVersionCode2=msg.getData().getString("version");
				 String netVersionName2=msg.getData().getString("versionName");
				 TaskData.latestVersionName=netVersionName2;
				 Toast.makeText(mContext, "已经是最新版本"+TaskData.latestVersionName, Toast.LENGTH_SHORT).show();

				//showDownloadDialog();
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}
	};
    
    
    
	private Runnable xmlRunnable = new Runnable() {
		 
		 UpdateInfo updateInfo = new UpdateInfo();
			URL url;
		  @Override
		  public void run() { 
			  try {
					url = new URL(TaskData.hostname+"update.xml");
					HttpURLConnection connection = (HttpURLConnection) url.openConnection();
					// connection.setConnectTimeout(5000);
					connection.connect();
					//updateInfo = ParseXmlUtils.parseXml(connection.getInputStream());
				     updateInfo = XmlUtils.Pull2Xml(connection.getInputStream()).get(0);
					//connection.connect();
					/*
					 File file = new File(xmlPath);
					    if (!file.exists()) {
					     file.mkdir();
					     Log.d("file", "new"+file.getAbsolutePath());	
					    }else{
					      Log.d("file","have"+file.getAbsolutePath());	
					    }
					File xmlFile = new File(xmlFileName);
					Log.d("xmlfile", xmlFile.getAbsolutePath());	
					String curVer=VersionUtil.getVersionName(mContext);
					Log.d("curVer", curVer);
			        
					File xmlFile = getFilePath("/DCIM/","update.xml");
					
					 inputstreamtofile(connection.getInputStream(),xmlFile); 
					 
					// Log.d("xml to file", xmlFile.getAbsolutePath());
					 try {   
				            //1.获取factory   
				            SAXParserFactory factory = SAXParserFactory.newInstance();   
				            //2.获取parser   
				            SAXParser parser = factory.newSAXParser();   
				            //3.获取解析时的监听器对象   
				            SaxUtil su = new SaxUtil();   
				            //4.开始解析   
				            //parser.parse(new File("src/user-params.xml"), su);   
				            parser.parse(xmlFile, su);   
				            //System.out.println(su.getUser());   
				            updateInfo=su.getUpdateInfo();
				            Log.d("su updateInfo",updateInfo.getVersion());
				           // Log.d("versionutil",VersionUtil.getVersionName(mContext));
				        } catch (ParserConfigurationException e) {   
				            e.printStackTrace();   
				        } catch (SAXException e) {   
				            e.printStackTrace();   
				        } catch (IOException e) {   
				            e.printStackTrace();   
				        }   		
					*/
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			        int netVersionCode = Integer.parseInt(updateInfo.getVersion());
			        String netVersionName = updateInfo.getVersionName();
			        int mobileVersionCode=Integer.parseInt(versionCode);
			         Log.d("version check","官方最新版本"+netVersionCode+" 本机最新版"+mobileVersionCode);
					if (netVersionCode<=mobileVersionCode) {
						//Toast.makeText(mContext, "版本相同,不需要升级!", Toast.LENGTH_SHORT).show();
						Bundle bundle=new Bundle();
						bundle.putString("version",""+netVersionCode);
						bundle.putString("versionName", netVersionName);
						Message msg = new Message();
						msg.setData(bundle);
						  msg.what = 2;						  
						  myHandler.sendMessage(msg);
					
					} else {
						//Toast.makeText(mContext,"当前版本"+versionCode+"最新版本"+updateInfo.getVersion(), Toast.LENGTH_SHORT).show();

						//checkUpdateInfo();
						  Bundle bundle=new Bundle();
						  bundle.putString("version",""+netVersionCode);
						  bundle.putString("versionName", netVersionName);
						  Message msg = new Message();
						  msg.setData(bundle);
						  msg.what = 1;						  
						  myHandler.sendMessage(msg);
                    }

	 }
};	  
	 
		  void findVersion() {
			  downLoadThread = new Thread(xmlRunnable);
			  downLoadThread.start();
			 }
  
		  
	 protected void showDownloadDialog() {
	  android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(mContext);
	  builder.setTitle("软件版本更新");
	  final LayoutInflater inflater = LayoutInflater.from(mContext);
	  View v = inflater.inflate(R.layout.progress, null);
	  mProgress = (ProgressBar) v.findViewById(R.id.progress);
	  builder.setView(v);// 设置对话框的内容为一个View
	  builder.setNegativeButton("取消", new OnClickListener() {
	   @Override
	   public void onClick(DialogInterface dialog, int which) {
	    dialog.dismiss();
	    interceptFlag = true;
	   }
	  });
	  downloadDialog = builder.create();
	  downloadDialog.show();
	  downloadApk();
	 }
	 private void downloadApk() {
	  downLoadThread = new Thread(mdownApkRunnable);
	  downLoadThread.start();
	 }
	 protected void installApk() {
	  File apkfile = new File(saveFileName);
	  if (!apkfile.exists()) {
	   return;
	  }
	  /*
	  Intent i = new Intent(Intent.ACTION_VIEW);
	  i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
	    "application/vnd.android.package-archive");// File.toString()会返回路径信息
	  mContext.startActivity(i);
	  */
	  Intent i = new Intent(Intent.ACTION_VIEW);
	  i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	  i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
	  "application/vnd.android.package-archive");
	  mContext.startActivity(i);
	  
	  
	 }
	 
	 public static String execCommand(String ...command)  {  
			Process process=null;  
			InputStream errIs=null;  
			InputStream inIs=null;  
			String result="";  

			try {  
				process=new ProcessBuilder().command(command).start();  
				ByteArrayOutputStream baos = new ByteArrayOutputStream();  
				int read = -1;  
				errIs=process.getErrorStream();           
				while((read=errIs.read())!=-1){  
					baos.write(read);  
				}  
				inIs=process.getInputStream();  
				while((read=inIs.read())!=-1){  
					baos.write(read);  
				}  
				result=new String(baos.toByteArray());  
				if(inIs!=null)  
					inIs.close();  
				if(errIs!=null)  
					errIs.close();  
				process.destroy();  
			} catch (IOException e) {  
				result = e.getMessage();  
			}  
			return result;  
		}  
	 
	 protected void unstallApk() {
		 String result = execCommand("pm","uninstall", "com.example.easygoal");
		 Toast.makeText(mContext, "卸载结果:"+result, Toast.LENGTH_LONG).show();
		 }
	 
	 private Runnable mdownApkRunnable = new Runnable() {
	  @Override
	  public void run() {
	   URL url;
	   try {
	    url = new URL(apkUrl);
	    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    conn.connect();
	    int length = conn.getContentLength();
	    InputStream ins = conn.getInputStream();
	    File file = new File(savePath);
	    if (!file.exists()) {
	     file.mkdir();
	    }
	    String apkFile = saveFileName;
	    File ApkFile = new File(apkFile);
	    FileOutputStream outStream = new FileOutputStream(ApkFile);
	    int count = 0;
	    byte buf[] = new byte[1024];
	    do {
	     int numread = ins.read(buf);
	     count += numread;
	     progress = (int) (((float) count / length) * 100);
	     // 下载进度
	     mHandler.sendEmptyMessage(DOWN_UPDATE);
	     if (numread <= 0) {
	      // 下载完成通知安装
	      mHandler.sendEmptyMessage(DOWN_OVER);
	      break;
	     }
	     outStream.write(buf, 0, numread);
	    } while (!interceptFlag);// 点击取消停止下载
	    outStream.close();
	    ins.close();
	   } catch (Exception e) {
	    e.printStackTrace();
	   }
	  }
	 };
}

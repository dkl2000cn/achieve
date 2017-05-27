package com.easygoal.achieve;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.jakewharton.disklrucache.DiskLruCache;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.util.LruCache;

public class FileUtils {

	public static File getDiskCacheDir(Context context, String uniqueName) {
	    String cachePath;  
	    if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())  
	            || !Environment.isExternalStorageRemovable()) {  
	        cachePath = context.getExternalCacheDir().getPath();  
	    } else {  
	        cachePath = context.getCacheDir().getPath();  
	    }  
	    return new File(cachePath + File.separator + uniqueName);  
	}  
	
	public static String getDiskCacheDirPath(Context context) {  
	    String cachePath;  
	    if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())  
	            || !Environment.isExternalStorageRemovable()) {  
	        cachePath = context.getExternalCacheDir().getPath();  
	    } else {  
	        cachePath = context.getCacheDir().getPath();  
	    }  
	    return cachePath+ File.separator;  
	}  
    /*
	public static LruCache<K,V> openCache(Context context){
			//获取系统分配给每个应用程序的最大内存，每个应用系统分配32M
			int maxMemory = (int) Runtime.getRuntime().maxMemory();
			int mCacheSize = maxMemory / 8;
			//给LruCache分配1/8 4M
			LruCache<K, V> mMemoryCache = new LruCache<K, V>(mCacheSize) {


			};
           return  mMemoryCache;
	}*/

	public static LruCache<String,Object> openCache(Context context){
		//获取系统分配给每个应用程序的最大内存，每个应用系统分配32M
		int maxMemory = (int) Runtime.getRuntime().maxMemory();
		int mCacheSize = maxMemory / 8;
		//给LruCache分配1/8 4M
		LruCache<String,Object> mMemoryCache = new LruCache<String,Object>(mCacheSize) {


		};
		return  mMemoryCache;
	}
	/**
	 * 添加Bitmap到内存缓存
	 * @param key
	 * @param bitmap
	 */

	public static void addToMemoryCache(LruCache<String,Object> mMemoryCache,String key, Object object) {
		if (getFromMemCache(mMemoryCache,key) == null && object != null) {
			mMemoryCache.put(key, object);
		}
	}

	/**
	 * 从内存缓存中获取一个Bitmap
	 * @param key
	 * @return
	 */
	public static  Object getFromMemCache(LruCache<String,Object> mMemoryCache,String key) {
		return mMemoryCache.get(key);
	}


	public static DiskLruCache openDishCache(Context context,String dirname, int valueCount, long maxSize){
		DiskLruCache mDiskLruCache = null;  
		try {  
			File cacheDir = getDiskCacheDir(context, dirname);  
	      if (!cacheDir.exists()) {  
	         cacheDir.mkdirs();  
	      }  
	         mDiskLruCache = DiskLruCache.open(cacheDir, Integer.parseInt(AppUtils.getVersionCode(context)), valueCount, maxSize);
	         return mDiskLruCache;
		} catch (IOException e) {  
			e.printStackTrace();  
	    } catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}

		return null;
    }  
	
	public static String hashKeyForDisk(String key) {
	    String cacheKey;  
	    try {  
	        final MessageDigest mDigest = MessageDigest.getInstance("MD5");  
	        mDigest.update(key.getBytes());  
	        cacheKey = bytesToHexString(mDigest.digest());  
	    } catch (NoSuchAlgorithmException e) {  
	        cacheKey = String.valueOf(key.hashCode());  
	    }  
	    return cacheKey;  
	}  
	  
	private static String bytesToHexString(byte[] bytes) {
	    StringBuilder sb = new StringBuilder();  
	    for (int i = 0; i < bytes.length; i++) {  
	        String hex = Integer.toHexString(0xFF & bytes[i]);  
	        if (hex.length() == 1) {  
	            sb.append('0');  
	        }  
	        sb.append(hex);  
	    }  
	    return sb.toString();  
	}  
	

	   public static void writeBmp2File(Bitmap b,String outfilepath){
		 String Tags="Bmp2File";	
		 FileOutputStream fos = null;  
	     try {  
	         fos = new FileOutputStream(outfilepath);  
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
	  
	   public byte[] Bitmap2Bytes(Bitmap bm) {
		      ByteArrayOutputStream baos = new ByteArrayOutputStream();
		      bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		      return baos.toByteArray();
		  }	 
	   
		public Bitmap getBitmapFromUri(Context context,Uri uri){
			  try
			  {
			   // 读取uri所在的图片
			   Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
			   return bitmap;
			  }
			  catch (Exception e)
			  {
			   Log.e("[Android]", e.getMessage());
			   Log.e("[Android]", "目录为：" + uri);
			   e.printStackTrace();
			   return null;
			  }
			}
	   
		 public static void savePic(Bitmap b, File filePath) {  
		        FileOutputStream fos = null;  
		        try {  
		            fos = new FileOutputStream(filePath);  
		            if (null != fos) {  
		                b.compress(Bitmap.CompressFormat.PNG, 100, fos);  
		                fos.flush();  
		                fos.close();  
		                Log.d("|fos|", fos.toString());
		            }  
		        } catch (FileNotFoundException e) {  
		            // e.printStackTrace();  
		        	Log.d("fos error", "Filenotfound");
		        } catch (IOException e) {  
		            // e.printStackTrace();
		        	Log.d("fos error", "IOException");
		        }  
		    }  
		
	   public static void writeBmp2Cache(Context context,Bitmap b){
			 String Tags="Bmp2File";	
			 FileOutputStream fos = null; 
			 String outfilepath=getDiskCacheDirPath(context);
		     try {  
		         fos = new FileOutputStream(outfilepath);  
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
	   /*
	   public void writeBmp2Cache(Bitmap b,DiskLruCache mDiskLruCache){
			 String Tags="writeBmp2Cache";
			 //File file=new File(filename);
			 //FileOutputStream fos = null; 
			 //String key=hashKeyForDisk(filename);
			  FileOutputStream fos = null; 
			  final DiskLruCache  mdc= mDiskLruCache;
			  
					 new Thread(new Runnable() {  
						    @Override  
						    public void run() {  
						        try {  
						            //String imageUrl = "http://img.my.csdn.net/uploads/201309/01/1378037235_7476.jpg";  
						            //String key = hashKeyForDisk(fn);  
						            //DiskLruCache.Editor editor = mdc.edit(key);  
						           // if (editor != null) {  
						                //OutputStream outputStream = editor.newOutputStream(0);
						                //FileInputStream in = new FileInputStream(fn);
						                /*
						                while ((outputStream=in.read())!=-1) {  
						                    editor.commit();  
						                } else {  
						                    editor.abort();  
						                }  
						                
						            }  
						            mdc.flush();  
						        } catch (IOException e) {  
						            e.printStackTrace();  
						        }  
						    }  
						}).start();  	 
			} 
	      */
	      
   public static void writeFile2Cache(String filename,DiskLruCache mDiskLruCache){
		 String Tags="writeFile2Cache";
		 //File file=new File(filename);
		 //FileOutputStream fos = null; 
		 //String key=hashKeyForDisk(filename);
		  final String fn=filename;
		  final DiskLruCache  mdc= mDiskLruCache;
		  
				 new Thread(new Runnable() {  
					    @Override  
					    public void run() {  
					        try {  
					            //String imageUrl = "http://img.my.csdn.net/uploads/201309/01/1378037235_7476.jpg";  
					            String key = hashKeyForDisk(fn);  
					            DiskLruCache.Editor editor = mdc.edit(key);  
					            if (editor != null) {  
					                OutputStream outputStream = editor.newOutputStream(0);
					                FileInputStream in = new FileInputStream(fn);
					                /*
					                while ((outputStream=in.read())!=-1) {  
					                    editor.commit();  
					                } else {  
					                    editor.abort();  
					                }  
					                */
					            }  
					            mdc.flush();  
					        } catch (IOException e) {  
					            e.printStackTrace();  
					        }  
					    }  
					}).start();  	 
		} 
   
   
   public static void readFileByBytes(String fileName) {
       File file = new File(fileName);
       InputStream in = null;
       try {
           System.out.println("以字节为单位读取文件内容，一次读一个字节：");
           // 一次读一个字节
           in = new FileInputStream(file);
           int tempbyte;
           while ((tempbyte = in.read()) != -1) {
               System.out.write(tempbyte);
           }
           in.close();
       } catch (IOException e) {
           e.printStackTrace();
           return;
       }
       try {
           System.out.println("以字节为单位读取文件内容，一次读多个字节：");
           // 一次读多个字节
           byte[] tempbytes = new byte[100];
           int byteread = 0;
           in = new FileInputStream(fileName);
            //ReadFromFile.showAvailableBytes(in);
           // 读入多个字节到字节数组中，byteread为一次读入的字节数
           while ((byteread = in.read(tempbytes)) != -1) {
               System.out.write(tempbytes, 0, byteread);
           }
       } catch (Exception e1) {
           e1.printStackTrace();
       } finally {
           if (in != null) {
               try {
                   in.close();
               } catch (IOException e1) {
               }
           }
       }
   }

   /**
    * 以字符为单位读取文件，常用于读文本，数字等类型的文件
    */
   public static void readFileByChars(String fileName) {
       File file = new File(fileName);
       Reader reader = null;
       try {
           System.out.println("以字符为单位读取文件内容，一次读一个字节：");
           // 一次读一个字符
           reader = new InputStreamReader(new FileInputStream(file));
           int tempchar;
           while ((tempchar = reader.read()) != -1) {
               // 对于windows下，\r\n这两个字符在一起时，表示一个换行。
               // 但如果这两个字符分开显示时，会换两次行。
               // 因此，屏蔽掉\r，或者屏蔽\n。否则，将会多出很多空行。
               if (((char) tempchar) != '\r') {
                   System.out.print((char) tempchar);
               }
           }
           reader.close();
       } catch (Exception e) {
           e.printStackTrace();
       }
       try {
           System.out.println("以字符为单位读取文件内容，一次读多个字节：");
           // 一次读多个字符
           char[] tempchars = new char[30];
           int charread = 0;
           reader = new InputStreamReader(new FileInputStream(fileName));
           // 读入多个字符到字符数组中，charread为一次读取字符数
           while ((charread = reader.read(tempchars)) != -1) {
               // 同样屏蔽掉\r不显示
               if ((charread == tempchars.length)
                       && (tempchars[tempchars.length - 1] != '\r')) {
                   System.out.print(tempchars);
               } else {
                   for (int i = 0; i < charread; i++) {
                       if (tempchars[i] == '\r') {
                           continue;
                       } else {
                           System.out.print(tempchars[i]);
                       }
                   }
               }
           }

       } catch (Exception e1) {
           e1.printStackTrace();
       } finally {
           if (reader != null) {
               try {
                   reader.close();
               } catch (IOException e1) {
               }
           }
       }
   }

   
   
   /**
    * 以行为单位读取文件，常用于读面向行的格式化文件
    */
   public static void readFileByLines(String fileName) {
       File file = new File(fileName);
       BufferedReader reader = null;
       try {
           System.out.println("以行为单位读取文件内容，一次读一整行：");
           reader = new BufferedReader(new FileReader(file));
           String tempString = null;
           int line = 1;
           // 一次读入一行，直到读入null为文件结束
           while ((tempString = reader.readLine()) != null) {
               // 显示行号
               System.out.println("line " + line + ": " + tempString);
               line++;
           }
           reader.close();
       } catch (IOException e) {
           e.printStackTrace();
       } finally {
           if (reader != null) {
               try {
                   reader.close();
               } catch (IOException e1) {
               }
           }
       }
   }

   /**
    * 随机读取文件内容
    */
   public void readFileByRandomAccess(String fileName) {
       RandomAccessFile randomFile = null;
       try {
           System.out.println("随机读取一段文件内容：");
           // 打开一个随机访问文件流，按只读方式
           randomFile = new RandomAccessFile(fileName, "r");
           // 文件长度，字节数
           long fileLength = randomFile.length();
           // 读文件的起始位置
           int beginIndex = (fileLength > 4) ? 4 : 0;
           // 将读文件的开始位置移到beginIndex位置。
           randomFile.seek(beginIndex);
           byte[] bytes = new byte[10];
           int byteread = 0;
           // 一次读10个字节，如果文件内容不足10个字节，则读剩下的字节。
           // 将一次读取的字节数赋给byteread
           while ((byteread = randomFile.read(bytes)) != -1) {
               System.out.write(bytes, 0, byteread);
           }
       } catch (IOException e) {
           e.printStackTrace();
       } finally {
           if (randomFile != null) {
               try {
                   randomFile.close();
               } catch (IOException e1) {
               }
           }
       }
   }

   /**
    * 显示输入流中还剩的字节数
    */
   private  void showAvailableBytes(InputStream in) {
       try {
           System.out.println("当前字节输入流中的字节数为:" + in.available());
       } catch (IOException e) {
           e.printStackTrace();
       }
   }
   
}

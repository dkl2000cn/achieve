package com.easygoal.achieve;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Created by Acer on 2017/4/27.
 */

public class ImageUtils {

    public static byte[] getByteFromUri(Context context,String imgUri)
    {
        ContentResolver resolver = context.getContentResolver();
        byte[] imgData = new byte[0];
        try {
            imgData= ImageUtils.readStream(resolver.openInputStream(Uri.parse(imgUri.toString())));
        } catch (Exception e) {
            e.printStackTrace();
        }
        BitmapFactory.Options opts = getOpts();
        Bitmap bm=getPicFromBytes(imgData,opts);
        byte[] imgByte=getImgByte(bm);
        return imgByte;
    }


    public static byte[] getImgByte(Bitmap bitmap)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //Bitmap bitmap = ((BitmapDrawable) getResources().getDrawable(id)).getBitmap();
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, baos);
        return baos.toByteArray();
    }

    public static BitmapFactory.Options getOpts(){
        BitmapFactory.Options opt=new BitmapFactory.Options();
        opt.inSampleSize=4;
        opt.inDensity=50;
        return opt;
    }

    public static Bitmap getPicFromBytes(byte[] bytes, BitmapFactory.Options opts) {

        if (bytes != null)
            if (opts != null)
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, opts);
            else
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return null;
    }

    public static byte[] readStream(InputStream inStream) throws Exception {
        byte[] buffer = new byte[1024];
        int len = -1;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();
        outStream.close();
        inStream.close();
        return data;

    }
    /**
     * 保持长宽比缩小Bitmap
     *
     * @param bitmap
     * @param maxWidth
     * @param maxHeight
     * @return
     */
    public Bitmap resizeBitmap(Bitmap bitmap, int maxWidth, int maxHeight) {

        int originWidth  = bitmap.getWidth();
        int originHeight = bitmap.getHeight();

        // no need to resize
        if (originWidth < maxWidth && originHeight < maxHeight) {
            return bitmap;
        }

        int width  = originWidth;
        int height = originHeight;

        // 若图片过宽, 则保持长宽比缩放图片
        if (originWidth > maxWidth) {
            width = maxWidth;

            double i = originWidth * 1.0 / maxWidth;
            height = (int) Math.floor(originHeight / i);

            bitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
        }

        // 若图片过长, 则从上端截取
        if (height > maxHeight) {
            height = maxHeight;
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height);
        }

//        Log.i(TAG, width + " width");
//        Log.i(TAG, height + " height");

        return bitmap;
    }

    /**
     * 获取本地图片并指定高度和宽度
     */
    public static Bitmap getNativeImage(String imagePath)
    {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
// 获取这个图片的宽和高
        Bitmap myBitmap = BitmapFactory.decodeFile(imagePath, options); //此时返回myBitmap为空
//计算缩放比
        int be = (int)(options.outHeight / (float)200);
        int ys = options.outHeight % 200;//求余数
        float fe = ys / (float)200;
        if (fe >= 0.5)
            be = be + 1;
        if (be <= 0)
            be = 1;
        options.inSampleSize = be;
//重新读入图片，注意这次要把options.inJustDecodeBounds 设为 false
        options.inJustDecodeBounds = false;
        myBitmap = BitmapFactory.decodeFile(imagePath, options);
        return myBitmap;
    }

    /**
     * 以最省内存的方式读取本地资源的图片
     * @param context
     * @param resId
     * @return
     */
    public static Bitmap readBitMap(Context context, int resId)
    {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
//获取资源图片
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }


    /**
     * 以最省内存的方式读取本地资源的图片 或者SDCard中的图片
     * @param imagePath
     * 图片在SDCard中的路径
     * @return
     */
    public static Bitmap getSDCardImg(String imagePath) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
//获取资源图片
        return BitmapFactory.decodeFile(imagePath, opt);
    }
/*
	private Bitmap getBitmapFromUri(Uri uri)
	{
		try
		{
			// 读取uri所在的图片
			Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
			return bitmap;
		}
		catch (Exception e)
		{
			Log.e("[Android]", e.getMessage());
			Log.e("[Android]", "目录为：" + uri);
			e.printStackTrace();
			return null;
		}

    public byte[] img(int id)
 {
              ByteArrayOutputStream baos = new ByteArrayOutputStream();
              Bitmap bitmap = ((BitmapDrawable) context.getResources().getDrawable(id)).getBitmap();
              bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
              return baos.toByteArray();
  }

    //将图片一字节形式存储数据库读取操作
    public Drawable chage_to_drawable(Bitmap bp)
 {
             //因为BtimapDrawable是Drawable的子类，最终直接使用bd对象即可。
             Bitmap bm=bp;
             BitmapDrawable bd= new BitmapDrawable(context.getResources(), bm);
            return bd;
         }*/

}

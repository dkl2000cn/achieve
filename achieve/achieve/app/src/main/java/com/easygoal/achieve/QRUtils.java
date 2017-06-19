package com.easygoal.achieve;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * ��Ŀ���ƣ�achieve
 * ��������
 * �����ˣ�Acer
 * ����ʱ�䣺2017/6/1 21:30
 * �޸��ˣ�Acer
 * �޸�ʱ�䣺2017/6/1 21:30
 * �޸ı�ע��
 */

public class QRUtils {
    public static int IMAGE_HALFWIDTH=100;
    public static int QR_WIDTH=430;

    public static Bitmap generateBitmap(String content, int width, int height) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, String> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        try {
            BitMatrix encode = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            int[] pixels = new int[width * height];
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (encode.get(j, i)) {
                        pixels[i * width + j] = 0x00000000;
                    } else {
                        pixels[i * width + j] = 0xffffffff;
                    }
                }
            }
            return Bitmap.createBitmap(pixels, 0, width, width, height, Bitmap.Config.RGB_565);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Bitmap createQRwithLogo(String string, Resources res,int resint){
        Bitmap logo= BitmapFactory.decodeResource(res,resint);
        Bitmap logoQR=null;
        try {
            logoQR=createCodeWithLogo(string,logo,BarcodeFormat.QR_CODE);
            LogUtils.d("logo qr created");
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return logoQR;
    }

    /**
     * ���ɶ�ά��
     * @param string ��ά���а������ı���Ϣ
     * @param mBitmap logoͼƬ
     * @param format  �����ʽ
     * [url=home.php?mod=space&uid=309376]@return[/url] Bitmap λͼ
     * @throws WriterException
     */
    public static Bitmap createCodeWithLogo(String string,Bitmap mBitmap, BarcodeFormat format)
            throws WriterException {
        Matrix m = new Matrix();
        float sx = (float) 2 * IMAGE_HALFWIDTH / mBitmap.getWidth();
        float sy = (float) 2 * IMAGE_HALFWIDTH / mBitmap.getHeight();
        m.setScale(sx, sy);//����������Ϣ
        //��logoͼƬ��martix���õ���Ϣ����
        mBitmap = Bitmap.createBitmap(mBitmap, 0, 0,
                mBitmap.getWidth(), mBitmap.getHeight(), m, false);
        MultiFormatWriter writer = new MultiFormatWriter();
        Hashtable<EncodeHintType, String> hst = new Hashtable<EncodeHintType, String>();
        hst.put(EncodeHintType.CHARACTER_SET, "UTF-8");//�����ַ�����
        BitMatrix matrix = writer.encode(string, format,QR_WIDTH, QR_WIDTH, hst);//���ɶ�ά�������Ϣ
        int width = matrix.getWidth();//����߶�
        int height = matrix.getHeight();//������
        int halfW = width / 2;
        int halfH = height / 2;
        int[] pixels = new int[width * height];//�������鳤��Ϊ����߶�*�����ȣ����ڼ�¼������������Ϣ
        for (int y = 0; y < height; y++) {//���п�ʼ��������
            for (int x = 0; x < width; x++) {//������
                if (x > halfW - IMAGE_HALFWIDTH && x < halfW + IMAGE_HALFWIDTH
                        && y > halfH - IMAGE_HALFWIDTH
                        && y < halfH + IMAGE_HALFWIDTH) {//��λ�����ڴ��ͼƬ��Ϣ
//��¼ͼƬÿ��������Ϣ
                    pixels[y * width + x] = mBitmap.getPixel(x - halfW
                            + IMAGE_HALFWIDTH, y - halfH + IMAGE_HALFWIDTH);              } else {
                    if (matrix.get(x, y)) {//����кڿ�㣬��¼��Ϣ
                        pixels[y * width + x] = 0xff000000;//��¼�ڿ���Ϣ
                    }
                }
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        // ͨ��������������bitmap
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    /*
    Bitmap encodeAsBitmap(String str){
        Bitmap bitmap = null;
        BitMatrix result = null;
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            result = multiFormatWriter.encode(str, BarcodeFormat.QR_CODE, 200, 200);
            // ʹ�� ZXing Android Embedded Ҫд�Ĵ���
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            bitmap = barcodeEncoder.createBitmap(result);
        } catch (WriterException e){
            e.printStackTrace();
        } catch (IllegalArgumentException iae){ // ?
            return null;
        }

        // �����ʹ�� ZXing Android Embedded �Ļ���Ҫд�Ĵ���

//        int w = result.getWidth();
//        int h = result.getHeight();
//        int[] pixels = new int[w * h];
//        for (int y = 0; y < h; y++) {
//            int offset = y * w;
//            for (int x = 0; x < w; x++) {
//                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
//            }
//        }
//        bitmap = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);
//        bitmap.setPixels(pixels,0,100,0,0,w,h);

        return bitmap;
    }*/
}

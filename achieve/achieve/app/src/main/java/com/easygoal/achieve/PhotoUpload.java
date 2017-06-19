package com.easygoal.achieve;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Acer on 2017/4/29.
 */

public class PhotoUpload extends Activity {
    private String newName ="image.jpg";
    private String uploadFile ="/sdcard/image.JPG";
    private String actionUrl ="http://192.168.0.71:8086/HelloWord/myForm";
    /*
    private TextView mText1;
    private TextView mText2;
    private Button mButton;
    */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            /*
            setContentView(R.layout.photo_upload);
            mText1 = (TextView) findViewById(R.id.myText2);
            //"�ļ�·����\n"+
            mText1.setText(uploadFile);
            mText2 = (TextView) findViewById(R.id.myText3);
            //"�ϴ���ַ��\n"+
            mText2.setText(actionUrl);
        //����mButton��onClick�¼�����
            mButton = (Button) findViewById(R.id.myButton);
            mButton.setOnClickListener(new View.OnClickListener()
            {
                publicvoid onClick(View v)
                {
                    uploadFile();
                }
            });
        }*/
        /* �ϴ��ļ���Server�ķ��� */
    }

    private void uploadFile(String imgUrl,String uploadUrl)
    {
        String end ="\r\n";
        String twoHyphens ="--";
        String boundary ="*****";
        try
        {
            URL url =new URL( uploadUrl);
            HttpURLConnection con=(HttpURLConnection)url.openConnection();
          /* ����Input��Output����ʹ��Cache */
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setUseCaches(false);
          /* ���ô��͵�method=POST */
            con.setRequestMethod("POST");
          /* setRequestProperty */
            con.setRequestProperty("Connection", "Keep-Alive");
            con.setRequestProperty("Charset", "UTF-8");
            con.setRequestProperty("Content-Type",
                    "multipart/form-data;boundary="+boundary);
          /* ����DataOutputStream */
            DataOutputStream ds =
                    new DataOutputStream(con.getOutputStream());
            ds.writeBytes(twoHyphens + boundary + end);
            ds.writeBytes("Content-Disposition: form-data; "+
                    "name=\"file1\";filename=\""+
                    newName +"\""+ end);
            ds.writeBytes(end);
          /* ȡ���ļ���FileInputStream */
            FileInputStream fStream =new FileInputStream(imgUrl);
          /* ����ÿ��д��1024bytes */
            int bufferSize =1024;
            byte[] buffer =new byte[bufferSize];
            int length =-1;
          /* ���ļ���ȡ������������ */
            while((length = fStream.read(buffer)) !=-1)
            {
            /* ������д��DataOutputStream�� */
                ds.write(buffer, 0, length);
            }
            ds.writeBytes(end);
            ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
          /* close streams */
            fStream.close();
            ds.flush();
          /* ȡ��Response���� */
            InputStream is = con.getInputStream();
            int ch;
            StringBuffer b =new StringBuffer();
            while( ( ch = is.read() ) !=-1 )
            {
                b.append( (char)ch );
            }
          /* ��Response��ʾ��Dialog */
            showDialog("�ϴ��ɹ�"+b.toString().trim());
          /* �ر�DataOutputStream */
            ds.close();
        }
        catch(Exception e)
        {
            showDialog("�ϴ�ʧ��"+e);
        }
    }
    /* ��ʾDialog��method */
    private void showDialog(String mess)
    {
        new AlertDialog.Builder(PhotoUpload.this).setTitle("Message")
                .setMessage(mess)
                .setNegativeButton("ȷ��",new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                    }
                })
                .show();
    }
}

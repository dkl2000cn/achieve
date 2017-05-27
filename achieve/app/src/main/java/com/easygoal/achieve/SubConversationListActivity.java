package com.easygoal.achieve;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Acer on 2017/4/23.
 */

public class SubConversationListActivity extends FragmentActivity {
    //private static final String TAG =SingleChatActivity.class.getSimpleName();

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //Ψһ���õĴ��룬����һ�� layout
        setContentView(R.layout.subconversationlist);
        //�̳е���ActionBarActivity��ֱ�ӵ��� �Դ��� Actionbar��������Actionbar �����ã�������ÿɺ��ԡ�
        //getSupportActionBar().setTitle("����");
        //getSupportActionBar().setLogo(R.drawable.logo);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_black32px);
        getIntent().getData().getQueryParameter("targetId");//��ȡid
        getIntent().getData().getQueryParameter("title");//��ȡ��Ϣtitle
        TextView tv_conversation=(TextView)findViewById(R.id.tv_conversationlist);
        ImageButton btn_back=(ImageButton)findViewById(R.id.ibtn_back);
        ImageButton btn_share=(ImageButton)findViewById(R.id.ibtn_share);
        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shot();
            }
        });
        getIntent().getData().getQueryParameter("targetId");//��ȡid
        String title=getIntent().getData().getQueryParameter("title");
        if (!TextUtils.isEmpty(title)) {
            tv_conversation.setText(getIntent().getData().getQueryParameter("title"));//��ȡ��Ϣtitle
        }else{
            tv_conversation.setText(TaskData.usergroup);//��ȡ��Ϣtitle
        }
        btn_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                finish();
            }
        });
    }

    private void shot(){
        File sdFileDir= Environment.getExternalStorageDirectory();
        SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm");
        Date curDate = new Date(System.currentTimeMillis());//��ȡ��ǰʱ��
        String curTime1 = formatter.format(curDate);
        //File file1=new File(sdFileDir.getAbsoluteFile()+"/DCIM/"+"[Ŀ���]"+curTime1+".png");
        File file1=new File(sdFileDir.getAbsoluteFile()+"/DCIM/"+"[Ŀ���]"+curTime1+".png");
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
            screenshot.shoot(this,file1);
            Log.d("screenshot",file1.toString()+file1.getTotalSpace()+u.toString());
            intent.putExtra(Intent.EXTRA_STREAM, u);
            intent.putExtra(Intent.EXTRA_SUBJECT, "����");
            intent.putExtra(Intent.EXTRA_TEXT, "screen");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(Intent.createChooser(intent, "����"));
        } else{
            Log.d("screenshot",file1.toString()+"not exist");
            Toast.makeText(getApplicationContext(), "�ļ�������",Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

}
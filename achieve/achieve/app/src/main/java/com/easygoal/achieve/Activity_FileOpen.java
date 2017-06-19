package com.easygoal.achieve;



import java.io.File;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.view.ActionMode;
import android.support.v7.view.ActionMode.Callback;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

//import com.umeng.message.PushAgent;

public class Activity_FileOpen extends ListActivity implements AppCompatCallback {


	private ArrayList<String> items = null;
	private ArrayList<String> paths = null;
	private String rootPath = "/";
	private TextView mPath;
	private String clickedfile;
	Toolbar toolbar;
	private AppCompatDelegate delegate;
	long waitTime = 2000;
	long touchTime = 0;
	final String Tags="Activity_FileOpen";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//getWindow().requestFeature(Window.FEATURE_NO_TITLE);

		delegate = AppCompatDelegate.create(this, this);
		//we need to call the onCreate() of the AppCompatDelegate
		//delegate.requestWindowFeature(Window.FEATURE_NO_TITLE);
		delegate.onCreate(savedInstanceState);
		//we use the delegate to inflate the layout
		delegate.requestWindowFeature(Window.FEATURE_NO_TITLE);

		delegate.setContentView(R.layout.activity_fileopen);
		//PushAgent.getInstance(getApplicationContext()).onAppStart();


		//ActionBar actionBar = getActionBar();
		//actionBar.setDisplayHomeAsUpEnabled(true);
		//actionBar.setHomeAsUpIndicator(R.drawable.back_48px);

		toolbar = (Toolbar) findViewById(R.id.toolbar);
		delegate.setSupportActionBar(toolbar);
		//toolbar.setTitle("Title");
		//toolbar.setSubtitle("SubTitle");
		//toolbar.setLogo(R.drawable.ic_launcher);
		toolbar.setNavigationIcon(R.drawable.back_48px);
		//mDrawerLayout = (CustomDrawerLayout) findViewById(R.id.main_drawer);
		//Button mLvDrawer = (Button) findViewById(R.id.bt_drawer);
		delegate.getSupportActionBar().setDisplayHomeAsUpEnabled(true);


		toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener(){

			@Override
			public boolean onMenuItemClick(MenuItem menuItem) {
				// TODO Auto-generated method stub

				switch (menuItem.getItemId()) {
					case android.R.id.home:
						finish();
						Log.d(Tags, "home");
						break;


					default:break;
				}
				return false;
			}
		});

		mPath = (TextView)findViewById(R.id.mPath);
		getFileDir(rootPath);
		mPath.setTextColor(Color.RED);


	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_fileopen, menu);
		return true;
	}



	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(event.getAction() == KeyEvent.ACTION_DOWN && KeyEvent.KEYCODE_BACK == keyCode) {
			finish();

		}
		return super.onKeyDown(keyCode, event);
	}


	private void getFileDir(String filePath) {
		mPath.setText(filePath);

		items = new ArrayList<String>();
		paths = new ArrayList<String>();
		File file = new File(filePath);
		File[] files = file.listFiles();
		if(!filePath.equals(rootPath)) {
			items.add("返回根目录 " + rootPath);
			paths.add(rootPath);
			items.add("返回根目录 ../");
			paths.add(file.getParent());
		}
		for(File fileTemp :files) {
			if (fileTemp.canRead()){
				items.add(fileTemp.getName());
				paths.add(fileTemp.getPath());
			}
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(Activity_FileOpen.this,R.layout.file_now,items);
		setListAdapter(adapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		final File file = new File(paths.get(position));
		if(file.canRead()) {
			if(file.isDirectory()) {
				getFileDir(paths.get(position));
			}else {
				new AlertDialog.Builder(this)
						.setTitle("Message")
						.setMessage(file.getAbsolutePath())
						.setPositiveButton("ok", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								clickedfile=file.getAbsolutePath();
								Intent intent = new Intent();
								intent.putExtra("return", clickedfile);
								setResult(RESULT_OK, intent);
								finish();

							}
						}).show();

			}
		}else {
			new AlertDialog.Builder(this)
					.setTitle("Message")
					.setMessage("无访问权限")
					.setPositiveButton("ok", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {

						}
					}).show();

		}
	}

	@Override
	public void onSupportActionModeFinished(ActionMode arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSupportActionModeStarted(ActionMode arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public ActionMode onWindowStartingSupportActionMode(Callback arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub

		switch (item.getItemId()) {
			case android.R.id.home:
				finish();
				Log.d(Tags, "home");
				break;

			default:break;
		}
		return super.onOptionsItemSelected(item);
	}
}

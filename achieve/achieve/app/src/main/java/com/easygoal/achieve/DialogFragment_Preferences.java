package com.easygoal.achieve;

import java.util.ArrayList;
import java.util.List;

import com.easygoal.achieve.SlideSwitch.OnSwitchChangedListener;

import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class DialogFragment_Preferences extends DialogFragment {

	   SharedPreferences spp;
       Editor sppEditor;
       int model ;
	   int dataperiod;
	   int preweight;
	   int prehour;
	   int endhour;
	   int endmin;
	   boolean durationcheck;
	   boolean completionAnimation;
	   boolean completionSound;
	public DialogFragment_Preferences() {
		// TODO Auto-generated constructor stub
	  
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v=inflater.inflate(R.layout.leftmenu_preferences, container);
	    final SeekBar sb_preweight=(SeekBar)v.findViewById(R.id.sb_preweight);
	    final SeekBar sb_prehour=(SeekBar)v.findViewById(R.id.sb_prehour);
	    final TextView tv_preweight=(TextView)v.findViewById(R.id.tv_preweight);
	    final TextView tv_prehour=(TextView)v.findViewById(R.id.tv_prehour);
	    final Spinner spin_endhour=(Spinner)v.findViewById(R.id.spin_endhour);
	    final Spinner spin_endmin=(Spinner)v.findViewById(R.id.spin_endmin);
	    final RadioButton rb_model1=(RadioButton)v.findViewById(R.id.rb_model1);
	    final RadioButton rb_model2=(RadioButton)v.findViewById(R.id.rb_model2);
	    final RadioButton rb_period1=(RadioButton)v.findViewById(R.id.rb_period1);
	    final RadioButton rb_period2=(RadioButton)v.findViewById(R.id.rb_period2);
	    final RadioButton rb_period3=(RadioButton)v.findViewById(R.id.rb_period3);
	    final android.support.v7.widget.SwitchCompat cb_durationlimit=(android.support.v7.widget.SwitchCompat)v.findViewById(R.id.cb_durationlimit1);
	    final com.easygoal.achieve.SlideSwitch sw_durationcheck=(com.easygoal.achieve.SlideSwitch)v.findViewById(R.id.sw_durationlimit);
		final android.support.v7.widget.SwitchCompat sw_completionAnimation=(android.support.v7.widget.SwitchCompat)v.findViewById(R.id.sw_completionAnimation);
		final android.support.v7.widget.SwitchCompat sw_completionSound=(android.support.v7.widget.SwitchCompat)v.findViewById(R.id.sw_completionSound);

		Button btn_closewin = (Button) v.findViewById(R.id.btn_closewin);
		 
		 final List<InputValueSet> list_endhour=new ArrayList<InputValueSet>();
		 for (int i=0;i<=23;i++){
           list_endhour.add(new InputValueSet(i,""+i));
		 }  
		 // list_imp.add(new InputValueSet(4,"至关重要"));
		  mArrayAdapter adapt_spin_endhour = new mArrayAdapter(getActivity(), R.layout.spinner_duration, list_endhour);
		 // adap_spin4_taskimportance.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		  spin_endhour.setAdapter(adapt_spin_endhour); 
		  
		 final List<InputValueSet> list_endmin=new ArrayList<InputValueSet>();
		 for (int j=0;j<=60;j++){
           list_endmin.add(new InputValueSet(j,""+j));
		 }  
		 
		  mArrayAdapter adapt_spin_endmin = new mArrayAdapter(getActivity(), R.layout.spinner_duration, list_endmin);
		 // adap_spin4_taskimportance.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	      spin_endmin.setAdapter(adapt_spin_endmin); 

			final SharedPreferences spp=getActivity().getSharedPreferences("userinfo",Context.MODE_PRIVATE);
		    final Editor sppEditor = spp.edit();
			if (spp!=null){
				TaskData.usermodel = spp.getInt("model", 0);
				TaskData.dataperiod = spp.getInt("dataperiod", 1);
				TaskData.preweight = spp.getInt("preweight", 100);
				TaskData.prehour = spp.getInt("prehour", 8);
				TaskData.endhour = spp.getInt("endhour", 23);
				TaskData.endmin = spp.getInt("endmin", 59);
		        model = spp.getInt("model", 0);
			    dataperiod = spp.getInt("dataperiod", 1);
			    preweight = spp.getInt("preweight", 100);
			    prehour = spp.getInt("prehour", 8);
			    endhour=spp.getInt("endhour", 23);
			    endmin=spp.getInt("endmin", 59);
			    durationcheck=spp.getBoolean("durationcheck",false);
				completionAnimation=spp.getBoolean("completionAnimation",true);
				completionSound=spp.getBoolean("completionSound",true);
			    spin_endhour.setSelection(endhour);
			    spin_endmin.setSelection(endmin);
			    //sw_durationcheck.setPressed(durationcheck);
			    cb_durationlimit.setChecked(durationcheck);
				sw_completionAnimation.setChecked(completionAnimation);
				sw_completionSound.setChecked(completionSound);
			}else{
		      
		       sppEditor.putInt("model",0);
			   sppEditor.putInt("preweight",100);
			   sppEditor.putInt("dataperiod", 1);
			   sppEditor.putInt("prehour", 8);
			   sppEditor.putInt("endhour", 23);
			   sppEditor.putInt("endmin", 59);
			   sppEditor.putBoolean("durationcheck",false);
				sppEditor.putBoolean("completionAnimation",true);
				sppEditor.putBoolean("completionSound",true);
			   sppEditor.commit();
		       TaskData.usermodel = 0;
			   TaskData.dataperiod = 1;
	           TaskData.preweight = 100;
	           TaskData.prehour = 8;
	           TaskData.endhour = 23;
			   TaskData.endmin = 59;
			   TaskData.durationcheck=true;
				TaskData.completionAnimation=true;
				TaskData.completionSound=true;
			   spin_endhour.setSelection(23);
			   spin_endmin.setSelection(59);
			   //sw_durationcheck.setPressed(true);
			   cb_durationlimit.setChecked(durationcheck);
				sw_completionAnimation.setChecked(completionAnimation);
				sw_completionSound.setChecked(completionSound);
		    }
		    
		    /*
			final Editor sppEditor = spp.edit();
		    model = spp.getInt("model", 0);
		    dataperiod = spp.getInt("dataperiod", 1);
		    preweight = spp.getInt("preweight", 80);
		    
		    TaskData.usermodel = spp.getInt("model", 0);
		    TaskData.dataperiod = spp.getInt("dataperiod", 1);
		    TaskData.preweight = spp.getInt("preweight", 80);
			//Log.d("alarm value", "1"+v1+" 2"+v2+" 3"+v3+" v4"+v4);
			*/
			 
		    //cb1.setChecked(spp.getBoolean("alarmclock", false));
		    //Toast.makeText(getActivity(),""+model+preweight+dataperiod, Toast.LENGTH_SHORT).show();	
			
	    btn_closewin.setOnClickListener(new OnClickListener() {  
	  
	            @Override  
	            public void onClick(View arg0) {  
	                // 关闭对话框  
	                dismiss();  
	            }  
	        });  
	    
	    Button btn_cancel = (Button) v.findViewById(R.id.btn_cancel);  
		 
	    btn_cancel.setOnClickListener(new OnClickListener() {  
	  
	            @Override  
	            public void onClick(View arg0) {  
	                // 关闭对话框  
	                dismiss();  
	            }  
	        }); 
	    
	    tv_preweight.setText(String.valueOf(preweight));
	    sb_preweight.setProgress(preweight);
	    tv_prehour.setText(String.valueOf(prehour));
	    sb_prehour.setProgress(prehour);
	    switch (model){
	    	case 0:	rb_model1.setChecked(true);
                    rb_model2.setChecked(false);
	    		    break;
	    	case 1:	rb_model1.setChecked(false);
                    rb_model2.setChecked(true);
	    		     break;
	        default:break;		     
	    }
	    switch (dataperiod){
    	    case 1:	rb_period1.setChecked(true);
    	            rb_period2.setChecked(false);
    	            rb_period3.setChecked(false);
    	    	   break;
    	    case 2:
    	    	rb_period1.setChecked(false);
	            rb_period2.setChecked(true);
	            rb_period3.setChecked(false);
    	    	   break;
    	    case 3:
    	    	rb_period1.setChecked(false);
	            rb_period2.setChecked(false);
	            rb_period3.setChecked(true);
    	    	   break;
    	    default:break;	   
        }
		OnCheckedChangeListener rblisterner_model = new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked){
					if (buttonView.getText().toString().equals("标准模式")){
						model= 1;
						
					}
					if (buttonView.getText().toString().equals("简易模式")){
						model= 0;
						durationcheck=false;
					}
					
				}
				
			}	
    		
    	};
    	
    	
    	OnCheckedChangeListener rblisterner_period = new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked){
					
					if (buttonView.getText().toString().equals("3个月")){
						dataperiod= 1;
					}
					if (buttonView.getText().toString().equals("1年")){
						dataperiod= 2;
					}
					if (buttonView.getText().toString().equals("全部")){
						dataperiod= 3;
					}
				}
				
			}	
    		
    	};
    	
    	rb_model1.setOnCheckedChangeListener(rblisterner_model);
    	rb_model2.setOnCheckedChangeListener(rblisterner_model);
    	rb_period1.setOnCheckedChangeListener(rblisterner_period);
    	rb_period2.setOnCheckedChangeListener(rblisterner_period);
    	rb_period3.setOnCheckedChangeListener(rblisterner_period);
    	
    	OnSwitchChangedListener swlistener_durationcheck = new SlideSwitch.OnSwitchChangedListener(){

			@Override
			public void onSwitchChanged(SlideSwitch obj, int status) {
				// TODO Auto-generated method stub
				switch (status) {
				case 1:durationcheck=true;
					   Toast.makeText(getActivity(),"滑块状态"+status, Toast.LENGTH_SHORT).show();
					   break;
				case 0:durationcheck=false;
					   Toast.makeText(getActivity(),"滑块状态"+status, Toast.LENGTH_SHORT).show();
					   break;
				default:Toast.makeText(getActivity(),"滑块状态"+status, Toast.LENGTH_SHORT).show();
					   break;	   
					
				}
			}


    	};

		OnSwitchChangedListener swlistener_completionAnimation = new SlideSwitch.OnSwitchChangedListener(){

			@Override
			public void onSwitchChanged(SlideSwitch obj, int status) {
				// TODO Auto-generated method stub
				switch (status) {
					case 1:completionAnimation=true;
						Toast.makeText(getActivity(),"滑块状态"+status, Toast.LENGTH_SHORT).show();
						break;
					case 0:completionAnimation=false;
						Toast.makeText(getActivity(),"滑块状态"+status, Toast.LENGTH_SHORT).show();
						break;
					default:Toast.makeText(getActivity(),"滑块状态"+status, Toast.LENGTH_SHORT).show();
						break;

				}
			}


		};

		OnSwitchChangedListener swlistener_completionSound = new SlideSwitch.OnSwitchChangedListener(){

			@Override
			public void onSwitchChanged(SlideSwitch obj, int status) {
				// TODO Auto-generated method stub
				switch (status) {
					case 1:completionSound =true;
						Toast.makeText(getActivity(),"滑块状态"+status, Toast.LENGTH_SHORT).show();
						break;
					case 0:completionSound =false;
						Toast.makeText(getActivity(),"滑块状态"+status, Toast.LENGTH_SHORT).show();
						break;
					default:Toast.makeText(getActivity(),"滑块状态"+status, Toast.LENGTH_SHORT).show();
						break;

				}
			}


		};

    	  sw_durationcheck.setOnSwitchChangedListener(swlistener_durationcheck);
    	  sb_preweight.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){

  			@Override
  			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
  				// TODO Auto-generated method stub
  				
  				 preweight= progress;
  				 tv_preweight.setText(String.valueOf(preweight));
  				
  			}

  			@Override
  			public void onStartTrackingTouch(SeekBar seekBar) {
  				// TODO Auto-generated method stub
  				
  			}

  			@Override
  			public void onStopTrackingTouch(SeekBar seekBar) {
  				// TODO Auto-generated method stub
  				
  			}
  			  	  
  		  });
    	  
    	  sb_prehour.setMax(24);
    	  
    	  sb_prehour.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){

    			@Override
    			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
    				// TODO Auto-generated method stub
    				
    				 prehour= progress;
    				 tv_prehour.setText(String.valueOf(prehour));
    				
    			}

    			@Override
    			public void onStartTrackingTouch(SeekBar seekBar) {
    				// TODO Auto-generated method stub
    				
    			}

    			@Override
    			public void onStopTrackingTouch(SeekBar seekBar) {
    				// TODO Auto-generated method stub
    				
    			}
    			  	  
    		  });
    	  
	    Button btn_confirm = (Button) v.findViewById(R.id.btn_confirm);  
		 
	    btn_confirm.setOnClickListener(new OnClickListener() {  
	  
	            @Override  
	            public void onClick(View arg0) {  
	                // 关闭对话框 
	              /*
	            	AppData alarmsetting =((AppData)getActivity().getApplicationContext());;
	              // alarmsetting=new AlarmSettingBean();	
	               alarmsetting.setAlarmclock(cb1.isChecked());
	               alarmsetting.setVibration(cb2.isChecked());
	               alarmsetting.setAlarmRing(cb3.isChecked());
	               alarmsetting.setNotification(cb4.isChecked());
	              */
	            //  SharedPreferences sp=getActivity().getSharedPreferences("userinfo",Context.MODE_PRIVATE);
	            	 durationcheck= cb_durationlimit.isChecked();
					 completionAnimation=sw_completionAnimation.isChecked();
					 completionSound=sw_completionSound.isChecked();
	                 sppEditor.putInt("model", model);
					 sppEditor.putInt("preweight", preweight);
					 sppEditor.putInt("dataperiod", dataperiod);
					 sppEditor.putInt("prehour", prehour);  
					 sppEditor.putInt("endhour", spin_endhour.getSelectedItemPosition());
					 sppEditor.putInt("endmin", spin_endmin.getSelectedItemPosition());
					 sppEditor.putBoolean("durationcheck", durationcheck);
					 sppEditor.putBoolean("completionAnimation",completionAnimation);
					 sppEditor.putBoolean("completionSound", completionSound);
					 sppEditor.commit();
					 TaskData.usermodel = model;
					 TaskData.dataperiod = dataperiod;
					 TaskData.preweight =preweight;
					 TaskData.prehour =prehour;
					 TaskData.endhour =spin_endhour.getSelectedItemPosition();
					 TaskData.endmin =spin_endmin.getSelectedItemPosition();
					 TaskData.durationcheck =durationcheck;
					 TaskData.completionAnimation=completionAnimation;
					 TaskData.completionSound=completionSound;
	            //   String totalAlarmSetting = ""+cb1.isChecked()+
	            //		   cb3.isChecked()+cb2.isChecked()+(cb4.isChecked());
	               Toast.makeText(getActivity(),"用户设置成功", Toast.LENGTH_SHORT).show();
	               dismiss();
	            }  
	        });  
 		return v;	
    }
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		DisplayMetrics dm = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics( dm );
		getDialog().getWindow().setLayout( dm.widthPixels, getDialog().getWindow().getAttributes().height );
	}  
}

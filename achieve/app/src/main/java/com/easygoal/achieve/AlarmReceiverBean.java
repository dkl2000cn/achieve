package com.easygoal.achieve;

import org.litepal.crud.DataSupport;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;

public class AlarmReceiverBean  extends DataSupport{
   
	private String sourceid;
	private String no;
	private PendingIntent pi;
	private BroadcastReceiver breceiver;
	
	public AlarmReceiverBean() {
		// TODO Auto-generated constructor stub
		
	}

	public String getSourceid() {
		return sourceid;
	}

	public void setSourceid(String sourceid) {
		this.sourceid = sourceid;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public PendingIntent getPi() {
		return pi;
	}

	public void setPi(PendingIntent pi) {
		this.pi = pi;
	}

	public BroadcastReceiver getBreceiver() {
		return breceiver;
	}

	public void setBreceiver(BroadcastReceiver breceiver) {
		this.breceiver = breceiver;
	}


	
}

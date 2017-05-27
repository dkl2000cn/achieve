package com.easygoal.achieve;


import org.litepal.crud.DataSupport;

public class Reminder extends DataSupport{
	private String sourceId;
	private String sn;
	private String username;
	private String name;
	private String content;
	private String createdtime;
	private String deadlinetime;
	private String advance;
	private String frequency;
	private String alarminterval;
	private String piaction;

	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreatedtime() {
		return createdtime;
	}
	public void setCreatedtime(String createdtime) {
		this.createdtime = createdtime;
	}
	
	public String getDeadlinetime() {
		return deadlinetime;
	}
	public void setDeadlinetime(String deadlinetime) {
		this.deadlinetime = deadlinetime;
	}
	
	public String getAdvance() {
		return advance;
	}
	public void setAdvance(String advance) {
		this.advance = advance;
	}
	public String getFrequency() {
		return frequency;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	
	public String getSourceId() {
		return sourceId;
	}
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	
	public String getPiaction() {
		return piaction;
	}
	public void setPiaction(String piaction) {
		this.piaction = piaction;
	}
	public String getAlarminterval() {
		return alarminterval;
	}
	public void setAlarminterval(String alarminterval) {
		this.alarminterval = alarminterval;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}

}

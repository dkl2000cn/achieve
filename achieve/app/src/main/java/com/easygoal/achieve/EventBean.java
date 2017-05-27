package com.easygoal.achieve;

public class EventBean {
	private int Id;
	private String sourceId;
	private String name;
	private String content;
	private String type;
	private String createdtime;
	private String lastmoidified;
	private String startedtime;
	private String deadlinetime;
	private String remainingtime;
	private String advance;
	private String frequency;
	private String password;
	
	public EventBean() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCreatedtime() {
		return createdtime;
	}

	public void setCreatedtime(String createdtime) {
		this.createdtime = createdtime;
	}

	public String getLastmoidified() {
		return lastmoidified;
	}

	public void setLastmoidified(String lastmoidified) {
		this.lastmoidified = lastmoidified;
	}

	public String getStartedtime() {
		return startedtime;
	}

	public void setStartedtime(String startedtime) {
		this.startedtime = startedtime;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRemainingtime() {
		//double time1 = new Date().getTime()/1000;
		//double time2 = TimeData.changeStrToTime_YYYY(deadlinetime)*60;	
		
		//TimeData.convertTime_YYYYTIMEtoYYTIME(deadlinetime);
		
		return remainingtime;
	}

	public void setRemainingtime(String timegap) {
		// TODO Auto-generated method stub
		this.remainingtime = timegap;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	
}

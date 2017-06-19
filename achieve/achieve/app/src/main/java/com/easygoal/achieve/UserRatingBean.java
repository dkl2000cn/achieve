package com.easygoal.achieve;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.litepal.crud.DataSupport;

import android.widget.ImageView;

public class UserRatingBean extends DataSupport {
	private int userId;
	private int sn;
	private String username;
	private int satisfaction;
	private int problemsolving;
	private int userconvenience;
	private int uicomfort;
	private String ratingTime;
	private long ratingTimeData;
	private int getUserId() {
		return userId;
	}
	public int getSatisfaction() {
		return satisfaction;
	}
	public void setSatisfaction(int satisfaction) {
		this.satisfaction = satisfaction;
	}
	

	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getProblemsolving() {
		return problemsolving;
	}
	public void setProblemsolving(int problemsolving) {
		this.problemsolving = problemsolving;
	}
	public int getUserconvenience() {
		return userconvenience;
	}
	public void setUserconvenience(int userconvenience) {
		this.userconvenience = userconvenience;
	}
	public int getUicomfort() {
		return uicomfort;
	}
	public void setUicomfort(int uicomfort) {
		this.uicomfort = uicomfort;
	}
	public String getRatingTime() {
		return ratingTime;
	}
	public void setRatingTime(String ratingTime) {
		this.ratingTime = ratingTime;
	}
	public long getRatingTimeData() {
		return ratingTimeData;
	}
	public void setRatingTimeData(long ratingTimeData) {
		this.ratingTimeData = ratingTimeData;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String string) {
		this.username = string;
	}
	public int getSn() {
		return sn;
	}
	public void setSn(int sn) {
		this.sn = sn;
	}
	
	
}

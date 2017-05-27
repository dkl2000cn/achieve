package com.easygoal.achieve;

import org.litepal.crud.DataSupport;

public class Memo  extends DataSupport{

	private String name;
	private String sn;
	private String username;
	private String content;
	private String picUriStr;
	private byte[] pic;
	private String fileUriStr;
	private byte[] file;
	private String location;
	private String createdtime;
	private String deadlinetime;

	public Memo() {
		// TODO Auto-generated constructor stub

	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
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


	public String getCreatedtime() {
		return createdtime;
	}

	public void setCreatedtime(String createdtime) {
		this.createdtime = createdtime;
	}

	public byte[] getPic() {
		return pic;
	}

	public void setPic(byte[] pic) {
		this.pic = pic;
	}

	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}

	public String getPicUriStr() {
		return picUriStr;
	}

	public void setPicUriStr(String picUriStr) {
		this.picUriStr = picUriStr;
	}

	public String getFileUriStr() {
		return fileUriStr;
	}

	public void setFileUriStr(String fileUriStr) {
		this.fileUriStr = fileUriStr;
	}

	public String getDeadlinetime() {
		return deadlinetime;
	}

	public void setDeadlinetime(String deadlinetime) {
		this.deadlinetime = deadlinetime;
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

package com.easygoal.achieve;

public class MemoBean {
	private int Id;
	private String name;
	private String content;
	private String type;
	private String createdtime;
	private String lastmoidified;
	private String password;
	
	public MemoBean() {
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}

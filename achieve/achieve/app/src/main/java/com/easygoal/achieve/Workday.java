package com.easygoal.achieve;


import java.util.Date;
import java.util.List;

import org.litepal.crud.DataSupport;

class Workday  extends DataSupport{
	private Date workday;
	private String content;
	private List todolist;
	
	public Date getWorkday() {
		return workday;
	}
	public void setWorkday(Date workday) {
		this.workday = workday;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public List getTodolist() {
		return todolist;
	}
	public void setTodolist(List todolist) {
		this.todolist = todolist;
	}
	
	
}

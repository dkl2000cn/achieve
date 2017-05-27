package com.easygoal.achieve;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.litepal.crud.DataSupport;

import android.widget.ImageView;

public class UserBean extends DataSupport implements Iterable<Object>{
	private int userId;
	private String username;
	private String sn;
	private String password;
	private String nickname;
	private String gender;
	private String organization;
	private String title;
	private String email;
	private ImageView headpic;
	private String phoneno;
	private String usertype;
	private String userprivilege;
	private List usergroup;
	private String usergrade;
	private int usercredit;
	private Timestamp registeredtime;
	private Timestamp createdtime;
	private Timestamp lastmoidified;
	private Timestamp lastlogintime;
	private Timestamp lastvalidtime;
	private int logintimes;
	
	public UserBean() {
		// TODO Auto-generated constructor stub
	}


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public ImageView getHeadpic() {
		return headpic;
	}

	public void setHeadpic(ImageView headpic) {
		this.headpic = headpic;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	public List getUsergroup() {
		return usergroup;
	}

	public void setUsergroup(List usergroup) {
		this.usergroup = usergroup;
	}

	
	public int getUsercredit() {
		return usercredit;
	}

	public void setUsercredit(int usercredit) {
		this.usercredit = usercredit;
	}

	public String getUsergrade() {
		return usergrade;
	}

	public void setUsergrade(String usergrade) {
		this.usergrade = usergrade;
	}

	public Timestamp getRegisteredtime() {
		return registeredtime;
	}

	public void setRegisteredtime(Timestamp registeredtime) {
		this.registeredtime = registeredtime;
	}

	public Timestamp getLastmoidified() {
		return lastmoidified;
	}

	public void setLastmoidified(Timestamp lastmoidified) {
		this.lastmoidified = lastmoidified;
	}

	public int getLogintimes() {
		return logintimes;
	}

	public void setLogintimes(int logintimes) {
		this.logintimes = logintimes;
	}

	public Timestamp getLastlogintime() {
		return lastlogintime;
	}

	public void setLastlogintime(Timestamp lastlogintime) {
		this.lastlogintime = lastlogintime;
	}



	public int getUserId() {
		return userId;
	}



	public void setUserId(int userId) {
		this.userId = userId;
	}


	public Timestamp getCreatedtime() {
		return createdtime;
	}


	public void setCreatedtime(Timestamp createdtime) {
		this.createdtime = createdtime;
	}


	public String getPhoneno() {
		return phoneno;
	}


	public void setPhoneno(String phoneno) {
		this.phoneno = phoneno;
	}


	@Override
	public Iterator<Object> iterator() {
		// TODO Auto-generated method stub
		return null;
	}


	public String getSn() {
		return sn;
	}


	public void setSn(String sn) {
		this.sn = sn;
	}


	public String getOrganization() {
		return organization;
	}


	public void setOrganization(String organization) {
		this.organization = organization;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public Timestamp getLastvalidtime() {
		return lastvalidtime;
	}


	public void setLastvalidtime(Timestamp lastvalidtime) {
		this.lastvalidtime = lastvalidtime;
	}


	public String getUserprivilege() {
		return userprivilege;
	}


	public void setUserprivilege(String userprivilege) {
		this.userprivilege = userprivilege;
	}

}

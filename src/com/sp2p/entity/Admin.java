package com.sp2p.entity;

import java.io.Serializable;
import java.util.Date;

public class Admin implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private	String userName;
	private String password;
	private int enable ;
	private Date lastTime;
	private String lastIP;
	private long roleId;
	private int loginErrorCount;
	private int isLoginLimit;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public int getLoginErrorCount() {
		return loginErrorCount;
	}
	public void setLoginErrorCount(int loginErrorCount) {
		this.loginErrorCount = loginErrorCount;
	}
	public int getIsLoginLimit() {
		return isLoginLimit;
	}
	public void setIsLoginLimit(int isLoginLimit) {
		this.isLoginLimit = isLoginLimit;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getEnable() {
		return enable;
	}
	public void setEnable(int enable) {
		this.enable = enable;
	}
	public Date getLastTime() {
		return lastTime;
	}
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}
	public String getLastIP() {
		return lastIP;
	}
	public void setLastIP(String lastIP) {
		this.lastIP = lastIP;
	}
	public long getRoleId() {
		return roleId;
	}
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}
	
	
	
}

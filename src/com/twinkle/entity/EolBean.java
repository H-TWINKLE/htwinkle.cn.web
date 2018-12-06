package com.twinkle.entity;

import java.util.Date;

public class EolBean {

	private Datas datas;
	private int status;
	private String sessionid;

	public void setDatas(Datas datas) {
		this.datas = datas;
	}

	public Datas getDatas() {
		return datas;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getStatus() {
		return status;
	}

	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}

	public String getSessionid() {
		return sessionid;
	}

	public static class User {

		private int id;
		private String username;
		private String realname;

		public void setId(int id) {
			this.id = id;
		}

		public int getId() {
			return id;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getUsername() {
			return username;
		}

		public void setRealname(String realname) {
			this.realname = realname;
		}

		public String getRealname() {
			return realname;
		}

	}

	public static class Userinfo {

		private Date lastLoginDate;
		private int loginTimes;
		private User user;
		private int totalOnlineTime;

		public void setLastLoginDate(Date lastLoginDate) {
			this.lastLoginDate = lastLoginDate;
		}

		public Date getLastLoginDate() {
			return lastLoginDate;
		}

		public void setLoginTimes(int loginTimes) {
			this.loginTimes = loginTimes;
		}

		public int getLoginTimes() {
			return loginTimes;
		}

		public void setUser(User user) {
			this.user = user;
		}

		public User getUser() {
			return user;
		}

		public void setTotalOnlineTime(int totalOnlineTime) {
			this.totalOnlineTime = totalOnlineTime;
		}

		public int getTotalOnlineTime() {
			return totalOnlineTime;
		}

	}

	public static class Datas {

		private int userrole;
		private Userinfo userinfo;

		public void setUserrole(int userrole) {
			this.userrole = userrole;
		}

		public int getUserrole() {
			return userrole;
		}

		public void setUserinfo(Userinfo userinfo) {
			this.userinfo = userinfo;
		}

		public Userinfo getUserinfo() {
			return userinfo;
		}

	}

}

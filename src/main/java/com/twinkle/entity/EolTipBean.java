package com.twinkle.entity;

import java.util.Date;
import java.util.List;

public class EolTipBean {

	private List<Datas> datas;
	private int status;
	private String sessionid;

	public void setDatas(List<Datas> datas) {
		this.datas = datas;
	}

	public List<Datas> getDatas() {
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

	public static class ReminderList1 {

		private Date pubTime;
		private int id;
		private String title;
		private boolean publishStatus;
		private int fullmark;
		private int lessId;
		private Date deadline;

		public void setPubTime(Date pubTime) {
			this.pubTime = pubTime;
		}

		public Date getPubTime() {
			return pubTime;
		}

		public void setId(int id) {
			this.id = id;
		}

		public int getId() {
			return id;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getTitle() {
			return title;
		}

		public void setPublishStatus(boolean publishStatus) {
			this.publishStatus = publishStatus;
		}

		public boolean getPublishStatus() {
			return publishStatus;
		}

		public void setFullmark(int fullmark) {
			this.fullmark = fullmark;
		}

		public int getFullmark() {
			return fullmark;
		}

		public void setLessId(int lessId) {
			this.lessId = lessId;
		}

		public int getLessId() {
			return lessId;
		}

		public void setDeadline(Date deadline) {
			this.deadline = deadline;
		}

		public Date getDeadline() {
			return deadline;
		}

	}

	public static class Datas {

		private List<ReminderList1> reminderList1;
		private int courseId;
		private String courseName;

		public void setReminderList1(List<ReminderList1> reminderList1) {
			this.reminderList1 = reminderList1;
		}

		public List<ReminderList1> getReminderList1() {
			return reminderList1;
		}

		public void setCourseId(int courseId) {
			this.courseId = courseId;
		}

		public int getCourseId() {
			return courseId;
		}

		public void setCourseName(String courseName) {
			this.courseName = courseName;
		}

		public String getCourseName() {
			return courseName;
		}

	}

}
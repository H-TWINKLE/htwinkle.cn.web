package com.twinkle.entity;

import java.util.List;

import com.twinkle.utils.Constant;

public class BmobPost {

	private String title;

	private User author;

	private String content;

	private Array pic;

	private Array topic;

	private String url;

	private String place;

	private String hot;

	public String getHot() {
		return hot;
	}

	public void setHot(String hot) {
		this.hot = hot;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	private Integer types;

	private String newsdate;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Array getPic() {
		return pic;
	}

	public void setPic(Array pic) {
		this.pic = pic;
	}

	public Array getTopic() {
		return topic;
	}

	public void setTopic(Array topic) {
		this.topic = topic;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getTypes() {
		return types;
	}

	public void setTypes(Integer types) {
		this.types = types;
	}

	public String getNewsdate() {
		return newsdate;
	}

	public void setNewsdate(String newsdate) {
		this.newsdate = newsdate;
	}

	public class User {

		public String __type = "Pointer";

		private String className;

		private String objectId = Constant.CDNUNEWS;

		public User() {
			super();
		}

		public User(String className, String objectId) {
			super();
			this.className = className;
			this.objectId = objectId;
		}

		public String getClassName() {
			return className;
		}

		public void setClassName(String className) {
			this.className = className;
		}

		public String getObjectId() {
			return objectId;
		}

		public void setObjectId(String objectId) {
			this.objectId = objectId;
		}

	}

	public class Array {

		public Array() {
			super();
		}

		public Array(List<String> objects) {
			super();
			this.objects = objects;
		}

		public String __op = "Add";

		private List<String> objects;

		public List<String> getObjects() {
			return objects;
		}

		public void setObjects(List<String> objects) {
			this.objects = objects;
		}

	}

}

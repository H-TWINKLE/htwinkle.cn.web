package cn.htwinkle.web.base.bmob;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class BaseBmob {

	private String __type;
	
	private String className;

	private Date updatedAt;
	private String objectId;
	private Date createdAt;

	{

		if ("user".equals(this.getClass().getSimpleName().toLowerCase())) {
			className = "_User";
		} else {
			className = this.getClass().getSimpleName().toLowerCase();
		}

	}

	@JSONField(name="__type")
	public String get__type() {
		return __type;
	}

	@JSONField(name="__type")
	public void set__type(String __type) {
		this.__type = __type;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

}

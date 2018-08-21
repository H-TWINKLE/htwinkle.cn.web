package com.twinkle.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseNetmusic<M extends BaseNetmusic<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}
	
	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setNetname(java.lang.String netname) {
		set("netname", netname);
		return (M)this;
	}
	
	public java.lang.String getNetname() {
		return getStr("netname");
	}

	public M setNetsinger(java.lang.String netsinger) {
		set("netsinger", netsinger);
		return (M)this;
	}
	
	public java.lang.String getNetsinger() {
		return getStr("netsinger");
	}

	public M setNetmusicurl(java.lang.String netmusicurl) {
		set("netmusicurl", netmusicurl);
		return (M)this;
	}
	
	public java.lang.String getNetmusicurl() {
		return getStr("netmusicurl");
	}

	public M setNetcommid(java.lang.String netcommid) {
		set("netcommid", netcommid);
		return (M)this;
	}
	
	public java.lang.String getNetcommid() {
		return getStr("netcommid");
	}

	public M setNetcomm(java.lang.String netcomm) {
		set("netcomm", netcomm);
		return (M)this;
	}
	
	public java.lang.String getNetcomm() {
		return getStr("netcomm");
	}

	public M setNettauthor(java.lang.String nettauthor) {
		set("nettauthor", nettauthor);
		return (M)this;
	}
	
	public java.lang.String getNettauthor() {
		return getStr("nettauthor");
	}

	public M setNetpic(java.lang.String netpic) {
		set("netpic", netpic);
		return (M)this;
	}
	
	public java.lang.String getNetpic() {
		return getStr("netpic");
	}

	public M setNettime(java.util.Date nettime) {
		set("nettime", nettime);
		return (M)this;
	}
	
	public java.util.Date getNettime() {
		return get("nettime");
	}

	public M setDate(java.util.Date date) {
		set("date", date);
		return (M)this;
	}
	
	public java.util.Date getDate() {
		return get("date");
	}

}

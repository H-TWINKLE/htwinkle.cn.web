package com.jfinal.service;


import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

public class IndexService {

	public Integer getGlobalCount() {

		Record r = Db.findFirst("select sum(num) 'num' from globalcount");

		if (r == null)
			return 10001;

		return r.getInt("num");

	}

}

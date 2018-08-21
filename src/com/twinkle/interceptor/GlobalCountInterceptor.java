package com.twinkle.interceptor;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.twinkle.common.model.Globalcount;
import com.twinkle.plugin.IpSpider;
import com.twinkle.utils.CommUtils;

public class GlobalCountInterceptor implements Interceptor {

	public static AtomicInteger globalCount = new AtomicInteger(getGlobalCount());

	@Override
	public void intercept(Invocation inv) {

		CommUtils.INSTANCE.addToExeCutorPool(() -> {
			countAdd(inv.getController());
		});

		inv.invoke();

	}

	private void countAdd(Controller c) {

		globalCount.getAndIncrement();

		String ip = CommUtils.INSTANCE.getRemoteAddrIp(c);

		if (ip == null || "".equals(ip))
			return;

		String place = getPlace(ip);

		Globalcount global = Globalcount.dao.findFirst("select * from globalcount where ip = ? ", ip);

		if (global == null) {
			global = new Globalcount().setDates(new Date(System.currentTimeMillis())).setIp(ip).setNum(1)
					.setPlace(place);
			global.save();
		} else {
			global.setNum(global.getNum() + 1).setIp(ip).setPlace(place).setDates(new Date(System.currentTimeMillis()));
			global.update();
		}

	}

	private String getPlace(String ip) {
		IpSpider spider = new IpSpider();

		String place = spider.getIpByChinaZ(ip);

		if (!"".equals(place))
			return place;

		place = spider.getIpByIp(ip);

		return place;

	}

	private static Integer getGlobalCount() {

		Record r = Db.findFirst("select sum(num) 'num' from globalcount");

		return r == null ? 10001 : r.getInt("num");

	}

}

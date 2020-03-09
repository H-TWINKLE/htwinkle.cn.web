package com.twinkle.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.twinkle.common.config.MainConfig;
import com.twinkle.common.model.Globalcount;
import com.twinkle.plugin.IpSpiderPlugin;
import com.twinkle.utils.CommUtils;

import java.util.Date;

public class GlobalCountInterceptor implements Interceptor {

	@Override
	public void intercept(Invocation inv) {
		CommUtils.INSTANCE.addToExeCutorPool(() -> countAdd(inv.getController()));
		inv.invoke();
	}

	private void countAdd(Controller c) {

		MainConfig.GLOBAL_COUNT.getAndIncrement();

		String ip = CommUtils.INSTANCE.getRemoteAddr(c);

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
		IpSpiderPlugin spider = new IpSpiderPlugin();

		String place = spider.getIpByXpcha(ip);

		if (!"".equals(place))
			return place;

		place = spider.getIpByIp(ip);

		if (!"".equals(place))
			return place;

        place = spider.getIpByChinaZ(ip);
		return place;

	}

}

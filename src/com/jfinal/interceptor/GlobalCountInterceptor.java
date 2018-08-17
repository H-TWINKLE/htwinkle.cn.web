package com.jfinal.interceptor;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.common.model.Globalcount;
import com.jfinal.core.Controller;
import com.jfinal.plugin.cron4j.ITask;
import com.jfinal.service.IndexService;
import com.twinkle.utils.ExeCutorPool;

public class GlobalCountInterceptor implements Interceptor {

	public static AtomicInteger globalCount = new AtomicInteger(new IndexService().getGlobalCount());

	@Override
	public void intercept(Invocation inv) {

		globalCount.getAndIncrement();

		String ip = getRemoteAddr(inv.getController());

		ExeCutorPool.INSTANCE.AddToExeCutorPool(new toAddCount(ip));

		inv.invoke();

	}

	private String getRemoteAddr(Controller c) {
		if (c.getHeader("x-forwarded-for") == null) {

			return c.getRequest().getRemoteAddr();
		}
		return c.getRequest().getHeader("x-forwarded-for");
	}

	private class toAddCount implements ITask {

		private String ip;

		private toAddCount(String ip) {
			this.ip = ip;

		}

		@Override
		public void run() {

			if (ip == null)
				return;

			Globalcount c = Globalcount.dao.findFirst("select * from globalcount where ip = ? ", ip);

			if (c == null) {
				c = new Globalcount();
				c.setDates(new Date(System.currentTimeMillis()));
				c.setIp(ip);
				c.setNum(1);
				c.save();
			} else {
				c.setNum(c.getNum() + 1);
				c.update();
			}

		}

		@Override
		public void stop() {
			// TODO 自动生成的方法存根

		}

	}

}

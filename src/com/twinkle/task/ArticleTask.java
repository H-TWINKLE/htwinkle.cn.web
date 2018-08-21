package com.twinkle.task;

import com.jfinal.plugin.cron4j.ITask;
import com.twinkle.proxy.ArticleProxy;

public class ArticleTask implements ITask {
		 

	@Override
	public void run() {
		new ArticleProxy().getArtHtml();

	}

	@Override
	public void stop() {
		// TODO 自动生成的方法存根

	}

}

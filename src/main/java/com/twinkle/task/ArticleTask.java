package com.twinkle.task;

import com.jfinal.plugin.cron4j.ITask;
import com.twinkle.proxy.ArticleProxy;

public class ArticleTask implements ITask {

	@Override
	public void run() {

		getListArticle();

	}

	@Override
	public void stop() {
		// TODO 自动生成的方法存根

	}

	private void getListArticle() {

		try {
			new ArticleProxy().getArtHtml();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

package com.twinkle.task;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.cron4j.ITask;
import com.twinkle.proxy.ImgProxy;

public class ImgTask implements ITask {

	@Override
	public void run() {

		getListImg();
	}

	@Override
	public void stop() {
		// TODO 自动生成的方法存根

	}

	private void getListImg() {

		deleteObsoluteImg();

		try {
			String[] str = new String[] { "fengjing", "keai", "jianzhu", "zhiwu", "dongwu" }; // 风景，可爱，建筑，植物，动物

			ImgProxy i = new ImgProxy();

			for (String s : str) {
				i.get3GBiZhiImg(s);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void deleteObsoluteImg() {

		Db.update("DELETE FROM Img WHERE to_days(`date`)!=to_days(now());");

	}

}

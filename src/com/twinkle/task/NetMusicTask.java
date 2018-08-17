package com.twinkle.task;

import com.jfinal.plugin.cron4j.ITask;
import com.jfinal.service.NetMusicService;

public class NetMusicTask implements ITask {
	
	@Override
	public void run() {
		try {

			new NetMusicService().onRun();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void stop() {
		// TODO 自动生成的方法存根

	}

}

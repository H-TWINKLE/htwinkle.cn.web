package com.twinkle.task;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.cron4j.ITask;
import com.twinkle.common.model.Netmusic;
import com.twinkle.proxy.NetMusicProxy;

public class NetMusicTask implements ITask {

	@Override
	public void run() {
		addListNetMusic();
	}

	@Override
	public void stop() {
		// TODO 自动生成的方法存根

	}

	private void deleteObsoluteMusic() {

		Db.update("DELETE FROM netmusic WHERE to_days(`date`)!=to_days(now());");
	}

	public void addListNetMusic() {

		deleteObsoluteMusic();

		List<Netmusic> list = new ArrayList<>();

		NetMusicProxy neProxy = new NetMusicProxy();

		Netmusic netmusic;

		for (Netmusic net : neProxy.getMusicId()) {

			netmusic = neProxy.getMusicComm(net);

			netmusic.save();

			list.add(netmusic);

		}

	}

}

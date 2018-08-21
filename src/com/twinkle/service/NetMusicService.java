package com.twinkle.service;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.twinkle.common.model.Netmusic;
import com.twinkle.proxy.NetMusicProxy;
import com.twinkle.task.NetMusicTask;
import com.twinkle.utils.CommUtils;

public class NetMusicService {

	public List<Netmusic> getMusic(int num) {

		List<Netmusic> list = new Netmusic().find(
				"select * from `netmusic` where to_days(`date`) = to_days(now()) " + "ORDER BY rand() LIMIT " + num);

		if (list == null || list.size() == 0) {
			
			CommUtils.INSTANCE.addToExeCutorPool(new NetMusicTask());
									
			return null;
		}

		return list;

	}
	
	
	private void deleteObsoluteMusic() {
		
		Db.update("DELETE FROM netmusic WHERE to_days(`date`)!=to_days(now());");
	}

	public List<Netmusic> onRun() {
		
		deleteObsoluteMusic();

		List<Netmusic> list = new ArrayList<>();

		NetMusicProxy neProxy = new NetMusicProxy();

		Netmusic netmusic;

		for (Netmusic net : neProxy.getMusicId()) {

			netmusic = neProxy.getMusicComm(net);

			netmusic.save();

			list.add(netmusic);

		}

		return list;
	}

}

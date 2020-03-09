package com.twinkle.service;

import java.util.List;

import com.twinkle.common.model.Netmusic;
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
	
}

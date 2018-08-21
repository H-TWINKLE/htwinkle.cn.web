package com.twinkle.controller;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.twinkle.common.model.Netmusic;
import com.twinkle.service.NetMusicService;
import com.twinkle.utils.BaseController;

public class NetMusicController extends BaseController {

	static NetMusicService netMusicService = new NetMusicService();

	public void index() {

		List<Netmusic> netmusic = getMusic();

		setAttr("netmusic", netmusic);
		setAttr("title", "我爱音乐");
		render("/pages/page_net.html");

	}

	public void add_api() {
		renderJson(JSON.toJSONString(netMusicService.onRun()));
	}

	@Override
	public void api() {
		Integer num = getParaToInt("num");

		if (num == null || num == 0) {
			num = 10;
		}

		renderJson(JSON.toJSONString(getMusic()));

	}

	private List<Netmusic> getMusic() {

		Integer num = getParaToInt("num");
		if (num == null || num == 0) {
			num = 10;
		}
		return netMusicService.getMusic(num.intValue());

	}

}

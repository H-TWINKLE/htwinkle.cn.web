package com.twinkle.controller;

import java.util.List;

import com.twinkle.common.model.Netmusic;
import com.twinkle.service.NetMusicService;
import com.twinkle.utils.BaseController;

public class NetMusicController extends BaseController {

	static NetMusicService netMusicService = new NetMusicService();

	public void index() {
		
		setAttr("netmusic", getMusic());
		setAttr("title", "我爱音乐");
		render("/pages/page_net.html");

	}

	@Override
	public void api() {
		Integer num = getParaToInt("num");

		if (num == null || num == 0) {
			num = 10;
		}

		renderJson(getMusic());

	}

	private List<Netmusic> getMusic() {

		Integer num = getParaToInt("num");
		if (num == null || num == 0) {
			num = 10;
		}
		return netMusicService.getMusic(num);

	}

}

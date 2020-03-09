package com.twinkle.proxy.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.twinkle.common.model.Netmusic;

public enum NetMusicUtils {

	INSTANCE;

	@SuppressWarnings("rawtypes")
	public List<Netmusic> analMusicId(String html) {

		if (html == null || html.length() == 0)
			return null;

		Document doc = Jsoup.parse(html);

		Elements ele = doc.getElementsByTag("textarea").first().getElementsByAttributeValue("style", "display:none;");

		if (ele.size() == 0)
			return null;

		List<Object> list;

		list = JSON.parseArray(ele.text());

		List<Netmusic> nlist = new ArrayList<>();

		Netmusic netmusic;

		for (Object obj : list) {

			String oj = obj.toString();	

			netmusic = new Netmusic();

			netmusic.setNetname(JSONObject.parseObject(oj).get("name").toString());
			
			netmusic.setNetmusicurl(JSONObject.parseObject(oj).get("id").toString());

			netmusic.setNetsinger(JSONObject.parseObject(
					(JSONObject.parseArray((JSONObject.parseObject(oj).get("artists")).toString()).getString(0)))
					.getString("name"));

			netmusic.setNetcommid(JSONObject.parseObject(oj).get("commentThreadId").toString());

			netmusic.setNetpic(((Map) JSONObject.parseObject(oj).get("album")).get("picUrl").toString());
			
			netmusic.setDate(new Date(System.currentTimeMillis()));

			nlist.add(netmusic);

		}

		return nlist;

	}

	@SuppressWarnings("rawtypes")
	public Netmusic analMusicComm(String html, Netmusic net) {
		
		if (html == null || html.length() == 0)
			return null;

		String obj = JSONObject.parseArray(JSONObject.parseObject(html).getString("hotComments")).getString(0);
				
		net.setNetcomm(JSONObject.parseObject(obj).getString("content").replaceAll("[^\\u0000-\\uFFFF]", ""));

		net.setNettime(new Date(Long.parseLong((JSONObject.parseObject(obj).getString("time")))));

		net.setNettauthor(((Map) JSONObject.parseObject(obj).get("user")).get("nickname").toString());

		return net;

	}

}

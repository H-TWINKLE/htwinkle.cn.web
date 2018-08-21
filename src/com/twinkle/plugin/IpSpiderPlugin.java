package com.twinkle.plugin;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class IpSpiderPlugin {

	public String getIpByChinaZ(String ip) {

		Document doc = null;
		try {
			doc = Jsoup.connect("http://ip.chinaz.com/" + ip).timeout(2000).get();
		} catch (IOException e) {
			return "";
		}

		if (doc == null)
			return "";

		Element ele = doc.selectFirst("p.WhwtdWrap:nth-child(2) > span:nth-child(4)");

		if (ele == null)
			return null;

		return ele.text();

	}

	public String getIpByIp(String ip) {

		Document doc = null;
		try {
			doc = Jsoup.connect("https://ip.cn/index.php?ip=" + ip)
					.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:61.0) Gecko/20100101 Firefox/61.0")
					.header("Host", "ip.cn").timeout(2000).get();
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}

		if (doc == null) {			
			return "";
		}
		
		Element ele = doc.selectFirst(".well > p:nth-child(2) > code:nth-child(1)");

		if (ele == null)
			return null;

		return ele.text();

	}

}

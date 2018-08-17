package com.twinkle.proxy;

import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.jfinal.common.model.Article;
import com.twinkle.init.Constant;

public class ArticleProxy {

	@SuppressWarnings("deprecation")
	public Article getArtHtml() {

		Document doc = null;

		try {
			doc = Jsoup.connect(Constant.meiRiYiWenUrl).validateTLSCertificates(false).get();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (doc == null)
			return null;

		Element ele = doc.getElementById("article_show");

		Article art = new Article();

		art.setTitle(ele.getElementsByTag("h1").first().text());

		art.setAuthor(ele.getElementsByTag("span").first().text());

		art.setContent(ele.getElementsByClass("article_text").first().getElementsByTag("p").toString());

		art.setDates(new Date(System.currentTimeMillis()));

		art.save();

		return art;

	}

}

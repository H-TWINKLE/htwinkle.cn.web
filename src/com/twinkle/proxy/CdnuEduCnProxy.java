package com.twinkle.proxy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.twinkle.common.model.Post;

import com.twinkle.utils.Constant;

public class CdnuEduCnProxy {

	public List<Post> getPostByCdnuEduCn(int pages) {

		String url;

		if (pages == 1) {
			url = Constant.CDNUEDUCNNOTICES + Constant.HTML;
		} else {
			url = Constant.CDNUEDUCNNOTICES + "_" + pages + Constant.HTML;
		}

		Document doc = null;

		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			return null;
		}

		Post post;

		List<Post> list = new ArrayList<>();

		Elements eles = doc.getElementsByAttributeValue("class", "media-heading");

		Iterator<Element> iterable = eles.iterator();

		while (iterable.hasNext()) {

			Element ele = iterable.next().getElementsByTag("a").first();

			post = new Post();

			post.setTopic(Constant.NEWS);

			post.setAuthor(Constant.CDNUNEWS);

			post.setTitle(ele.text());

			post.setUrl(ele.attr("href"));

			list.add(post);

		}

		return list;

	}

	public Post getDetailPostContent(Post post) {

		Document doc = null;

		try {
			doc = Jsoup.connect(Constant.CDNU + post.getUrl()).get();
		} catch (Exception e) {
			return null;
		}

		String newsdate = doc.getElementsByAttributeValue("class", "cuhksz-detail-author").first()
				.getElementsByTag("li").first().text();

		if (newsdate != null) {
			post.setNewsdate(newsdate);
		}

		String content = doc.select("div.field-item.even").first().html();

		if (content != null) {

			try {
				Document d = Jsoup.parseBodyFragment(content);

				String text = d.body().wholeText();

				if ("".equals(text.trim())) {
					return null;
				}

				post.setContent(text);

			} catch (Exception e) {
				return null;
			}
		}

		Elements pic = doc.select("div.field-item.even").first().getElementsByTag("img");

		if (pic != null && pic.size() > 0) {

			boolean flag = false;

			int x = 0;

			Iterator<Element> iterator = pic.iterator();

			StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append("[");

			if (iterator.hasNext()) {

				x++;

				if (flag) {
					stringBuffer.append(",");
				}

				flag = true;

				String url = iterator.next().attr("src");

				if (url != null) {
					stringBuffer.append(Constant.CDNU);
					stringBuffer.append(url);
				}

			}

			stringBuffer.append("]");

			if (x == 1) {
				post.setTypes(Constant.ONE_ING_TEXT);
			} else if (x > 1) {
				post.setTypes(Constant.IMG_TEXT);
			}

			post.setPic(stringBuffer.toString());
		} else {
			post.setTypes(Constant.TEXT);
		}

		post.setDates(new Date());

		// System.out.println(post.toJson());

		post.save();

		return post;

	}

}

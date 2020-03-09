package com.twinkle.proxy;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.jfinal.kit.StrKit;
import com.twinkle.common.model.Post;
import com.twinkle.utils.Constant;

public class TieBaProxy {

	public List<Post> getTieBa(int pages, String authorid, String author, String topic) {

		pages = pages * 50;

		String url = Constant.TieBaUrl + authorid + "&ie=utf-8&pn=" + pages;

		Document doc = null;

		try {
			doc = Jsoup.connect(url)
					.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:62.0) Gecko/20100101 Firefox/62.0")
					.header("Host", "tieba.baidu.com").get();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		String text = doc.getElementById("pagelet_html_frs-list/pagelet/thread_list").toString().replace("<!--", "")
				.replace("-->", "");

		try {

			doc = Jsoup.parseBodyFragment(text);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		Elements eles = doc.select("li.j_thread_list.clearfix");

		if (eles == null)
			return null;

		Post post;

		List<Post> list = new ArrayList<>();

		int x = 0;

		Iterator<Element> iterator = eles.iterator();

		while (iterator.hasNext()) {

			x++;

			post = new Post();

			Element element = (Element) iterator.next();

			if (pages == 0 && x < 4) {

				continue;

			}

			post.setTitle(element.select("div.threadlist_title.pull_left.j_th_tit").text()
					.replaceAll("[^\\u0000-\\uFFFF]", ""));

			post.setAuthor(Constant.CDNUTIEBASHARE);

			post.setTopic(Constant.TIEBASHARE);

			post.setContent(element.select("div.threadlist_abs.threadlist_abs_onlyline").text()
					.replaceAll("[^\\u0000-\\uFFFF]", ""));

			if (StrKit.isBlank(post.getContent())) {
				post.setContent(element.select("div.threadlist_title.pull_left.j_th_tit").text()
						.replaceAll("[^\\u0000-\\uFFFF]", ""));
			}

			post.setUrl(Constant.TieBa + element.select("div.threadlist_title.pull_left.j_th_tit").first()
					.getElementsByTag("a").attr("href"));

			post.setNewsdate(element.select("span.pull-right.is_show_create_time").text());

			if (element.select("div.small_list_gallery").size() > 0) {

				List<Element> pic = element.select("div.small_list_gallery").first().getElementsByTag("img");

				if (pic != null && pic.size() > 0) {

					Iterator<Element> iter = pic.iterator();

					boolean flag = false;

					StringBuffer stringBuffer = new StringBuffer();
					stringBuffer.append("[");

					while (iter.hasNext()) {

						if (flag) {
							stringBuffer.append(",");
						}

						flag = true;

						String urls = iter.next().attr("bpic");

						if (urls != null) {
							stringBuffer.append(urls);
						}

					}

					stringBuffer.append("]");

					post.setPic(stringBuffer.toString());

					if (pic.size() == 1) {
						post.setTypes(Constant.ONE_ING_TEXT);
					} else {
						post.setTypes(Constant.IMG_TEXT);
					}

				}

			} else {
				post.setTypes(Constant.TEXT);
			}

			post.setDates(new Date());

			list.add(post);

			// System.out.println(post.toJson());

		}

		return list;

	}

}

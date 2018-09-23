package com.twinkle.proxy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.twinkle.common.model.Post;
import com.twinkle.entity.Mblog;
import com.twinkle.utils.Constant;

public class WeiboProxy {

	public List<Post> getWeiBo(int pages, String authorurl, String author, String topic) {

		Response response = null;

		try {
			response = Jsoup.connect(Constant.WEIBOJSON + authorurl + pages)
					.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:62.0) Gecko/20100101 Firefox/62.0")
					.header("Host", "m.weibo.cn").ignoreContentType(true).execute();
		} catch (IOException e) {
			return null;
		}

		List<Post> list = new ArrayList<>();

		Post post;

		JSONObject json = JSONObject.parseObject(response.body());

		JSONObject jsonToCards = JSON.parseObject(json.getString("data"));

		// System.out.println(JSON.toJSONString(jsonToCards));

		JSONArray array = JSONArray.parseArray(jsonToCards.getString("cards"));

		for (int x = array.size() - 1; x > 0; x--) {

			if (pages == 1 && x == 1) {
				continue;
			}

			JSONObject jsonToMblog = JSON.parseObject(array.get(x).toString());

			Mblog blog = JSON.parseObject(jsonToMblog.get("mblog").toString(), Mblog.class);

			// System.out.println(array.get(x).toString());

			// System.out.println(JSON.toJSONString(blog));

			if (blog == null)
				continue;

			post = new Post();

			String text = blog.getText().replaceAll("[^\\u0000-\\uFFFF]", "");

			post.setContent(text);

			post.setAuthor(author);

			if (blog.getPics() != null && blog.getPics().size() > 0) {

				Iterator<Mblog.PicsBean> iterator = blog.getPics().iterator();

				boolean flag = false;

				StringBuffer stringBuffer = new StringBuffer();
				stringBuffer.append("[");

				while (iterator.hasNext()) {

					if (flag) {
						stringBuffer.append(",");
					}

					flag = true;

					String url = iterator.next().getLarge().getUrl();

					if (url != null) {
						stringBuffer.append(url);
					}

				}

				stringBuffer.append("]");

				post.setPic(stringBuffer.toString());

				if (blog.getPics().size() == 1) {
					post.setTypes(Constant.ONE_ING_TEXT);
				} else {
					post.setTypes(Constant.IMG_TEXT);
				}

			} else {
				post.setTypes(Constant.TEXT);
			}

			post.setTopic(topic);

			post.setPlace(blog.getSource().replaceAll("[^\\u0000-\\uFFFF]", ""));

			post.setUrl(jsonToMblog.get("scheme").toString());

			post.setNewsdate(blog.getCreated_at());

			post.setDates(new Date());
			
			//System.out.println(post.toJson());

			 //post.save();

			list.add(post);

			// System.out.println(post.toJson());

		}

		return list;

	}

}

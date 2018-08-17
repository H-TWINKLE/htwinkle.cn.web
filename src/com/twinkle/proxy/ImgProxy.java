package com.twinkle.proxy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.jfinal.common.model.Img;
import com.twinkle.init.Constant;

public enum ImgProxy {

	INSTANCE;

	public List<Img> get3GBiZhiImg(String types) {

		try {

			Document doc = Jsoup.connect(Constant.g3BiZhiUrl + types + "/").get();

			Elements ele = doc.getElementById("load-img").getElementsByTag("img");

			if (ele == null || ele.size() == 0)
				return null;

			List<Img> list = new ArrayList<>();

			Img img = null;

			for (Element e : ele) {

				img = new Img();

				img.setHost(Constant.g3BiZhiUrl);

				img.setName(e.attr("alt"));

				img.setTypes(types);

				img.setPic(e.attr("src").replace(".255.344.jpg", ""));

				img.setDate(new Date(System.currentTimeMillis()));
				
				img.save();

				list.add(img);

			}

			return list;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

}

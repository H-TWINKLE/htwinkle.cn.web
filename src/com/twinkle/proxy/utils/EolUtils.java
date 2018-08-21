package com.twinkle.proxy.utils;

import java.util.Date;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.twinkle.common.model.Eol;
import com.twinkle.common.model.Eoltip;
import com.twinkle.utils.CommUtils;
import com.twinkle.utils.Constant;

public class EolUtils {

	private static EolUtils instance = new EolUtils();

	private EolUtils() {
	}

	public static EolUtils getInstance() {
		return instance;
	}

	private Elements eles;

	private int count = 0;

	public Eol analLogin(Eol eol, String html) {

		eol.setDates(new Date(System.currentTimeMillis()));

		Document doc = Jsoup.parse(html);

		Element ele = doc.getElementsByTag("script").first();

		if (ele == null) {
			eol.setTip("网络异常！");
			eol.setCode(Constant.NETFAILURE);
		} else {
			String tip = CommUtils.INSTANCE.mathChinese(ele.toString()).trim();
			if ("".equals(tip)) {
				eol.setCode(Constant.SUCCESS);
				eol.setTip("登陆成功！");
			} else {
				eol.setCode(Constant.FAILURE);
				eol.setTip(tip);
			}
		}
		return eol;
	}

	public Eol analStudentIndex(Eol eol, String html) {

		Document doc = Jsoup.parse(html);

		Element ele = doc.select("ul").first();

		if (ele == null) {
			eol.setTip("网络异常！");
			eol.setCode(Constant.NETFAILURE);
			return eol;
		} else {
			eol.setName(ele.select("li").get(0).text());
			eol.setOnlinetime(ele.select("li").get(2).text());
			eol.setLoginfre(ele.select("li").get(3).text());

			if (count != 0) {
				count = 0;
			}

			Elements elecount = doc.getElementById("reminder").getElementsByAttribute("title");

			for (Element el : elecount) {
				if (CommUtils.INSTANCE.regex(el.toString(), "有待提交作业")) {
					count = Integer.parseInt(el.getElementsByTag("span").first().text());
				}
			}

			// System.out.println("count:"+count);

			eles = doc.getElementById("reminder").getElementsByAttribute("onclick");

		}

		return eol;
	}

	public List<Eoltip> analStudentIndexClassTip(List<Eoltip> list) {

		if (eles == null)
			return null;

		if (count == 0)
			return null;

		// System.out.println(eles.toString());

		// List<Eoltip> list = new ArrayList<>();
		Eoltip tip = null;
		for (int x = eles.size() - count; x < eles.size(); x++) {
			tip = new Eoltip();
			String text = eles.get(x).attr("onclick");
			tip.setUrl(text.substring(text.indexOf("/"), text.lastIndexOf("'")));
			tip.setSubject(eles.get(x).text());
			list.add(tip);
		}

		return list;
	}

	public List<Eoltip> analOneTip(Eoltip eol, String html,List<Eoltip> list) {

		Document doc = Jsoup.parse(html);

		Elements ele = doc.select("table.infotable_hr").select("tr");
		if (ele == null)
			return null;	
		
		Eoltip tip;
		
		for (int x = 1; x < ele.size(); x++) {

			Elements el = ele.get(x).select("td");       
			if (CommUtils.INSTANCE.regex(el.get(5).toString(), "提交作业")) {
				tip = new Eoltip();
				tip.setUrl(eol.getUrl());
				tip.setSubject(eol.getSubject());
				tip.setTitle(el.get(0).text());
				tip.setAbort(el.get(1).text());
				tip.setDates(new Date(System.currentTimeMillis()));
				tip.save();
				list.add(tip);			
			}
			

		}
		
		//System.out.println("总体数据："+Arrays.toString(list.toArray()));
		

		return list;
	}

}

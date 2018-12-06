package com.twinkle.proxy.utils;

import java.util.ArrayList;
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


	public Eol analLogin(Eol eol, String html) {

		eol.setDates(new Date());

		Document doc = Jsoup.parse(html);

		Element ele = doc.getElementsByClass("loginerror_mess") == null ? null
				: doc.getElementsByClass("loginerror_mess").first();

		if (ele == null) {
			eol.setCode(Constant.SUCCESS);
			eol.setTip("登陆成功！");

		} else {
			eol.setCode(Constant.FAILURE);
			eol.setTip(ele.text());
		}

		return eol;
	}

	public Eol analStudentIndex(Eol eol, String html) {

		Document doc = Jsoup.parse(html);

		Element ele = doc.select("userinfobody").first();

		if (ele == null) {
			eol.setTip("网络异常！");
			eol.setCode(Constant.NETFAILURE);
			return eol;
		} else {
			eol.setName(ele.select("li").get(1).text());
			eol.setOnlinetime(ele.select("li").get(3).text());
			eol.setLoginfre(ele.select("li").get(4).text());
			}

		return eol;
	}
	
	
	
	

	public List<Eoltip> analStudentClassTip(Eol eol,String html) {
		
		Document doc = Jsoup.parse(html);

		Element ele = doc.getElementById("reminder");
		
		if(ele.getElementsByTag("span")==null) {
			return null;
		}
		
		List<Eoltip> list = new ArrayList<>();
		
		Elements eles = ele.select("a[target=_blank]");
		
		Eoltip eoltip;
		
		for(Element e:eles) {
			eoltip = new Eoltip();
			eoltip.setUrl(e.attr("href"));
			eoltip.setSubject(e.text());
			eoltip.setUserid(eol.getId());
			list.add(eoltip);
		}
		
		return list;
			
		
	}

	public List<Eoltip> analOneTip(Eoltip eol, String html, List<Eoltip> list) {

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

		// System.out.println("总体数据："+Arrays.toString(list.toArray()));

		return list;
	}

}

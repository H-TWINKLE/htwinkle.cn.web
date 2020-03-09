package com.twinkle.proxy.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSONObject;
import com.twinkle.common.model.Jwgl;
import com.twinkle.common.model.Jwglinfo;
import com.twinkle.common.model.Jwglscore;
import com.twinkle.common.model.Jwglttb;
import com.twinkle.utils.CommUtils;
import com.twinkle.utils.Constant;
import com.twinkle.utils.OcrEntity;
import com.twinkle.utils.OcrEntity.Result;

public class JwglUtils {

	private static JwglUtils instance = new JwglUtils();

	private JwglUtils() {
	}

	public static JwglUtils getInstance() {
		return instance;
	}

	public Map<String, String> LoginParmas(String string) {

		if (string == null)
			return null;

		Document doc = Jsoup.parse(string.toString());

		Elements ele = doc.getElementById("form1").select("input");

		Map<String, String> hashmap;
		hashmap = new HashMap<>();

		for (int x = 0; x < ele.size(); x++) {

			hashmap.put(ele.get(x).attr("name").trim(), ele.get(x).attr("value"));

		}

		if (hashmap.get("Button2") != null) {
			hashmap.remove("Button2");
		}

		if (hashmap.get("txtUserName") != null) {
			hashmap.remove("txtUserName");
		}

		if (hashmap.get("TextBox1") != null) {
			hashmap.remove("TextBox1");
		}

		if (hashmap.get("TextBox2") != null) {
			hashmap.remove("TextBox2");
		}

		if (hashmap.get("TextBox3") != null) {
			hashmap.remove("TextBox3");
		}

		if (hashmap.get("RadioButtonList1") != null) {
			hashmap.remove("RadioButtonList1");
		}

		return hashmap;
	}

	public String analCode(String json) {

		OcrEntity ocr = JSONObject.parseObject(json, OcrEntity.class);

		Integer num = ocr.getWords_result_num();

		if (num == 0)
			return null;

		for (Result ob : ocr.getWords_result()) {

			if (ob.getWords().length() == 4) {
				return ob.getWords();
			}
		}
		return null;

	}

	public Jwgl analLogin(Jwgl jwglb, String html) {

		jwglb.setDates(new Date(System.currentTimeMillis()));

		Document doc = Jsoup.parse(html);
		Element ele = doc.getElementById("xhxm");

		if (ele == null) {

			Elements eles = doc.getElementsByTag("script");

			if (eles != null) {

				int size = eles.size();

				if (size > 0) {
					jwglb.setCode(Constant.FAILURE); // 登录失败，用户或者密码错误，或者验证码错误。
					jwglb.setTip("登录失败:" + CommUtils.INSTANCE.mathChinese(eles.get(size - 1).toString()));
				} else {
					jwglb.setCode(Constant.NETFAILURE); // 网络异常
					jwglb.setTip("登录失败:网络异常");
				}
			} else {
				jwglb.setCode(Constant.NETFAILURE); // 网络异常
				jwglb.setTip("登录失败:网络异常\n");
			}

		} else {
			if (jwglb.getCookies() != null) {
				jwglb.setCode(Constant.SUCCESS);
				jwglb.setTip("登录成功：" + ele.text());
				jwglb.setName(ele.text().substring(0, ele.text().length() - 2));
			} else {
				jwglb.setCode(Constant.NETFAILURE); // 网络异常
				jwglb.setTip("登录失败:网络异常\n");
			}

			try {
				ImgUtils.getInstance().deleteImg(CommUtils.INSTANCE.isServicePath()); // 删除图片代码
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return jwglb;
	}

	public Jwglinfo analInfo(String html, Jwgl jwgl) {

		Jwglinfo jwglinfo = new Jwglinfo();

		Document doc = Jsoup.parse(html);

		if (doc.getElementById("xh") == null) {

			String tip = "";
			try {
				tip = CommUtils.INSTANCE.mathChinese(doc.html());
			} catch (Exception e) {

			}
			jwgl.setTip(tip);
			jwgl.setCode(Constant.NEVERTOENAL);

			return null;
		}

		jwglinfo.setUserid(jwgl.getId());
		jwglinfo.setDates(new Date(System.currentTimeMillis()));

		jwglinfo.setXh(getElementById(doc, "xh"));
		jwglinfo.setXm(getElementById(doc, "xm"));
		jwglinfo.setXszp(doc.getElementById("xszp").attr("src"));
		jwglinfo.setLblXb(getElementById(doc, "lbl_xb"));
		jwglinfo.setLblRxrq(getElementById(doc, "lbl_rxrq"));
		jwglinfo.setLblCsrq(getElementById(doc, "lbl_csrq"));
		jwglinfo.setLblMz(getElementById(doc, "lbl_mz"));
		jwglinfo.setLblZzmm(getElementById(doc, "lbl_zzmm"));
		jwglinfo.setLblLxdh(getElementById(doc, "lbl_lxdh"));
		jwglinfo.setLblYzbm(getElementById(doc, "lbl_yzbm"));
		jwglinfo.setLblZkzh(getElementById(doc, "lbl_zkzh"));
		jwglinfo.setLblSfzh(getElementById(doc, "lbl_sfzh"));
		jwglinfo.setLblCc(getElementById(doc, "lbl_CC"));
		jwglinfo.setLblXy(getElementById(doc, "lbl_xy"));
		jwglinfo.setLblJtszd(getElementById(doc, "lbl_jtszd"));
		jwglinfo.setLblZymc(getElementById(doc, "lbl_zymc"));
		jwglinfo.setLblXzb(getElementById(doc, "lbl_xzb"));
		jwglinfo.setLblYycj(getElementById(doc, "lbl_YYCJ"));
		jwglinfo.setLblXz(getElementById(doc, "lbl_xz"));
		jwglinfo.setLblXjzt(getElementById(doc, "lbl_xjzt"));
		jwglinfo.setLblDqszj(getElementById(doc, "lbl_dqszj"));
		jwglinfo.setLblKsh(getElementById(doc, "lbl_ksh"));

		jwglinfo.save();

		return jwglinfo;
	}

	private String getElementById(Document doc, String text) {

		if (text == null) {
			return null;
		}

		try {
			return doc.getElementById(text).text();
		} catch (Exception e) {
			return null;
		}

	}

	public Jwglttb analTtb(String html, Jwgl jwglb) {

		Jwglttb ttb = new Jwglttb();

		Document doc = Jsoup.parse(html);

		Elements eles = doc.getElementById("Table1").getElementsByAttributeValue("rowspan", "2");

		if (eles.size() == 0)
			return null;

		List<String> list1 = new ArrayList<>();
		List<String> list2 = new ArrayList<>();
		List<String> list3 = new ArrayList<>();
		List<String> list4 = new ArrayList<>();
		List<String> list5 = new ArrayList<>();

		for (Element ele : eles) {
			String text = "*" + ele.text() + "*";

			if (CommUtils.INSTANCE.regex(text, "周一")) {
				list1.add(text);
			}
			if (CommUtils.INSTANCE.regex(text, "周二")) {
				list2.add(text);
			}
			if (CommUtils.INSTANCE.regex(text, "周三")) {
				list3.add(text);
			}
			if (CommUtils.INSTANCE.regex(text, "周四")) {
				list4.add(text);
			}
			if (CommUtils.INSTANCE.regex(text, "周五")) {
				list5.add(text);
			}
		}

		ttb.setUserid(jwglb.getId());
		ttb.setDates(new Date(System.currentTimeMillis()));

		ttb.setWeek1(Arrays.toString(list1.toArray()));
		ttb.setWeek2(Arrays.toString(list2.toArray()));
		ttb.setWeek3(Arrays.toString(list3.toArray()));
		ttb.setWeek4(Arrays.toString(list4.toArray()));
		ttb.setWeek5(Arrays.toString(list5.toArray()));

		ttb.save();

		return ttb;
	}

	public String ScoreParma(StringBuffer sBuffer) {

		if (sBuffer == null)
			return "";

		Document doc = Jsoup.parse(sBuffer.toString());

		String ele;

		try {
			ele = doc.getElementById("Form1").select("input").attr("value");
		} catch (Exception e) {
			ele = "";
		}

		return ele;

	}

	public List<Jwglscore> analScore(String html, Jwgl jwglb) {

		if ("".equals(html))
			return null;

		List<Jwglscore> list = new ArrayList<>();

		Document doc = Jsoup.parse(html);
		Elements eletr;

		try {
			eletr = doc.getElementById("Datagrid1").select("tr");
		} catch (Exception e) {
			eletr = null;
		}

		if (eletr == null)
			return null;

		Jwglscore jwgl;

		for (int x = 1; x < eletr.size(); x++) {
			Elements eletd = eletr.get(x).select("td");
			jwgl = new Jwglscore();

			jwgl.setUserid(jwglb.getId());
			jwgl.setDates(new Date(System.currentTimeMillis()));

			jwgl.setXuenian(eletd.get(0).text());
			jwgl.setXueqi(eletd.get(1).text());
			jwgl.setKechengdaima(eletd.get(2).text());
			jwgl.setKechengmingcheng(eletd.get(3).text());
			jwgl.setKechengxingzhi(eletd.get(4).text());
			jwgl.setKechengguishu(eletd.get(5).text());
			jwgl.setXuefen(eletd.get(6).text());
			jwgl.setJidian(eletd.get(7).text());
			jwgl.setChengji(eletd.get(8).text());
			jwgl.setFuxiubiaoji(eletd.get(9).text());
			jwgl.setBukaochengji(eletd.get(10).text());
			jwgl.setChongxiuchengji(eletd.get(11).text());
			jwgl.setXueyuanmingchen(eletd.get(12).text());
			jwgl.setChongxiubiaoji(eletd.get(14).text());
			jwgl.save();

			list.add(jwgl);

		}

		return list;

	}

}

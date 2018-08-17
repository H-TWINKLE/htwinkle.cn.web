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

import com.jfinal.common.model.Jwgl;
import com.jfinal.common.model.Jwglinfo;
import com.jfinal.common.model.Jwglscore;
import com.jfinal.common.model.Jwglttb;
import com.twinkle.init.CommUtils;
import com.twinkle.init.Constant;

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

		if (hashmap.get("Textbox1") != null) {
			hashmap.remove("Textbox1");
		}

		if (hashmap.get("TextBox2") != null) {
			hashmap.remove("TextBox2");
		}

		if (hashmap.get("txtSecretCode") != null) {
			hashmap.remove("txtSecretCode");
		}

		if (hashmap.get("RadioButtonList1") != null) {
			hashmap.remove("RadioButtonList1");
		}

		return hashmap;
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

		jwglinfo.setXh(doc.getElementById("xh").text());
		jwglinfo.setXm(doc.getElementById("xm").text());
		jwglinfo.setXszp(doc.getElementById("xszp").attr("src"));
		jwglinfo.setLblXb(doc.getElementById("lbl_xb").text());
		jwglinfo.setLblRxrq(doc.getElementById("lbl_rxrq").text());
		jwglinfo.setLblCsrq(doc.getElementById("lbl_csrq").text());
		jwglinfo.setLblMz(doc.getElementById("lbl_mz").text());
		jwglinfo.setLblZzmm(doc.getElementById("lbl_zzmm").text());
		jwglinfo.setLblLxdh(doc.getElementById("lbl_lxdh").text());
		jwglinfo.setLblYzbm(doc.getElementById("lbl_yzbm").text());
		jwglinfo.setLblZkzh(doc.getElementById("lbl_zkzh").text());
		jwglinfo.setLblSfzh(doc.getElementById("lbl_sfzh").text());
		jwglinfo.setLblCc(doc.getElementById("lbl_CC").text());
		jwglinfo.setLblXy(doc.getElementById("lbl_xy").text());
		jwglinfo.setLblJtszd(doc.getElementById("lbl_jtszd").text());
		jwglinfo.setLblZymc(doc.getElementById("lbl_zymc").text());
		jwglinfo.setLblXzb(doc.getElementById("lbl_xzb").text());
		jwglinfo.setLblYycj(doc.getElementById("lbl_YYCJ").text());
		jwglinfo.setLblXz(doc.getElementById("lbl_xz").text());
		jwglinfo.setLblXjzt(doc.getElementById("lbl_xjzt").text());
		jwglinfo.setLblDqszj(doc.getElementById("lbl_dqszj").text());
		jwglinfo.setLblKsh(doc.getElementById("lbl_ksh").text());

		jwglinfo.save();

		return jwglinfo;
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

			if (CommUtils.INSTANCE.Regex(text, "周一")) {
				list1.add(text);
			}
			if (CommUtils.INSTANCE.Regex(text, "周二")) {
				list2.add(text);
			}
			if (CommUtils.INSTANCE.Regex(text, "周三")) {
				list3.add(text);
			}
			if (CommUtils.INSTANCE.Regex(text, "周四")) {
				list4.add(text);
			}
			if (CommUtils.INSTANCE.Regex(text, "周五")) {
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

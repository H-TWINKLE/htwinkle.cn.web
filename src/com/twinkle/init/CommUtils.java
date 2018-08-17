package com.twinkle.init;

import java.net.URLEncoder;
import java.util.regex.Pattern;

public  enum CommUtils {

   INSTANCE;

	public Boolean Regex(String text, String regex) {
		return Pattern.compile(regex).matcher(text).find();
	}

	public String mathChinese(String text) {
		return Pattern.compile("[^\u4E00-\u9FA5]").matcher(text).replaceAll(""); // [\u4E00-\u9FA5]是unicode2的中文区间
	}

	public String isServicePath() {
		// System.out.println(path);
		return Regex(System.getProperty(Constant.JavaProperty), Constant.JavaPath) ? Constant.demoPath
				: Constant.tomcatPath;
	}

	public String ocrPath() {
		// System.out.println(path);
		return Regex(System.getProperty(Constant.JavaProperty), Constant.JavaPath) ? Constant.orcPath
				: Constant.orcTomcatPath;
	}

	public String JwglUrl(String flag, String xh, String xm) {

		if (!"".equals(xm)) {
			try {
				xm = URLEncoder.encode(xh, "gb2312");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		switch (flag) {
		case "Host":
			return " jwgl.cdnu.edu.cn";
		case "Index":
			return "http://jwgl.cdnu.edu.cn/xs_main.aspx?xh=" + xh;
		case "Info":
			return "http://jwgl.cdnu.edu.cn/xsgrxx.aspx?xh=" + xh + "&xm=" + xm + "&gnmkdm=N121501";
		case "TimeTable":
			return "http://jwgl.cdnu.edu.cn/xskbcx.aspx?xh=" + xh + "&xm=" + xm + "&gnmkdm=N121603";
		case "Score":
			return "http://jwgl.cdnu.edu.cn/xscj_gc.aspx?xh="+xh+"&xm="+xm+"&gnmkdm=N121605";
		default:
			return "";
		}
	}
	
	public String EolUrl(String subject) {
		return "http://eol.cdnu.edu.cn/eol"+subject+"&_="+System.currentTimeMillis();				
	}	

}

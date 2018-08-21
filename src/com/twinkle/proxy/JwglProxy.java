package com.twinkle.proxy;

import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import com.twinkle.common.model.Jwgl;
import com.twinkle.common.model.Jwglinfo;
import com.twinkle.common.model.Jwglscore;
import com.twinkle.common.model.Jwglttb;
import com.twinkle.proxy.utils.JwglUtils;
import com.twinkle.utils.CommUtils;
import com.twinkle.utils.Constant;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.FormBody.Builder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class JwglProxy {

	private Jwgl jwglb = new Jwgl();

	private OkHttpClient okHttpClient = new OkHttpClient();

	private Response response;

	private Map<String, String> map;

	public JwglProxy() {

		if (map == null) {
			map = JwglUtils.getInstance().LoginParmas(getLoginParams()); // viewstate loginParams // //获取登录参数 1
		}
	}

	private okhttp3.Request.Builder addHeader = new Request.Builder().addHeader("User-Agent",
			"Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:61.0) Gecko/20100101 Firefox/61.0");

	private String getHtmlResult(byte[] bytes) {

		String text = "";

		try {
			text = new String(bytes, "gb2312");
		} catch (Exception e) {
			e.printStackTrace();
		}

		if ("".equals(text)) {
			return null;
		} else {
			return text;
		}

	}

	public String getLoginParams() { // 获取登录参数Viewstate 1

		Request request = addHeader.url(Constant.jwglHome).get().build();

		Call call = okHttpClient.newCall(request);

		try {
			response = call.execute();

			return getHtmlResult(response.body().bytes());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			response.close();
		}
		return null;

	}

	public String getCodeImg() { // getimg imgCookies&imgOrc 2 //0.png path demo

		Request request = new Request.Builder().url(Constant.imgUrl).get().build();

		Call call = okHttpClient.newCall(request);

		try {
			response = call.execute();

			try {
				jwglb.setCookies(response.header("Set-Cookie", ""));
			} catch (Exception e) {
				jwglb.setCookies("");
			}

			byte[] bytes = CommUtils.INSTANCE.imgConvert(response.body().byteStream());

			if (bytes == null)
				return null;

			String json = CommUtils.INSTANCE.getOrcText(bytes);

			return JwglUtils.getInstance().analCode(json);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			response.close();
		}
		return null;

	}

	public Jwgl jwglLogin(String admin, String pass) { // 开始登录 逐步获取参数 0

		jwglb.setAdmin(admin);
		jwglb.setPass(pass);

		if (map == null) {
			jwglb.setTip("教务管理系统维护中！");
			jwglb.setCode(Constant.SERVERERROR);
			return jwglb;
		}

		String radioButtonList1 = Constant.RadioButtonList1;
		try {
			radioButtonList1 = URLEncoder.encode(radioButtonList1, "gb2312");
		} catch (Exception e) {
			e.printStackTrace();
		}

		String codeImg = getCodeImg();

		if (codeImg == null) {
			jwglb.setTip("验证码为空！");
			jwglb.setCode(Constant.CODEIMGERROR);
			return jwglb;
		}

		Builder body = new FormBody.Builder().add("TextBox1", admin).add("TextBox2", pass).add("Textbox3", codeImg)
				.add("RadioButtonList1", radioButtonList1);

		for (String k : map.keySet()) {
			body.add(k, map.get(k));
		}

		Request request = addHeader.url(Constant.jwglHome).post(body.build()).header("Cookie", jwglb.getCookies())
				.header("Referer", Constant.jwglHome).build();

		Call call = okHttpClient.newCall(request);

		try {

			response = call.execute();

			return JwglUtils.getInstance().analLogin(jwglb, getHtmlResult(response.body().bytes()));

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			response.close();
		}
		return jwglb;
	}

	public Jwglinfo JwglInfo() { // 获得个人信息 3

		Request request = addHeader.url(CommUtils.INSTANCE.jwglUrl("Info", jwglb.getAdmin(), jwglb.getName())).get()
				.header("Cookie", jwglb.getCookies())
				.header("Referer", CommUtils.INSTANCE.jwglUrl("Index", jwglb.getAdmin(), ""))
				.header("Host", CommUtils.INSTANCE.jwglUrl("Host", "", "")).build();

		Call call = okHttpClient.newCall(request);

		try {
			response = call.execute();

			byte[] bytes = response.body().bytes();

			String text = "";

			try {
				text = new String(bytes, "gb2312");
			} catch (Exception e) {
				e.printStackTrace();
			}

			if ("".equals(text)) {
				return null;
			} else {
				return JwglUtils.getInstance().analInfo(text, jwglb);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			response.close();
		}
		return null;

	}

	public Jwglttb JwglTtb() { // 获得个人课表 4

		Request request = addHeader.url(CommUtils.INSTANCE.jwglUrl("TimeTable", jwglb.getAdmin(), jwglb.getName()))
				.get().header("Cookie", jwglb.getCookies())
				.header("Referer", CommUtils.INSTANCE.jwglUrl("Index", jwglb.getAdmin(), ""))
				.header("Host", CommUtils.INSTANCE.jwglUrl("Host", "", "")).build();

		Call call = okHttpClient.newCall(request);

		try {
			response = call.execute();

			byte[] bytes = response.body().bytes();

			String text = "";

			try {
				text = new String(bytes, "gb2312");
			} catch (Exception e) {

				e.printStackTrace();
			}

			if ("".equals(text)) {
				return null;
			} else {
				return JwglUtils.getInstance().analTtb(text, jwglb);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			response.close();
		}
		return null;

	}

	public String getScoreParam() { // 获得个人成绩viewsite 6

		Request request = addHeader.url(CommUtils.INSTANCE.jwglUrl("Score", jwglb.getAdmin(), jwglb.getName())).get()
				.header("Cookie", jwglb.getCookies())
				.header("Referer", CommUtils.INSTANCE.jwglUrl("Index", jwglb.getAdmin(), ""))
				.header("Host", CommUtils.INSTANCE.jwglUrl("Host", "", "")).build();

		Call call = okHttpClient.newCall(request);

		try {
			response = call.execute();

			byte[] bytes = response.body().bytes();

			String text = "";

			try {
				text = new String(bytes, "gb2312");
			} catch (Exception e) {

				e.printStackTrace();
			}

			if ("".equals(text)) {
				return null;
			} else {
				return JwglUtils.getInstance().ScoreParma(new StringBuffer().append(text));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			response.close();
		}
		return null;

	}

	public List<Jwglscore> Jwglscore() { // 获得个人成绩 5

		String Button1 = Constant.Button1;
		try {
			Button1 = URLEncoder.encode(Button1, "gb2312");
		} catch (Exception e) {
			e.printStackTrace();
		}

		RequestBody rbody = new FormBody.Builder().add("__VIEWSTATE", getScoreParam()) // 获得个人成绩viewsite 6
				.add("Button1", Button1).add("ddlXN", "").add("ddlXQ", "").build();

		Request request = addHeader.url(CommUtils.INSTANCE.jwglUrl("Score", jwglb.getAdmin(), jwglb.getName()))
				.post(rbody).header("Cookie", jwglb.getCookies())
				.header("Referer", CommUtils.INSTANCE.jwglUrl("Index", jwglb.getAdmin(), ""))
				.header("Host", CommUtils.INSTANCE.jwglUrl("Host", "", "")).build();

		Call call = okHttpClient.newCall(request);

		try {
			response = call.execute();

			byte[] bytes = response.body().bytes();

			String text = "";

			try {
				text = new String(bytes, "gb2312");
			} catch (Exception e) {

				e.printStackTrace();
			}

			if ("".equals(text)) {
				return null;
			} else {
				return JwglUtils.getInstance().analScore(text, jwglb);

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			response.close();
		}
		return null;

	}

}

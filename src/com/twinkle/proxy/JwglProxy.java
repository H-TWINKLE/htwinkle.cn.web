package com.twinkle.proxy;

import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import com.jfinal.common.model.Jwgl;
import com.jfinal.common.model.Jwglinfo;
import com.jfinal.common.model.Jwglscore;
import com.jfinal.common.model.Jwglttb;
import com.twinkle.init.Constant;
import com.twinkle.init.CommUtils;
import com.twinkle.proxy.utils.ImgUtils;
import com.twinkle.proxy.utils.JwglUtils;
import com.twinkle.utils.JwglImgHandle;
import com.twinkle.utils.OcrText;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.FormBody.Builder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class JwglProxy {

	private static final String path = CommUtils.INSTANCE.isServicePath();

	private Jwgl jwglb = new Jwgl();

	private OkHttpClient okHttpClient = new OkHttpClient();

	private Response response;

	private Map<String, String> map;
	
	
	public JwglProxy() {
		
		if (map == null) {
			map = JwglUtils.getInstance().LoginParmas(getLoginParams()); // viewstate loginParams // //获取登录参数 1
		}
		
	}
		

	public String getLoginParams() { // 获取登录参数Viewstate 1

		Request request = new Request.Builder().url(Constant.jwglHome).get().build();

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
				return text;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			response.close();
		}
		return null;

	}

	public String getImgs(String imgName, String imgpath) { // getimg imgCookies&imgOrc 2 //0.png path demo

		Request request = new Request.Builder().url(Constant.imgUrl).get().build();

		Call call = okHttpClient.newCall(request);

		try {
			response = call.execute();

			try {
				jwglb.setCookies(response.header("Set-Cookie", ""));
			} catch (Exception e) {
				jwglb.setCookies("");
			}

			ImgUtils.getInstance().saveSecret(response.body().byteStream(), imgName, imgpath); // 保存图片

			JwglImgHandle.changeImge(imgName, imgName + "s"); // 修改图片

			return OcrText.ocrText(path + "//" + imgName + "s.jpg"); // 识别图片 0.pngs.jpg

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

		Builder body = new FormBody.Builder().add("txtUserName", admin).add("Textbox1", admin).add("TextBox2", pass)
				.add("txtSecretCode", getImgs("0.png", path)) // getimg imgCookies&imgOrc 2
				.add("RadioButtonList1", radioButtonList1);

		for (String k : map.keySet()) {
			body.add(k, map.get(k));

		}

		RequestBody rbody = body.build();

		Request request = new Request.Builder().url(Constant.jwglHome).post(rbody)
				.header("Cookie", jwglb.getCookies()).header("Referer", Constant.jwglHome).build();

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
				return JwglUtils.getInstance().analLogin(jwglb, text);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			response.close();
		}
		return jwglb;
	}

	public Jwglinfo JwglInfo() { // 获得个人信息 3

		Request request = new Request.Builder()
				.url(CommUtils.INSTANCE.JwglUrl("Info", jwglb.getAdmin(), jwglb.getName())).get()
				.header("Cookie", jwglb.getCookies())
				.header("Referer", CommUtils.INSTANCE.JwglUrl("Index", jwglb.getAdmin(), ""))
				.header("Host", CommUtils.INSTANCE.JwglUrl("Host", "", "")).build();

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

		Request request = new Request.Builder()
				.url(CommUtils.INSTANCE.JwglUrl("TimeTable", jwglb.getAdmin(), jwglb.getName())).get()
				.header("Cookie", jwglb.getCookies())
				.header("Referer", CommUtils.INSTANCE.JwglUrl("Index", jwglb.getAdmin(), ""))
				.header("Host", CommUtils.INSTANCE.JwglUrl("Host", "", "")).build();

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

		Request request = new Request.Builder()
				.url(CommUtils.INSTANCE.JwglUrl("Score", jwglb.getAdmin(), jwglb.getName())).get()
				.header("Cookie", jwglb.getCookies())
				.header("Referer", CommUtils.INSTANCE.JwglUrl("Index", jwglb.getAdmin(), ""))
				.header("Host", CommUtils.INSTANCE.JwglUrl("Host", "", "")).build();

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

		Request request = new Request.Builder()
				.url(CommUtils.INSTANCE.JwglUrl("Score", jwglb.getAdmin(), jwglb.getName())).post(rbody)
				.header("Cookie", jwglb.getCookies())
				.header("Referer", CommUtils.INSTANCE.JwglUrl("Index", jwglb.getAdmin(), ""))
				.header("Host", CommUtils.INSTANCE.JwglUrl("Host", "", "")).build();

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

package com.twinkle.proxy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.twinkle.common.model.Eol;
import com.twinkle.common.model.Eoltip;
import com.twinkle.proxy.utils.EolUtils;
import com.twinkle.utils.CommUtils;
import com.twinkle.utils.Constant;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class EolProxy {

	private Eol eol = new Eol();

	private OkHttpClient okHttpClient = new OkHttpClient();

	private Response response;

	public String eolGetIndex() { // 预先登录获得cookie 1

		Request request = new Request.Builder().url(Constant.eolLogin).get().build();

		Call call = okHttpClient.newCall(request);

		String cook = "";

		try {

			response = call.execute();

			cook = response.header("Set-Cookie", "");

			if (cook == null || "".equals(cook))
				return null;
			cook = cook.substring(0, cook.indexOf(";"));

			eol.setCookies(cook);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			response.close();
		}

		return cook;

	}

	public Eol eolpreLogin(String admin, String pass) { // 初始化登录方法 0

		eol.setAdmin(admin);
		eol.setPass(pass);

		RequestBody rbody = new FormBody.Builder().add("IPT_LOGINUSERNAME", admin)
				.add("IPT_LOGINPASSWORD", pass)
				.add("logintoken", String.valueOf(System.currentTimeMillis()))
				.build();

		Request request = new Request.Builder().url(Constant.eolLogin).post(rbody).header("Cookie", eolGetIndex()) // 调用预登录获取cookie
				.build();

		Call call = okHttpClient.newCall(request);

		try {
			response = call.execute();

			byte[] bytes = response.body().bytes();

			String text = "";

			try {
				text = new String(bytes, "gbk");
			} catch (Exception e) {

				e.printStackTrace();
			}

			if ("".equals(text)) {

				return null;

			} else {
				return EolUtils.getInstance().analLogin(eol, text);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			response.close();
		}

		return null;

	}

	/*
	 * public Eol eolgetLogin() {
	 * 
	 * OkHttpClient okHttpClient = new OkHttpClient();
	 * 
	 * Request request = new
	 * Request.Builder().url(InitString.eolindex).get().header("Cookie",
	 * eol.getCookies()) .build();
	 * 
	 * Call call = okHttpClient.newCall(request);
	 * 
	 * try { Response response = call.execute();
	 * 
	 * byte[] bytes = response.body().bytes();
	 * 
	 * String text = "";
	 * 
	 * try { text = new String(bytes, "gbk"); } catch (Exception e) {
	 * 
	 * e.printStackTrace(); }
	 * 
	 * if ("".equals(text)) {
	 * 
	 * return null;
	 * 
	 * } else { // System.out.println("output:"+text); }
	 * 
	 * } catch (Exception e) { e.printStackTrace(); }
	 * 
	 * return eol;
	 * 
	 * }
	 */

	public Eol eolStudentIndex() { // eol主页 获取用户基本信息 2

		Request request = new Request.Builder().url(Constant.eolStudentIndex).get().header("Cookie", eol.getCookies())
				.build();

		Call call = okHttpClient.newCall(request);

		try {
			response = call.execute();

			byte[] bytes = response.body().bytes();

			String text = "";

			try {
				text = new String(bytes, "gbk");
			} catch (Exception e) {

				e.printStackTrace();
			}

			if ("".equals(text)) {

				return null;

			} else {
				return EolUtils.getInstance().analStudentIndex(eol, text);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			response.close();
		}

		return eol;

	}
	
	public List<Eoltip> eolStudentWorkTip() { // eol主页 获取用户作业提示

		Request request = new Request.Builder().url(Constant.eolTask).get().header("Cookie", eol.getCookies())
				.build();

		Call call = okHttpClient.newCall(request);

		try {
			response = call.execute();

			byte[] bytes = response.body().bytes();

			String text = "";

			try {
				text = new String(bytes, "gbk");
			} catch (Exception e) {

				e.printStackTrace();
			}

			if ("".equals(text)) {

				return null;

			} else {
				return EolUtils.getInstance().analStudentClassTip(eol, text);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			response.close();
		}

		return null;

	}
	
	

	public List<List<Eoltip>> analClassTip(List<Eoltip> list) { // 分别获取每科作业提示方法 3

		List<List<Eoltip>> li = new ArrayList<>();

		if (list == null)
			return null;

		for (Eoltip tip : list) {
			li.add(analOneTip(tip)); // 获取获取每科作业提示实例 5
			// System.out.println(Arrays.toString(analOneTip(tip).toArray()));
		}

		return li;
	}

	public List<Eoltip> analOneTip(Eoltip tip) { // 获取获取每科作业提示实例 5

		// menu

		Request request = new Request.Builder().url(CommUtils.INSTANCE.eolUrl(tip.getUrl())).get()
				.header("Cookie", eol.getCookies()).build();

		Call call = okHttpClient.newCall(request);

		try {
			response = call.execute();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}finally {
			response.close();
		}

		// task

		request = new Request.Builder().url(Constant.eolTask).get().header("Cookie", eol.getCookies()).build();

		call = okHttpClient.newCall(request);

		try {
			response = call.execute();
			byte[] bytes = response.body().bytes();

			String text = "";

			try {
				text = new String(bytes, "gbk");
			} catch (Exception e) {

				e.printStackTrace();
			}finally {
				response.close();
			}

			if ("".equals(text)) {

				return null;

			} else {
				eol.setDates(new Date(System.currentTimeMillis()));

				return EolUtils.getInstance().analOneTip(tip, text,new ArrayList<>());
			}

		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} finally {
			response.close();
		}

		return null;
	}

}

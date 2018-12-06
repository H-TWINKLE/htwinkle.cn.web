package com.twinkle.plugin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.HashKit;
import com.jfinal.kit.StrKit;
import com.twinkle.common.model.Eol;
import com.twinkle.common.model.Eoltip;
import com.twinkle.entity.EolBean;
import com.twinkle.entity.EolTipBean;
import com.twinkle.entity.EolTipBean.Datas;
import com.twinkle.entity.EolTipBean.ReminderList1;
import com.twinkle.utils.Constant;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class EolSpiderByMobile {

	public static final MediaType TYPES = MediaType.parse("application/x-www-form-urlencoded; charset=UTF-8 ");

	public static final String GET_SESSION = "http://eol.cdnu.edu.cn/mobile/getSessionId.do";

	public static final String TO_LOGIN = "http://eol.cdnu.edu.cn/mobile/login_check.do";

	public static final String TASK_LIST = "http://eol.cdnu.edu.cn/mobile/stuUnDoTaskList.do";

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss",Locale.CHINA);

	private static OkHttpClient client = new OkHttpClient();

	private static Map<String, String> map = new HashMap<>();

	private Map<String, String> initMap() {

		map.put("appVersion", "8.3.5");
		map.put("deviceVersion", "1.0");
		return map;

	}

	private Map<String, String> addParaToMap(String admin, String pass) {

		Map<String, String> map = initMap();

		map.put("j_username", admin);
		map.put("j_password", HashKit.md5(pass));
		map.put("deviceUuid", StrKit.getRandomUUID());
		map.put("deviceName", StrKit.getRandomUUID());
		map.put("devicePlatform", StrKit.getRandomUUID());

		return map;

	}

	private String getLoginPara(Map<String, String> map) {

		StringBuffer s = new StringBuffer();

		for (String key : map.keySet()) {

			s.append(key);
			s.append("=");
			s.append(map.get(key));
			s.append("&");

		}

		return s.toString();

	}

	private String getSession() {

		Request r = new Request.Builder().url(GET_SESSION).get().build();

		try (Response response = client.newCall(r).execute()) {

			return JSONObject.parseObject(response.body().string().toString()).getString("sessionid");

		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}

	}

	public Eol toLogin(String account, String pass) {

		String session = "JSESSIONID=" + getSession();

		RequestBody body = RequestBody.create(TYPES, getLoginPara(addParaToMap(account, pass)));

		Request r = new Request.Builder().url(TO_LOGIN).post(body).header("Cookie", session)
				.header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8").build();

		try (Response response = client.newCall(r).execute()) {

			String text = response.body().string();

			JSONObject js = JSONObject.parseObject(text);

			Eol e = new Eol();

			e.setAdmin(account);
			e.setPass(pass);
			e.setDates(new Date());
			e.setCookies(session);

			if (js.getInteger("status") == 2) {

				e.setCode(Constant.FAILURE);
				e.setTip(((Map<?, ?>) js.get("datas")).get("errorMessage").toString());

			} else if (js.getInteger("status") == 1) {
				e.setCode(Constant.SUCCESS);
				e.setTip("登录成功！");

				EolBean bean = JSONObject.parseObject(text, EolBean.class);

				e.setName(bean.getDatas().getUserinfo().getUser().getRealname());
				e.setLoginfre("登录次数：" + bean.getDatas().getUserinfo().getLoginTimes());
				e.setOnlinetime("总共在线分钟数：" + bean.getDatas().getUserinfo().getTotalOnlineTime());

			} else {
				e.setCode(Constant.NETFAILURE);
				e.setTip("测试失败！");
			}
			return e;

		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}

	}

	public List<List<Eoltip>> getListEolTip(Eol eol) {

		RequestBody body = RequestBody.create(TYPES, "context=");

		Request r = new Request.Builder().url(TASK_LIST).post(body).header("Cookie", eol.getCookies())
				.header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8").build();

		try (Response response = client.newCall(r).execute()) {

			EolTipBean e = JSONObject.parseObject(response.body().string(), EolTipBean.class);

			Eoltip tip;

			List<List<Eoltip>> parent = new ArrayList<>();

			for (Datas datas : e.getDatas()) {

				List<Eoltip> list = new ArrayList<>();

				for (ReminderList1 remind : datas.getReminderList1()) {

					tip = new Eoltip();
					tip.setUserid(eol.getId());
					tip.setSubject(datas.getCourseName());
					tip.setTitle(remind.getTitle());
					tip.setAbort(sdf.format(remind.getDeadline()));
					tip.setDates(new Date());

					if (tip.getId() != null) {
						tip.setId(null);
					}

					tip.save();

					list.add(tip);

				}

				parent.add(list);

			}

			return parent;

		} catch (Exception e) {
			e.printStackTrace();
			return null;

		}

	}

}

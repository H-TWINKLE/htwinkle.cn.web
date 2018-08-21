package com.twinkle.proxy;

import java.util.List;

import com.twinkle.common.model.Netmusic;
import com.twinkle.proxy.utils.NetMusicUtils;
import com.twinkle.utils.Constant;

import okhttp3.Call;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetMusicProxy {

	private OkHttpClient client = new OkHttpClient();

	private Response response;

	private List<Netmusic> list;

	private Headers headers() {

		return new Headers.Builder().add("Referer", "http://music.163.com/").add("Host", "music.163.com")
				.add("User-Agent",
						"Mozilla/5.0 (X11; Linux x86_64; rv:38.0) Gecko/20100101 Firefox/38.0 Iceweasel/38.3.0")
				.add("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8").build();

	}

	public List<Netmusic> getMusicId() {

		Request request = new Request.Builder().headers(headers()).url(Constant.NetMusicUrl).build();

		Call call = client.newCall(request);

		try {
			response = call.execute();

			list = NetMusicUtils.INSTANCE.analMusicId(response.body().string());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			response.close();
		}

		return list;

	}

	public Netmusic getMusicComm(Netmusic net) {

		Request request = new Request.Builder().headers(headers()).url(Constant.NetMusicCommUrl + net.getNetcommid())
				.build();

		Call call = client.newCall(request);

		try {
			response = call.execute();

			byte[] b = response.body().bytes();

			String html = "";

			try {
				html = new String(b, "utf-8");
			} catch (Exception e) {

				e.printStackTrace();
			}

			if ("".equals(html))
				return null;

			net = NetMusicUtils.INSTANCE.analMusicComm(html, net);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			response.close();
		}

		return net;
	}

}

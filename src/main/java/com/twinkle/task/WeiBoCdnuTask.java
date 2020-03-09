package com.twinkle.task;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.plugin.cron4j.ITask;
import com.twinkle.common.model.Post;
import com.twinkle.proxy.WeiboProxy;
import com.twinkle.utils.CommUtils;
import com.twinkle.utils.Constant;

public class WeiBoCdnuTask implements ITask {

	@Override
	public void run() {

		Post post = getTheNewestPost();

		if (post == null)
			return;

		//System.out.println("----------标准url: " + post.getUrl() + " -- " + post.getNewsdate());

		WeiboProxy proxy = new WeiboProxy();

		int pages = 1;

		List<Post> list = new ArrayList<>();

		OUT: while (true) {

			List<Post> li = proxy.getWeiBo(pages, Constant.WEIBOJSONBYCDNU, Constant.CDNUWEIBOLIFESHARE,
					Constant.SHARE);

			for (int x = li.size() - 1; x >=0; x--) {

				Post post2 = li.get(x);

				if (post.getUrl().equals(post2.getUrl())) {
					//System.out.println("----------post url: " + post.getUrl() + "----------post url " + post2.getUrl()
						//	+ "  相等，正在退出...");

					break OUT;
				} else {
					//System.out.println("----------post url: " + post.getUrl() + "----------post url " + post2.getUrl()
						//	+ "  不相等--" + post2.getNewsdate());
					list.add(post2);
					// post2.save();
					// CommUtils.INSTANCE.postPostToBmobDatabase(post2);
				}

			}

			pages++;

		}

		for (int x = list.size() - 1; x >=0; x--) {

			Post po = list.get(x);

			po.save();

			CommUtils.INSTANCE.postPostToBmobDatabase(po);

		}

	}

	@Override
	public void stop() {
		// TODO 自动生成的方法存根

	}

	private Post getTheNewestPost() {

		return Post.dao.findFirst("select * from post where author=? order by id desc limit 1",
				Constant.CDNUWEIBOLIFESHARE);

	}

}

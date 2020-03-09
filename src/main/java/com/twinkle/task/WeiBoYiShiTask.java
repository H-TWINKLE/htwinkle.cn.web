package com.twinkle.task;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.plugin.cron4j.ITask;
import com.twinkle.common.model.Post;
import com.twinkle.proxy.WeiboProxy;
import com.twinkle.utils.CommUtils;
import com.twinkle.utils.Constant;

public class WeiBoYiShiTask implements ITask {

	@Override
	public void run() {

		Post post = getTheNewestPost();
		
		if(post==null)return;

		WeiboProxy proxy = new WeiboProxy();

		int pages = 1;

		List<Post> list = new ArrayList<>();

		OUT:while (true) {

			List<Post> li = proxy.getWeiBo(pages, Constant.WEIBOJSONBYYISHI, Constant.CDNUWEIBOYISHI,
					Constant.YISHI);

			for (int x = li.size() - 1; x >=0; x--) {

				Post post2 = li.get(x);

				if (post.getUrl().equals(post2.getUrl())) {
					break OUT;
				} else {
					list.add(post2);
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
				Constant.CDNUWEIBOYISHI);

	}

}

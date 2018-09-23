package com.twinkle.task;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.plugin.cron4j.ITask;
import com.twinkle.common.model.Post;
import com.twinkle.proxy.CdnuEduCnProxy;
import com.twinkle.utils.CommUtils;
import com.twinkle.utils.Constant;

public class CdnuNewsTask implements ITask {

	@Override
	public void run() {

		Post post = getTheNewestPost();
		
		if(post==null)return;

		CdnuEduCnProxy proxy = new CdnuEduCnProxy();

		int pages = 1;

		List<Post> list = new ArrayList<>();

		OUT: while (true) {

			List<Post> li = proxy.getPostByCdnuEduCn(pages);

			for (int x = 0; x < li.size(); x++) {

				Post post2 = li.get(x);

				if (post.getTitle().equals(post2.getTitle())) {
					// System.out.println("第" + pages + "页，第" + x + "条 post -- " + post.getTitle() +
					// " --- post2 "
					// + post2.getTitle() + " 已经被被采集,正在跳出······");
					break OUT;
				} else {
					// System.out.println("第" + pages + "页，第" + x + "条 post -- " + post.getTitle() +
					// " --- post2 "
					// + post2.getTitle() + " 没有被采集");

					list.add(post2);
				}

			}

			pages++;

		}

		for (int x = list.size() - 1; x >=0; x--) {

			Post post3 = proxy.getDetailPostContent(list.get(x));

			if (post3 != null) {

				CommUtils.INSTANCE.postPostToBmobDatabase(post3);
			}
		}

	}

	@Override
	public void stop() {
		
	}

	private Post getTheNewestPost() {

		return Post.dao.findFirst("select * from post where author=? order by id desc limit 1", Constant.CDNUNEWS);

	}

}

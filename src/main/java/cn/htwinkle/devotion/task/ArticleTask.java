package cn.htwinkle.devotion.task;

import cn.htwinkle.devotion.model.Article;
import cn.htwinkle.devotion.spider.ArticleSpiderImpl;
import cn.htwinkle.devotion.spider.ISpider;
import com.jfinal.aop.Aop;
import com.jfinal.plugin.cron4j.ITask;

import java.util.logging.Logger;

/**
 * Article定时任务
 *
 * @author : twinkle
 * @date : 2020/3/15 13:12
 */
public class ArticleTask implements ITask {

    private ISpider<Article> spider = Aop.get(ArticleSpiderImpl.class);
    /**
     * ArticleTask的输出日志对象
     */
    private static final Logger LOGGER = Logger.getLogger(ArticleTask.class.getName());

    @Override
    public void stop() {

    }

    @Override
    public void run() {
        try {
            spider.get();
        } catch (Exception e) {
            LOGGER.info("ArticleTask - run : " + e.getMessage());
        }
    }
}

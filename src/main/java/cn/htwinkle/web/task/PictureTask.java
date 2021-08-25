package cn.htwinkle.web.task;

import cn.htwinkle.web.constants.Constants;
import cn.htwinkle.web.model.Picture;
import cn.htwinkle.web.spider.ISpider;
import cn.htwinkle.web.spider.PictureSpiderImpl;
import com.jfinal.aop.Aop;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.cron4j.ITask;
import org.apache.log4j.Logger;

/**
 * Picture定时任务
 *
 * @author : twinkle
 * @date : 2020/3/15 13:12
 */
public class PictureTask implements ITask {

    private ISpider<Picture> spider = Aop.get(PictureSpiderImpl.class);

    /**
     * PictureTask的输出日志对象
     */
    private static final Logger LOGGER = Logger.getLogger(PictureTask.class.getName());

    @Override
    public void stop() {

    }

    @Override
    public void run() {
        getListImg();
    }

    private void getListImg() {
        deleteOldImg();
        try {
            for (String s : Constants.G3_BIZHI_TYPES) {
                spider.get(s);
            }
        } catch (Exception e) {
            LOGGER.info("PictureTask - getListImg : " + e.getMessage());
        }
    }

    private void deleteOldImg() {
        Db.update("DELETE FROM picture WHERE to_days(`date`)!=to_days(now());");
    }


}

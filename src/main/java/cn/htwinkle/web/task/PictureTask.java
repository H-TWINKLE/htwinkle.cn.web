package cn.htwinkle.web.task;

import cn.htwinkle.web.constants.Constants;
import cn.htwinkle.web.domain.PictureOption;
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

    private final ISpider<Picture, PictureOption> spider = Aop.get(PictureSpiderImpl.class);

    /**
     * PictureTask的输出日志对象
     */
    private static final Logger LOGGER = Logger.getLogger(PictureTask.class.getName());

    @Override
    public void stop() {

    }

    @Override
    public void run() {
        deleteOldImg();
        getListImg();
    }

    private void getListImg() {
        try {
            PictureOption option = new PictureOption();
            option.setPlate(Picture.PLATE_All);
            for (String type : Constants.G3_BIZHI_TYPES) {
                option.setType(type);
                spider.getList(option);
            }
        } catch (Exception e) {
            LOGGER.info("PictureTask - getListImg : " + e.getMessage());
        }
    }

    private void deleteOldImg() {
        Db.update("DELETE FROM picture WHERE to_days(`pictureDate`)!=to_days(now())");
    }


}

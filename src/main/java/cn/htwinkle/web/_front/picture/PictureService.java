package cn.htwinkle.web._front.picture;

import cn.htwinkle.web.base.BaseService;
import cn.htwinkle.web.kit.PoolExecutorKit;
import cn.htwinkle.web.model.Picture;
import cn.htwinkle.web.spider.ISpider;
import cn.htwinkle.web.spider.PictureSpiderImpl;
import com.jfinal.aop.Aop;

import java.util.List;

/**
 * 图片的服务层
 *
 * @author : twinkle
 * @date : 2020/3/15 11:38
 */
public class PictureService extends BaseService {

    private ISpider<Picture> spider = Aop.get(PictureSpiderImpl.class);

    public List<Picture> getPictureList(int num, String types) {
        List<Picture> list = Picture.dao.find(
                "SELECT * from picture " +
                "where to_days(`pictureDate`) = to_days(now()) AND pictureTypes = ? " +
                "ORDER BY rand() LIMIT 0," + num, types);
        if (list.size() == 0) {
            PoolExecutorKit.INSTANCE.execute(() -> spider.get());
            return null;
        }
        return list;
    }


    public Picture getOnePicture() {
        List<Picture> list = Picture.dao
                .find("SELECT * FROM picture " +
                        "WHERE to_days(`pictureDate`) = to_days(now()) ORDER BY rand() LIMIT 1");
        if (list.size() == 0) {
            PoolExecutorKit.INSTANCE.execute(() -> spider.get());
            return null;
        }
        return list.get(0);
    }

}

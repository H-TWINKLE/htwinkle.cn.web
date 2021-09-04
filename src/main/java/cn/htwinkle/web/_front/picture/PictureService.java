package cn.htwinkle.web._front.picture;

import cn.htwinkle.web.base.BaseService;
import cn.htwinkle.web.constants.Constants;
import cn.htwinkle.web.kit.PoolExecutorKit;
import cn.htwinkle.web.model.Picture;
import cn.htwinkle.web.model.base.BasePicture;
import cn.htwinkle.web.spider.ISpider;
import cn.htwinkle.web.spider.PictureSpiderImpl;
import com.jfinal.aop.Aop;
import com.jfinal.kit.Kv;
import com.jfinal.kit.StrKit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 图片的服务层
 *
 * @author : twinkle
 * @date : 2020/3/15 11:38
 */
public class PictureService extends BaseService {

    private ISpider<Picture> spider = Aop.get(PictureSpiderImpl.class);

    /**
     * 获取1920*1080的电脑桌面级别的图片资源地址
     *
     * @return List<String>
     */
    public List<String> getPictureListIndexBy() {
        List<Picture> list = Picture.dao.find(
                "SELECT * from picture " +
                        "where to_days(`pictureDate`) = to_days(now()) AND " +
                        "pictureTypes = ? AND " +
                        "picturePlate = ? ",
                Constants.G3_BIZHI_TYPES[0], Picture.PLATE_DESK);
        if (list.size() == 0) {
            PoolExecutorKit.INSTANCE.execute(() -> spider.get());
            return getDefaultPicArr();
        }
        List<String> filterList = list
                .stream()
                .filter(item -> StrKit.notBlank(item.getPictureUrl()))
                .map(BasePicture::getPictureUrl)
                .collect(Collectors.toList());
        Collections.shuffle(filterList);
        Collections.shuffle(filterList);
        return filterList.size() >= Constants.DEFAULT_APP_PIC_ARR.length ?
                new ArrayList<>(filterList.subList(0, Constants.DEFAULT_APP_PIC_ARR.length)) : getDefaultPicArr();
    }

    /**
     * 获取图片
     *
     * @param num num
     * @return List<Picture>
     */
    public List<Picture> getPictureList(int num) {
        return getPictureList(num, Picture.PLATE_DESK, Constants.G3_BIZHI_TYPES[0]);
    }

    public List<Picture> getPictureList(int num, int plate, String type) {
        List<Picture> list = Picture.dao.find(
                "SELECT * from picture " +
                        "where to_days(`pictureDate`) = to_days(now()) AND " +
                        "pictureTypes = ? AND" +
                        "picturePlate = ? " +
                        "ORDER BY rand() LIMIT 0," + num, type, plate);
        if (list.isEmpty()) {
            PoolExecutorKit.INSTANCE.execute(() ->
                    spider.getList(Kv.create().set("type", type).set("plate", plate)));
        }
        return list;
    }

    public List<Picture> getPictureList(int num, String type) {
        List<Picture> list = Picture.dao.find(
                "SELECT * from picture " +
                        "where to_days(`pictureDate`) = to_days(now()) AND " +
                        "pictureTypes = ? " +
                        "ORDER BY rand() LIMIT 0," + num, type);
        if (list.isEmpty()) {
            PoolExecutorKit.INSTANCE.execute(() -> spider.getList(Kv.create().set("type", type)));
            return null;
        }
        return list;
    }


    public Picture getOnePicture() {
        List<Picture> list = Picture.dao
                .find("SELECT * FROM picture " +
                        "WHERE to_days(`pictureDate`) = to_days(now()) " +
                        "ORDER BY rand() LIMIT 1");
        if (list.size() == 0) {
            PoolExecutorKit.INSTANCE.execute(() -> spider.get());
            return null;
        }
        return list.get(0);
    }

    /**
     * 获取默认的图片
     *
     * @return List<String>
     */
    private List<String> getDefaultPicArr() {
        return new ArrayList<>(Arrays.asList(Constants.DEFAULT_APP_PIC_ARR));
    }
}

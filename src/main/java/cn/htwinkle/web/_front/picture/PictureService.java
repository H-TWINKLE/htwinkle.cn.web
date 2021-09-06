package cn.htwinkle.web._front.picture;

import cn.htwinkle.web.base.BaseService;
import cn.htwinkle.web.constants.Constants;
import cn.htwinkle.web.domain.PictureOption;
import cn.htwinkle.web.kit.PoolExecutorKit;
import cn.htwinkle.web.model.Picture;
import cn.htwinkle.web.model.base.BasePicture;
import cn.htwinkle.web.spider.ISpider;
import cn.htwinkle.web.spider.PictureSpiderImpl;
import com.jfinal.aop.Aop;
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

    private final ISpider<Picture, PictureOption> spider = Aop.get(PictureSpiderImpl.class);

    /**
     * 获取1920*1080的电脑桌面级别的图片资源地址
     *
     * @return List<String>
     */
    public List<String> getPictureListIndexBy() {
        String type = Constants.G3_BIZHI_TYPES[0];
        List<Picture> list = Picture.dao.find(
                "SELECT * from picture " +
                        "where to_days(`pictureDate`) = to_days(now()) AND " +
                        "pictureTypes = ? AND " +
                        "picturePlate = ? ", type, Picture.PLATE_DESK);
        if (null == list || list.isEmpty()) {
            asyncGetList(type, Picture.PLATE_DESK);
            return getDefaultPicArr();
        }
        List<String> filterList = list
                .stream()
                .map(BasePicture::getPictureUrl)
                .filter(StrKit::notBlank)
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
                        "pictureTypes = ? AND " +
                        "picturePlate = ? " +
                        "ORDER BY rand() LIMIT 0," + num, type, plate);
        if (null == list || list.isEmpty()) {
            asyncGetList(type, plate);
        }
        return list;
    }

    public List<Picture> getPictureList(int num, String type) {
        List<Picture> list = Picture.dao.find(
                "SELECT * from picture " +
                        "where to_days(`pictureDate`) = to_days(now()) AND " +
                        "pictureTypes = ? " +
                        "ORDER BY rand() LIMIT 0," + num, type);
        if (null == list || list.isEmpty()) {
            asyncGetList(type, Picture.PLATE_All);
            return null;
        }
        return list;
    }


    public Picture getOnePicture() {
        List<Picture> list = Picture.dao
                .find("SELECT * FROM picture " +
                        "WHERE to_days(`pictureDate`) = to_days(now()) " +
                        "ORDER BY rand() LIMIT 1");
        if (null == list || list.isEmpty()) {
            PoolExecutorKit.INSTANCE.execute(spider::get);
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

    /**
     * 异步的获取数据
     *
     * @param type type
     * @param plate plate
     */
    private void asyncGetList(String type, int plate) {
        PoolExecutorKit.INSTANCE.execute(() ->
                spider.getList(new PictureOption()
                        .setType(type)
                        .setPlate(plate)));
    }
}

package cn.htwinkle.web._front.picture;

import cn.htwinkle.web.base.BaseService;
import cn.htwinkle.web.constants.Constants;
import cn.htwinkle.web.domain.PictureOption;
import cn.htwinkle.web.kit.PoolExecutorKit;
import cn.htwinkle.web.kit.ProxyKit;
import cn.htwinkle.web.kit.Safety;
import cn.htwinkle.web.model.Picture;
import cn.htwinkle.web.spider.ISpider;
import cn.htwinkle.web.spider.PictureSpiderImpl;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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

    public static final String BASE_IMG_JPG = "http://htwinkle.cn/images/baseImg.jpg";
    public static final String BING_COM = "https://www.bing.com";
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
        if (CollUtil.isEmpty(list)) {
            asyncGetList(type, Picture.PLATE_DESK);
            return getDefaultPicArr();
        }
        List<String> filterList = list
                .stream()
                .map(item -> ProxyKit.getProxyUrl(item.getPictureUrl()))
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
                        "where to_days(`pictureDate`) = to_days(now()) " +
                        "AND pictureTypes = ? " +
                        "AND picturePlate = ? " +
                        "ORDER BY rand() LIMIT 0," + num, type, plate);
        if (CollUtil.isEmpty(list)) {
            asyncGetList(type, plate);
        }
        return list;
    }

    public List<Picture> getPictureList(int num, String type) {
        List<Picture> list = Picture.dao.find(
                "SELECT * from picture " +
                        "where to_days(`pictureDate`) = to_days(now()) " +
                        "AND pictureTypes = ? " +
                        "ORDER BY rand() LIMIT 0," + num, type);
        if (CollUtil.isEmpty(list)) {
            asyncGetList(type, Picture.PLATE_All);
            return null;
        }
        return list.stream().map(item -> item.setPictureUrl(ProxyKit.getProxyUrl(item.getPictureUrl()))).collect(Collectors.toList());
    }

    public Picture getOnePicture() {
        return getOnePicture(Picture.PLATE_DESK, Constants.G3_BIZHI_TYPES[0]);
    }

    public String getBiyingPic() {
        String url = BING_COM + "/HPImageArchive.aspx?format=js&idx=0&n=1";
        return Safety.get(() -> {
            String response = HttpUtil.get(url);
            JSONObject jsonObject = JSONObject.parseObject(response);
            if (jsonObject.containsKey("images")) {
                JSONArray array = jsonObject.getJSONArray("images");
                if (CollUtil.isNotEmpty(array)) {
                    JSONObject img = array.getJSONObject(0);
                    if (img.containsKey("url")) {
                        return BING_COM + img.get("url");
                    }
                }
            }
            return BASE_IMG_JPG;
        }, true, BASE_IMG_JPG);
    }

    public Picture getOnePicture(int plate, String type) {
        List<Picture> list = Picture.dao
                .find("SELECT * FROM picture " +
                        "WHERE to_days(`pictureDate`) = to_days(now()) " +
                        "AND pictureTypes = ? " +
                        "AND picturePlate = ? " +
                        "ORDER BY rand() LIMIT 1", type, plate);
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
     * @param type  type
     * @param plate plate
     */
    private void asyncGetList(String type, int plate) {
        PoolExecutorKit.INSTANCE.execute(() ->
                spider.getList(new PictureOption()
                        .setType(type)
                        .setPlate(plate)));
    }
}

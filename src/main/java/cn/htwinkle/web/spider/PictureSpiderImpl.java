package cn.htwinkle.web.spider;

import cn.htwinkle.web.constants.Constants;
import cn.htwinkle.web.model.Picture;
import com.jfinal.kit.Kv;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

/**
 * 通过图片的类型爬取图片的实现类
 *
 * @author : twinkle
 * @date : 2020/3/15 11:15
 */
public class PictureSpiderImpl implements ISpider<Picture> {
    /**
     * PictureSpiderImpl的输出日志对象
     */
    private static final Logger LOGGER = Logger.getLogger(PictureSpiderImpl.class.getName());

    @Override
    public Picture get() {
        Kv kv = Kv.create().set("type", getDefaultType());
        return get(kv);
    }

    @Override
    public Picture get(Kv kv) {
        List<Picture> list = getList(kv);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<Picture> getList() {
        Kv kv = Kv.create().set("type", getDefaultType());
        return getList(kv);
    }

    @Override
    public List<Picture> getList(Kv kv) {
        return getPictureFromHtml(kv);
    }

    private String getDefaultType() {
        return Constants.G3_BIZHI_TYPES[0];
    }

    /**
     * 轮询获取多张图片
     * 翻页为5次
     *
     * @return List<Picture>
     */
    private List<Picture> getPictureFromHtml(Kv kv) {
        List<Picture> list = new ArrayList<>();
        fillerMobilePicList(kv.getStr("type"), list);
        fillerComputerPicList(kv.getStr("type"), list);
        return list;
    }

    private void fillerComputerPicList(String picType, List<Picture> list) {
        IntStream.range(1, 10).forEach(index -> {
            try {
                List<Picture> temp = getPictureFromHtmlComputerBy(picType, index);
                list.addAll(Optional.ofNullable(temp).orElse(new ArrayList<>()));
            } catch (Exception e) {
                LOGGER.error("解析电脑壁纸失败 : " + e.getLocalizedMessage());
            }
        });
    }

    private void fillerMobilePicList(String picType, List<Picture> list) {
        IntStream.range(0, 10).forEach(index -> {
            try {
                List<Picture> temp = getPictureFromHtmlMobileBy(picType, index);
                list.addAll(Optional.ofNullable(temp).orElse(new ArrayList<>()));
            } catch (Exception e) {
                LOGGER.error("解析手机壁纸失败 : " + e.getLocalizedMessage());
            }
        });
    }

    /**
     * 获取图片通过手机
     *
     * @param type type
     * @param page page
     * @return List<Picture>
     */
    private List<Picture> getPictureFromHtmlMobileBy(String type, int page) {
        String url = Constants.MOBILE_G3_BIZHI_URL + type + "/1080x1920/" + page + ".html";
        return analyzePictureList(type, url, Picture.PLATE_MOBILE);
    }

    /**
     * 获取图片通过电脑
     *
     * @param type type
     * @param page page
     * @return List<Picture>
     */
    private List<Picture> getPictureFromHtmlComputerBy(String type, int page) {
        String url = Constants.COMPUTER_G3_BIZHI_URL + type + "/1920x1080/" + page + ".html";
        return analyzePictureList(type, url, Picture.PLATE_DESK);
    }

    /**
     * 解析数据
     *
     * @param type type
     * @return List<Picture>
     */
    private List<Picture> analyzePictureList(String type, String url, int plate) {
        Document doc;

        try {
            doc = Jsoup.connect(url).get();
        } catch (Exception e) {
            LOGGER.info("PictureSpiderImpl - getPictureFromHtml : " + e.getMessage());
            return null;
        }

        Elements ele = doc.select("ul.pic-list2.martop.clearfix").first()
                .getElementsByTag("img");

        if (ele == null || ele.size() == 0)
            return null;

        List<Picture> list = new ArrayList<>();
        Picture picture;
        for (Element e : ele) {
            picture = new Picture();
            picture.setPictureHost(Constants.MOBILE_G3_BIZHI_URL);
            picture.setPictureName(e.attr("title"));
            picture.setPictureTypes(type);
            picture.setPicturePlate(plate);
            picture.setPictureUrl(getReplaceSrc(e, plate));
            picture.setPictureDate(LocalDateTime.now());
            picture.save();
            list.add(picture);
        }
        return list;
    }

    @NotNull
    private String getReplaceSrc(Element e, int plate) {
        if (e.hasAttr("src")) {
            String value = e.attr("src").replace("https", "http");
            return plate == Picture.PLATE_MOBILE ?
                    value.replace("208x312c5", "1080x1920c") :
                    value.replace("208x130c5", "1920x1080c");
        }
        return "";
    }


}

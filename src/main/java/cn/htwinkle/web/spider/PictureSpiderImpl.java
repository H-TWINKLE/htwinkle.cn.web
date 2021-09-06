package cn.htwinkle.web.spider;

import cn.htwinkle.web.constants.Constants;
import cn.htwinkle.web.domain.PictureOption;
import cn.htwinkle.web.model.Picture;
import com.jfinal.kit.StrKit;
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
public class PictureSpiderImpl implements ISpider<Picture, PictureOption> {
    /**
     * PictureSpiderImpl的输出日志对象
     */
    private static final Logger LOGGER = Logger.getLogger(PictureSpiderImpl.class.getName());

    @Override
    public Picture get() {
        List<Picture> list = getList();
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public Picture get(PictureOption option) {
        List<Picture> list = getList(option);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<Picture> getList() {
        PictureOption option = new PictureOption();
        option.setPlate(Picture.PLATE_All);
        option.setType(getDefaultType());
        return getList(option);
    }

    @Override
    public List<Picture> getList(PictureOption option) {
        return getPictureFromHtml(option);
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
    private List<Picture> getPictureFromHtml(PictureOption option) {
        List<Picture> list = new ArrayList<>();
        if (Picture.PLATE_All == option.getPlate() || Picture.PLATE_MOBILE == option.getPlate()) {
            fillerMobilePicList(option.getType(), list);
        }
        if (Picture.PLATE_All == option.getPlate() || Picture.PLATE_DESK == option.getPlate()) {
            fillerDeskPicList(option.getType(), list);
        }
        return list;
    }

    private void fillerDeskPicList(String picType, List<Picture> list) {
        IntStream.range(1, 10).forEach(index -> {
            try {
                String url = getHost(Picture.PLATE_DESK) + picType + "/1920x1080/" + index + ".html";
                List<Picture> temp = analyzePictureList(picType, url, Picture.PLATE_DESK);
                list.addAll(Optional.ofNullable(temp).orElse(new ArrayList<>()));
            } catch (Exception e) {
                LOGGER.error("解析电脑壁纸失败 : " + e.getLocalizedMessage());
            }
        });
    }

    private void fillerMobilePicList(String picType, List<Picture> list) {
        IntStream.range(0, 10).forEach(index -> {
            try {
                String url = getHost(Picture.PLATE_MOBILE) + "/bizhi/" + picType + "/1080x1920/" + index + ".html";
                List<Picture> temp = analyzePictureList(picType, url, Picture.PLATE_MOBILE);
                list.addAll(Optional.ofNullable(temp).orElse(new ArrayList<>()));
            } catch (Exception e) {
                LOGGER.error("解析手机壁纸失败 : " + e.getLocalizedMessage());
            }
        });
    }

    /**
     * 获取图片通过电脑
     *
     * @param type type
     * @param page page
     * @return List<Picture>
     */
    private List<Picture> getPictureFromHtmlBy(String type, String width, int plate, int page) {
        String url = getHost(plate) + type + width + page + ".html";
        return analyzePictureList(type, url, plate);
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
            LOGGER.info(String.format("获取图片失败：地址 %s 原因 %s", url, e.getMessage()));
            return null;
        }

        Elements aLinks = doc.select("ul.pic-list2.clearfix").first()
                .getElementsByTag("a");

        if (aLinks == null || aLinks.size() == 0)
            return null;

        List<Picture> list = new ArrayList<>();

        for (Element aLink : aLinks) {
            Elements imgEles = aLink.getElementsByTag("img");
            if (imgEles != null && !aLink.attr("href").contains("exe")) {
                List<Picture> childList = analyzePictureDetailList(imgEles.first().attr("title"),
                        getHost(plate) + aLink.attr("href"), type, plate);
                if (null != childList && !childList.isEmpty()) {
                    list.addAll(childList);
                }
            }
        }
        return list;
    }

    /**
     * 获取子页面具体的图片信息
     *
     * @param title title
     * @param url   url
     * @param type  type
     * @param plate plate
     * @return List<Picture>
     */
    private List<Picture> analyzePictureDetailList(String title, String url, String type, int plate) {
        Document doc;

        try {
            doc = Jsoup.connect(url).get();
        } catch (Exception e) {
            LOGGER.info(String.format("获取图片失败：地址 %s 原因 %s", url, e.getMessage()));
            return null;
        }

        Element ele = doc.getElementById("showImg");

        if (ele == null)
            return null;

        Elements imgEles = ele.getElementsByTag("img");

        if (imgEles == null || imgEles.isEmpty())
            return null;

        List<Picture> list = new ArrayList<>();
        for (Element imgEle : imgEles) {
            fillerPicture(title, type, plate, list, imgEle);
        }
        return list;
    }

    /**
     * 填充picture
     *
     * @param title  title
     * @param type   type
     * @param plate  plate
     * @param list   list
     * @param imgEle imgEle
     */
    private void fillerPicture(String title, String type, int plate, List<Picture> list, Element imgEle) {
        Picture picture = new Picture();
        picture.setPictureHost(getHost(plate));
        picture.setPictureTypes(type);
        picture.setPicturePlate(plate);
        picture.setPictureDate(LocalDateTime.now());
        picture.setPictureName(title);
        picture.setPictureUrl(getReplaceSrcDetailBy(imgEle, plate));
        if (StrKit.notBlank(picture.getPictureUrl())) {
            picture.save();
            list.add(picture);
        }
    }

    @NotNull
    private String getReplaceSrcDetailBy(Element e, int plate) {
        String src = getSrc(e);
        if (null == src) {
            return "";
        }
        if (plate == Picture.PLATE_MOBILE) {
            return src.replace("120x90c5", "1080x1920c")
                    .replace("120x90", "1080x1920");
        }
        if (plate == Picture.PLATE_DESK) {
            return src.replace("144x90c5", "1920x1080c")
                    .replace("144x90", "1920x1080");
        }
        return "";
    }

    private String getSrc(Element e) {
        if (e.hasAttr("src")) {
            return e.attr("src").replace("https", "http");
        }
        if (e.hasAttr("srcs")) {
            return e.attr("srcs").replace("https", "http");
        }
        return null;
    }

    /**
     * 获取houst
     *
     * @param plate plate
     * @return String
     */
    private String getHost(int plate) {
        return plate == Picture.PLATE_MOBILE ? Constants.SJ_ZOL_COM_CN_BIZHI : Constants.DESK_ZOL_COM_CN;
    }
}

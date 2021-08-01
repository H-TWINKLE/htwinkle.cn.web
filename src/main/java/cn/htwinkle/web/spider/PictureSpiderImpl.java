package cn.htwinkle.web.spider;

import cn.htwinkle.web.constants.Constants;
import cn.htwinkle.web.model.Picture;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

/**
 * TODO 描述用途
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
        String types = Constants.G3_BIZHI_TYPES[new Random().nextInt(4)];
        List<Picture> list = getPictureFromHtml(types);
        return list == null ? null : list.get(0);
    }

    @Override
    public Picture get(String types) {
        List<Picture> list = getPictureFromHtml(types);
        return list == null ? null : list.get(0);
    }

    private List<Picture> getPictureFromHtml(String types) {
        Document doc;

        try {
            doc = Jsoup.connect(Constants.G3_BIZHI_URL + types + "/").get();
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
            picture.setPictureHost(Constants.G3_BIZHI_URL);
            picture.setPictureName(e.attr("title"));
            picture.setPictureTypes(types);
            picture.setPictureUrl(e.attr("src").replace("https", "http")
                    .replace("208x312c5", "1920x1080c"));
            picture.setPictureDate(new Date());
            picture.save();
            list.add(picture);
        }
        return list;
    }


}

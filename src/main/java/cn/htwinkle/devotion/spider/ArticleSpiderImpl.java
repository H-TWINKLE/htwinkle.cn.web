package cn.htwinkle.devotion.spider;

import cn.htwinkle.devotion.constants.Constants;
import cn.htwinkle.devotion.model.Article;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.Date;
import java.util.logging.Logger;

/**
 * TODO 描述用途
 *
 * @author : twinkle
 * @date : 2020/3/15 11:04
 */
public class ArticleSpiderImpl implements ISpider<Article> {

    /**
     * ArticleSpiderImpl的输出日志对象
     */
    private static final Logger LOGGER = Logger.getLogger(ArticleSpiderImpl.class.getName());


    @Override
    public Article get() {
        Document doc;

        try {
            doc = Jsoup.connect(Constants.MEI_RI_YI_WEN_URL).validateTLSCertificates(false).get();
        } catch (Exception e) {
            LOGGER.info("ArticleSpiderImpl - get : " + e.getMessage());
            return null;
        }

        if (doc == null)
            return null;

        Element ele = doc.getElementById("article_show");

        if (ele == null) {
            return null;
        }

        Article art = new Article();
        art.setArticleTitle(ele.getElementsByTag("h1").first().text());
        art.setArticleAuthor(ele.getElementsByTag("span").first().text());
        art.setArticleContent(ele.getElementsByClass("article_text").first().getElementsByTag("p").toString());
        art.setArticleDate(new Date(System.currentTimeMillis()));

        art.save();

        return art;
    }

    @Override
    public Article get(String types) {
        return get();
    }
}

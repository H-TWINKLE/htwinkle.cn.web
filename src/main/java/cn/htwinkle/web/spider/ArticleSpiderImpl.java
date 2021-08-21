package cn.htwinkle.web.spider;

import cn.htwinkle.web.constants.Constants;
import cn.htwinkle.web.model.Article;
import org.jetbrains.annotations.Nullable;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * 爬取文章的工具实现类
 * 不存在类型，纯粹随机
 *
 * @author : twinkle
 * @date : 2020/3/15 11:04
 */
public class ArticleSpiderImpl implements ISpider<Article> {

    /**
     * ArticleSpiderImpl的输出日志对象
     */
    private static final Logger LOGGER = Logger.getLogger(ArticleSpiderImpl.class.getName());
    /**
     * 默认列表爬虫次数
     */
    public static final int DEFAULT_SPIDER_COUNT = 10;


    @Override
    public Article get() {
        return getArticle();
    }

    @Nullable
    private Article getArticle() {
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
        art.setArticleContent(ele.getElementsByClass("article_text").first().
                getElementsByTag("p").toString());
        art.setArticleDate(new Date(System.currentTimeMillis()));
        art.save();
        return art;
    }

    @Override
    public Article get(String types) {
        return get();
    }

    @Override
    public List<Article> getList() {
        List<Article> articleList = new ArrayList<>();
        for (int i = 0; i < DEFAULT_SPIDER_COUNT; i++) {
            Article article = getArticle();
            if (null != article) {
                articleList.add(article);
            }
        }
        return articleList;
    }

    @Override
    public List<Article> getList(String types) {
        return getList();
    }


}

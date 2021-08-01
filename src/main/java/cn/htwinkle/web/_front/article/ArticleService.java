package cn.htwinkle.web._front.article;

import cn.htwinkle.web.base.BaseService;
import cn.htwinkle.web.kit.PoolExecutorKit;
import cn.htwinkle.web.model.Article;
import cn.htwinkle.web.spider.ArticleSpiderImpl;
import cn.htwinkle.web.spider.ISpider;
import com.jfinal.aop.Aop;

import java.util.List;

/**
 * 每日一文的服务层
 *
 * @author : twinkle
 * @date : 2020/3/15 11:37
 */
public class ArticleService extends BaseService {

    private ISpider<Article> spider = Aop.get(ArticleSpiderImpl.class);

    /**
     * 获取到一篇文章
     *
     * @return Article
     */
    public Article getOneArticle() {
        Article article = Article.dao.findFirst(
                "SELECT * FROM `article` AS t1 " +
                        "JOIN (SELECT ROUND(RAND() * " +
                        "((SELECT MAX(articleId) FROM `article`)-(SELECT MIN(articleId) FROM `article`))+" +
                        "(SELECT MIN(articleId) FROM `article`)) AS id) AS t2 " +
                        "WHERE t1.articleId >= t2.id ORDER BY t1.articleId LIMIT 1");
        if (article == null) {
            return spider.get();
        }
        return article;
    }

    /**
     * 获得一定数量的文章
     *
     * @param num num
     * @return List<Article>
     */
    public List<Article> getArticleList(Integer num) {
        List<Article> list = Article.dao.find(
                "SELECT t1.articleId,t1.articleTitle,t1.articleAuthor,t1.articleContent,t1.articleDate " +
                        "FROM `article` AS t1 " +
                        "JOIN (SELECT ROUND(RAND() * " +
                        "((SELECT MAX(articleId) FROM `article`)-(SELECT MIN(articleId) FROM `article`))+" +
                        "(SELECT MIN(articleId) FROM `article`)) AS id) AS t2 " +
                        "WHERE t1.articleId >= t2.id ORDER BY t1.articleId LIMIT 0,?", num);
        if (list == null) {
            PoolExecutorKit.INSTANCE.execute(() -> spider.get());
            return null;
        }
        return list;
    }


}

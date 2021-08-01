package cn.htwinkle.web._front.article;

import cn.htwinkle.web.base.BaseController;
import cn.htwinkle.web.model.Article;
import com.jfinal.aop.Inject;

import java.util.List;

/**
 * 每日一问文的控制器
 *
 * @author : twinkle
 * @date : 2020/3/15 11:36
 */
public class ArticleController extends BaseController {

    @Inject
    ArticleService articleService;


    @Override
    public void index() {
        setTitle("每日一文");
        setAttr("article", articleService.getOneArticle());
        render("index.html");
    }

    @Override
    public void api() {
        Integer num = getDefaultNumForApi();
        List<Article> list = articleService.getArticleList(num);
        renderJson(getDefaultJson(num, list));
    }


}

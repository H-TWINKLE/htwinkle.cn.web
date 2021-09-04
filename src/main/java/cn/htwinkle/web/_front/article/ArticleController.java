package cn.htwinkle.web._front.article;

import cn.htwinkle.web.base.BaseController;
import cn.htwinkle.web.model.Article;
import com.jfinal.aop.Inject;
import org.leon.swagger.annotation.Api;
import org.leon.swagger.annotation.ApiOperation;
import org.leon.swagger.annotation.Param;
import org.leon.swagger.model.constant.HttpMethod;

import java.util.List;

/**
 * 每日一问文的控制器
 *
 * @author : twinkle
 * @date : 2020/3/15 11:36
 */
@Api(tag = "Article", description = "每日一文")
public class ArticleController extends BaseController {

    @Inject
    ArticleService articleService;

    @Override
    public void index() {
        setTitle("每日一文");
        setAttr("article", articleService.getOneArticle());
        render("index.html");
    }

    @ApiOperation(url = "/article/api",
            tag = "Article",
            httpMethod = HttpMethod.GET,
            description = "获取文章列表")
    @Param(name = "num", description = "文章的条数",
            defaultValue = "10")
    public void api() {
        Integer num = getDefaultNumForApi();
        List<Article> list = articleService.getArticleList(num);
        renderJson(getDefaultJson(num, list));
    }

}

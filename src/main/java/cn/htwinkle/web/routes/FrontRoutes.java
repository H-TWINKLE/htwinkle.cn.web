package cn.htwinkle.web.routes;

import cn.htwinkle.web._front.article.ArticleController;
import cn.htwinkle.web._front.diao.DiAoController;
import cn.htwinkle.web._front.idea.IdeaController;
import cn.htwinkle.web._front.index.IndexController;
import cn.htwinkle.web._front.picture.PictureController;
import com.jfinal.config.Routes;

/**
 * TODO 描述用途
 *
 * @author : twinkle
 * @date : 2020/3/9 21:53
 */
public class FrontRoutes extends Routes {
    @Override
    public void config() {
        setBaseViewPath("/template");
        add("/", IndexController.class, "/index");
        add("/picture", PictureController.class, "/picture");
        add("/article", ArticleController.class, "/article");
        add("/diao", DiAoController.class, "/diao");
        add("/idea", IdeaController.class, "/idea");
    }
}

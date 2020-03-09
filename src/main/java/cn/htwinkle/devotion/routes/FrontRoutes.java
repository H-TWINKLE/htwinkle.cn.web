package cn.htwinkle.devotion.routes;

import cn.htwinkle.devotion._front.index.IndexController;
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
    }
}

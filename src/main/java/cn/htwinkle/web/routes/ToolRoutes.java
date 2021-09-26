package cn.htwinkle.web.routes;

import cn.htwinkle.web._end.SwaggerController;
import cn.htwinkle.web._end._api.ApiToolController;
import com.jfinal.config.Routes;

/**
 * 前端路由器
 *
 * @author : twinkle
 * @date : 2020/3/9 21:53
 */
public class ToolRoutes extends Routes {
    @Override
    public void config() {
        add("/swagger", SwaggerController.class);
        add("/api", ApiToolController.class);
    }
}

package cn.htwinkle.web._end;

import cn.htwinkle.web.base.BaseController;
import org.leon.swagger.utils.SwaggerJsonUtil;

/**
 * swagger路由
 *
 * @author : twinkle
 * @date : 2021/8/21 18:39
 */
public class SwaggerController extends BaseController {

    public void index() {
        setTitle("swagger文档");
        this.render("index.html");
    }

    public void api() {
        this.renderText(SwaggerJsonUtil.getJson());
    }

    public void downloadJson() {
        SwaggerJsonUtil.writeJson(this.getRequest(), SwaggerJsonUtil.getJson());
        this.renderFile(SwaggerJsonUtil.getJsonFile(this.getRequest()));
    }
}

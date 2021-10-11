package cn.htwinkle.web._end._api;

import cn.htwinkle.web.base.BaseController;
import com.jfinal.aop.Inject;
import com.jfinal.kit.PropKit;
import org.leon.swagger.annotation.Api;
import org.leon.swagger.annotation.ApiOperation;
import org.leon.swagger.annotation.Param;
import org.leon.swagger.model.constant.HttpMethod;

import static cn.htwinkle.web.constants.Constants.UPDATE_TIME;

/**
 * 实现api接口的类型
 *
 * @author : twinkle
 * @date : 2021/8/21 18:14
 */
@Api(tag = "ApiToolCenter", description = "API工具类")
public class ApiToolController extends BaseController {

    @Inject
    private ApiToolService toolService;

    @ApiOperation(url = "/api", tag = "ApiToolCenter",
            httpMethod = HttpMethod.GET, description = "获取接口基本信息")
    @Override
    public void index() {
        renderDefaultJson();
    }

    @ApiOperation(url = "/api/transferUrl", tag = "ApiToolCenter",
            httpMethod = HttpMethod.POST, description = "接口转换工具，避免跨域问题")
    @Param(name = "url", description = "需要进行代理的地址", required = true)
    public void transferUrl(String url) {
        String resp = toolService.transferUrl(url);
        renderJson(resp);
    }

    @ApiOperation(url = "/api/updateTime", tag = "ApiToolCenter",
            httpMethod = HttpMethod.GET, description = "获取当前版本信息")
    public void updateTime() {
        renderText(PropKit.get(UPDATE_TIME));
    }
}

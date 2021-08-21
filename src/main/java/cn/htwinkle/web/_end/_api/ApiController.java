package cn.htwinkle.web._end._api;

import cn.htwinkle.web.base.BaseController;
import com.jfinal.kit.Kv;
import org.leon.swagger.annotation.Api;
import org.leon.swagger.annotation.ApiOperation;
import org.leon.swagger.model.constant.HttpMethod;

/**
 * 实现api接口的类型
 *
 * @author : twinkle
 * @date : 2021/8/21 18:14
 */
@Api(tag = "ApiCenter", description = "API接口")
public class ApiController extends BaseController {

    @ApiOperation(url = "/api", tag = "ApiCenter",
            httpMethod = HttpMethod.GET, description = "获取接口基本信息")
    @Override
    public void index() {
        renderDefaultJson();
    }
}

package cn.htwinkle.web._front.idea;

import cn.htwinkle.web.base.BaseController;
import com.jfinal.aop.Inject;
import com.jfinal.kit.StrKit;
import org.leon.swagger.annotation.Api;
import org.leon.swagger.annotation.ApiOperation;
import org.leon.swagger.annotation.Param;
import org.leon.swagger.annotation.Params;
import org.leon.swagger.model.constant.HttpMethod;

import static cn.htwinkle.web._front.idea.IdeaService.JIHUOMA_ZIP;

/**
 * idea的注册码
 *
 * @author : twinkle
 * @date : 2020/9/27 20:07
 */
@Api(tag = "Idea", description = "获取idea激活码")
public class IdeaController extends BaseController {

    public static final String DEFAULT_KEY = "lookdiv.com";

    public static final String DEFAULT_FILTER = "jpg";

    @Inject
    private IdeaService ideaService;

    @Override
    @ApiOperation(url = "/idea/",
            tag = "Idea",
            httpMethod = HttpMethod.GET,
            description = "获取idea激活码")
    public void index() {
        renderJson(ideaService.getListCode(DEFAULT_FILTER, DEFAULT_KEY));
    }

    @ApiOperation(url = "/idea/filter",
            tag = "Idea",
            httpMethod = HttpMethod.GET,
            description = "获取idea激活码")
    @Params({
            @Param(name = "filter", description = "过滤条件", defaultValue = DEFAULT_FILTER),
            @Param(name = "key", description = "关键字", defaultValue = DEFAULT_KEY)
    })
    public void filter(String filter, String key) {
        filter = getDefault(filter, DEFAULT_FILTER);
        key = getDefault(key, DEFAULT_KEY);
        renderJson(ideaService.getListCode(filter, key));
    }

    @ApiOperation(url = "/idea/getCodeFile",
            tag = "Idea",
            httpMethod = HttpMethod.GET,
            description = "获取idea激活码文件")
    public void getCodeFile() {
        redirect(JIHUOMA_ZIP);
    }

    @ApiOperation(url = "/idea/medeming",
            tag = "Idea",
            httpMethod = HttpMethod.GET,
            description = "获取idea激活码by - medeming")
    @Param(name = "filter", description = "过滤条件", defaultValue = DEFAULT_FILTER)
    public void medeming(String filter) {
        filter = getDefault(filter, DEFAULT_FILTER);
        renderJson(ideaService.getZipFile(filter));
    }

    @ApiOperation(url = "/idea/lookdiv",
            tag = "Idea",
            httpMethod = HttpMethod.GET,
            description = "获取idea激活码by - lookdiv")
    @Param(name = "key", description = "关键字", defaultValue = DEFAULT_KEY)
    public void lookdiv(String key) {
        key = getDefault(key, DEFAULT_KEY);
        renderJson(ideaService.getRetCode(key));
    }

    private String getDefault(String key, String defaultValue) {
        if (StrKit.isBlank(key)) {
            return defaultValue;
        }
        return key;
    }
}

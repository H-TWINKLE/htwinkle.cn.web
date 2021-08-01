package cn.htwinkle.web._front.idea;

import cn.htwinkle.web.base.BaseController;
import com.jfinal.aop.Inject;
import com.jfinal.kit.StrKit;

/**
 * idea的注册码
 *
 * @author : twinkle
 * @date : 2020/9/27 20:07
 */
public class IdeaController extends BaseController {

    public static final String DEFAULT_KEY = "5263";

    public static final String DEFAULT_FILTER = "jpg";
    @Inject
    private IdeaService ideaService;

    public void filter(String filter, String key) {
        filter = getDefault(filter, DEFAULT_FILTER);
        key = getDefault(key, DEFAULT_KEY);
        renderJson(ideaService.getListCode(filter, key));
    }

    public void getCodeFile() {
        redirect("http://idea.medeming.com/jihuoma/images/jihuoma.zip");
    }

    public void medeming(String filter) {
        filter = getDefault(filter, DEFAULT_FILTER);
        renderJson(ideaService.getZipFile(filter));
    }

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

    @Override
    public void index() {
        renderJson(ideaService.getListCode(DEFAULT_FILTER, DEFAULT_KEY));
    }

    @Override
    public void api() {

    }
}

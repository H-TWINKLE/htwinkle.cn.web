package cn.htwinkle.devotion._front.idea;

import cn.htwinkle.devotion.base.BaseController;
import com.jfinal.aop.Inject;

/**
 * idea的注册码
 *
 * @author : twinkle
 * @date : 2020/9/27 20:07
 */
public class IdeaController extends BaseController {

    @Inject
    private IdeaService ideaService;

    @Override
    public void index() {
        renderJson(ideaService.getListCode());
    }

    public void getCodeFile() {
        redirect("http://idea.medeming.com/jihuoma/images/jihuoma.zip");
    }

    public void medeming() {
        renderJson(ideaService.getZipFile());
    }

    public void lookdiv() {
        renderJson(ideaService.getRetCode());
    }

    @Override
    public void api() {

    }
}

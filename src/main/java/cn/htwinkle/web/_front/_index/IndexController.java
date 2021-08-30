package cn.htwinkle.web._front._index;

import cn.htwinkle.web._front.picture.PictureService;
import cn.htwinkle.web.base.BaseController;
import cn.htwinkle.web.constants.Constants;
import com.jfinal.aop.Inject;
import com.jfinal.kit.PropKit;

import static cn.htwinkle.web.interceptor.GolbalInterceptor.ATOMIC_INTEGER;

/**
 * 主页类型
 *
 * @author : twinkle
 * @date : 2020/3/9 21:46
 */
public class IndexController extends BaseController {

    @Inject
    private PictureService pictureService;

    @Override
    public void index() {
        setTitle(PropKit.get(Constants.RECORD_TITLE));
        set("picArr",pictureService.getPictureListIndexBy());
        set("indexPage", true);
        set("globalCount", ATOMIC_INTEGER.get());
        render("index.html");
    }
}

package cn.htwinkle.devotion._front.index;

import cn.htwinkle.devotion.base.BaseController;
import cn.htwinkle.devotion.constants.Constants;
import com.jfinal.kit.PropKit;

import static cn.htwinkle.devotion.interceptor.GolbalInterceptor.ATOMIC_INTEGER;

/**
 * 主页类型
 *
 * @author : twinkle
 * @date : 2020/3/9 21:46
 */
public class IndexController extends BaseController {

    @Override
    public void index() {
        setTitle(PropKit.get(Constants.RECORD_TITLE));
        set("globalCount", ATOMIC_INTEGER.get());
        render("index.html");
    }

    @Override
    public void api() {
        renderDefaultJson();
    }


}

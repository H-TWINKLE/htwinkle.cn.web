package cn.htwinkle.devotion._front.index;

import cn.htwinkle.devotion.base.BaseController;
import cn.htwinkle.devotion.constants.Constants;
import com.jfinal.kit.PropKit;

/**
 * 主页类型
 *
 * @author : twinkle
 * @date : 2020/3/9 21:46
 */
public class IndexController extends BaseController {

    public void index() {
        setTitle(PropKit.get(Constants.RECORD_TITLE));
        setMsgTip("123");
        render("index.html");
    }


}

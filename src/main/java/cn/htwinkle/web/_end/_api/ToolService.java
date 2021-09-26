package cn.htwinkle.web._end._api;

import cn.htwinkle.web.base.BaseService;
import com.jfinal.kit.HttpKit;

/**
 * api接口的服务类
 *
 * @author : twinkle
 * @date : 2021/8/21 18:14
 */
public class ToolService extends BaseService {

    public String transferUrl(String url) {
        return HttpKit.get(url);
    }
}

package cn.htwinkle.web._end._api;

import cn.htwinkle.web.base.BaseService;
import com.jfinal.kit.HttpKit;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;

/**
 * api接口的服务类
 *
 * @author : twinkle
 * @date : 2021/8/21 18:14
 */
public class ApiToolService extends BaseService {

    public String transferUrl(String url) {
        if (StrKit.notBlank(url)) {
            String resp = HttpKit.get(url);
            if (StrKit.notBlank(resp)) {
                return resp;
            }
        }
        return Ret.fail("is empty").toJson();
    }
}

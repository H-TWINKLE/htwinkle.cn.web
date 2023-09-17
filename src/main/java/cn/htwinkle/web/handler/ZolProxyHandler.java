package cn.htwinkle.web.handler;

import cn.htwinkle.web.render.MimeTypeRender;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.Method;
import com.jfinal.handler.Handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 代理的网站
 *
 * @author : twinkle
 * @date : 2020/12/28 22:21
 */
public class ZolProxyHandler extends Handler {

    public static final String ZOL_PROXY = "/zolProxy/";

    @Override
    public void handle(String target, HttpServletRequest request, HttpServletResponse response,
                       boolean[] isHandled) {
        if (StrUtil.isNotEmpty(target) && (target.contains(ZOL_PROXY))) {
            forwardProxy(target, request, response);
            isHandled[0] = true;
            return;
        }
        next.handle(target, request, response, isHandled);
    }

    private void forwardProxy(String target, HttpServletRequest request, HttpServletResponse response) {
        String realPath = target.replace(ZOL_PROXY, "");
        HttpResponse httpResponse = HttpRequest.of(realPath).header("Referer", "https://desk.zol.com.cn/").method(Method.GET).execute();
        new MimeTypeRender(realPath, httpResponse.bodyStream()).setContext(request, response).render();
    }
}

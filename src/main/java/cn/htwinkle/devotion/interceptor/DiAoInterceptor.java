package cn.htwinkle.devotion.interceptor;

import cn.htwinkle.devotion.constants.Constants;
import cn.htwinkle.devotion.kit.EhcacheKit;
import cn.htwinkle.devotion.model.User;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

/**
 * DiAoInterceptor
 *
 * @author : twinkle
 * @date : 2020/3/15 14:46
 */
public class DiAoInterceptor implements Interceptor {

    @Override
    public void intercept(Invocation invocation) {

        String cookies = invocation.getController().getCookie(Constants.SESSION__DIAO_KEY);
        if (cookies == null) {
            invocation.getController().forwardAction("/diao/login");
            return;
        }
        User user = EhcacheKit.INSTANCE.getValue(cookies, User.class);
        if (user == null) {
            invocation.getController().forwardAction("/diao/login");
            return;
        }

        invocation.getController().set(Constants.SESSION_USER, user);
        invocation.invoke();

    }
}

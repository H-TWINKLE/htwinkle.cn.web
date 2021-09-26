package cn.htwinkle.web.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

public class AllowOriginInterceptor implements Interceptor {

    @Override
    public void intercept(Invocation arg0) {

        Controller c = arg0.getController();
        c.getResponse().setHeader("Access-Control-Allow-Origin", "*");
        c.getResponse().setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS, DELETE, PUT");
        c.getResponse().setHeader("Access-Control-Max-Age", "3600");
        c.getResponse().setHeader("Access-Control-Allow-Headers",
                "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

        arg0.invoke();

    }

}

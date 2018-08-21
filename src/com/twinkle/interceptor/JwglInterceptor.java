package com.twinkle.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

public class JwglInterceptor implements Interceptor {

	@Override
	public void intercept(Invocation inv) {

		Controller r = inv.getController();

		if (r.getPara("admin").length() != 12) {
			
			r.setAttr("adminMsg", "请输入12位学号喔!");			
			r.render("/pages/page_jwgl.html");
			
		} else if (r.getPara("pass").length() < 6) {
			
			r.setAttr("passMsg", "密码最低为6位!");
			r.render("/pages/page_jwgl.html");			
		}

		else {
			inv.invoke();
		}				
	}
	
}

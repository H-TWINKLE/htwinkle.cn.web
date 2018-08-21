package com.twinkle.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

public class JLSuccessInterceptor implements Interceptor {

	@Override
	public void intercept(Invocation inv) {
		Controller c = inv.getController();

		if (c.getAttr("jwgl") == null) {
			c.render("/pages/page_jwgl.html");
			return;
		}
		
	    inv.invoke();

	}

}

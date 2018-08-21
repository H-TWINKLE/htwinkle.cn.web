package com.twinkle.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

public class ElSuccessInterceptor implements Interceptor{
	
	@Override
	public void intercept(Invocation inv) {
		Controller c = inv.getController();

		if (c.getAttr("eol") == null) {
			c.render("/pages/page_eol.html");
			return;
		}
		
	    inv.invoke();

	}

}

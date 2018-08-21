package com.twinkle.controller;

import com.jfinal.core.Controller;
import com.twinkle.interceptor.GlobalCountInterceptor;


public class IndexController extends Controller {

	public void index() {

		setAttr("num", GlobalCountInterceptor.globalCount.get());
		render("/index.html");

	}

}

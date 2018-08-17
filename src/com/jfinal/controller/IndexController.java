package com.jfinal.controller;

import com.jfinal.core.Controller;
import com.jfinal.interceptor.GlobalCountInterceptor;


public class IndexController extends Controller {

	//static IndexService i = new IndexService();

	public void index() {

		setAttr("num", GlobalCountInterceptor.globalCount.get());
		render("index.html");

	}

}

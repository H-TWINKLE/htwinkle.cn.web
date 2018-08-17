package com.jfinal.controller;

import com.alibaba.fastjson.JSON;
import com.jfinal.aop.Before;
import com.jfinal.common.model.Eol;
import com.jfinal.interceptor.ElSuccessInterceptor;
import com.jfinal.interceptor.EolInterceptor;
import com.jfinal.service.EolService;
import com.jfinal.validator.JEValidator;
import com.twinkle.init.BaseController;
import com.twinkle.init.Constant;

public class EolController extends BaseController {

	static EolService eolService = new EolService();

	public void index() {
		render("/pages/page_eol.html");
	}

	@Before({ JEValidator.class, EolInterceptor.class })
	public void login() {

		Eol eol = getEol();
		if (eol.getCode() == Constant.SUCCESS) {
			setAttr("eol", eol);
			render("/pages/page_eol_info.html");
			return;
		}

		setAttr("TipMsg", eol.getTip());
		render("/pages/page_eol.html");

	}

	@Before(ElSuccessInterceptor.class)
	public void login_success() {
		render("/pages/page_eol_info.html");
	}

	@Override
	public void api() {
		renderJson(JSON.toJSONString(getEol()));

	}

	private Eol getEol() {
		String admin = getPara("admin");
		String pass = getPara("pass");
		return eolService.toLogin(admin, pass);
	}

}

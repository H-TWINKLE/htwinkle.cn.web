package com.twinkle.controller;

import com.alibaba.fastjson.JSON;
import com.jfinal.aop.Before;
import com.twinkle.common.model.Eol;
import com.twinkle.service.EolService;
import com.twinkle.utils.BaseController;
import com.twinkle.utils.Constant;
import com.twinkle.validator.JEValidator;

public class EolController extends BaseController {

	static EolService eolService = new EolService();

	public void index() {
		render("/pages/page_eol.html");
	}

	@Before({ JEValidator.class})
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

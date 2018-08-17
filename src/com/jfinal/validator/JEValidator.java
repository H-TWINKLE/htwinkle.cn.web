package com.jfinal.validator;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

public class JEValidator extends Validator {

	@Override
	protected void validate(Controller c) {
		validateRequiredString("admin", "adminMsg", "请输入学号");
		validateRequiredString("pass", "passMsg", "请输入密码");

	}

	@Override
	protected void handleError(Controller c) {
		String actionKey = getActionKey();
		
		if (actionKey.equals("/jwgl/login"))
			controller.render("/pages/page_jwgl.html");
		
		else if(actionKey.equals("/eol/login"))
			controller.render("/pages/page_eol.html");
	}

}

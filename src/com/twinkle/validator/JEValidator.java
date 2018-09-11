package com.twinkle.validator;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

public class JEValidator extends Validator {

	@Override
	protected void validate(Controller c) {
		
		validateRequiredString("admin", "adminMsg", "请输入学号");
		validateRequiredString("pass", "passMsg", "请输入密码");
		
		
		validateString("admin", 12, 12, "adminMsg", "请输入12位学号喔!");
		validateString("pass", 6, 100, datePattern, "密码最低为6位!");;

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

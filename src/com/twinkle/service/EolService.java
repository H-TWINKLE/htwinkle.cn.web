package com.twinkle.service;

import java.util.Date;

import com.jfinal.plugin.activerecord.Db;
import com.twinkle.common.model.Eol;
import com.twinkle.proxy.EolProxy;
import com.twinkle.utils.Constant;

public class EolService {

	public Eol toLogin(String admin, String pass) {
		
		EolProxy eProxy = new EolProxy();
		
		Eol eol = eProxy.eolpreLogin(admin, pass);

		if (eol == null) {
			
			eol = new Eol();
			eol.setDates(new Date(System.currentTimeMillis()));
			eol.setCode(Constant.SERVERERROR);
			eol.setTip(Constant.FAILURESERVER);
			return eol;
		}

		if (eol.getCode() == Constant.SUCCESS) {

			eol = eProxy.eolStudentIndex();

			checkEolLoginInDelete(admin);

			eol.save();

			eol.setList(eProxy.analClassTip());

		}

		return eol;
	}

	private void checkEolLoginInDelete(String admin) { // eol表存在连接，CASCADE级联策略。

		Db.update("DELETE FROM eol WHERE admin=?", admin);

	}

}

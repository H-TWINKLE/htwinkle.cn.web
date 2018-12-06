package com.twinkle.service;

import java.util.Date;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.twinkle.common.model.Eol;
import com.twinkle.common.model.Eoltip;
import com.twinkle.plugin.EolSpiderByMobile;
import com.twinkle.proxy.EolProxy;
import com.twinkle.utils.Constant;

public class EolService {

	public Eol toLoginAsOld(String admin, String pass) {

		if (admin == null || "".equals(admin)) {
			return null;
		}

		if (pass == null || "".equals(pass)) {
			return null;
		}

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

			List<Eoltip> list = eProxy.eolStudentWorkTip();

			if (list == null) {
				eol.setTip("您的所有作业都写完咯！");
				eol.setCode(Constant.WORK_COMPLETE);
				eol.save();
				return eol;
			}

			eol.save();
			eol.setList(eProxy.analClassTip(list));

		}

		return eol;
	}

	public Eol toLogin(String admin, String pass) {

		if (admin == null || "".equals(admin)) {
			return null;
		}

		if (pass == null || "".equals(pass)) {
			return null;
		}

		EolSpiderByMobile e = new EolSpiderByMobile();

		Eol eol = e.toLogin(admin, pass);

		if (eol == null) {

			eol = new Eol();
			eol.setDates(new Date(System.currentTimeMillis()));
			eol.setCode(Constant.SERVERERROR);
			eol.setTip(Constant.FAILURESERVER);
			return eol;
		}

		if (eol.getCode() == Constant.SUCCESS) {			

			checkEolLoginInDelete(admin);
			
			eol.save();

			List<List<Eoltip>> list = e.getListEolTip(eol);

			if (list == null) {
				eol.setTip("您的所有作业都写完咯！");
				eol.setCode(Constant.WORK_COMPLETE);
				eol.save();
				return eol;
			}

			eol.setList(list);

		}

		return eol;
	}

	private void checkEolLoginInDelete(String admin) { // eol表存在连接，CASCADE级联策略。

		Db.update("DELETE FROM eol WHERE admin=?", admin);

	}

}

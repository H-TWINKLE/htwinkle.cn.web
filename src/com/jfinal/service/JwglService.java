package com.jfinal.service;

import java.util.Date;

import com.jfinal.common.model.Jwgl;
import com.jfinal.common.model.Jwglinfo;
import com.jfinal.common.model.Jwglscore;
import com.jfinal.common.model.Jwglttb;
import com.jfinal.plugin.activerecord.Db;
import com.twinkle.init.CommUtils;
import com.twinkle.init.Constant;
import com.twinkle.proxy.JwglProxy;

public class JwglService {

	public Jwgl toLogin(String admin, String pass) {

		JwglProxy jProxy = new JwglProxy();

		Jwgl jwglb = null;

		for (int x = 0; x < 20; x++) {

			jwglb = jProxy.jwglLogin(admin, pass); // 尝试登录

			if (jwglb == null) {
				jwglb = new Jwgl(); // 为空则为内部错误
				jwglb.setTip(Constant.FAILURESERVER);
				jwglb.setCode(Constant.NETFAILURE);
				jwglb.setDates(new Date(System.currentTimeMillis()));
				return jwglb;

			}

			if (jwglb.getCode() == Constant.SERVERERROR)
				break; // 服务器错误

			String tip = jwglb.getTip();

			int code = jwglb.getCode();

			if (code == Constant.SUCCESS) {

				checkJwglLoginInDelete(admin); // 删除数据库中jwgl表中admin对应对应的登录数据，保持数据的唯一性。

				jwglb.save();

				jwglb.setJwglttb(jProxy.JwglTtb()); // 个人课表

				jwglb.setJwglinfo(jProxy.JwglInfo()); // 个性信息

				if (jwglb.getCode() != Constant.NEVERTOENAL) {          //没有评价不能够查成绩！
					jwglb.setJwglscore(jProxy.Jwglscore()); // 个人成绩
				}

				break;
			} else if (code == Constant.NETFAILURE || code == Constant.NETFAILURE)
				break;
			else if (CommUtils.INSTANCE.Regex(tip, "用户名不存在"))
				break;
			else if (CommUtils.INSTANCE.Regex(tip, "错误"))
				break;
		}

		return jwglb;
	}

	private void checkJwglLoginInDelete(String admin) { // jwgl表存在连接，CASCADE级联策略。

		Db.update("DELETE FROM jwgl WHERE admin=?", admin);

	}

	public Jwgl findByAdmin(String admin, String pass) {

		Jwgl jwglb = Jwgl.dao.findFirst("SELECT * FROM jwgl WHERE admin = ? AND pass=?", admin, pass);

		if (jwglb != null) {
			jwglb.setJwglinfo(Jwglinfo.dao.findFirst("SELECT * FROM jwglinfo WHERE userid = ?", jwglb.getId()));

			jwglb.setJwglscore(Jwglscore.dao.find("SELECT * FROM jwglscore WHERE userid = ?", jwglb.getId()));

			jwglb.setJwglttb(Jwglttb.dao.findFirst("SELECT * FROM jwglttb WHERE userid = ?", jwglb.getId()));

			/*
			 * if(jwglb.getJwglinfo()==null||jwglb.getJwglscore()==null||jwglb.getJwglttb()=
			 * =null) { //信息不完整 return toLogin(admin, pass); }
			 */

			return jwglb;

		} else {

			return null;

		}

	}

}

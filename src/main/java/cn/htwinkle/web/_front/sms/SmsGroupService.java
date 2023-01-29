package cn.htwinkle.web._front.sms;

import cn.htwinkle.web.base.BaseService;
import cn.htwinkle.web.model.SmsGroup;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;

import java.util.Collections;
import java.util.List;

/**
 * 短信服务的服务层
 *
 * @author : twinkle
 * @date : 2020/3/15 11:38
 */
public class SmsGroupService extends BaseService {

    public Ret getList() {
        List<SmsGroup> smsGroups = SmsGroup.dao.findAll();
        return CollUtil.isNotEmpty(smsGroups) ? Ret.ok().set("data", smsGroups) : Ret.ok().set("data", Collections.emptyList());
    }

    public Ret getListBy(String sn) {
        List<SmsGroup> smsGroups = SmsGroup.dao.find("SELECT * FROM sms_group " +
                "WHERE tel_sn = ? ", sn);
        return CollUtil.isNotEmpty(smsGroups) ? Ret.ok().set("data", smsGroups) : Ret.ok().set("data", Collections.emptyList());
    }

    public Ret insertOrUpdate(SmsGroup smsGroup) {
        if (smsGroup == null) {
            return Ret.fail("数据为空，请检查");
        }
        if (StrUtil.isEmpty(smsGroup.getSmsTel())) {
            return Ret.fail("电话为空，请检查");
        }
        if (StrUtil.isEmpty(smsGroup.getTelSn())) {
            return Ret.fail("设备号为空，请检查");
        }
        SmsGroup exist = SmsGroup.dao.findFirst("SELECT * FROM sms_group " +
                "WHERE sms_tel = ? " +
                "AND tel_sn = ? " +
                "LIMIT 1", smsGroup.getSmsTel(), smsGroup.getTelSn());
        boolean bFlag = exist == null;
        boolean saveOrUpdate = bFlag ? smsGroup.save() : smsGroup.update();
        String action = bFlag ? "新增" : "修改";
        return saveOrUpdate ? Ret.ok(action + "成功").set("data", smsGroup) : Ret.fail(action + "失败").set("data", smsGroup);
    }

    public Ret delete(SmsGroup smsGroup) {
        if (smsGroup == null) {
            return Ret.fail("数据为空，请检查");
        }
        if (StrUtil.isEmpty(smsGroup.getSmsTel())) {
            return Ret.fail("电话为空，请检查");
        }
        if (StrUtil.isEmpty(smsGroup.getTelSn())) {
            return Ret.fail("设备号为空，请检查");
        }
        int deleteById = Db.delete("DELETE FROM sms_group " +
                "WHERE sms_tel = ? " +
                "AND tel_sn=? ", smsGroup.getSmsTel(), smsGroup.getTelSn());
        Ret ret = deleteById > 0 ? Ret.ok("删除成功") : Ret.fail("删除失败");
        return ret.set("tel", smsGroup.getSmsTel()).set("sn", smsGroup.getTelSn());
    }

    public Ret batchDeleteBySn(String sn) {
        if (StrUtil.isEmpty(sn)) {
            return Ret.fail("设备号为空，请检查");
        }
        int deleteById = Db.delete("DELETE FROM sms_group " +
                "WHERE tel_sn=? ", sn);
        return deleteById > 0 ? Ret.ok("批量删除成功").set("data", sn) : Ret.fail("批量删除失败").set("data", sn);
    }

}

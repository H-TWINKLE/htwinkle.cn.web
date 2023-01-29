package cn.htwinkle.web._front.sms;

import cn.htwinkle.web.base.BaseController;
import cn.htwinkle.web.model.SmsGroup;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Inject;
import com.jfinal.kit.Ret;
import org.leon.swagger.annotation.Api;
import org.leon.swagger.annotation.ApiOperation;
import org.leon.swagger.annotation.Param;
import org.leon.swagger.model.constant.HttpMethod;

@Api(tag = SmsGroupController.TAG, description = "短信群发")
public class SmsGroupController extends BaseController {
    protected static final String TAG = "SMS_GROUP";

    @Inject
    private SmsGroupService smsGroupService;

    @ApiOperation(url = "/sms/", tag = SmsGroupController.TAG, httpMethod = HttpMethod.GET, description = "获取接口信息")
    @Override
    public void index() {
        renderJson(Ret.ok("短信群发服务"));
    }

    @ApiOperation(url = "/sms/all", tag = SmsGroupController.TAG, httpMethod = HttpMethod.GET, description = "获取所有联系人信息")
    public void all() {
        renderJson(smsGroupService.getList());
    }

    @ApiOperation(url = "/sms/getBySn", tag = SmsGroupController.TAG, httpMethod = HttpMethod.GET, description = "通过sn获取联系人")
    @Param(name = "sn", description = "设备号", required = true)
    public void getBySn(String sn) {
        renderJson(smsGroupService.getListBy(sn));
    }

    @ApiOperation(url = "/sms/saveOrUpdate", tag = SmsGroupController.TAG, httpMethod = HttpMethod.POST, description = "新增或者修改联系人")
    @Param(description = "新增或者修改信息", required = true, in = "body", name = "body")
    public void saveOrUpdate() {
        SmsGroup smsGroup = JSONObject.parseObject(getRawData(), SmsGroup.class);
        renderJson(smsGroupService.insertOrUpdate(smsGroup));
    }

    @ApiOperation(url = "/sms/deleteBy", tag = SmsGroupController.TAG, httpMethod = HttpMethod.POST, description = "删除联系人")
    @Param(description = "删除信息", required = true, in = "body", name = "body")
    public void deleteBy() {
        SmsGroup smsGroup = JSONObject.parseObject(getRawData(), SmsGroup.class);
        renderJson(smsGroupService.delete(smsGroup));
    }

    @ApiOperation(url = "/sms/deleteBatch", tag = SmsGroupController.TAG, httpMethod = HttpMethod.POST, description = "批量删除联系人")
    @Param(name = "sn", description = "批量删除的设备号", required = true)
    public void deleteBatch(String sn) {
        renderJson(smsGroupService.batchDeleteBySn(sn));
    }
}

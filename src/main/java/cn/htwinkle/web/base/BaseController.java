package cn.htwinkle.web.base;

import cn.htwinkle.web.constants.Constants;
import cn.htwinkle.web.model.User;
import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;

public abstract class BaseController extends Controller {

    /**
     * 首页
     */
    public abstract void index();

    protected User getSessionUser() {
        return getAttr(Constants.SESSION_USER);
    }

    protected void setSessionUser(User user) {
        setAttr(Constants.SESSION_USER, user);
    }


    /**
     * 返回默认的请求条数为api负责
     *
     * @return Integer
     */
    protected Integer getDefaultNumForApi() {
        Integer num = getParaToInt("num");
        if (num == null || num == 0) {
            return 10;
        }
        return num;
    }


    protected String getDefaultWhats(String whats) {
        if (StrKit.isBlank(whats)) {
            return "%%";
        }
        return "%" + whats + "%";
    }

    protected void setMsgTip(String tip) {
        setAttr(Constants.TIP_MSG, tip);
    }

    protected void setTitle(String title) {
        setAttr(Constants.TITLE, title);
    }


    /**
     * 获得当前方法的名字
     *
     * @return String
     */
    private String getNowMethodName() {
        StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
        StackTraceElement e = stacktrace[3];
        return e.getMethodName();

    }

    protected String getLocalHostWithContext() {
        return getRequest().getServerName() + ":" + getRequest().getServerPort() + getRequest().getContextPath();
    }

    /**
     * 默认的返回的json的值
     */
    protected void renderDefaultJson() {
        renderJson(Ret.ok("msg", "我们的生活"));
    }

    /**
     * 返回默认的Json
     *
     * @param num  num
     * @param list list
     * @return Ret
     */
    protected Ret getDefaultJson(Integer num, Object list) {
        return Ret.ok("msg", PropKit.get(Constants.RECORD_TITLE)).set("num", num).set("list", list);
    }


}
package cn.htwinkle.devotion.base;

import cn.htwinkle.devotion.constants.Constants;
import com.jfinal.core.Controller;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;

import java.util.List;

public abstract class BaseController extends Controller {

    protected Integer getDefaultPages(Integer pages) {
        if (pages == null || pages == 0) {
            return 1;
        }
        return pages;
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

    protected void setTitle(String title, Integer id) {
        if (id == null) {
            setAttr(Constants.TITLE, "修改" + title);
        } else {
            setAttr(Constants.TITLE, "新增" + title);
        }
    }

    protected void vailObjectAsJson(Object... o) {

        for (Object ob : o) {
            if (ob == null) {
                renderJson(Ret.fail("msg", "参数为空"));
                return;
            }

        }

    }

    protected void vailObjectAsHtmlText(Object... o) {

        for (Object ob : o) {

            if (ob == null) {
                renderText("请正确填写参数");
                return;
            }

        }

    }

    protected <T> void vailT(T t) {
        if (t == null)
            renderError(404);
        return;
    }

    protected void vailInteger(Integer para) {
        if (para == null || para == 0) {
            renderError(404);
            return;
        }

    }

    protected void vailStringParas(String... paras) {
        for (String pa : paras) {
            if (StrKit.isBlank(pa)) {
                renderError(404);
                return;
            }
        }

    }

    protected void vailParas(Object... paras) {
        for (Object pa : paras) {
            if (pa == null) {
                renderError(404);
                return;
            }
        }

    }

    protected void vailIntegerParas(Integer... paras) {

        for (Integer pa : paras) {
            if (pa == null || pa == 0) {
                renderError(404);
                return;
            }
        }

    }

    protected void vailParas(Integer... paras) {

        for (Integer pa : paras) {
            if (pa == null || pa == 0) {
                renderError(404);
                return;
            }
        }

    }

    protected boolean vailParasIsNull(Object... paras) {
        for (Object pa : paras) {
            if (pa == null) {
                return true;
            }
        }
        return false;
    }

    protected boolean vailIntegerParasIsNull(Integer... paras) {
        for (Integer pa : paras) {
            if (pa == null || pa == 0) {
                return true;
            }
        }
        return false;
    }

    protected <T extends List<?>> void vailListT(T t) {
        if (t == null || t.size() == 0)
            renderError(404);
        return;
    }

    protected <T extends List<?>> boolean vailListIsNull(T t) {
        return t == null || t.size() == 0;
    }

    protected void setTypesValidator(String types) {
        if (types == null || "".equals(types)) {

            renderError(404);
            return;

        }
    }

    private String getNowMethodName() {

        StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
        StackTraceElement e = stacktrace[3];
        return e.getMethodName();

    }

    protected String getLocalHostWithContext() {
        return getRequest().getServerName() + ":" + getRequest().getServerPort() + getRequest().getContextPath();
    }

}
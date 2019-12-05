package cn.htwinkle.we.controller;

import cn.htwinkle.we.base.BaseController;
import com.jfinal.kit.Ret;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class AppErrorController extends BaseController implements ErrorController {

    private static final String ERROR_PATH = "/_error";


    private ErrorAttributes errorAttributes;


    @Autowired
    public AppErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    /**
     * JSON格式错误信息
     */
    @ResponseBody
    @RequestMapping(value = ERROR_PATH, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Ret errorAsJson(HttpServletRequest request, HttpServletResponse response) {

        if (response.getStatus() == 500) {
            return Ret.fail("msg", "服务器异常").set("body", getErrorAttributes(request));
        }

        return Ret.fail("msg", "请求内容不存在");
    }

    @RequestMapping(value = ERROR_PATH, produces = {MediaType.TEXT_HTML_VALUE})
    public ModelAndView errorAsHtml(HttpServletRequest request, HttpServletResponse response) {

        if (response.getStatus() == 500) {
            return renderError500(getErrorAttributes(request));
        }
        return renderError404();
    }


    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

    private Map<?, ?> getErrorAttributes(HttpServletRequest request) {

        WebRequest requestAttributes = new ServletWebRequest(request);
        return errorAttributes.getErrorAttributes(requestAttributes, true);
    }


}

package cn.htwinkle.we.controller;


import cn.htwinkle.we.base.BaseController;
import com.jfinal.kit.StrKit;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/jwgl")
public class JwglController extends BaseController {


    @RequestMapping("")
    public ModelAndView jwgl(ModelAndView modelAndView) {

        modelAndView.setViewName("jwgl");

        setTitle(modelAndView, "教务管理系统");
        return modelAndView;
    }

    @RequestMapping("/login")
    public ModelAndView login(ModelAndView modelAndView,
                              String admin, String pass, String checks) {


        if (StrKit.isBlank(admin)) {

            setTipMsg(modelAndView, "请输入学号");
            return forward(modelAndView, "/_jwgl/jwgl");
        }


        return modelAndView;
    }


}

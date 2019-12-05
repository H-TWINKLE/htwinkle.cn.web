package cn.htwinkle.we.base;

import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

public class BaseController {


    protected ModelAndView forward(ModelAndView modelAndView, String view) {
        modelAndView.setViewName("forward:" + view);
        return modelAndView;
    }

    protected ModelAndView redirect(ModelAndView modelAndView, String view) {
        modelAndView.setViewName("redirect:" + view);
        return modelAndView;
    }

    protected ModelAndView renderError404() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/_error/404");
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }

    protected ModelAndView renderError500(Map<?, ?> map) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/_error/500");
        modelAndView.addObject("body", map);
        modelAndView.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        return modelAndView;
    }


    protected void setTipMsg(ModelAndView modelAndView, String tipMsg) {
        modelAndView.addObject("tipMsg", tipMsg);
    }

    protected void setTitle(ModelAndView modelAndView, String title) {
        modelAndView.addObject("title", title);
    }


}

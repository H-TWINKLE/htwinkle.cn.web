package cn.htwinkle.we.controller;

import cn.htwinkle.we.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class IndexController extends BaseController {


    @RequestMapping("/")
    public ModelAndView index(ModelAndView modelAndView) {

        modelAndView.setViewName("index");

        setTitle(modelAndView, "TWINKLE");

        return modelAndView;

    }

    @RequestMapping("/img")
    public ModelAndView img(ModelAndView modelAndView) {

        modelAndView.setViewName("img");
        modelAndView.addObject("plist", "1");

        setTitle(modelAndView, "图片瀑布");
        return modelAndView;
    }












}

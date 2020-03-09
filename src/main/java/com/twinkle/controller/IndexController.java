package com.twinkle.controller;

import com.jfinal.core.Controller;
import com.twinkle.common.config.MainConfig;


public class IndexController extends Controller {

    public void index() {

        setAttr("num", MainConfig.GLOBAL_COUNT.get());
        render("/index.html");

    }


}

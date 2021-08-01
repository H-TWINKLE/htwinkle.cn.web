package cn.htwinkle.web;

import cn.htwinkle.web.config.MainConfig;
import com.jfinal.server.undertow.UndertowServer;

import static cn.htwinkle.web.constants.Constants.CONFIG_DEV_FILE_NAME;

/**
 * TODO 描述用途
 *
 * @author : twinkle
 * @date : 2021/8/1 12:32
 */
public class App {

    /**
     * 运行程序
     *
     * @param args 传入参数
     */
    public static void main(String[] args) {
        UndertowServer.create(MainConfig.class, CONFIG_DEV_FILE_NAME).start();
    }

}

package cn.htwinkle.web.kit;

import cn.hutool.core.lang.Console;
import org.junit.Test;

public class EnvKitTest {

    @Test
    public void getEnvironmentValue() {
        String key = "${MYSQL_PASSWORD}--------${PATH}--------${PATHBBBBBB";
        Console.log("old: " + key);
        Console.log("wrapper: " + EnvKit.INSTANCE.getEnvironmentValue(key));
    }
}
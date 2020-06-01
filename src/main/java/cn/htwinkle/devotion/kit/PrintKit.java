package cn.htwinkle.devotion.kit;

import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;

import java.util.Enumeration;

/**
 * TODO 描述用途
 *
 * @author : twinkle
 * @date : 2020/6/1 23:52
 */
public enum PrintKit {
    INSTANCE;

    public void printHeader(Controller controller) {
        if (controller == null)
            return;
        if (!PropKit.getBoolean("logHeader", false))
            return;
        System.out.println("\n-----------------开始 header 输出-----------------");
        Enumeration<String> attrs = controller.getRequest().getHeaderNames();
        while (attrs.hasMoreElements()) {
            String attrName = attrs.nextElement();
            System.out.println(String.format("header: %s   value: %s", attrName,
                    controller.getRequest().getHeader(attrName)));
        }
    }
}

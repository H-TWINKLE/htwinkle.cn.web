package kit;

import cn.htwinkle.web.plugin.TestPlugin;
import org.junit.Before;

/**
 * 测试类的基类，方便测试
 *
 * @author : twinkle
 * @date : 2021/8/8 11:50
 */
public class TestKit {

    @Before
    public void initTest() {
        new TestPlugin().start();
    }

}

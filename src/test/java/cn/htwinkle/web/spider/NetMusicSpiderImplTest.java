package cn.htwinkle.web.spider;

import cn.htwinkle.web.kit.TestKit;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class NetMusicSpiderImplTest extends TestKit {


    @Test
    public void getHtmlList() {
        NetMusicTopListImpl netMusicSpider = new NetMusicTopListImpl();
        netMusicSpider.get();
    }
}
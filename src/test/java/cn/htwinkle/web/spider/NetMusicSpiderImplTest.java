package cn.htwinkle.web.spider;

import kit.TestKit;
import org.junit.Test;

public class NetMusicSpiderImplTest extends TestKit {


    @Test
    public void getHtmlList() {
        NetMusicTopListImpl netMusicSpider = new NetMusicTopListImpl();
        netMusicSpider.get();
    }
}
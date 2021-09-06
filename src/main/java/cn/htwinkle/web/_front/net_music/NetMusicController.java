package cn.htwinkle.web._front.net_music;

import cn.htwinkle.web.base.BaseController;
import org.leon.swagger.annotation.Api;
import org.leon.swagger.annotation.ApiOperation;
import org.leon.swagger.model.constant.HttpMethod;

/**
 * 网易云音乐页面
 *
 * @author : twinkle
 * @date : 2021/8/8 11:28
 */
@Api(tag = "NetMusic", description = "网易云音乐")
public class NetMusicController extends BaseController {
    @Override
    public void index() {

    }

    @ApiOperation(url = "/netMusic/getMv",
            tag = "NetMusic",
            httpMethod = HttpMethod.GET,
            description = "随机获取一个mv")
    public void getMv() {

    }

    @ApiOperation(url = "/netMusic/getMusic",
            tag = "NetMusic",
            httpMethod = HttpMethod.GET,
            description = "随机获取某一个热门榜单的一首歌曲")
    public void getMusic() {

    }

}

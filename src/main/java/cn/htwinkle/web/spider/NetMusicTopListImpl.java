package cn.htwinkle.web.spider;

import cn.htwinkle.web.constants.Constants;
import cn.htwinkle.web.model.NetMusic;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.StrKit;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static cn.htwinkle.web.constants.Constants.NET_MUSIC_BASE_TOP_LIST_URL;

/**
 * 网易云音乐解析类
 *
 * @author : twinkle
 * @date : 2021/8/8 11:30
 */
public class NetMusicTopListImpl implements ISpider<NetMusic> {
    /**
     * ArticleSpiderImpl的输出日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NetMusicTopListImpl.class.getName());
    /**
     * 排榜
     */
    public static final String TOPLIST = "toplist";

    @Override
    public NetMusic get() {
        String types = Constants.NET_MUSIC_TOP_LIST[new Random().nextInt(1)];
        return getNetMusicList(types);
    }

    @Override
    public NetMusic get(String types) {
        return getNetMusicList(types);
    }

    /**
     * 获取歌曲信息
     *
     * @param types types
     * @return NetMusic
     */
    private NetMusic getNetMusicList(String types) {
        List<NetMusic> musicList = this.getMusicListByHtml(types);
        return null == musicList ? null : musicList.get(0);
    }


    /**
     * 获取歌曲列表
     *
     * @param typeId typeId
     * @return List<NetMusic>
     */
    private List<NetMusic> getMusicListByHtml(String typeId) {
        Document document;
        try {
            document = Jsoup
                    .connect(NET_MUSIC_BASE_TOP_LIST_URL + typeId)
                    .header("Referer", "http://music.163.com/")
                    .header("Host", "music.163.com")
                    .header("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:38.0) Gecko/20100101 Firefox/38.0 Iceweasel/38.3.0")
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                    .get();
        } catch (IOException e) {
            LOGGER.error("NetMusicSpiderImpl - getListByHtml : 获取数据失败" + e.getLocalizedMessage());
            return null;
        }
        return analyzeMusicListByTopList(document, typeId);
    }

    /**
     * 分析数据
     *
     * @return List<NetMusic>
     */
    private List<NetMusic> analyzeMusicListByTopList(Document document, String typeId) {
        Element eleByListMusic = document.getElementById("song-list-pre-data");

        if (null == eleByListMusic) {
            return null;
        }

        JSONArray jsonArray = JSONArray.parseArray(eleByListMusic.text());
        List<NetMusic> musicList = new ArrayList<>();

        for (int index = 0; index < jsonArray.size(); index++) {
            JSONObject songInfo = jsonArray.getJSONObject(index);
            NetMusic netMusic = new NetMusic();
            netMusic.setNetMusicSongId(songInfo.getString("id"));
            netMusic.setNetMusicSongType(TOPLIST);
            netMusic.setNetMusicSongTypeId(typeId);
            netMusic.setNetMusicSongName(songInfo.getString("name"));
            netMusic.setNetMusicSongAuthor(getAuthor(songInfo.getJSONArray("artists")));
            netMusic.setNetMusicSongTime(songInfo.getString("duration"));
            netMusic.setNetMusicSongAlbum(getAlbum(songInfo.getJSONObject("album")));
            netMusic.setNetMusicSongMvId(songInfo.getString("mvid"));
            netMusic.setNetMusicSongDate(new Date());
            System.out.println(netMusic);
            musicList.add(netMusic);
        }
        return musicList;
    }

    /**
     * 获取歌曲作者
     *
     * @param jsonArrayByAuthor jsonArrayByAuthor
     * @return String
     */
    private String getAuthor(JSONArray jsonArrayByAuthor) {
        if (null == jsonArrayByAuthor) {
            return "";
        }
        List<String> stringList = new ArrayList<>();
        for (int index = 0; index < jsonArrayByAuthor.size(); index++) {
            JSONObject object = jsonArrayByAuthor.getJSONObject(index);
            String name = object.getString("name");
            stringList.add(null == name ? "" : name);
        }
        return StrKit.join(stringList, ",");
    }

    /**
     * 获取专辑名称
     *
     * @param jsonObject jsonObject
     * @return getAlbum
     */
    private String getAlbum(JSONObject jsonObject) {
        if (null == jsonObject) {
            return "";
        }
        return jsonObject.getString("name");
    }
}

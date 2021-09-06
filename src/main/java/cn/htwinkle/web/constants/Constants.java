package cn.htwinkle.web.constants;

import com.jfinal.kit.PropKit;

/**
 * 常量
 *
 * @author : twinkle
 * @date : 2020/3/9 19:19
 */
public interface Constants {

    String DEV_MODE = "devMode";
    String POST_FILE_SIZE = "postFileSize";
    String CONFIG_DEV_FILE_NAME = "config-dev.properties";
    String CONFIG_PRO_FILE_NAME = "config-pro.properties";
    String UPLOAD_PATH = "uploadPath";
    String DOWNLOAD_PATH = "downloadPath";
    String DIAO_PATH = "diaoPath";
    String VIEW_OF_ERROR = "viewOfError";
    String VIEW_OF_ABNORMAL = "viewOfAbnormal";
    String JDBC_URL = "jdbcUrl";
    String JDBC_USER = "user";
    String JDBC_PASS = "password";
    String TIP_MSG = "TipMsg";
    String TITLE = "title";
    String RECORD_URL = "recordUrl";
    String RECORD_NUMBER = "recordNumber";
    String RECORD_TITLE = "recordTitle";
    String KEY_OF_IMAGE = "keyOfImage";
    String RECORD_LINK_URL = "recordLinkUrl";
    String USER_AGENT = "user-agent";
    String LOCALIP = "0:0:0:0:0:0:0:1";

    String MEI_RI_YI_WEN_URL = "https://meiriyiwen.com/random";
    String SJ_ZOL_COM_CN_BIZHI = "http://sj.zol.com.cn/";
    String DESK_ZOL_COM_CN = "http://desk.zol.com.cn/";

    /**
     * 风景，可爱，建筑，植物，动物，美食
     */
    String[] G3_BIZHI_TYPES = {"fengjing", "keai", "jianzhu", "zhiwu", "dongwu", "meishi"};

    String SESSION_USER = "_user";
    String SESSION__DIAO_KEY = "_hd";

    /**
     * cookies默认有效期 5天
     */
    int MAX_SESSION_DATE = 60 * 60 * 24 * 5;

    /**
     * 榜单链接
     */
    String NET_MUSIC_BASE_TOP_LIST_URL = "https://music.163.com/discover/toplist?id=";


    /**
     * 榜单链接
     */
    String NET_MUSIC_BASE_PLAY_LIST_URL = "https://music.163.com/playlist?id=";

    /**
     * 排行榜
     * 3778678 热歌榜
     */
    String[] NET_MUSIC_TOP_LIST = {"3778678"};

    /**
     * 歌单
     * 6907590247 热爱可抵岁月漫长
     */
    String[] NET_MUSIC_PLAY_LIST = {"6907590247"};

    /**
     * 获取默认app应用展示的图片
     */
    String[] DEFAULT_APP_PIC_ARR = {
            "http://" + PropKit.get(KEY_OF_IMAGE) + "/2020/03/15/226d1a7240f6d357809665ac5d151c2a.jpg",
            "http://" + PropKit.get(KEY_OF_IMAGE) + "/2020/03/15/665675de407a6b4980c3e40c4edc60b8.jpg",
            "http://" + PropKit.get(KEY_OF_IMAGE) + "/2020/03/15/2b0a5a5a409933ac80df18f7a0c5f5e2.jpg",
            "http://" + PropKit.get(KEY_OF_IMAGE) + "/2020/03/15/46428d0440c06b6c8051b6dd749699be.jpg"};
}

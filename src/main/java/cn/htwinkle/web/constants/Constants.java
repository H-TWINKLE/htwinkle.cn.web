package cn.htwinkle.web.constants;

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
    String CONFIG_EHCACHE_FILE_NAME = "ehcache.xml";
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
    String G3_BIZHI_URL = "http://sj.zol.com.cn/bizhi/";

    // 风景，可爱，建筑，植物，动物
    String[] G3_BIZHI_TYPES = {"fengjing", "keai", "jianzhu", "zhiwu", "dongwu"};

    String SESSION_USER = "_user";
    String SESSION__DIAO_KEY = "_hd";

    /**
     * cookies默认有效期 5天
     */
    int MAX_SESSION_DATE = 60 * 60 * 24 * 5;

}

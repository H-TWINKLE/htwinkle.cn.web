package cn.htwinkle.web.kit;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ContentTypeKit {
    public static Map<String, String> map = new ConcurrentHashMap<String, String>();

    static {
        map.put("jpeg", "image/jpeg");
        map.put("jpg", "image/jpeg");
        map.put("jpe", "image/jpeg");
        map.put("png", "image/png");
    }

    public static String get(String mimetype) {
        return map.get(mimetype);
    }
}
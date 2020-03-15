package cn.htwinkle.devotion.kit;

import com.alibaba.fastjson.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public enum BmobUtilsKit {

    INSTANCE;

    /**
     * 增加一对一关联查询
     */
    public String analInclude(String... include) {

        if (include == null) {
            return "";
        }
        StringBuilder s = new StringBuilder();
        s.append("?include=");
        for (int x = 0; x < include.length; x++) {
            s.append(include[x]);
            if (x < include.length - 1) {
                s.append(",");
            }
        }
        return s.toString();
    }

    /**
     * 增加条件查询
     */
    public String analWhere(JSONObject json) {
        try {
            return URLEncoder.encode("?where=" + json.toJSONString(), "utf-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }

    }

    /**
     * 增加一对多关联查询
     */
    public String analRelatedTo() {
        return "";
    }

}

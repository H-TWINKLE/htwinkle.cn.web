package cn.htwinkle.devotion.bean;

import cn.htwinkle.devotion.base.bmob.BaseBmob;
import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

/**
 * Copyright 2018 bejson.com
 */

/**
 * Auto-generated: 2018-11-28 11:8:24
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class User extends BaseBmob implements Serializable {

    private static final long serialVersionUID = -3557885682189847519L;


    public User() {
        super();
    }

    public User(String __type, String objectId) {
        super();
        this.set__type(__type);
        this.setObjectId(objectId);

    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }

}

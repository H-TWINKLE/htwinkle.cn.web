package cn.htwinkle.we.constant;

import cn.htwinkle.we.entity.Jwgl;

import java.time.LocalDateTime;

public enum Status {

    /**
     * 登录失败，用户或者密码错误，或者验证码错误
     */
    LOGIN_FAILURE(10001),

    /**
     * 登录成功
     */
    LOGIN_SUCCESS(10000),

    /**
     * 网络异常 内部异常
     */
    NET_FAILURE(10002),

    /**
     * host错误 服务器不存在
     */
    SERVER_ERROR(10003),

    /**
     * 没有进行系统评价
     */
    NEVER_TO_EVAL(10004),

    /**
     * 验证码为空
     */
    CODE_IMG_ERROR(10005),

    /**
     * 作业全部完成
     */
    WORK_COMPLETE(10006),

    /**
     * string 服务器错误
     */
    FAILURE_SERVER("服务器错误");


    public int CODE;
    public String VALUE;

    Status(String value) {
        this.VALUE = value;
    }

    Status(int code) {
        CODE = code;
    }


    public static Jwgl SetErrorTip(Jwgl j, Integer code, String value) {

        j.setCode(code);

        if (code == Status.SERVER_ERROR.CODE) {

            j.setTip("教务管理系统维护中！");

        } else if (code == Status.CODE_IMG_ERROR.CODE) {

            j.setTip("验证码为空");
        } else if (code == Status.LOGIN_FAILURE.CODE) {

            j.setTip("登录失败:" + value);
        } else if (code == Status.NET_FAILURE.CODE) {

            j.setTip("登录失败:网络异常");
        } else if (code == Status.LOGIN_SUCCESS.CODE) {

            j.setTip("登录成功：" + value);
        } else if (code == Status.NEVER_TO_EVAL.CODE) {

            j.setTip(value);
        } else if (code == Status.FAILURE_SERVER.CODE) {

            j.setTip("服务器内部错误！");
        } else {
            j.setTip("内部错误");
        }


        return j.setDates(LocalDateTime.now());


    }


}

package cn.htwinkle.web.kit;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.lang.func.Func0;
import cn.hutool.core.lang.func.VoidFunc0;
import org.apache.log4j.Logger;

public class Safety {
    /**
     * Safety的输出日志对象
     */
    private static final Logger LOGGER = Logger.getLogger(Safety.class.getName());

    public static void get(VoidFunc0 voidFunc0, boolean ignoreError) {
        try {
            voidFunc0.call();
        } catch (Exception e) {
            if (ignoreError) {
                LOGGER.error("error : " + ExceptionUtil.stacktraceToString(e));
            }
        }
    }

    public static void get(VoidFunc0 voidFunc0) {
        get(voidFunc0, true);
    }

    public static <R> R get(Func0<R> func0, boolean ignoreError, R r) {
        try {
            return func0.call();
        } catch (Exception e) {
            if (ignoreError) {
                LOGGER.error("error : " + ExceptionUtil.stacktraceToString(e));
            }
        }
        return r;
    }

    public static <R> R get(Func0<R> func0, R r) {
        return get(func0, true, r);
    }

    public static <R> R get(Func0<R> func0) {
        return get(func0, true, null);
    }

}

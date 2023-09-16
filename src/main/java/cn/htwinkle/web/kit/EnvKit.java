package cn.htwinkle.web.kit;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.SystemPropsUtil;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public enum EnvKit {
    INSTANCE;

    /**
     * 自动判断是否为服务器环境
     */
    private static int IS_PRO_ENV = 0;

    public boolean isProEnviron() {
        if (IS_PRO_ENV == 0) {
            IS_PRO_ENV = System.getProperty("os.name").toLowerCase().contains("windows") ? 2 : 1;
        }
        return IS_PRO_ENV == 1;
    }

    public String getEnvironmentValue(String value) {
        if (!StrUtil.contains(value, "$")) {
            return value;
        }
        return getEnvironmentByWrapper(value);
    }

    private String getEnvironmentByWrapper(String value) {
        String[] array = value.split("\\$\\{");
        AtomicInteger count = new AtomicInteger();
        return Arrays.stream(array)
                .map(item -> {
                    if (item.contains("}")) {
                        int position = item.indexOf("}");
                        String key = item.substring(0, position);
                        String enValue = SystemPropsUtil.get(key);
                        if (StrUtil.isNotEmpty(enValue)) {
                            return item.replace(key + "}", enValue).trim();
                        }
                    }
                    return ((count.get() == 0 || count.get() == array.length) ? "" : "${") + item.trim();
                })
                .peek(item -> count.getAndIncrement())
                .collect(Collectors.joining(""));
    }
}

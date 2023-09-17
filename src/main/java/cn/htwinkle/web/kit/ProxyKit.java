package cn.htwinkle.web.kit;

import org.jetbrains.annotations.NotNull;

public class ProxyKit {
    @NotNull
    public static String getProxyUrl(String url) {
        return "/zolProxy/" + url;
    }
}

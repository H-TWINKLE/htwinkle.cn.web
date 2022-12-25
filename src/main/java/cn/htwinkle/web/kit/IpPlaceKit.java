package cn.htwinkle.web.kit;

import cn.htwinkle.web.constants.Constants;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public enum IpPlaceKit {

    INSTANCE;

    private String getIpByChinaZ(String ip) {
        Document doc;
        try {
            doc = Jsoup.connect("http://ip.chinaz.com/" + ip).timeout(5000).get();
        } catch (IOException e) {
            return "";
        }
        if (doc == null)
            return "";
        Element ele = doc.selectFirst("p.WhwtdWrap:nth-child(2) > span:nth-child(4)");
        if (ele == null)
            return "";
        return ele.text();
    }

    private String getIpByIp(String ip) {
        Document doc;
        try {
            doc = Jsoup.connect("https://ip.cn/index.php?ip=" + ip)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:61.0) Gecko/20100101 Firefox/61.0")
                    .header("Host", "ip.cn").timeout(5000).get();
        } catch (IOException e) {
            return "";
        }
        if (doc == null) {
            return "";
        }
        Element ele = doc.selectFirst(".well > p:nth-child(2) > code:nth-child(1)");
        if (ele == null)
            return "";
        return ele.text();
    }

    private String getIpByXpcha(String ip) {
        Document doc;
        try {
            doc = Jsoup.connect("http://ip.xpcha.com/?q=" + ip)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:61.0) Gecko/20100101 Firefox/61.0")
                    .timeout(5000).get();
        } catch (IOException e) {
            return "";
        }
        if (doc == null) {
            return "";
        }
        Element ele = doc.selectFirst("dl.shaixuan_1:nth-child(4) > dd:nth-child(2)");
        if (ele == null)
            return "";
        ele.selectFirst("a").remove();
        return ele.select("dd").text();
    }

    private String getIpByTaoBao(String ip) {
        Response response;
        try {
            response = Jsoup.connect("http://ip.taobao.com/service/getIpInfo.php?ip=" + ip)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:61.0) Gecko/20100101 Firefox/61.0")
                    .timeout(5000).execute();
        } catch (IOException e) {
            return "";
        }
        String json = response.body();
        if (StrKit.isBlank(json))
            return null;
        JSONObject jsonObject = JSONObject.parseObject(json);
        StringBuilder stringBuilder = new StringBuilder();
        if (StrKit.notBlank(jsonObject.getString("country"))) {
            stringBuilder.append(jsonObject.get("country"));
            stringBuilder.append(" - ");
        }
        if (StrKit.notBlank(jsonObject.getString("region"))) {
            stringBuilder.append(jsonObject.get("region"));
            stringBuilder.append(" - ");
        }
        if (StrKit.notBlank(jsonObject.getString("city"))) {
            stringBuilder.append(jsonObject.get("city"));
            stringBuilder.append(" - ");
        }
        if (StrKit.notBlank(jsonObject.getString("county"))) {
            stringBuilder.append(jsonObject.get("county"));
            stringBuilder.append(" - ");
        }
        if (StrKit.notBlank(jsonObject.getString("isp"))) {
            stringBuilder.append(jsonObject.get("isp"));
        }
        return stringBuilder.toString();
    }

    public String getIpPlace(String ip) {
        if (StrKit.isBlank(ip))
            return "";
        String placeString = getIpByXpcha(ip);
        if (StrKit.notBlank(placeString))
            return placeString;
        placeString = getIpByIp(ip);
        if (StrKit.notBlank(placeString))
            return placeString;
        placeString = getIpByChinaZ(ip);
        if (StrKit.notBlank(placeString))
            return placeString;
        return ip;

    }

    public String getRemoteAddrIp(Controller c) {
        if (c == null)
            return null;
        String ip = c.getRequest().getHeader("x-forwarded-for");
        if (validatorIp(ip)) {
            return ip;
        }
        ip = c.getRequest().getHeader("Proxy-Client-IP");
        if (validatorIp(ip)) {
            return ip;
        }
        ip = c.getRequest().getHeader("WL-Proxy-Client-IP");
        if (validatorIp(ip)) {
            return ip;
        }
        ip = c.getRequest().getHeader("HTTP_CLIENT_IP");
        if (validatorIp(ip)) {
            return ip;
        }
        ip = c.getRequest().getHeader("X-Real-IP");
        if (validatorIp(ip)) {
            return ip;
        }
        ip = c.getRequest().getRemoteAddr();
        if (validatorIp(ip)) {
            if (ip.equals(Constants.LOCALIP)) {
                return "127.0.0.1";
            }
            return ip;
        }
        return "";
    }

    private boolean validatorIp(String ip) {
        return ip != null && ip.length() > 0 && !ip.equals("unknown");
    }

    public String getUserAgent(Controller controller) {
        return controller.getHeader(Constants.USER_AGENT);
    }

}

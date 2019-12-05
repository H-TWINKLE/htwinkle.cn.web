package cn.htwinkle.we.utils;

import cn.htwinkle.we.constant.Status;
import cn.htwinkle.we.entity.Jwgl;
import cn.htwinkle.we.utils.Utils;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Objects;

public enum AnalUtils {

    INSTANCE;

    private static OkHttpClient okHttpClient = new OkHttpClient();

    private static final String HTML_CODE_UNICODE = "gb2312";



    public Jwgl analLoginInfo(Jwgl jwgl, String html) {

        Document doc = Jsoup.parse(html);
        Element ele = doc.getElementById("xhxm");

        if (ele == null) {
            Elements eles = doc.getElementsByTag("script");

            if (eles != null) {
                int size = eles.size();
                if (size > 0) {

                    Status.SetErrorTip(jwgl, Status.LOGIN_FAILURE.CODE,
                            Utils.INSTANCE.mathChinese(eles.get(size - 1).toString()));
                } else {

                    Status.SetErrorTip(jwgl, Status.NET_FAILURE.CODE, null);
                }
            } else {
                Status.SetErrorTip(jwgl, Status.NET_FAILURE.CODE, null);
            }

        } else {

            Status.SetErrorTip(jwgl, Status.LOGIN_SUCCESS.CODE, ele.text())
                    .setName(ele.text().substring(0, ele.text().length() - 2));
        }
        return jwgl;

    }



    public String encodeHtml(byte[] bytes) {

        if (bytes == null)
            return null;

        try {
            return new String(bytes, HTML_CODE_UNICODE);
        } catch (Exception e) {
            return null;
        }

    }

    public Response getResponse(Request request) {

        try (Response response = okHttpClient.newCall(request).execute()) {
            return response;
        } catch (Exception e) {
            return null;
        }

    }


    public String getHtml(Request request) {

        try (ResponseBody responseBody = Objects.requireNonNull(getResponse(request)).body()) {

            if (responseBody != null)

                return encodeHtml(responseBody.bytes());

        } catch (Exception e) {
            return null;
        }
        return null;
    }


}

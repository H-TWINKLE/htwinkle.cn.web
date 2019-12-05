package cn.htwinkle.we.kit;

import cn.htwinkle.we.constant.Status;
import cn.htwinkle.we.entity.Jwgl;
import cn.htwinkle.we.utils.AnalUtils;
import cn.htwinkle.we.utils.Utils;
import com.jfinal.kit.StrKit;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JwglKit {

    private Logger logger = LoggerFactory.getLogger(JwglKit.class);

    private final static String JWGL_CODE_IMG = "http://jwgl.cdnu.edu.cn/CheckCode.aspx";
    private final static String JWGL_HOME = "http://jwgl.cdnu.edu.cn/default5.aspx";
    private final static String JWGL_INDEX = "http://jwgl.cdnu.edu.cn/xs_main.aspx?xh=";
    private final static String JWGL_LOGIN_AS_STUDENT = "学生";
    private final static String JWGL_QUERY_AS_TERM = "按学期查询";

    private Request.Builder requestBuilder = new Request.Builder().addHeader("User-Agent",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:61.0) Gecko/20100101 Firefox/61.0");


    private Map<String, String> getLoginParam() {

        Request request = requestBuilder.url(JWGL_HOME).build();

        String html = AnalUtils.INSTANCE.getHtml(request);

        if (StrKit.isBlank(html))
            return null;

        Document doc = Jsoup.parse(html);

        Elements eles = doc.getElementById("form1").select("input");

        Map<String, String> map = new HashMap<>();

        for (Element ele : eles) {

            map.put(ele.attr("name").trim(), ele.attr("value"));

        }

        return map;

    }

    private Jwgl getLoginCode(Jwgl jwgl) {

        Request request = requestBuilder.url(JWGL_CODE_IMG).build();

        Response response = AnalUtils.INSTANCE.getResponse(request);

        if (response == null || response.body() == null)
            return Status.SetErrorTip(jwgl, Status.SERVER_ERROR.CODE, null);

        String cookies = response.header("Set-Cookie", "");

        jwgl.setCookies(cookies);


        String json;

        try {
            json = MyAipOcr.INSTANCE.getOrcText(response.body().bytes());
        } catch (IOException | NullPointerException e) {
            return Status.SetErrorTip(jwgl, Status.CODE_IMG_ERROR.CODE, null);
        }

        String code = Utils.INSTANCE.analCode(json);

        if (code == null)
            return Status.SetErrorTip(jwgl, Status.CODE_IMG_ERROR.CODE, null);

        return jwgl.setVeri_code(code);


    }


    public Jwgl toLogin(String admin, String pass) {

        Jwgl jwgl = new Jwgl();

        jwgl.setAdmin(admin).setPass(pass);

        Map<String, String> loginParams = getLoginParam();

        if (loginParams == null)

            return Status.SetErrorTip(jwgl, Status.SERVER_ERROR.CODE, null);

        Utils.INSTANCE.printMap(loginParams);


        jwgl = getLoginCode(jwgl);

        if (StrKit.isBlank(jwgl.getVeri_code()))
            return jwgl;

        logger.debug("veri_code"+jwgl.getVeri_code());

        String text = Utils.INSTANCE.encodeUrl(JWGL_LOGIN_AS_STUDENT);

        FormBody.Builder body = new FormBody.Builder()
                .add("TextBox1", admin)
                .add("TextBox2", pass)
                .add("Textbox3", jwgl.getVeri_code())
                .add("RadioButtonList1", text);

        for (String key : loginParams.keySet()) {

            body.add(key, loginParams.get(key));
        }

        Request request = requestBuilder.url(JWGL_HOME).post(body.build())
                .header("Cookie", jwgl.getCookies()).build();

        String html = AnalUtils.INSTANCE.getHtml(request);

        return AnalUtils.INSTANCE.analLoginInfo(jwgl, html);


    }


}

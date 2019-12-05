package cn.htwinkle.we.kit;

import com.baidu.aip.ocr.AipOcr;

import java.util.HashMap;

public enum MyAipOcr {

    INSTANCE;

    public static final String APP_ID = "11692607";
    public static final String API_KEY = "OoKiNeBN2YBc0l2W2T0NI83P";
    public static final String SECRET_KEY = "OWrrF6mpueItSb16RPvpBH222LDydriu";

    private AipOcr orcClient = new AipOcr(APP_ID, API_KEY, SECRET_KEY);


    private HashMap<String, String> options = new HashMap<String, String>() {
        {
            put("language_type", "ENG");
        }
    };

    public String getOrcText(byte[] bytes) {

        return orcClient.basicGeneral(bytes, options).toString();
    }


}

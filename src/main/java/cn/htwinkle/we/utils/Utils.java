package cn.htwinkle.we.utils;

import cn.htwinkle.we.entity.Jwgl;
import cn.htwinkle.we.entity.OcrEntity;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.StrKit;

import java.net.URLEncoder;
import java.util.Map;
import java.util.concurrent.*;
import java.util.regex.Pattern;

public enum Utils {

    INSTANCE;


    private ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 20, 0, TimeUnit.SECONDS, new ArrayBlockingQueue<>(20));

    public String analCode(String json) {

        OcrEntity ocr = JSONObject.parseObject(json, OcrEntity.class);

        Integer num = ocr.getWords_result_num();

        if (num == 0)
            return null;

        for (OcrEntity.Result ob : ocr.getWords_result()) {

            if (ob.getWords().length() == 4) {
                return ob.getWords();
            }
        }
        return null;

    }

    public String encodeUrl(String text) {

        if (StrKit.isBlank(text))
            return null;

        try {
            return URLEncoder.encode(text, "gb2312");
        } catch (Exception e) {
            return null;
        }
    }

    public String mathChinese(String text) {
        return Pattern.compile("[^\u4E00-\u9FA5]").matcher(text).replaceAll(""); // [\u4E00-\u9FA5]是unicode2的中文区间
    }


    public Future<Jwgl> addExecute(Callable<Jwgl> task) {
        return executor.submit(task);
    }

    public void printMap(Map<?, ?> map) {

        for (Object o : map.keySet()) {
            System.out.println(map.get(o));
        }
    }


}

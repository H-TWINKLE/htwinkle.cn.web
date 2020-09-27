package cn.htwinkle.devotion._front.idea;

import cn.htwinkle.devotion.base.BaseService;
import com.jfinal.kit.LogKit;
import com.jfinal.kit.Ret;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * TODO 描述用途
 *
 * @author : twinkle
 * @date : 2020/9/27 20:26
 */
public class IdeaService extends BaseService {

    /**
     * 获取所以的激活码
     * @return Ret
     */
    public Ret getListCode() {
        List<String> list = new ArrayList<>();
        Ret ret = getRetCode();
        if (ret.isOk()) {
            list.add(ret.getStr("code"));
        }
        ret = getZipFile();
        if (ret.isOk()) {
            list.addAll(ret.getAs("list"));
        }
        if (list.size() > 0) {
            return Ret.ok().set("list", list);
        }
        return Ret.fail();
    }

    /**
     * 获取code
     *
     * @return Ret
     */
    public Ret getRetCode() {
        try {
            Document doc = Jsoup.connect("http://lookdiv.com/index/index/indexcode.html")
                    .data("key", "lookdiv.com").post();
            Elements text = doc.getElementsByTag("textarea");
            if (text.size() > 0) {
                return Ret.create().setOk().set("code", text.get(0).text());
            } else {
                return Ret.create().setFail();
            }
        } catch (IOException e) {
            return Ret.create().setFail();
        }
    }

    /**
     * 获取压缩文件
     *
     * @return Ret
     */
    public Ret getZipFile() {
        Ret ret = Ret.create();
        try {
            Connection.Response connection =
                    Jsoup.connect("http://idea.medeming.com/jihuoma/images/jihuoma.zip")
                            .ignoreContentType(true)
                            .execute();
            InputStream in = connection.bodyStream();
            try (ZipInputStream zipInputStream = new ZipInputStream(in)) {
                List<String> list = new ArrayList<>();
                ZipEntry zipFile;
                //循环读取zip中的cvs/txt文件，zip文件名不能包含中文
                while ((zipFile = zipInputStream.getNextEntry()) != null) {
                    //获得cvs名字
                    String fileName = zipFile.getName();
                    //检测文件是否存在
                    if (fileName != null && fileName.contains(".")) {
                        LogKit.info("---------------------开始解析文件："
                                + fileName + "-----------------------------");
                        //读取
                        BufferedReader br =
                                new BufferedReader(
                                        new InputStreamReader(zipInputStream, Charset.forName("GBK")));
                        StringBuilder stringBuilder = new StringBuilder();
                        String line;
                        //内容不为空，输出
                        while ((line = br.readLine()) != null) {
                            stringBuilder.append(line);
                        }
                        list.add(stringBuilder.toString());
                        //一定记得关闭流
                        zipInputStream.closeEntry();
                    }
                }
                ret.set("list", list).setOk();
                return ret;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ret.setFail();
        }
    }

}

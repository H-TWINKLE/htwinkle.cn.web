package cn.htwinkle.web.kit;

import cn.htwinkle.web.plugin.TestPlugin;
import com.jfinal.plugin.activerecord.generator.Generator;

import java.io.File;

public class GeneratorModelKit {

    /**
     * model 所使用的包名
     */
    public static final String MODEL_PKG = "cn.htwinkle.web.model";

    /**
     * base model 所使用的包名
     */
    public static final String BASE_MODEL_PKG = MODEL_PKG + ".base";

    /**
     * model 文件保存路径
     */
    public static final String MODEL_DIR = getNowPath() +
            "/src/main/java/" + MODEL_PKG.replaceAll("\\.", "/");

    /**
     * base model 文件保存路径
     */
    public static final String BASE_MODEL_DIR = MODEL_DIR + "/base";


    private static String getNowPath() {
        File file = new File("");
        return file.getAbsolutePath();
    }

    public static void main(String... args) {
        Generator generator = new Generator(new TestPlugin(true).getDateSource(),
                BASE_MODEL_PKG, BASE_MODEL_DIR, MODEL_PKG, MODEL_DIR);
        // 在 getter、setter 方法上生成字段备注内容
        generator.setGenerateRemarks(true);
        // 生成链式结构类似builder
        generator.setGenerateChainSetter(true);
        // 在 生成字典  一个txt文件 可以查看表数据
        generator.setGenerateDataDictionary(true);
        //生成查询dao
        generator.setGenerateDaoInModel(true);
        generator.generate();
    }


}

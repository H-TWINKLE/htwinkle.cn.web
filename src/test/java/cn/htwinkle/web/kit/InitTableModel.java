package cn.htwinkle.web.kit;

import cn.htwinkle.web.plugin.TestPlugin;
import com.jfinal.plugin.activerecord.generator.Generator;
import org.junit.Ignore;
import org.junit.Test;

import static cn.htwinkle.web.kit.GeneratorModelKit.*;

/**
 * 初始化数据表
 *
 * @author : twinkle
 * @date : 2021/8/8 11:48
 */
@Ignore
public class InitTableModel extends TestKit {


    @Test
    public void initModel() {
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

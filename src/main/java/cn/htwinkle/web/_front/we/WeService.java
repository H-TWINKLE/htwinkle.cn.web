package cn.htwinkle.web._front.we;

import cn.htwinkle.web.base.BaseService;
import cn.htwinkle.web.kit.FileKit;

import java.io.File;

/**
 * 我们的生活的服务层
 *
 * @author : twinkle
 * @date : 2020/3/15 11:37
 */
public class WeService extends BaseService {

    public static final String WE_BACKUP_DIR_TAG = "we";

    /**
     * 移动文件到指定的位置
     *
     * @param file file
     * @return boolean
     */
    public boolean backUpWebHomeFile(File file) {
        return FileKit.copyFile(file, WE_BACKUP_DIR_TAG);
    }
}

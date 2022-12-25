package cn.htwinkle.web._front.we;

import cn.htwinkle.web.base.BaseService;
import cn.htwinkle.web.entity.WavItem;
import cn.htwinkle.web.kit.FileKit;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
    public File backUpWebHomeFile(File file) {
        return FileKit.toBackupFile(file, WE_BACKUP_DIR_TAG);
    }

    public List<WavItem> getBackUpWavFile() {
        File backupDir = FileKit.getBackupDir();
        if (backupDir == null) {
            return Collections.emptyList();
        }
        List<File> files = FileUtil.loopFiles(backupDir);
        if (CollUtil.isEmpty(files)) {
            return Collections.emptyList();
        }
        return files.stream()
                .map(WavItem::new)
                .sorted((o1, o2) -> {
                    if (o1.getRecordTime() == null || o2.getRecordTime() == null) {
                        return -1;
                    }
                    return o1.getRecordTime().compareTo(o2.getRecordTime());
                })
                .collect(Collectors.toList());
    }
}

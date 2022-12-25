package cn.htwinkle.web._front.diao;

import cn.htwinkle.web.base.BaseService;
import cn.htwinkle.web.constants.Constants;
import cn.htwinkle.web.kit.FileKit;
import cn.htwinkle.web.model.User;
import com.jfinal.kit.*;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.*;

/**
 * 地奥制药服务
 *
 * @author : twinkle
 * @date : 2020/3/15 14:11
 */
public class DiAoService extends BaseService {

    /**
     * DiAoService的输出日志对象
     */
    private static final Logger LOGGER = Logger.getLogger(DiAoService.class.getName());

    /**
     * 基础的文件下载目录
     */
    public static final String DI_AO_WEB_APP_PATH =
            PathKit.getWebRootPath() + File.separator + PropKit.get(Constants.DIAO_PATH);

    public static final String DIAO_TAG = "diao";

    /**
     * 登录的方法
     *
     * @param admin admin
     * @param pass  pass
     * @return User
     */
    public User toLogin(String admin, String pass) {
        User user = User.dao.findFirst("SELECT * FROM user " +
                "WHERE userAdmin=? LIMIT 1", admin);

        if (user == null)
            return null;

        LOGGER.info("DiAoService - toLogin : " + user);

        String salt = user.getUserSalt();
        String realPass = getRealPass(salt, pass);
        if (!realPass.equals(user.getUserPass())) {
            return null;
        }

        user.removeSecretParams();
        return user;
    }

    /**
     * 获取加密后的密码
     *
     * @param salt salt
     * @param pass pass
     * @return String
     */
    private String getRealPass(String salt, String pass) {
        return HashKit.sha256(salt + pass);
    }

    /**
     * 获取所有能够下载的文件
     *
     * @return List<File>
     */
    public List<Kv> getAllCanDownloadFile() {
        List<Kv> fileList = new ArrayList<>();
        Set<String> addedFileSet = new HashSet<>();
        File[] files = getFilesBy(DI_AO_WEB_APP_PATH);

        if (null != files) {
            Arrays.stream(files).forEach(detailFile -> {
                addedFileSet.add(detailFile.getName());
                addFileToList(fileList, detailFile, true);
            });
        }

        File[] backUpFiles = getFilesBy(getDiAoBackUpPath());

        if (null != backUpFiles) {
            Arrays.stream(backUpFiles).forEach(detailFile -> {
                if (!addedFileSet.contains(detailFile.getName())) {
                    addedFileSet.add(detailFile.getName());
                    addFileToList(fileList, detailFile, false);
                }
            });
        }
        return fileList;
    }

    /**
     * 移动文件到指定的位置
     *
     * @param file file
     * @return boolean
     */
    public boolean fileRenameToDiAoPath(File file) {
        FileKit.toBackupFile(file, DIAO_TAG);
        return file.renameTo(new File(DI_AO_WEB_APP_PATH, file.getName()));
    }

    /**
     * 删除指定的文件
     *
     * @param fileName fileName
     * @return boolean
     */
    public boolean deleteFile(String fileName) {
        if (StrKit.isBlank(fileName)) {
            return false;
        }
        File file = getFileNameBy(fileName);
        boolean flag = null != file && file.exists() && file.delete();
        LOGGER.info(String.format("删除文件状态 %s", flag));
        return flag;
    }

    /**
     * 获取下载的文件
     *
     * @param fileName fileName
     * @return File
     */
    public File getDownloadFile(String fileName) {
        if (StrKit.isBlank(fileName)) {
            return null;
        }
        return getFileNameBy(fileName);
    }

    /**
     * 寻找某一个文件
     *
     * @param fileName fileName
     * @return File
     */
    private File getFileNameBy(String fileName) {
        File file = new File(DI_AO_WEB_APP_PATH, fileName);
        if (file.exists() && file.isFile()) {
            return file;
        }
        File copyFile = new File(getDiAoBackUpPath(), fileName);
        if (copyFile.exists() && copyFile.isFile()) {
            return copyFile;
        }
        return null;
    }

    /**
     * 添加文件到列表中
     *
     * @param fileList   fileList
     * @param detailFile detailFile
     */
    private void addFileToList(List<Kv> fileList, File detailFile, boolean directDownload) {
        fileList.add(Kv.create()
                .set("directDownload", directDownload)
                .set("name", detailFile.getName())
                .set("date", new Date(detailFile.lastModified()))
                .set("size", FileKit.getPrintSize(detailFile.length())));
    }

    private File[] getFilesBy(String filePath) {
        File file = new File(filePath);
        if (!file.isDirectory()) {
            return null;
        }
        File[] files = file.listFiles();
        if (files == null || files.length == 0) {
            return null;
        }
        return files;
    }

    /**
     * 备份文件的路径
     *
     * @return String
     */
    private String getDiAoBackUpPath() {
        return new File(PropKit.get(FileKit.BACKUP_FILE_PATH), DIAO_TAG).getAbsolutePath();
    }
}

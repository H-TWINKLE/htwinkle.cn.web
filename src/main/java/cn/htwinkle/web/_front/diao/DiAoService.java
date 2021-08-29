package cn.htwinkle.web._front.diao;

import cn.htwinkle.web.base.BaseService;
import cn.htwinkle.web.config.MainConfig;
import cn.htwinkle.web.constants.Constants;
import cn.htwinkle.web.kit.FileKit;
import cn.htwinkle.web.model.User;
import com.jfinal.kit.*;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
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
    public static final String DI_AO_PATH =
            PathKit.getWebRootPath() + File.separator + PropKit.get(Constants.DIAO_PATH);

    /**
     * 复制文件的目录
     */
    public static final String COPY_FILE_PATH = "copyFilePath";

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
     * 获取所有能够下载的文件
     *
     * @return List<File>
     */
    public List<Kv> getAllCanDownloadFile() {
        return getDownLoadFileList();
    }

    /**
     * 移动文件到指定的位置
     *
     * @param file file
     * @return boolean
     */
    public boolean fileRenameToDiAoPath(File file) {
        copyFile(file);
        return file.renameTo(new File(DI_AO_PATH, file.getName()));

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
        return null != file && file.exists() && file.delete();
    }

    /**
     * 复制文件
     *
     * @param file file
     * @return boolean
     */
    public boolean copyFile(File file) {
        String copyPath = PropKit.get(COPY_FILE_PATH);
        if (!(StrKit.notBlank(copyPath) && MainConfig.isProEnviron())) {
            return false;
        }
        try {
            FileUtils.copyFile(file, new File(copyPath + File.separator + file.getName()));
            return true;
        } catch (IOException e) {
            LogKit.error(e.getMessage());
            return false;
        }
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
        File file = new File(DI_AO_PATH, fileName);
        if (file.exists() && file.isFile()) {
            return file;
        }
        File copyFile = new File(PropKit.get(COPY_FILE_PATH), fileName);
        if (copyFile.exists() && copyFile.isFile()) {
            return copyFile;
        }
        return null;
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
     * 获取不重复的set
     *
     * @return Set<File>
     */
    private List<Kv> getDownLoadFileList() {

        List<Kv> fileList = new ArrayList<>();

        Set<String> addedFileSet = new HashSet<>();

        File[] files = getFileFromBaseDir();

        if (null != files) {
            Arrays.stream(files).forEach(detailFile -> {
                addedFileSet.add(detailFile.getName());
                addFileToList(fileList, detailFile, true);
            });
        }

        File[] backUpFiles = getFileFromBackUpDir();

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

    /**
     * 获取文件从基本的webApp目录
     *
     * @return File[]
     */
    private File[] getFileFromBaseDir() {
        File diAoPathFiles = new File(DI_AO_PATH);

        if (!diAoPathFiles.isDirectory()) {
            return null;
        }

        File[] files = diAoPathFiles.listFiles();
        if (files == null || files.length == 0) {
            return null;
        }
        return files;
    }

    /**
     * 获取文件从备份的文件夹
     *
     * @return File[]
     */
    private File[] getFileFromBackUpDir() {
        File diAoPathFiles = new File(PropKit.get(COPY_FILE_PATH));

        if (!diAoPathFiles.isDirectory()) {
            return null;
        }

        File[] files = diAoPathFiles.listFiles();
        if (files == null || files.length == 0) {
            return null;
        }
        return files;
    }
}

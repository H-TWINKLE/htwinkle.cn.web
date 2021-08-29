package cn.htwinkle.web._front.diao;

import cn.htwinkle.web.base.BaseController;
import cn.htwinkle.web.constants.Constants;
import cn.htwinkle.web.interceptor.DiAoInterceptor;
import cn.htwinkle.web.kit.EhcacheKit;
import cn.htwinkle.web.model.User;
import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.aop.Inject;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.upload.UploadFile;

import java.io.File;

/**
 * 黄丹的需求
 *
 * @author : twinkle
 * @date : 2020/3/15 14:06
 */
@Before(DiAoInterceptor.class)
public class DiAoController extends BaseController {

    /**
     * 标题
     */
    public static final String HD_TITLE = "HD @ 一二三";

    @Inject
    DiAoService service;

    @Override
    public void index() {
        setTitle(HD_TITLE);
        setAttr("allFile", service.getAllCanDownloadFile());
        render("index.html");
    }

    @Clear(DiAoInterceptor.class)
    public void login() {
        setTitle(HD_TITLE);
        render("login.html");
    }

    public void logOut() {
        String cookies = getCookie(Constants.SESSION__DIAO_KEY);
        EhcacheKit.INSTANCE.removeValue(cookies, User.class);
        forwardAction("/diao/login");
    }

    @Clear(DiAoInterceptor.class)
    public void toLogin(String admin, String pass) {
        User user = service.toLogin(admin, pass);
        if (user == null) {
            setMsgTip("用户名或者密码错误");
            forwardAction("/diao/login");
            return;
        }

        successToLogin(user);
        setMsgTip(String.format("欢迎您，%s ", user.getUserNickname()));
        redirect("/diao");
    }

    @Clear(DiAoInterceptor.class)
    public void uploadFile() {
        UploadFile uploadFile = getFile("uploadFile");
        if (uploadFile == null) {
            renderJson(Ret.fail("msg", "文件为空"));
            return;
        }

        String isApi = get("isApi");

        if (StrKit.notBlank(isApi)) {
            renderUploadFileAsApi(uploadFile);
        } else {
            renderUploadFileAsHtml(uploadFile);
        }
    }

    public void deleteFile(String fileName) {
        if (service.deleteFile(fileName)) {
            setMsgTip("删除文件成功！");
        } else {
            setMsgTip("删除文件失败！");
        }
        forwardAction("/diao");
    }

    /**
     * 下载文件的地址
     *
     * @param fileName fileName
     */
    public void downloadFile(String fileName) {
        File downloadFile = service.getDownloadFile(fileName);
        if (null != downloadFile) {
            renderFile(downloadFile);
            return;
        }
        renderError(404);
    }

    private void successToLogin(User user) {
        setSessionUser(user);
        String vaString = StrKit.getRandomUUID();
        setCookie(Constants.SESSION__DIAO_KEY, vaString, Constants.MAX_SESSION_DATE);
        EhcacheKit.INSTANCE.saveValue(vaString, user);
    }

    private void renderUploadFileAsApi(UploadFile uploadFile) {
        if (service.fileRenameToDiAoPath(uploadFile.getFile())) {
            renderJson(Ret
                    .ok("msg", "上传成功！")
                    .set("fileName", uploadFile.getFileName())
                    .set("path", uploadFile.getFile().getAbsolutePath()));
        } else {
            renderJson(Ret
                    .fail("msg", "上传成功,但是移动失败！")
                    .set("fileName", uploadFile.getFileName())
                    .set("path", uploadFile.getFile().getAbsolutePath()));
        }
    }

    private void renderUploadFileAsHtml(UploadFile uploadFile) {
        if (service.fileRenameToDiAoPath(uploadFile.getFile())) {
            setMsgTip("上传成功！");
        } else {
            setMsgTip("上传失败！");
        }
        forwardAction("/diao");
    }
}

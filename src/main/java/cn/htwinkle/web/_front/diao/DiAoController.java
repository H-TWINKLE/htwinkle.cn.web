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
import org.leon.swagger.annotation.Api;
import org.leon.swagger.annotation.ApiOperation;
import org.leon.swagger.annotation.Param;
import org.leon.swagger.annotation.Params;
import org.leon.swagger.model.constant.HttpMethod;

import java.io.File;

/**
 * 黄丹的需求
 *
 * @author : twinkle
 * @date : 2020/3/15 14:06
 */
@Before(DiAoInterceptor.class)
@Api(tag = DiAoController.TAG, description = "文件上传")
public class DiAoController extends BaseController {
    protected static final String TAG = "DI_AO";

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
        this.index();
    }

    @Clear(DiAoInterceptor.class)
    @ApiOperation(url = "/diao/uploadFile", tag = TAG, httpMethod = HttpMethod.POST, description = "上传文件")
    @Params({
            @Param(name = "uploadFile", description = "需要上传的文件", required = true, dataType = "file"),
            @Param(name = "isApi", description = "返回是否为json,只要存在值就是返回json", defaultValue = "1")
    })
    public void uploadFile() {
        UploadFile uploadFile = getFile("uploadFile");
        if (uploadFile == null) {
            renderJson(Ret.fail("msg", "文件为空"));
            return;
        }
        renderByIsApi(uploadFile);
    }

    /**
     * 删除文件
     *
     * @param fileName fileName
     */
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

    /**
     * 通过条件判断返回
     *
     * @param uploadFile uploadFile
     */
    private void renderByIsApi(UploadFile uploadFile) {
        String isApi = get("isApi");
        if (StrKit.notBlank(isApi)) {
            renderUploadFileAsApi(uploadFile);
        } else {
            renderUploadFileAsHtml(uploadFile);
        }
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

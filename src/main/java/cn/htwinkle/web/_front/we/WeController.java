package cn.htwinkle.web._front.we;

import cn.htwinkle.web.base.BaseController;
import com.jfinal.aop.Inject;
import com.jfinal.kit.Kv;
import com.jfinal.kit.Ret;
import com.jfinal.upload.UploadFile;
import org.leon.swagger.annotation.Api;
import org.leon.swagger.annotation.ApiOperation;
import org.leon.swagger.annotation.Param;
import org.leon.swagger.annotation.Params;
import org.leon.swagger.model.constant.HttpMethod;

import java.io.File;

/**
 * 我们的生活的控制器
 *
 * @author : twinkle
 * @date : 2020/3/15 11:36
 */
@Api(tag = WeController.TAG, description = "我们的生活")
public class WeController extends BaseController {

    protected static final String TAG = "WE";

    @Inject
    WeService weService;

    @Override
    public void index() {
        renderJson(Kv.create().set("path", "we"));
    }

    @ApiOperation(url = "/we/uploadFile", tag = WeController.TAG, httpMethod = HttpMethod.POST, description = "上传文件")
    @Params({
            @Param(name = "uploadFile", description = "需要上传的文件", required = true, dataType = "file")
    })
    public void uploadFile() {
        UploadFile uploadFile = getFile("uploadFile");
        if (uploadFile == null) {
            renderJson(Ret.fail("文件为空"));
            return;
        }
        File backUpFile = weService.backUpWebHomeFile(uploadFile.getFile());
        if (backUpFile != null) {
            renderJson(Ret.ok("上传成功").set("filePath", backUpFile.getAbsolutePath()).set("name", backUpFile.getName()));
            return;
        }
        renderJson(Ret.fail("上传失败"));
    }

    @ApiOperation(url = "/we/getWavList", tag = WeController.TAG, httpMethod = HttpMethod.GET, description = "获取上传的wav文件的时间线")
    public void getWavList() {
        renderJson(weService.getBackUpWavFile());
    }
}

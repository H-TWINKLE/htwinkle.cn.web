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

/**
 * 我们的生活的控制器
 *
 * @author : twinkle
 * @date : 2020/3/15 11:36
 */
@Api(tag = "WE", description = "我们的生活")
public class WeController extends BaseController {

    @Inject
    WeService weService;

    @Override
    public void index() {
        renderJson(Kv.create().set("path", "we"));
    }

    /**
     * 获取图片列表
     */
    @ApiOperation(url = "/we/uploadFile", tag = "WE", httpMethod = HttpMethod.POST, description = "上传文件")
    @Params({
            @Param(name = "uploadFile", description = "需要上传的文件", required = true, dataType = "file")
    })
    public void uploadFile() {
        UploadFile uploadFile = getFile("uploadFile");
        if (uploadFile == null) {
            renderJson(Ret.fail("msg", "文件为空"));
            return;
        }
        renderJson(weService.backUpWebHomeFile(uploadFile.getFile()) ? Ret.ok() : Ret.fail());
    }

}

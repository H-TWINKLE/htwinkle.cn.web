package cn.htwinkle.web._front.picture;

import cn.htwinkle.web.base.BaseController;
import cn.htwinkle.web.constants.Constants;
import cn.htwinkle.web.kit.ProxyKit;
import cn.htwinkle.web.model.Picture;
import com.jfinal.aop.Inject;
import com.jfinal.core.JFinal;
import com.jfinal.kit.StrKit;
import org.leon.swagger.annotation.Api;
import org.leon.swagger.annotation.ApiOperation;
import org.leon.swagger.annotation.Param;
import org.leon.swagger.annotation.Params;
import org.leon.swagger.model.constant.HttpMethod;

import java.util.Arrays;

/**
 * 图片的控制器
 *
 * @author : twinkle
 * @date : 2020/3/15 11:37
 */
@Api(tag = PictureController.TAG, description = "每日一图")
public class PictureController extends BaseController {

    protected static final String TAG = "PICTURE";

    @Inject
    PictureService pictureService;

    @Override
    public void index() {
        setTitle("图片瀑布");
        String type = getDefaultType();
        setAttr("picture", pictureService.getPictureList(100, type));
        render("index.html");
    }


    /**
     * 获取图片列表
     */
    @ApiOperation(url = "/picture/api", tag = PictureController.TAG, httpMethod = HttpMethod.GET, description = "获取图片列表")
    @Params({
            @Param(name = "num", description = "图片张数", defaultValue = "10"),
            @Param(name = "plate", description = "图片分类(1：手机、2：电脑)", defaultValue = "2"),
            @Param(name = "type", description = "图片类型(fengjing, keai, jianzhu, zhiwu, dongwu, meishi)", defaultValue = "fengjing")
    })
    public void api() {
        Integer num = getDefaultNumForApi();
        Integer plate = getDefaultPlate();
        String type = getDefaultType();
        renderJson(
                getDefaultJson(num, pictureService.getPictureList(num, plate, type))
                        .set("plate", plate)
                        .set("type", type));
    }

    /**
     * 获取图片列表
     */
    @ApiOperation(url = "/picture/randomImg", tag = PictureController.TAG, httpMethod = HttpMethod.GET, description = "随机获取一张图片")
    @Params({
            @Param(name = "plate", description = "图片分类(1：手机、2：电脑)", defaultValue = "2"),
            @Param(name = "type", description = "图片类型(fengjing, keai, jianzhu, zhiwu, dongwu, meishi)", defaultValue = "fengjing")
    })
    public void randomImg() {
        Integer plate = getDefaultPlate();
        String type = getDefaultType();
        Picture onePicture = pictureService.getOnePicture(plate, type);
        if (onePicture == null) {
            redirect(pictureService.getBiyingPic());
            return;
        }
        redirect(JFinal.me().getContextPath() + ProxyKit.getProxyUrl(onePicture.getPictureUrl()));
    }

    /**
     * 获取图片列表
     */
    @ApiOperation(url = "/picture/biYingUrl", tag = PictureController.TAG, httpMethod = HttpMethod.GET, description = "获取必应壁纸")
    public void biYingUrl() {
        redirect(pictureService.getBiyingPic());
    }

    /**
     * 从参数中取得类型
     *
     * @return String
     */
    private String getDefaultType() {
        String type = getPara("type");
        if (StrKit.isBlank(type)) {
            return Constants.G3_BIZHI_TYPES[0];
        }
        if (Arrays.toString(Constants.G3_BIZHI_TYPES).contains(type))
            return type;
        return Constants.G3_BIZHI_TYPES[0];
    }

    /**
     * 获取默认的类型
     *
     * @return Integer
     */
    private Integer getDefaultPlate() {
        Integer plate = getParaToInt("plate");
        if (null == plate) {
            plate = Picture.PLATE_DESK;
        }
        return plate;
    }
}

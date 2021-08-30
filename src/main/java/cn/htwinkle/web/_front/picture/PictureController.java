package cn.htwinkle.web._front.picture;

import cn.htwinkle.web.base.BaseController;
import cn.htwinkle.web.constants.Constants;
import cn.htwinkle.web.model.Picture;
import com.jfinal.aop.Inject;
import com.jfinal.kit.StrKit;

import java.util.Arrays;
import java.util.List;

/**
 * 图片的控制器
 *
 * @author : twinkle
 * @date : 2020/3/15 11:37
 */
public class PictureController extends BaseController {

    @Inject
    PictureService pictureService;

    @Override
    public void index() {
        setTitle("图片瀑布");
        setAttr("picture", getPictureList(100));
        render("index.html");
    }


    public void api() {
        Integer num = getDefaultNumForApi();
        renderJson(getDefaultJson(num, getPictureList(num))
                .set("type", getDefaultTypesFromParams()));
    }


    /**
     * 从参数中取得类型
     *
     * @return String
     */
    private String getDefaultTypesFromParams() {
        String types = getPara("types");
        if (StrKit.isBlank(types)) {
            return Constants.G3_BIZHI_TYPES[0];
        }
        if (Arrays.toString(Constants.G3_BIZHI_TYPES).contains(types))
            return types;
        return Constants.G3_BIZHI_TYPES[0];
    }

    /**
     * 获取图片列表
     *
     * @param num num
     * @return List<Picture>
     */
    private List<Picture> getPictureList(Integer num) {
        String type = getDefaultTypesFromParams();
        return pictureService.getPictureList(num, type);
    }


}

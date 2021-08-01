package cn.htwinkle.web.bean;

import cn.htwinkle.web.base.bmob.BaseBmob;

import java.io.Serializable;

/**
 * TODO 描述用途
 *
 * @author : twinkle
 * @date : 2020/3/15 17:52
 */
public class Image extends BaseBmob implements Serializable {

    private static final long serialVersionUID = 7323589509441597328L;

    private String imgUrl;
    private String imageName;


    public Image() {
        super();
    }

    public Image(String __type, String objectId) {
        super();
        this.set__type(__type);
        this.setObjectId(objectId);
    }


    /**
     * Gets  the value of imgUrl.
     *
     * @return the value of imgUrl
     */
    public String getImgUrl() {
        return imgUrl;
    }

    /**
     * Sets  the imgUrl.
     *
     * @param imgUrl imgUrl
     * @return this
     */
    public Image setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
        return this;
    }

    /**
     * Gets  the value of imageName.
     *
     * @return the value of imageName
     */
    public String getImageName() {
        return imageName;
    }

    /**
     * Sets  the imageName.
     *
     * @param imageName imageName
     * @return this
     */
    public Image setImageName(String imageName) {
        this.imageName = imageName;
        return this;
    }
}

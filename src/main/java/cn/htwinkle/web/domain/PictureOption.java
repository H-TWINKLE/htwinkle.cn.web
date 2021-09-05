package cn.htwinkle.web.domain;

/**
 * 爬虫图片参数类
 *
 * @author : twinkle
 * @date : 2021/9/5 22:09
 */
public class PictureOption extends BaseOption {
    private String type;
    private int plate;

    /**
     * Gets  the value of type.
     *
     * @return the value of type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets  the type.
     *
     * @param type type
     * @return this
     */
    public PictureOption setType(String type) {
        this.type = type;
        return this;
    }

    /**
     * Gets  the value of plate.
     *
     * @return the value of plate
     */
    public int getPlate() {
        return plate;
    }

    /**
     * Sets  the plate.
     *
     * @param plate plate
     * @return this
     */
    public PictureOption setPlate(int plate) {
        this.plate = plate;
        return this;
    }
}

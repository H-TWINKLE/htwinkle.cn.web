package cn.htwinkle.web.domain;

/**
 * 无参数类型
 *
 * @author : twinkle
 * @date : 2021/9/5 22:26
 */
public class BaseOption implements IOption {
    private String type;
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
    public BaseOption setType(String type) {
        this.type = type;
        return this;
    }
}

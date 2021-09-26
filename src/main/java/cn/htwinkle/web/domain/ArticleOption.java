package cn.htwinkle.web.domain;

import static cn.htwinkle.web.base.BaseController.DEFAULT_NUMBER;

/**
 * 爬虫文章参数类
 *
 * @author : twinkle
 * @date : 2021/9/5 22:09
 */
public class ArticleOption extends BaseOption {
    private Integer number;

    /**
     * Gets  the value of number.
     *
     * @return the value of number
     */
    public int getNumber() {
        if (number == null) {
            number = DEFAULT_NUMBER;
        }
        return number;
    }

    /**
     * Sets  the number.
     *
     * @param number number
     * @return this
     */
    public ArticleOption setNumber(int number) {
        this.number = number;
        return this;
    }
}

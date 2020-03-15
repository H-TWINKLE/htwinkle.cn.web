package cn.htwinkle.devotion.spider;

import com.jfinal.plugin.activerecord.Model;

/**
 * 爬虫所用的接口
 *
 * @author : twinkle
 * @date : 2020/3/15 10:51
 */
public interface ISpider<T extends Model<T>> {

    /**
     * 返回一个爬虫的实体对象
     *
     * @return T
     */
    T get();

    /**
     * 返回一个需要类型的爬虫实体对象
     *
     * @param types 类型
     * @return T
     */
    T get(String types);


}

package cn.htwinkle.web.spider;

import cn.htwinkle.web.domain.IOption;
import cn.htwinkle.web.domain.PictureOption;
import com.jfinal.plugin.activerecord.Model;

import java.util.List;

/**
 * 爬虫所用的接口
 *
 * @author : twinkle
 * @date : 2020/3/15 10:51
 */
public interface ISpider<T extends Model<T>, V extends IOption> {

    /**
     * 返回一个爬虫的实体对象
     *
     * @return T
     */
    T get();

    /**
     * 返回一个需要类型的爬虫实体对象
     *
     * @param v 类型
     * @return T
     */
    T get(V v);

    /**
     * 获取爬虫的 数据列表
     *
     * @return return
     */
    List<T> getList();

    /**
     * 获取爬虫的 数据列表
     *
     * @return return
     */
    List<T> getList(V v);

}

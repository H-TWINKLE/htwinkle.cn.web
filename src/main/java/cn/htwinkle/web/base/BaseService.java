package cn.htwinkle.web.base;

import cn.htwinkle.web.model.Visit;
import com.jfinal.plugin.activerecord.Model;

import java.util.List;

/**
 * TODO 描述用途
 *
 * @author : twinkle
 * @date : 2020/3/10 18:16
 */
public class BaseService {

    public <T extends Model<T>> boolean save(T t) {
        return t.save();
    }

    public <T extends Model<T>> boolean delete(T t) {
        return t.delete();
    }

    public <T extends Model<T>> boolean update(T t) {
        return t.update();
    }

    public <T extends Model<T>> T findById(T t, Object id) {
        return t.findById(id);
    }

    public <T extends Model<T>> List<T> findAll(T t, Object id) {
        return t.findAll();
    }

    /**
     * 获取到总共的数量
     *
     * @return Integer
     */
    public Integer getAllVisitUserNum() {
        return Visit.dao.findFirst("SELECT COUNT(*) num FROM visit").getInt("num");
    }


}

package cn.htwinkle.we.service;

import cn.htwinkle.we.entity.Jwgl;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author twinkle
 * @since 2019-05-01
 */
public interface IJwglService extends IService<Jwgl> {

    void toLogin(String admin, String pass);

}

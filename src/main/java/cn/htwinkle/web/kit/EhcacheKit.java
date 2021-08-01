package cn.htwinkle.web.kit;

import cn.htwinkle.web.constants.EhcacheConstants;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.IBean;
import com.jfinal.plugin.ehcache.CacheKit;

/**
 * 缓存对象
 *
 * @author : twinkle
 * @date : 2020/3/15 15:03
 */
public enum EhcacheKit {

    INSTANCE;


    /**
     * 保存缓存
     *
     * @param key key
     * @param t   t
     */
    public <T> void saveValue(String key, T t) {
        if (StrKit.isBlank(key))
            return;
        String mainKeyName = getKeyMainName(t.getClass());
        CacheKit.put(mainKeyName, key, t);
    }

    /**
     * 获取缓存
     *
     * @param key    key
     * @param tClass tClass
     * @param <T>    <T>
     * @return T
     */
    public <T> T getValue(String key, Class<T> tClass) {
        if (StrKit.isBlank(key))
            return null;
        String mainKeyName = getKeyMainName(tClass);
        return CacheKit.get(mainKeyName, key);
    }

    /**
     * 移除掉缓存
     *
     * @param key    key
     * @param tClass tClass
     * @param <T>    <T>
     */
    public <T> void removeValue(String key, Class<T> tClass) {
        if (StrKit.isBlank(key))
            return;
        String mainKeyName = getKeyMainName(tClass);
        CacheKit.remove(mainKeyName, key);
    }


    /**
     * 获取到缓存名字
     *
     * @param tClass tClass
     * @param <T>    <T>
     * @return String
     */
    private <T> String getKeyMainName(Class<T> tClass) {
        //用来判断子类是否继承于父类的或者父接口的。
        if (tClass.isAssignableFrom(IBean.class)) {
            return tClass.getSimpleName().toLowerCase();
        } else {
            return EhcacheConstants.DEFAULTS;
        }
    }


}

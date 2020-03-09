package cn.htwinkle.devotion.config;

import cn.htwinkle.devotion.model._MappingKit;
import cn.htwinkle.devotion.routes.FrontRoutes;
import com.alibaba.druid.filter.stat.StatFilter;
import com.jfinal.config.*;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.json.MixedJsonFactory;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.render.ViewType;
import com.jfinal.server.undertow.UndertowServer;
import com.jfinal.template.Engine;

import static cn.htwinkle.devotion.constants.Constants.*;

/**
 * 启动配置类
 *
 * @author : twinkle
 * @date : 2020/3/9 19:13
 */
public class MainConfig extends JFinalConfig {

    /**
     * 自动判断是否为服务器环境
     */
    private static int IS_PRO_ENV = 0;

    /**
     * 配置JFinal常量
     */
    @Override
    public void configConstant(Constants me) {
        PropKit.use(CONFIG_DEV_FILE_NAME);
        if (isProEnviron()) {
            PropKit.append(CONFIG_PRO_FILE_NAME);
        }
        me.setDevMode(PropKit.getBoolean(DEV_MODE));
        me.setMaxPostSize(PropKit.getInt(POST_FILE_SIZE));
        me.setBaseUploadPath(PropKit.get(UPLOAD_PATH));
        me.setBaseDownloadPath(PropKit.get(DOWNLOAD_PATH));
        me.setViewType(ViewType.JFINAL_TEMPLATE);
        me.setError404View(PropKit.get(VIEW_OF_ERROR));
        me.setError500View(PropKit.get(VIEW_OF_ABNORMAL));
        me.setJsonFactory(MixedJsonFactory.me());
    }


    /**
     * 配置JFinal路由映射
     */
    @Override
    public void configRoute(Routes me) {
        me.add(new FrontRoutes());
    }

    /**
     * 配置JFinal插件 数据库连接池 ORM 缓存等插件 自定义插件
     */
    @Override
    public void configPlugin(Plugins me) {

        /*JDBC-PLUGIN*/
        DruidPlugin druidPlugin = new DruidPlugin(PropKit.get(JDBC_URL), PropKit.get(JDBC_USER),
                PropKit.get(JDBC_PASS).trim());
        StatFilter set = new StatFilter();
        set.setLogSlowSql(true);
        druidPlugin.addFilter(set);
        druidPlugin.setDriverClass("com.mysql.jdbc.Driver");

        ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);

        arp.setShowSql(PropKit.getBoolean(DEV_MODE));
        arp.setDialect(new MysqlDialect());

        _MappingKit.mapping(arp);

        me.add(druidPlugin);
        me.add(arp);

        /*CRON-PLUGIN*/
        //me.add(new Cron4jPlugin(PropKit.use(CONFIG_DEV_FILE_NAME)));

    }

    /**
     * 配置全局拦截器
     */
    @Override
    public void configInterceptor(Interceptors me) {
    }

    /**
     * 存在则为本地环境 返回 2 即false
     */
    public static boolean isProEnviron() {
        if (IS_PRO_ENV == 0) {
            IS_PRO_ENV = System.getProperty("os.name").toLowerCase().contains("windows") ? 2 : 1;
        }
        return IS_PRO_ENV == 1;
    }

    /**
     * 配置全局处理器
     */
    @Override
    public void configHandler(Handlers me) {
        me.add(new ContextPathHandler("base"));
    }

    /**
     * 配置模板引擎
     */
    @Override
    public void configEngine(Engine me) {
        me.setDevMode(PropKit.getBoolean(DEV_MODE));
        //template
        me.addSharedFunction("/template/comm/_layout.html");
        //value
        me.addSharedObject(RECORD_NUMBER, PropKit.get(RECORD_NUMBER));
        me.addSharedObject(RECORD_URL, PropKit.get(RECORD_URL));
        me.addSharedObject(RECORD_TITLE, PropKit.get(RECORD_TITLE));
        me.addSharedObject(KEY_OF_IMAGE, PropKit.get(KEY_OF_IMAGE));
    }

    public static void main(String[] args) {
        UndertowServer.create(MainConfig.class, CONFIG_DEV_FILE_NAME).start();
    }

}


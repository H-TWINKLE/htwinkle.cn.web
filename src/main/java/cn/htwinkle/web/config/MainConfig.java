package cn.htwinkle.web.config;

import cn.htwinkle.web.interceptor.AllowOriginInterceptor;
import cn.htwinkle.web.interceptor.GolbalInterceptor;
import cn.htwinkle.web.kit.EnvKit;
import cn.htwinkle.web.model._MappingKit;
import cn.htwinkle.web.routes.FrontRoutes;
import cn.htwinkle.web.routes.ToolRoutes;
import com.alibaba.druid.filter.stat.StatFilter;
import com.jfinal.config.*;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.json.MixedJsonFactory;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.cron4j.Cron4jPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.render.ViewType;
import com.jfinal.template.Engine;
import org.jetbrains.annotations.NotNull;
import org.leon.swagger.plugin.SwaggerPlugin;

import static cn.htwinkle.web.constants.Constants.*;

/**
 * 启动配置类
 *
 * @author : twinkle
 * @date : 2020/3/9 19:13
 */
public class MainConfig extends JFinalConfig {

    /**
     * 配置JFinal常量
     */
    @Override
    public void configConstant(Constants me) {
        PropKit.use(CONFIG_DEV_FILE_NAME);
        if (EnvKit.INSTANCE.isProEnviron()) {
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
        me.setInjectDependency(true);
    }


    /**
     * 配置JFinal路由映射
     */
    @Override
    public void configRoute(Routes me) {
        me.add(new FrontRoutes());
        me.add(new ToolRoutes());
    }

    /**
     * 配置JFinal插件 数据库连接池 ORM 缓存等插件 自定义插件
     */
    @Override
    public void configPlugin(Plugins me) {
        /*JDBC-PLUGIN*/
        DruidPlugin druidPlugin = new DruidPlugin(PropKit.get(JDBC_URL),
                PropKit.get(JDBC_USER),
                getMYSQLPass());
        StatFilter set = new StatFilter();
        set.setLogSlowSql(true);

        druidPlugin.addFilter(set);
        druidPlugin.setDriverClass("com.mysql.cj.jdbc.Driver");

        ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);

        arp.setShowSql(PropKit.getBoolean(DEV_MODE));
        arp.setDialect(new MysqlDialect());

        _MappingKit.mapping(arp);

        me.add(druidPlugin);
        me.add(arp);

        /*CRON-PLUGIN*/
        me.add(new Cron4jPlugin(PropKit.use(CONFIG_DEV_FILE_NAME)));
        /*EHCACHE-PLUGIN*/
        me.add(new EhCachePlugin());
        /*SWAGGER组件*/
        me.add(new SwaggerPlugin(CONFIG_DEV_FILE_NAME));
    }

    /**
     * 配置全局拦截器
     */
    @Override
    public void configInterceptor(Interceptors me) {
        me.add(new GolbalInterceptor());
        me.add(new AllowOriginInterceptor());
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
        me.addSharedFunction("/template/comm/_pour.html");
        me.addSharedFunction("/template/comm/_toolBox.html");
        //value
        me.addSharedObject(RECORD_NUMBER, PropKit.get(RECORD_NUMBER));
        me.addSharedObject(RECORD_URL, PropKit.get(RECORD_URL));
        me.addSharedObject(RECORD_TITLE, PropKit.get(RECORD_TITLE));
        me.addSharedObject(KEY_OF_IMAGE, PropKit.get(KEY_OF_IMAGE));
        me.addSharedObject(RECORD_LINK_URL, PropKit.get(RECORD_LINK_URL));
        me.addSharedObject(UPDATE_TIME, PropKit.get(UPDATE_TIME, ""));
    }

    @NotNull
    private String getMYSQLPass() {
        return EnvKit.INSTANCE.getEnvironmentValue(PropKit.get(JDBC_PASS).trim());
    }
}


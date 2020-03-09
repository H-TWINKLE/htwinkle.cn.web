package com.twinkle.common.config;

import com.alibaba.druid.filter.stat.StatFilter;
import com.jfinal.config.*;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.ext.handler.UrlSkipHandler;
import com.jfinal.json.MixedJsonFactory;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.cron4j.Cron4jPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.render.ViewType;
import com.jfinal.server.undertow.UndertowServer;
import com.jfinal.template.Engine;
import com.twinkle.common.model._MappingKit;
import com.twinkle.controller.*;
import com.twinkle.handler.JreBelHander;
import com.twinkle.interceptor.GlobalCountInterceptor;

import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;

public class MainConfig extends JFinalConfig {

    private static int isPro = 0;

    private static final String LOCAL_PATH = "E:\\MyMajor\\myeclipse\\Dy_Law\\src\\main\\webapp\\upload";

    public static final String CONFIG_FILE_NAME = "config-dev.properties";

    public static AtomicInteger GLOBAL_COUNT = null;

    /**
     * 配置JFinal常量
     */
    @Override
    public void configConstant(Constants me) {
        PropKit.use("config-dev.properties");
        if (isProEnviron()) {
            PropKit.append("config-pro.properties");
        }
        me.setDevMode(PropKit.getBoolean("devMode"));

        me.setMaxPostSize(1024 * 1024 * 10);
        me.setBaseUploadPath("upload");
        me.setBaseDownloadPath("download");

        me.setViewType(ViewType.JFINAL_TEMPLATE);
        me.setError404View("/comm/error/404.html");
        me.setError500View("/comm/error/500.html");

        me.setJsonFactory(MixedJsonFactory.me());

    }


    /**
     * 配置JFinal路由映射
     */
    @Override
    public void configRoute(Routes me) {
        me.add("/", IndexController.class);
        me.add("/jwgl", JwglController.class);
        me.add("/eol", EolController.class);
        me.add("/netmusic", NetMusicController.class);
        me.add("/img", ImgController.class);
        me.add("/kp", KpShareController.class);
        me.add("/article", ArticleController.class);
    }

    /**
     * 配置JFinal插件 数据库连接池 ORM 缓存等插件 自定义插件
     */
    @Override
    public void configPlugin(Plugins me) {
        DruidPlugin druidPlugin = new DruidPlugin(PropKit.get("jdbcUrl"), PropKit.get("user"),
                PropKit.get("password").trim());
        StatFilter set = new StatFilter();
        set.setLogSlowSql(true);
        druidPlugin.addFilter(set);
        druidPlugin.setDriverClass("com.mysql.jdbc.Driver");

        ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);

        arp.setShowSql(PropKit.getBoolean("devMode"));
        arp.setDialect(new MysqlDialect());

        _MappingKit.mapping(arp);

        me.add(druidPlugin);
        me.add(arp);
        me.add(new Cron4jPlugin(PropKit.use(CONFIG_FILE_NAME)));

    }

    /**
     * 配置全局拦截器
     */
    @Override
    public void configInterceptor(Interceptors me) {
        me.add(new GlobalCountInterceptor());
    }

    /**
     * 存在则为本地环境 返回 2 即false
     */
    public static boolean isProEnviron() {
        if (isPro == 0) {
            isPro = new File(LOCAL_PATH).exists() ? 2 : 1;
        }
        return isPro == 1;
    }

    /**
     * 配置全局处理器
     */
    @Override
    public void configHandler(Handlers me) {

        me.add(new JreBelHander());
        me.add(new ContextPathHandler("base"));
        me.add(new UrlSkipHandler("^/Law.+", false));

    }

    /**
     * 配置模板引擎
     */
    @Override
    public void configEngine(Engine me) {
        me.setDevMode(PropKit.getBoolean("devMode"));
        me.addSharedFunction("/comm/_layout.html");
        me.addSharedFunction("/comm/_pour.html");
        me.addSharedObject("key_of_image", PropKit.get("key_of_image"));


    }

    public static void main(String[] args) {
        UndertowServer.create(MainConfig.class, CONFIG_FILE_NAME).start();
    }

    private Integer getGlobalCount() {
        Record r = Db.findFirst("select sum(num) num from globalcount");
        return r == null ? 10001 : r.getInt("num");
    }

    @Override
    public void onStart() {
        MainConfig.GLOBAL_COUNT = new AtomicInteger(getGlobalCount());
    }
}

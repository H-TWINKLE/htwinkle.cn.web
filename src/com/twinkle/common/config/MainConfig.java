package com.twinkle.common.config;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.kit.PropKit;
import com.jfinal.template.Engine;
import com.twinkle.common.model._MappingKit;
import com.twinkle.controller.ArticleController;
import com.twinkle.controller.EolController;
import com.twinkle.controller.ImgController;
import com.twinkle.controller.IndexController;
import com.twinkle.controller.JwglController;
import com.twinkle.controller.KpShareController;
import com.twinkle.controller.NetMusicController;
import com.twinkle.interceptor.GlobalCountInterceptor;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.json.MixedJsonFactory;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.cron4j.Cron4jPlugin;
import com.jfinal.render.ViewType;

public class MainConfig extends JFinalConfig {
	/**
	 * 配置JFinal常量
	 */
	@Override
	public void configConstant(Constants me) {
		PropKit.use("config.properties");
		me.setDevMode(PropKit.getBoolean("devMode"));
		me.setBaseUploadPath("upload/temp/");
		me.setMaxPostSize(1024 * 1024 * 10);
		// 设置默认下载文件路径 renderFile使用
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
		me.add("/", IndexController.class); // index 主页
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
		ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
		arp.setShowSql(PropKit.getBoolean("devMode"));
		arp.setDialect(new MysqlDialect());

		_MappingKit.mapping(arp);

		me.add(druidPlugin);
		me.add(arp);
		me.add(new Cron4jPlugin(PropKit.use("config.properties")));

	}

	/**
	 * 配置全局拦截器
	 */
	@Override
	public void configInterceptor(Interceptors me) {

		me.add(new GlobalCountInterceptor());

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
		me.setDevMode(PropKit.getBoolean("devMode")); 
		me.addSharedFunction("/comm/_layout.html");
		me.addSharedFunction("/comm/_pour.html");

	}

	public static void main(String[] args) {

		JFinal.start("WebRoot", 1314, "/");
	}

}

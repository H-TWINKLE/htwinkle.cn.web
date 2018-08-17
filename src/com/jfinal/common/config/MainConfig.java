package com.jfinal.common.config;

import com.jfinal.common.model._MappingKit;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.controller.ArticleController;
import com.jfinal.controller.EolController;
import com.jfinal.controller.ImgController;
import com.jfinal.controller.IndexController;
import com.jfinal.controller.JwglController;
import com.jfinal.controller.KpShareController;
import com.jfinal.controller.NetMusicController;
import com.jfinal.core.JFinal;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.interceptor.GlobalCountInterceptor;
import com.jfinal.kit.PropKit;
import com.jfinal.template.Engine;
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
		// 读取数据库配置文件
		PropKit.use("config.properties");
		// 设置当前是否为开发模式
		me.setDevMode(PropKit.getBoolean("devMode"));
		// 设置默认上传文件保存路径 getFile等使用
		me.setBaseUploadPath("upload/temp/");
		// 设置上传最大限制尺寸  10M
		me.setMaxPostSize(1024*1024*10);
		// 设置默认下载文件路径 renderFile使用
		me.setBaseDownloadPath("download");
		// 设置默认视图类型
		me.setViewType(ViewType.JFINAL_TEMPLATE);
		// 设置404渲染视图
		me.setError404View("comm/error/error.html");
		// 设置json工厂
		me.setJsonFactory(MixedJsonFactory.me());

	}

	/**
	 * 配置JFinal路由映射
	 */
	@Override
	public void configRoute(Routes me) {
		me.add("/",IndexController.class);  //index 主页
		me.add("/jwgl", JwglController.class);
		me.add("/eol", EolController.class);
		me.add("/netmusic", NetMusicController.class);
		me.add("/img", ImgController.class);
		me.add("/kp",KpShareController.class);
		me.add("/article",ArticleController.class);
	}

	public static DruidPlugin createDruidPlugin() {
		return new DruidPlugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password").trim());
	}

	/**
	 * 配置JFinal插件 数据库连接池 ORM 缓存等插件 自定义插件
	 */
	@Override
	public void configPlugin(Plugins me) {
		// 配置数据库连接池插件
		DruidPlugin druidPlugin = createDruidPlugin();
		// orm映射 配置ActiveRecord插件
		ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
		arp.setShowSql(PropKit.getBoolean("devMode"));
		arp.setDialect(new MysqlDialect());
		/******** 在此添加数据库 表-Model 映射 *********/
		// 添加到插件列表中
		me.add(druidPlugin);
		me.add(arp);
		me.add(new Cron4jPlugin(PropKit.use("config.properties")));
		//me.add(new EhCachePlugin()); //提高系统的并发访问速度,将数据库的数据缓存在内存中
		_MappingKit.mapping(arp); // 所有映射在 MappingKit 中自动化搞定

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
		// 这里只有选择JFinal TPL的时候才用
		me.setDevMode(PropKit.getBoolean("devMode"));        //模板文件的修改会及时生效，相当于热加载功能
		me.addSharedFunction("/comm/_layout.html");     //共享index页面
		me.addSharedFunction("/comm/_pour.html"); 
		
	}

	public static void main(String[] args) {

		JFinal.start("WebRoot", 1314, "/");
	}

}

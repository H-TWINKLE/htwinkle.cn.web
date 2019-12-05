package cn.htwinkle.we.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@ServletComponentScan//这行是为了避免扫描不到Druid的Servlet
public class DruidConfig {

    /*@Bean
    public ServletRegistrationBean druidServlet() {
        ServletRegistrationBean reg = new ServletRegistrationBean();
        reg.setServlet(new StatViewServlet());
        reg.addUrlMappings("/druid/*");
        //reg.addInitParameter("allow", "127.0.0.1"); //白名单
        reg.addInitParameter("resetEnable", "false");
        return reg;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();

        filterRegistrationBean.setFilter(new WebStatFilter());
        //设置忽略请求 initParams.put("exclusions", "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*");
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }*/


    @Bean("druidDataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druidDataSource() {
        return new DruidDataSource();
    }

    // 配置事物管理器
    @Bean(name = "transactionManager")
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(druidDataSource());
    }

    /**
     * WebStatFilter用于采集web-jdbc关联监控的数据。
     * 属性filterName声明过滤器的名称,(可选用)
     * 属性urlPatterns指定要过滤 的URL模式,也可使用属性value来声明.(指定要过滤的URL模式是必选属性)
     */
/*
    @WebFilter(filterName = "druidWebStatFilter", urlPatterns = "/*", initParams = {
            @WebInitParam(name = "exclusions", value = "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*")
    })
    public class DruidStatFilter extends WebStatFilter {

    }*/

    /**
     * StatViewServlet用于展示Druid的统计信息。
     * 提供监控信息展示的html页面
     * 提供监控信息的JSON API
     * 内置监控页面的首页是/druid/index.html
     *
     * @Webservlet * 有两个属性可以用来表示Servlet的访问路径，
     * 分别是value和urlPatterns。value和urlPatterns都是数组形式，
     * 表示我们可以把一个Servlet映射到多个访问路径，
     * 但是value和urlPatterns不能同时使用。
     */
/*
    @WebServlet(urlPatterns = "/druid/*", initParams = {
            // @WebInitParam(name = "allow", value = ""),// IP白名单 (没有配置或者为空，则允许所有访问)
            // @WebInitParam(name = "deny", value = "192.168.16.111"),// IP黑名单 (存在共同时，deny优先于allow)
            @WebInitParam(name = "loginUsername", value = "admin"),// 用户名
            @WebInitParam(name = "loginPassword", value = "twinkle"),// 密码
            @WebInitParam(name = "resetEnable", value = "true")// 禁用HTML页面上的“Reset All”功能
    })
    public class DruidStatViewServlet extends StatViewServlet {
        private static final long serialVersionUID = 1L;
    }*/


}

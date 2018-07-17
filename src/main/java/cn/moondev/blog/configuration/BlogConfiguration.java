package cn.moondev.spider.configuration;

import cn.moondev.framework.provider.mysql.MybatisConfigurationSupport;
import cn.moondev.framework.provider.web.WebConfigurationSupport;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BlogConfiguration {

    @Configuration
    public static class WebConfiguration extends WebConfigurationSupport {

    }

    /**
     * mybatis配置
     */
    @Configuration
    @MapperScan(basePackages = {"cn.moondev.blog.mapper"})
    public static class MybatisConfiguration extends MybatisConfigurationSupport {

        @Value("${moon.jdbc.url}")
        private String jdbcUrl;
        @Value("${moon.jdbc.user}")
        private String jdbcUser;
        @Value("${moon.jdbc.password}")
        private String jdbcPassword;


        @Override
        protected void initConfiguration() {
            super.dataSourceUri = jdbcUrl;
            super.user = jdbcUser;
            super.password = jdbcPassword;
            super.mapperLocations = new String[]{"classpath:mapper/**/*.xml"};
        }
    }
}

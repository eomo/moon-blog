package cn.moondev.blog.configuration;

import cn.moondev.framework.provider.mysql.MybatisConfigurationSupport;
import cn.moondev.framework.provider.okhttp3.OkHttpOperations;
import cn.moondev.framework.provider.web.ExceptionInterceptor;
import cn.moondev.framework.provider.web.WebConfigurationSupport;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import java.util.concurrent.TimeUnit;

@Configuration
public class BlogConfiguration {

    @Configuration
    public static class WebConfiguration extends WebConfigurationSupport {

        @Override
        protected void addInterceptors(InterceptorRegistry registry) {
            super.addInterceptors(registry);
            registry.addInterceptor(new ExceptionInterceptor());
        }
    }

    @Bean
    public OkHttpOperations okHttpOperations() {
        return new OkHttpOperations(120, 0);
    }

    @Bean
    public Cache<String,String> tokenCache() {
        return CacheBuilder.newBuilder().maximumSize(1).expireAfterAccess(30,TimeUnit.MINUTES).build();
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

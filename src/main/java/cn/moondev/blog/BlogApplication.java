package cn.moondev.spider;

import cn.moondev.framework.provider.spring.SpringBeanHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@EnableAutoConfiguration
@ComponentScan(value = "cn.moondev.blog",basePackageClasses = SpringBeanHelper.class)
public class BlogApplication implements CommandLineRunner {
    private static final Logger LOG = LoggerFactory.getLogger(BlogApplication.class);

    @Autowired
    private Environment env;

    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }

    public void run(String... strings) throws Exception {
        LOG.info("moon blog on, run at profile: application-{}.properties", env.getActiveProfiles());
    }
}

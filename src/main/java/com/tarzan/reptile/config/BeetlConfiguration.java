package com.tarzan.reptile.config;


import org.beetl.core.resource.WebAppResourceLoader;
import org.beetl.ext.spring.BeetlGroupUtilConfiguration;
import org.beetl.ext.spring.BeetlSpringViewResolver;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.util.ResourceUtils;

import java.io.IOException;


/**
 * beelt配置
 *
 * @author taizan LIU
 * @date 2020-07-24
 */
@Configuration
public class BeetlConfiguration {

    /**
     * 模板路径（默认：templates）
     */
    @Value("${beelt.templates.path}")
    private String beeltTemplatesPath;
    /**
     * 配置文件（默认：config/beetl.properties）
     */
    @Value("${beelt.properties.path}")
    private String beeltPropertiesPath;
    /**
     * 模板后缀（默认：.html）
     */
    @Value("${beelt.suffix}")
    private String beeltSuffix;


    /**
     * beetl配置器
     */
    @Bean(initMethod = "init", name = "beetlConfig")
    public BeetlGroupUtilConfiguration getBeetlGroupUtilConfiguration() {
        BeetlGroupUtilConfiguration beetlGroupUtilConfiguration = new BeetlGroupUtilConfiguration();
        ResourcePatternResolver patternResolver = ResourcePatternUtils.getResourcePatternResolver(new DefaultResourceLoader());
        try {
            //指定beetl的根目录
            String root = patternResolver.getResource(ResourceUtils.CLASSPATH_URL_PREFIX + beeltTemplatesPath).getFile().toString();
            WebAppResourceLoader webAppResourceLoader = new WebAppResourceLoader(root);
            beetlGroupUtilConfiguration.setResourceLoader(webAppResourceLoader);
            //加载beetl配置文件
            beetlGroupUtilConfiguration.setConfigFileResource(patternResolver.getResource(ResourceUtils.CLASSPATH_URL_PREFIX + beeltPropertiesPath));
            return beetlGroupUtilConfiguration;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * beetl视图解析器
     */
    @Bean(name = "beetlViewResolver")
    @Qualifier("beetlConfiguration")
    public BeetlSpringViewResolver getBeetlSpringViewResolver(BeetlGroupUtilConfiguration beetlGroupUtilConfiguration) {
        BeetlSpringViewResolver beetlSpringViewResolver = new BeetlSpringViewResolver();
        beetlSpringViewResolver.setContentType("text/html;charset=UTF-8");
        beetlSpringViewResolver.setSuffix(beeltSuffix);
        beetlSpringViewResolver.setConfig(beetlGroupUtilConfiguration);
        return beetlSpringViewResolver;
    }

}

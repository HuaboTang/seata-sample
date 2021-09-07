package com.vbobot.sample.seata.at.storage.config;

import static io.seata.spring.annotation.datasource.AutoDataSourceProxyRegistrar.BEAN_NAME_SEATA_AUTO_DATA_SOURCE_PROXY_CREATOR;
import static io.seata.spring.annotation.datasource.AutoDataSourceProxyRegistrar.BEAN_NAME_SEATA_DATA_SOURCE_BEAN_POST_PROCESSOR;

import io.seata.spring.annotation.datasource.SeataAutoDataSourceProxyCreator;
import io.seata.spring.annotation.datasource.SeataDataSourceBeanPostProcessor;
import io.seata.spring.boot.autoconfigure.properties.SeataProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Bobo
 * @date 2021/7/28
 */
@Configuration
@ConditionalOnExpression("${seata.enable:true} && ${seata.enableAutoDataSourceProxy:true} && ${seata.enable-auto-data-source-proxy:true}")
public class SeataDataSourceProxyConfiguration {
    @Bean(BEAN_NAME_SEATA_DATA_SOURCE_BEAN_POST_PROCESSOR)
    @ConditionalOnMissingBean(SeataDataSourceBeanPostProcessor.class)
    public SeataDataSourceBeanPostProcessor seataDataSourceBeanPostProcessor(
            SeataProperties seataProperties) {
        return new SeataDataSourceBeanPostProcessor(seataProperties.getExcludesForAutoProxying(), seataProperties.getDataSourceProxyMode());
    }

    @Bean(BEAN_NAME_SEATA_AUTO_DATA_SOURCE_PROXY_CREATOR)
    @ConditionalOnMissingBean(SeataAutoDataSourceProxyCreator.class)
    public SeataAutoDataSourceProxyCreator seataAutoDataSourceProxyCreator(SeataProperties seataProperties) {
        return new SeataAutoDataSourceProxyCreator(seataProperties.isUseJdkProxy(),
                seataProperties.getExcludesForAutoProxying(), seataProperties.getDataSourceProxyMode());
    }
}

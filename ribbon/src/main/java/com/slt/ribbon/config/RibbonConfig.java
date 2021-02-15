package com.slt.ribbon.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @ProjectName: springcloud
 */
@Configuration
public class RibbonConfig {
    /**
     * @Description: 通过 @LoadBalanced 开启 restTemplate 负载均衡功能。
     * @Date: 2021-02-10
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}

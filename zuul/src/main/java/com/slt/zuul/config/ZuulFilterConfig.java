package com.slt.zuul.config;

import com.slt.zuul.filter.TokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: 将TokenFilter加入到请求拦截队列
 * @Version: 1.0
 */
@Configuration
public class ZuulFilterConfig {
//    @Bean
//    public TokenFilter tokenFilter() {
//        return new TokenFilter();
//    }

}

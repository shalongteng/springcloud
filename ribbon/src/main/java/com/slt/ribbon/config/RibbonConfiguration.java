package com.slt.ribbon.config;

import com.netflix.loadbalancer.IRule;
import com.slt.ribbon.rule.MsbRandomRule;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: 第二种 自定义ribbon负载策略
 * @Param:
 * @return:
 * @Author: shalongteng
 * @Date: 2021-02-10
 */
@RibbonClient(name = "spring-cloud-consumer-ribbon",configuration = RibbonConfiguration.class)
@Configuration
//@ExcudeRibbonConfig
public class RibbonConfiguration {

	
	/**
	 * 修改IRule
	 * @return
	 */
//	@Bean
//	public IRule ribbonRule() {
//		return new RandomRule();
//	}
	
	/**
	 * 自定义rule
	 * @return
	 */
	@Bean
	public IRule ribbonRule() {
		return new MsbRandomRule();
	}
	
}
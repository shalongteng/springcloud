//package com.slt.configclient.config;
//
//import org.springframework.boot.actuate.autoconfigure.security.reactive.EndpointRequest;
//
//@EnableWebSecurity
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//
//		http
//		.authorizeRequests()
//		.requestMatchers(EndpointRequest.toAnyEndpoint()).authenticated()
//		.and()
//		.csrf().ignoringAntMatchers("/encrypt", "/decrypt", "/actuator/bus-refresh");
//
//		super.configure(http);
//
//	}
//
//}
package com.slt.configclient.config;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
 
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
		.authorizeRequests()
		.requestMatchers(EndpointRequest.toAnyEndpoint()).authenticated()
		.and()
		.csrf().ignoringAntMatchers("/encrypt", "/decrypt", "/actuator/bus-refresh");
		
		super.configure(http);
		
	}
	
}
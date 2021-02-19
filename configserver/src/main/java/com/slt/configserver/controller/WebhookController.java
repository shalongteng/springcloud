package com.slt.configserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
@RequestMapping("/git-webhook")
public class WebhookController {
	
	@Autowired
	private RestTemplate restTemplate;
	/** 
	 * @Description: 因为GitHub的回调 body参数无法解析，所以当GitHub回调过来时候，
	 * 自己手动 调用server刷新一下
	 * @Author: shalongteng
	 * @Date: 2021-02-19 
	 */ 
	@PostMapping("/bus-refresh")
	public String refresh() {
		
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE,"application/json");
        HttpEntity<String> request = new HttpEntity<>(httpHeaders);
        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity("http://localhost:9300/actuator/refresh",
                request, String.class);

		
		return "webhook刷新成功";
	}
}

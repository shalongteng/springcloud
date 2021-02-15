package com.slt.ribbon.service;


import com.netflix.ribbon.proxy.annotation.Hystrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CallService {

    @Autowired
    private RestTemplate restTemplate;

//    @Hystrix(fallbackHandler = CallServiceFallBack.class)
    public String sayHello(String name){
        String url = "http://spring-cloud-provider/hello/"+name;
        return restTemplate.getForObject(url, String.class);
    }

//    /*熔断相关，后续会进行讲解*/
//    public String helloFallback() {
//        return "error";
//    }

    class CallServiceFallBack{

    }
}
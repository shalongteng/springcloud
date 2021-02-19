package com.slt.provider.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
@RestController
public class HelloController {
    @Value("${server.port}")
    private String port;
	@GetMapping("/hello/{name}")
    public String index(@PathVariable("name") String name) {
        System.out.println(System.currentTimeMillis()+name);
//        try{
//            Thread.sleep(1000);
//        }catch ( Exception e){
//            System.out.println("hello two error");
//        }
        return "hello "+name+"，this is "   + port +  ": messge";
    }

    @RequestMapping("/sayHello/{name}")
    public String sayHello(@PathVariable("name") String name) {
        return "hello "+name+"，this is second messge";
    }
}

package com.slt.provider.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {
	
    @GetMapping("/hello/{name}")
    public String index(@PathVariable("name") String name) {
        return "hello "+name+"，this is first messge";
    }

    @RequestMapping("/sayHello/{name}")
    public String sayHello(@PathVariable("name") String name) {
        return "hello "+name+"，this is second messge";
    }
}

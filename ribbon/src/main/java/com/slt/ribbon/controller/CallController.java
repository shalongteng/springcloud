package com.slt.ribbon.controller;

import com.slt.ribbon.service.CallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CallController {

    @Autowired
    CallService callService;

    @RequestMapping("hello/{name}")
    public String hello(@PathVariable("name") String name) {
        return callService.sayHello(name);
    }
}
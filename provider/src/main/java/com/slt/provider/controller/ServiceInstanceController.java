package com.slt.provider.controller;

import com.oracle.tools.packager.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: springcloud
 */
@RestController
public class ServiceInstanceController {
    @Autowired
    private DiscoveryClient discoveryClient;

    /**
     * @Description: 获取某个服务的 元数据 可以做灰度发布
     * applicationName 服务名     SPRING-CLOUD-CONSUMER
     */
    @GetMapping("/query-by-application-name/{applicationName}")
    public List<ServiceInstance> getInstance(@PathVariable String applicationName) {
        List<ServiceInstance> instances = discoveryClient.getInstances(applicationName);
        for (ServiceInstance serviceInstance : instances) {
            Map<String, String> metadata = serviceInstance.getMetadata();
            Log.info("元数据"+metadata.get("yueyi"));
        }
        return instances;
    }
}

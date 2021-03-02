package com.slt.consumer.remote;

import com.slt.consumer.config.FeignAuthConfiguration;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

//@FeignClient(name = "spring-cloud-provider",configuration = FeignAuthConfiguration.class,fallbackFactory = HelloRemote.FeignClientFallbackFactory.class)
@FeignClient(name = "spring-cloud-provider",fallbackFactory = HelloRemote.FeignClientFallbackFactory.class)
public interface HelloRemote {
    @RequestMapping(value = "/hello/{name}")
    public String hello(@PathVariable("name") String name);

    /**
     * @Description: 熔断回调类
     * @Author: shalongteng
     * @Date: 2021-02-14
     */
    @Component
    @Slf4j
    class FeignClientFallbackFactory implements FallbackFactory<HelloRemote> {
        @Override
        public HelloRemote create(Throwable throwable) {
            return new HelloRemote() {
                @Override
                public String hello(String name) {
                    log.error("远程查询数据失败,fallback reason:",throwable);
                    return "hello" +name+", this messge send failed ";
                }
            };
        }
    }
}

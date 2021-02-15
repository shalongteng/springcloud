package com.slt.eureka.event;

import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceCanceledEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @Version: 1.0
 */
@Component
public class CustomEvent {
    /**
     * @Description: 服务下线 监听事件
     *  可以发送邮件 通知管理员
     * @Author: shalongteng
     * @Date: 2021-02-08
     */
    @EventListener
    public void listen(EurekaInstanceCanceledEvent eurekaInstanceCanceledEvent){
        System.out.println(eurekaInstanceCanceledEvent.getServerId() + "下线");
    }

}

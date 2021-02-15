#1、provider的搭建
    引入依赖：
        spring-cloud-starter-netflix-eureka-client
    配置文件：
        eureka.client.serviceUrl.defaultZone=http://localhost:8000/eureka/
    启动类中添加@EnableDiscoveryClient注解
        添加@EnableDiscoveryClient注解后，项目就具有了服务注册的功能
#2、consumer的搭建
    引入依赖：
        spring-cloud-starter-netflix-eureka-client
        pring-cloud-starter-openfeign
    配置文件：
        eureka.client.serviceUrl.defaultZone=http://localhost:8000/eureka/
        
    启动类中添加@EnableDiscoveryClient，@EnableFeignClients
        @EnableDiscoveryClient注解后，项目就具有了服务注册的功能
        @EnableFeignClients：启用feign进行远程调用
            Feign是一个声明式Web Service客户端。
            
#3、provider的负载均衡
    服务中心自动提供了服务均衡负载的功能。请求会自动轮询到每个服务端来处理。

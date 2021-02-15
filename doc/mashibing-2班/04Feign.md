#1、概念
    feign是声明式、模板化的HTTP请求客户端。可以更加便捷、优雅地调用http api，
    feign在底层集成了ribbon，所以有了负载均衡的能力。
    Spring Cloud 给 Feign 添加了支持Spring MVC注解

    feign是一个 Http 请求调用的轻量级框架，以 Java 接口注解的方式调用 Http 请求，
    而不用像 Java 中通过封装 HTTP 请求报文的方式直接调用。通过处理注解，将请求模板化，
    当实际调用的时候，传入参数，根据参数再应用到请求上，进而转化成真正的请求，这种请求比较直观。
    Feign 封装 了HTTP 调用流程，面向接口编程。

#2、feign使用
    1、添加依赖
        spring-cloud-starter-openfeign
    2、启动类添加 @EnableFeignClients 注解
    3、建立Client接口，并在接口中定义需调用的服务方法
    4、调用Client接口。
    
    fiegn单独使用：
        测试feign作为一个http客户端使用
            @FeignClient(name = "service-valuation-without-eureka",url = "http://localhost:8060",configuration = FeignAuthConfiguration.class)
            public interface ServiceForecastWithoutEureka {
                @RequestMapping(value = "/forecast/single",method = RequestMethod.POST)
                public ResponseResult<ForecastResponse> forecast(@RequestBody ForecastRequest forecastRequest);
                
            }
#3、自定义feign配置
    feign的默认配置类是：org.springframework.cloud.openfeign.FeignClientsConfiguration。
    默认定义了feign使用的编码器，解码器等。
    
    允许使用@FeignClient的configuration的属性自定义Feign配置。自定义的配置优先级高于
    FeignClientsConfiguration。
    
    1、给服务提供者添加权限配置拦截，WebSecurityConfig
    2、自定义配置类，FeignAuthConfiguration
    3、在feign上加配置
        @FeignClient(name = "service-valuation",configuration = FeignAuthConfiguration.class)
    
#4、feign继承
    feign接口 可以继承 公用的feign接口.
    
    @FeignClient(name = "service-valuation")
    public interface ServiceForecast extends CommonServiceForecast {
    
    }
#5、feign压缩
    开启压缩可以有效节约网络资源，但是会增加CPU压力，
    建议把最小压缩的文档大小适度调大一点，进行gzip压缩。

#6、feign日志
```sh
feign:
  client: 
    config:  
      service-valuation: 
        logger-level: basic
        
//4种日志类型
none:不记录任何日志，默认值
basic:仅记录请求方法，url，响应状态码，执行时间。
headers：在basic基础上，记录header信息
full：记录请求和响应的header，body，元数据。
        
        
//上面的logger-level只对下面的 debug级别日志做出响应。
logging:
  level:
    com.online.taxi.passenger.feign.ServiceForecast: debug
```    
    

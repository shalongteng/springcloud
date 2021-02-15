#1、负载均衡
    负载均衡：
        软件：nginx，lvs
        硬件：F5
    软件负载均衡：
        第一层：一个域名可以对应多个ip地址。配置dns，让dns做第一层的分发
        第二层：反向代理
            根据一定规则，将http请求转发到集群中某一台机器上。
            
    第二层软件负载均衡：
        服务端负载均衡
            请求到服务端，在服务端保存所有可用服务列表，由服务端进行转发，Nginx
        客户端负载均衡    
            ribbon从server拉取可用服务列表，客户端根据自己情况选择一个进行转发，
            ribbon在这里是客户端。eureka是服务端。    
              

#2、概念
    ribbon 基于http的客户端 负载均衡器。
    
    Ribbon是springcloud 负载机制的实现。
    
    ribbon也可以单独使用，需要手动配置服务列表。 
    
    ribbon与openFeign和restTemplate无缝对接，让二者具有负载均衡能力，feign默认
    集成了ribbon。
#3、服务调用
    不使用微服务，使用http调用缺点：
        硬编码 url写死
        
    自己写服务调用步骤：
        根据服务名，找到对应的ip，这样ip切换不影响服务调用。Map<服务名，服务列表> map；
        加上负载均衡。
        
    springcloud提供服务调用方式：
        ribbon + RestTemplate
        feign
#4、ribbon使用
    导入依赖
        spring-cloud-starter-netflix-ribbo
        
    通过 @LoadBalanced 开启 restTemplate 负载均衡功能。
        @Bean
        @LoadBalanced
        public RestTemplate restTemplate(){
            return new RestTemplate();
        }
    使用restTemplate，发起请求
        restTemplate.getForObject(url, String.class)
        
        注意：
           restTemplate加上@LoadBalanced注解以后，url需要使用服务名替换ip和端口 
#5、ribbon组成
    ribbon-core 核心通用代码，api一下配置。
    ribbon-eureka 基于eureka，快速集成eureka
    ribbon-httpclient 基于httpclient封装的rest客户端
    ribbon-loadbalancer 负载均衡模块
    ribbon-transport 基于netty实现多协议支持
    ribbon-examples 例子
#6、Ribbon负载均衡策略： 
    轮询策略  默认
    随机策略
    可用过滤策略
        过滤掉连接失败的服务节点，过滤掉高并发的服务节点，
        从健康的服务节点中，使用轮询策略选出一个节点返回
    响应时间加权策略
        根据响应时间，分配一个权重weight，
        响应时间越长，weight越小，被选中的可能性越低
    最低并发策略
    轮询失败重试策略
#7、自定义ribbon 的负载均衡策略
    第一种定义方法：
        1. 自定义Rule。
        2. yml
            #正常ribbon
            service-sms: 
              ribbon: 
                # 自定义负载策略
                NFLoadBalancerRuleClassName: com.online.taxi.driver.ribbonconfig.MsbRandomRule
    
    
    第二种定义方法：
        1. 自定义Rule。
        2. 编写RibbonConfiguration配置类
        
    自定义的负载均衡配置类不能放在 @componentScan 所扫描的当前包下及其子包下，
    否则我们自定义的这个配置类就会被所有的Ribbon客户端所共享，
    
#1、什么是Eureka
    eureka来源于古希腊词汇，意为“发现了”
    
    它是服务注册中心。
    
    Eureka由两个组件组成
        Eureka服务器
        Eureka客户端
        
    Eureka架构：    
        Eureka 采用了 C-S 的设计架构。Eureka Server 作为服务注册功能的服务器，系统中的其他微服务
        作为 Eureka 的客户端连接到 Eureka Server，并维持心跳连接。
        
        Spring Cloud 的一些其他模块（比如Zuul）就可以通过 Eureka Server 来发现系统中的其他服务，
        并执行相关的逻辑。
#2、Eureka Server搭建
    1、pom中添加依赖
        spring-cloud-starter
        spring-cloud-starter-netflix-eureka-server
    2、添加启动代码中添加@EnableEurekaServer注解
    3、配置文件
        
#3、Eureka 集群
    Eureka通过互相注册的方式来实现高可用的部署，所以我们只需要将Eureke Server
    配置其他可用的serviceUrl就能实现高可用部署
    
    yml中，通过 --- 分隔多个不同配置，根据spring.profiles.active,的值来决定启用哪个配置，
        java -jar eureka-0.0.1-SNAPSHOT.jar --spring.profiles.active=peer1
    
    注意：
        profiles 不能写错 写错了会按照默认配置来，就是不加载配置文件，一切按照默认来。
        端口是默认的8080，serviceUrl是默认的http://localhost:8761/eureka
        
    集群同步原理：
        当peer1 启动会去 peer2 拉取注册表。
        将自己注册到peer2
#4、手写注册服务
    即使不是springboot服务，只要符合规范，就能向eureka来注册。
    http://localhost:8000/
    发一个post请求即可。
    
#5、eureka原理
    eureka 就是个web服务，使用jersey实现，是个mvc框架。
    
    手写注册中心：
        客户端：
            拉去注册表
            从注册表选取一个，进行调用。
        服务端：
            定义注册表 Map<name,Map<id,instanceInfo>>
            client 可以向服务端注册
            client 可以拉去其他client注册信息
            可以和服务端 共享注册表
    
    client端启动流程：
        读取client端交互信息，封装成EurekaClientConfig
        读取自身配置信息，封装成EurekaInstanceConfig
        从服务端拉去注册信息，缓存到本地
        服务注册
        初始化三个定时器
            发送心跳
            缓存刷新
            按需注册
#6、eureka元数据
    可以获取每个节点的元数据（基本信息）               
#7、eureka健康检查
    server和client端心跳保持连接，如果client端mysql挂了，他在server端依然显示client的status是UP
    可用状态，这是有问题的。
    可开启健康检查，将client端健康状态也同步到server端
    
#8、eureka服务端自我保护
    当server端短时间丢失大量客户端时，出发自我保护模式，注册表中的服务不会被注销。
#9、eureka缺陷
    server端是用http方式进行同步，（他是定时拉取）不满足cap中的c（数据一致性）
#10、eureka监听事件
    EurekaInstanceCanceledEvent 服务下线事件
    EurekaServerStartedEvent  注册中心启动
    EurekaInstanceRegisteredEvent 服务注册事件
    EurekaInstanceRenewedEvent 服务续约事件
    EurekaRegistryAvailableEvent 注册中心可用事件

#11、CAP理论
    CAP理论指的是一个分布式系统最多只能同时满足
        一致性（Consistency）、
        可用性（Availability）
        分区容错性（Partition tolerance）这三项中的两项。
        
    CAP原则的精髓就是要么AP，要么CP，要么AC，但是不存在CAP。如果在某个分布式系统中数据无副本，
    那么系统必然满足强一致性条件， 因为只有独一数据，不会出现数据不一致的情况，此时C和P两要素具备，
    但是如果系统发生了网络分区状况或者宕机，必然导致某些数据不可以访问，此时可用性条件就不能被满足，
    即在此情况下获得了CP系统，但是CAP不可同时满足 [2]  。
     
    因此在进行分布式架构设计时，必须做出取舍。当前一般是通过分布式缓存中各节点的最终一致性来提高系统的性能，
    通过使用多节点之间的数据异步复制技术来实现集群化的数据一致性。通常使用类似 memcached 之类的 NOSQL 作为实现手段。
    虽然 memcached 也可以是分布式集群环境的，但是对于一份数据来说，它总是存储在某一台 memcached 服务器上。
    如果发生网络故障或是服务器死机，则存储在这台服务器上的所有数据都将不可访问。由于数据是存储在内存中的，重启服务器，
    将导致数据全部丢失。当然也可以自己实现一套机制，用来在分布式 memcached 之间进行数据的同步和持久化
    ，但是实现难度是非常大的 [3]  。
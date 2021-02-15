#1、熔断降级
    为什么要用熔断：
        1、每个http请求，都会在服务中开启一个新的线程，下游服务挂了，线程会阻塞住，直到timeout，
        如果并发多一些这些，这些阻塞的线程会占用大量的资源，有可能会把服务本身资源耗尽，服务调用者也会挂掉。
        
        2、防止应用程序不断地尝试执行可能会失败的操作。
    熔断目的：
        当依赖的服务不可用时候，服务自身（调用方）不会被拖垮，防止服务级联异常。
    熔断本质：   
        隔离坏的服务不让坏的服务拖垮其他的服务。
    
    对每个请求设置单独的连接池，配置连接数，不要影响别的请求。
    
    雪崩效应：
        由基础服务导致的级联故障。
        
        服务提供者不可用
        重试会导致网络流量加大，更影响服务提供者。
        导致服务调用者不可用，由于服务调用者 一直等待返回，一直占用系统资源。
        （不可用的范围 被逐步放大）
    服务不可用原因：
        服务器宕机
        网络故障
        程序异常
        负载过大，导致服务提供者响应慢
        缓存击穿导致服务超负荷运行
#2、舱壁模式
    隔离每个服务的关键资源入 cpu，内存，硬盘。每个服务都有独立的cpu，内存，硬盘。
    优点：
        避免了单个服务消耗掉所有资源，从而导致其他服务出现故障的场景。
        
    Hystrix资源隔离：
        在Hystrix中, 主要通过线程池来实现资源隔离. 通常在使用的时候我们会根据调用的远程服务划分出多个线程池.
        例如调用产品服务的Command放入A线程池, 调用账户服务的Command放入B线程池. 这样做的主要优点是运行环境
        被隔离开了. 这样就算调用服务的代码存在bug或者由于其他原因导致自己所在线程池被耗尽时, 不会对系统的其他
        服务造成影响. 但是带来的代价就是维护多个线程池会对系统带来额外的性能开销. 
#3、容错机制
    设置超时时间
        一般的在几十毫秒
    使用断路器模式
        
#4、断路器
    打开状态：
        在一段时间内，失败次数打到一定的阈值，断路器就会打开。
        断路器打开以后，后续请求，则直接降级（快速失败），走备用逻辑。
    半开状态：
        断路器打开一段时间后，会自动进入“半开模式”，此时，断路器允许一个服务请求访问依赖的服务。
        如果此请求成功(或者成功达到一定比例)，则关闭断路器，恢复正常访问。否则，继续保持打开状态。
    关闭状态：
        正常情况下，断路器关闭，可以正常请求依赖的服务。
#5、服务降级
    为了在整体资源不够的时候，主动放弃部分服务，将主要的资源投放到核心服务中，待渡过难关之后
    再重启已关闭的服务，保证了系统核心服务的稳定。当服务停掉后，自动进入fallback替换主方法。

    熔断和降级：
        共同点：
            1、为了防止系统崩溃，保证主要功能的可用性和可靠性。
            2、用户体验到某些功能不能用。
        不同点：
            1、熔断由下级故障触发，主动惹祸。
            2、降级由调用方从负荷角度触发，无辜被抛弃。        
    举例：
        19年春晚 百度 红包，凤巢的5万台机器熄火4小时，让给了红包。
#6、hystrix概念
    是springcloud的容错组件，实现了超时机制和断路器模式
    
    主要功能：
        2. 防止雪崩。
        3. 包裹请求：使用HystrixCommand 包裹对依赖的调用逻辑，每个命令在独立线程中运行。
        4. 跳闸机制：当某服务失败率达到一定的阈值时，Hystrix可以自动跳闸，停止请求该服务一段时间。
        5. 资源隔离：Hystrix为每个请求都的依赖都维护了一个小型线程池，如果该线程池已满，发往该依赖的请求就被立即拒绝，从而加速失败判定。防止级联失败。
        6. 快速失败：Fail Fast。同时能快速恢复。侧重点是：（不去真正的请求服务，发生异常再返回），而是直接失败。
        7. 监控：Hystrix可以实时监控运行指标和配置的变化，提供近实时的监控、报警、运维控制。
        8. 回退机制：fallback，当请求失败、超时、被拒绝，或当断路器被打开时，执行回退逻辑。回退逻辑我们自定义，提供优雅的服务降级。
        9. 自我修复：断路器打开一段时间后，会自动进入“半开”状态，可以进行打开，关闭，半开状态的转换。前面有介绍。
#7、hystrix使用 
    单独使用
    和resttmeplate结合使用
        1、添加依赖
            spring-cloud-starter-netflix-hystrix
        2、启动类添加注解：@EnableCircuitBreaker
        1、调用的方法上，通过使用@HystrixCommand，将方法纳入到hystrix监控中。
    和feign结合
        1、feign.hystrix.enabled=true
        2、创建回调类 FeignClientFallbackFactory
        3、fallbackFactory = HelloRemote.FeignClientFallbackFactory.class


#8、hystrix监控
    Hystrix-dashboard
        是一款针对Hystrix进行实时监控的工具，通过Hystrix Dashboard我们可以
        看到各Hystrix Command的请求响应时间, 请求成功率等数据。
        只能看到单个应用内的服务信息. 
    Turbine
        可以汇总系统内多个服务的数据并显示到Hystrix Dashboard上
    
    使用流程：
        单个应用：
            添加依赖：
                spring-cloud-starter-hystrix-dashboard
                spring-boot-starter-actuator
            启动类添加注解：
                @EnableHystrixDashboard
                @EnableCircuitBreaker
                
            在输入框中输入： http://localhost:9001/actuator/hystrix.stream ，
            输入之后点击 monitor，进入页面
        集群使用：
            添加依赖：
                spring-cloud-starter-turbine
            配置文件：
                spring.application.name=hystrix-dashboard-turbine
                server.port=8001
                turbine.appConfig=node01,node02
                turbine.aggregator.clusterConfig= default
                turbine.clusterNameExpression= new String("default")
                eureka.client.serviceUrl.defaultZone=http://localhost:8000/eureka/

            启动类：
                @EnableTurbine
#1、hystrix可视化
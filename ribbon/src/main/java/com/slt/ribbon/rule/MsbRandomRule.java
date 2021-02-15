package com.slt.ribbon.rule;

import java.util.List;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
/**
 * @Description: 自定义ribbon负载均衡
 * @Author: shalongteng
 * @Date: 2021-02-10
 */
public class MsbRandomRule extends AbstractLoadBalancerRule{
 
	public Server choose(ILoadBalancer loadBalancer, Object key) {
		if (loadBalancer == null) {
			return null;
		}
		Server server = null;
 
		while (server == null) {
			if (Thread.interrupted()) {
				return null;
			}
			//激活可用的服务
			List<Server> upList = loadBalancer.getReachableServers();
			//所有的服务
			List<Server> allList = loadBalancer.getAllServers();
 
			int serverCount = allList.size();
			if (serverCount == 0) {
				return null;
			}
			//选自定义元数据的server，选择端口以2结尾的服务。
			for (int i = 0; i < upList.size(); i++) {
				server = upList.get(i);
				String port = server.getHostPort();
				if(port.endsWith("2") || port.endsWith("0")) {
					break;
				}
				
			}
			
                       						
			if (server == null) {
				Thread.yield();
				continue;
			}
 
			if (server.isAlive()) {
				return (server);
			}
 
			// Shouldn't actually happen.. but must be transient or a bug.
			server = null;
			Thread.yield();
		}
		return server;
	}
	@Override
	public Server choose(Object key){
		return choose(getLoadBalancer(), key);
	}
 
	@Override
	public void initWithNiwsConfig(IClientConfig clientConfig){
	}
}
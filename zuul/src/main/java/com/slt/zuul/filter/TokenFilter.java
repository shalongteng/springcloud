package com.slt.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 请求中含有Token便让请求继续往下走，如果请求不带Token就直接返回并给出提示。
 *
 * @Description: 定义filter的类型，有pre、route、post、error四种
 * @Author: shalongteng
 * @Date: 2021-02-17
 */
public class MyFilter extends ZuulFilter{
    /**
     * 定义filter的类型，有pre、route、post、error四种
     * 可以在请求被路由之前调用
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }
    /**
     * 定义filter的顺序，数字越小表示顺序越高，越先执行
     * @return
     */
    @Override
    public int filterOrder() {
        return 10;
    }
    /**
     * 表示是否需要执行该filter，true表示执行，false表示不执行
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }
    /**
     * filter需要执行的具体操作
     * 请求中含有Token便让请求继续往下走，如果请求不带Token就直接返回并给出提示。
     * @return
     */
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String token = request.getParameter("token");// 获取请求的参数

        if (StringUtils.isNotBlank(token)) {
            ctx.setSendZuulResponse(true); //对请求进行路由
            ctx.setResponseStatusCode(200);
            ctx.set("isSuccess", true);
            return null;
        } else {
            ctx.setSendZuulResponse(false); //不对其进行路由
            ctx.setResponseStatusCode(400);
            ctx.setResponseBody("token is empty");
            ctx.set("isSuccess", false);
            return null;
        }
    }
}

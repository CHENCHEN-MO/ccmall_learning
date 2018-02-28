package com.ccmall.controller.common;

import com.ccmall.common.Const;
import com.ccmall.pojo.User;
import com.ccmall.util.CookieUtil;
import com.ccmall.util.JsonUtil;
import com.ccmall.util.RedisSharderPoolUtil;
import org.apache.commons.lang.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by Administrator on 2018/2/27.
 */
public class SessionExpireFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;

        String loginToken = CookieUtil.readLoginToken(httpServletRequest);

        if (StringUtils.isNotEmpty(loginToken)){
            String userJsonStr = RedisSharderPoolUtil.get(loginToken);
            User user = JsonUtil.string2Obj(userJsonStr, User.class);
            if (user!=null){
                //如果user不为空，则重置session的时间，即调用expire命令
                RedisSharderPoolUtil.expire(loginToken, Const.RedisCacheExtime.REDIS_SESSION_EXTIME);
            }
        }
        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}

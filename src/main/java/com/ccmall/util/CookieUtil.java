package com.ccmall.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class CookieUtil {

    //此时aa.ccmall.com以及bb.ccmall.com域名下的服务均可以访问.ccmall.com域名下的COOKIE
    private final static String COOKIE_DOMAIN = ".ccmall.com";

    private final static String COOKIE_NAME = "ccmall_login_token";

    public static String readLoginToken(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if(cookies!=null){
            for (Cookie cookie : cookies) {
                log.info("read cookieName:{},cookieValue:{}",cookie.getName(),cookie.getValue());
                if (StringUtils.equals(cookie.getName(),COOKIE_NAME)){
                    log.info("return cookieName:{},cookieValue:{}",cookie.getName(),cookie.getValue());
                    return cookie.getValue();
                }
            }

        }
        return null;
    }


    public static void writeLoginToken(HttpServletResponse httpServletResponse,String token){
        Cookie ck = new Cookie(COOKIE_NAME,token);
        ck.setDomain(COOKIE_DOMAIN);
        ck.setPath("/");//代表设置在根目录下
        //设置成true表示该Cookie信息不可以通过脚本读取同时也不可以发送给第三方站点，保证了一定的安全性
        ck.setHttpOnly(true);
        //单位是秒，表示cookie的有效期，如果MaxAge不设置的话，cookie就不会写入硬盘，而是写入内存。只在当前页面有效。
        ck.setMaxAge(60 * 60 * 24 * 365);//如果是-1，代表永久
        log.info("write cookieName:{},cookieValue:{}",ck.getName(),ck.getValue());
        httpServletResponse.addCookie(ck);

    }

    public static void delLoginToken(HttpServletRequest request,HttpServletResponse httpServletResponse){
        Cookie[] cookies = request.getCookies();
        if(cookies!=null){
            for (Cookie cookie : cookies) {
                if (StringUtils.equals(cookie.getName(),COOKIE_NAME)){
                    cookie.setDomain(COOKIE_DOMAIN);
                    cookie.setPath("/");
                    cookie.setMaxAge(0);//设置成0，表示删除此cookie
                    log.info("del cookieName:{},cookieValue:{}",cookie.getName(),cookie.getValue());
                    httpServletResponse.addCookie(cookie);
                    return;
                }
            }
        }
    }

}

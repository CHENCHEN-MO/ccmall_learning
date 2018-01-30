package com.ccmall.util;

import com.ccmall.common.RedisPool;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

@Slf4j
public class RedisPoolUtil {

    public static String set(String key,String value){
        Jedis jedis = null;
        String result = null;
        try{
            jedis = RedisPool.getJedis();
            result =jedis.set(key,value);
        }catch (Exception e){
            log.error("set:{},value:{} error",key,value,e);
            RedisPool.returnBrokenResource(jedis);
            return result;
        }
        RedisPool.returnResource(jedis);
        return result;

    }

    //存储的时候设置过期时间，且过期时间的单位为：秒
    public static String setEx(String key,String value,int exTime){
        Jedis jedis = null;
        String result = null;
        try{
            jedis = RedisPool.getJedis();
            result = jedis.setex(key,exTime,value);
        }catch (Exception e){
            log.error("setex key:{},value:{} error",key,value,e);
            RedisPool.returnBrokenResource(jedis);
            return result;
        }
        RedisPool.returnResource(jedis);
        return result;

    }


    //对于一个已经存在的key－value，重新设置过期时间，且过期时间的单位为：秒
    public static Long expire(String key,int exTime){
        Jedis jedis = null;
        Long result = null;
        try{
            jedis = RedisPool.getJedis();
            result = jedis.expire(key,exTime);
        }catch (Exception e){
            log.error("expire: key {} error",key,e);
            RedisPool.returnBrokenResource(jedis);
            return result;
        }
        RedisPool.returnResource(jedis);
        return result;

    }

    public static String get(String key){
        Jedis jedis = null;
        String result = null;
        try{
            jedis = RedisPool.getJedis();
            result = jedis.get(key);
        }catch (Exception e){
            log.error("get key:{} error",key,e);
            RedisPool.returnBrokenResource(jedis);
            return result;
        }
        RedisPool.returnResource(jedis);
        return result;

    }


    public static Long del(String key){
        Jedis jedis = null;
        Long result = null;
        try{
            jedis = RedisPool.getJedis();
            result = jedis.del(key);
        }catch (Exception e){
            log.error("get key:{} error",key,e);
            RedisPool.returnBrokenResource(jedis);
            return result;
        }
        RedisPool.returnResource(jedis);
        return result;

    }

    public static void main(String[] args) {
        Jedis jedis = RedisPool.getJedis();

        RedisPoolUtil.set("name","chenchen");

        String res = RedisPoolUtil.get("name");

        RedisPoolUtil.setEx("year","19",20);

        RedisPoolUtil.expire("year",160);


    }


}
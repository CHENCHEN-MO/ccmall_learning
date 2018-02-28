package com.ccmall.util;

import com.ccmall.common.RedisShardedPool;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.ShardedJedis;

@Slf4j
public class RedisSharderPoolUtil {

    public static String set(String key,String value){
        ShardedJedis jedis = null;
        String result = null;
        try{
            jedis = RedisShardedPool.getJedis();
            result =jedis.set(key,value);
        }catch (Exception e){
            log.error("set:{},value:{} error",key,value,e);
            RedisShardedPool.returnBrokenResource(jedis);
            return result;
        }
        RedisShardedPool.returnResource(jedis);
        return result;

    }

    //存储的时候设置过期时间，且过期时间的单位为：秒
    public static String setEx(String key,String value,int exTime){
        ShardedJedis jedis = null;
        String result = null;
        try{
            jedis = RedisShardedPool.getJedis();
            result = jedis.setex(key,exTime,value);
        }catch (Exception e){
            log.error("setex key:{},value:{} error",key,value,e);
            RedisShardedPool.returnBrokenResource(jedis);
            return result;
        }
        RedisShardedPool.returnResource(jedis);
        return result;

    }


    //对于一个已经存在的key－value，重新设置过期时间，且过期时间的单位为：秒
    public static Long expire(String key,int exTime){
        ShardedJedis jedis = null;
        Long result = null;
        try{
            jedis = RedisShardedPool.getJedis();
            result = jedis.expire(key,exTime);
        }catch (Exception e){
            log.error("expire: key {} error",key,e);
            RedisShardedPool.returnBrokenResource(jedis);
            return result;
        }
        RedisShardedPool.returnResource(jedis);
        return result;

    }

    public static String get(String key){
        ShardedJedis jedis = null;
        String result = null;
        try{
            jedis = RedisShardedPool.getJedis();
            result = jedis.get(key);
        }catch (Exception e){
            log.error("get key:{} error",key,e);
            RedisShardedPool.returnBrokenResource(jedis);
            return result;
        }
        RedisShardedPool.returnResource(jedis);
        return result;

    }


    public static Long del(String key){
        ShardedJedis jedis = null;
        Long result = null;
        try{
            jedis = RedisShardedPool.getJedis();
            result = jedis.del(key);
        }catch (Exception e){
            log.error("get key:{} error",key,e);
            RedisShardedPool.returnBrokenResource(jedis);
            return result;
        }
        RedisShardedPool.returnResource(jedis);
        return result;

    }

    public static void main(String[] args) {
        ShardedJedis jedis = RedisShardedPool.getJedis();

        RedisSharderPoolUtil.set("name","chenchen");

        String res = RedisSharderPoolUtil.get("name");

        RedisSharderPoolUtil.setEx("year","19",20);

        RedisSharderPoolUtil.expire("year",160);


    }


}

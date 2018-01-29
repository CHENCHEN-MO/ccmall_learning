package com.ccmall.common;

import com.ccmall.util.PropertiesUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by Administrator on 2018/1/29.
 */
public class RedisPool {
    private static JedisPool jedisPool;//jedis连接池
    private static Integer maxTotal = Integer.valueOf(PropertiesUtil.getProperty("redis.max.total","20"));//控制jedis连接池中与Redis服务器的最大连接数，也就是jedisPool中jedis实例最大个数
    private static Integer maxIdle = Integer.valueOf(PropertiesUtil.getProperty("redis.max.idle","10"));//在jedisPool中最大的idle状态（空闲状态）jedis实例的个数
    private static Integer minIdle = Integer.valueOf(PropertiesUtil.getProperty("redis.min.idle","2"));//在jedisPool中最小的idle状态（空闲状态）jedis实例的个数
    private static Boolean testOnBorrow = Boolean.valueOf(PropertiesUtil.getProperty("redis.test.borrow","true"));//在borrow（借用，使用）一个jedis实例之前是否需要验证jedis实例的可用性。设置为true就是需要验证
    private static Boolean testOnReturn = Boolean.valueOf(PropertiesUtil.getProperty("redis.test.return","true"));//在Returen（放回）一个jedis实例之前是否需要验证jedis实例的可用性。设置为true就是需要验证
    private static String redisIp = PropertiesUtil.getProperty("redis.ip");
    private static Integer redisPort = Integer.valueOf(PropertiesUtil.getProperty("redis.port","6379"));

    private static void initPool(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);
        config.setTestOnBorrow(testOnBorrow);
        config.setTestOnReturn(testOnReturn);
        config.setBlockWhenExhausted(true);//连接耗尽时，是否阻塞：设置为false时，会抛出异常。设置为true时会阻塞直到超时（此时也会报异常，但是异常是不一样的），默认为true
        jedisPool = new JedisPool(config,redisIp,redisPort,1000*2);
    }

    static {
        initPool();
    }

    public static Jedis getJedis(){
        return jedisPool.getResource();
    }

    public static void returnResource(Jedis jedis){
            jedisPool.returnResource(jedis);
    }

    public static void returnBrokenResource(Jedis jedis){
        jedisPool.returnBrokenResource(jedis);
    }

    public static void main(String[] args) {
        Jedis jedis = jedisPool.getResource();
        jedis.set("name","chenchenbangbangbang");
        returnResource(jedis);

        jedisPool.destroy();//临时调用，销毁连接池中所有连接
    }

}

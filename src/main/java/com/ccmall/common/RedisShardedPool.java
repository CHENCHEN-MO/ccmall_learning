package com.ccmall.common;

import com.ccmall.util.PropertiesUtil;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.util.Hashing;
import redis.clients.util.Sharded;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/2/27.
 */
public class RedisShardedPool {

    private static ShardedJedisPool pool;//Shardedjedis连接池
    private static Integer maxTotal = Integer.valueOf(PropertiesUtil.getProperty("redis.max.total","20"));//控制jedis连接池中与Redis服务器的最大连接数，也就是jedisPool中jedis实例最大个数
    private static Integer maxIdle = Integer.valueOf(PropertiesUtil.getProperty("redis.max.idle","20"));//在jedisPool中最大的idle状态（空闲状态）jedis实例的个数
    private static Integer minIdle = Integer.valueOf(PropertiesUtil.getProperty("redis.min.idle","20"));//在jedisPool中最小的idle状态（空闲状态）jedis实例的个数
    private static Boolean testOnBorrow = Boolean.valueOf(PropertiesUtil.getProperty("redis.test.borrow","true"));//在borrow（借用，使用）一个jedis实例之前是否需要验证jedis实例的可用性。设置为true就是需要验证
    private static Boolean testOnReturn = Boolean.valueOf(PropertiesUtil.getProperty("redis.test.return","true"));//在Returen（放回）一个jedis实例之前是否需要验证jedis实例的可用性。设置为true就是需要验证
    private static String redis1Ip = PropertiesUtil.getProperty("redis1.ip");
    private static Integer redis1Port = Integer.valueOf(PropertiesUtil.getProperty("redis1.port","6379"));

    private static String redis2Ip = PropertiesUtil.getProperty("redis2.ip");
    private static Integer redis2Port = Integer.valueOf(PropertiesUtil.getProperty("redis2.port","6380"));

    private static void initPool(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);
        config.setTestOnBorrow(testOnBorrow);
        config.setTestOnReturn(testOnReturn);
        config.setBlockWhenExhausted(true);//连接耗尽时，是否阻塞：设置为false时，会抛出异常。设置为true时会阻塞直到超时（此时也会报异常，但是异常是不一样的），默认为true

        JedisShardInfo info1 = new JedisShardInfo(redis1Ip,redis1Port,1000 * 2);
        JedisShardInfo info2 = new JedisShardInfo(redis2Ip,redis2Port,1000 * 2);

        List<JedisShardInfo> list = new ArrayList<JedisShardInfo>(2);
        list.add(info1);
        list.add(info2);

        pool = new ShardedJedisPool(config,list, Hashing.MURMUR_HASH, Sharded.DEFAULT_KEY_TAG_PATTERN);
    }

    static {
        initPool();
    }

    public static ShardedJedis getJedis(){

        return pool.getResource();
    }

    public static void returnResource(ShardedJedis jedis){
        pool.returnResource(jedis);
    }

    public static void returnBrokenResource(ShardedJedis jedis){

        pool.returnBrokenResource(jedis);
    }

    public static void main(String[] args) {
        ShardedJedis jedis = pool.getResource();
        for (int i =0;i<10;i++){
            jedis.set("key"+i,"value"+i);
        }
        returnResource(jedis);
        //pool.destroy();//临时调用，销毁连接池中所有连接
    }

}

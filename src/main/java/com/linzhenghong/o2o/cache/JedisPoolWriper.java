package com.linzhenghong.o2o.cache;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis连接池
 * @author LinZhenHong
 */
public class JedisPoolWriper {

    /**
     * Redis连接池对象
     */
    private JedisPool jedisPool;


    public JedisPoolWriper(final JedisPoolConfig jedisPoolConfig,final String host, final int port) {
        try{
            jedisPool=new JedisPool(jedisPoolConfig,host,port);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 获取Redis连接池对象
     * @return
     */
    public JedisPool getJedisPool(){
        return jedisPool;
    }

    /**
     * 注入Redis连接池对象
     * @param jedisPool
     */
    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }
}

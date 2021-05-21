package com.redission.demo;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * @author machi
 */
public class RedissionDemo {

    public static void main(String[] args) {
        Config config = new Config();
        config.useClusterServers()
                .addNodeAddress("redis://192.168.31.114:7001")
                .addNodeAddress("redis://192.168.31.114:7002")
                .addNodeAddress("redis://192.168.31.114:7003")
                .addNodeAddress("redis://192.168.31.184:7001")
                .addNodeAddress("redis://192.168.31.184:7002")
                .addNodeAddress("redis://192.168.31.184:7003");

        RedissonClient redisson = Redisson.create(config);

        //可重入锁
        RLock lock = redisson.getLock("anyLock");
        lock.lock();
        lock.unlock();

        RMap<String, Object> map = redisson.getMap("anyMap");
        map.put("foo", "bar");

        map = redisson.getMap("anyMap");
        System.out.println(map.get("foo"));


    }
}

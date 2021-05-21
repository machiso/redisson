package com.redission.demo;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.concurrent.TimeUnit;

/**
 * 可重入锁demo
 * @author machi
 */
public class RedissionDemo {

    public static void main(String[] args) throws InterruptedException {
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

        //尝试获取锁，没有超时时间的限制
        lock.lock();
        lock.unlock();

        //指定10s内尝试获取锁，超时未获取则失败
        boolean tryLock = lock.tryLock(10, TimeUnit.SECONDS);
    }
}

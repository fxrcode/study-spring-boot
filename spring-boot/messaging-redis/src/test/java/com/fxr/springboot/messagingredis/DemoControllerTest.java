package com.fxr.springboot.messagingredis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 * https://segmentfault.com/a/1190000037434936
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DemoControllerTest {
    private final Logger logger = LoggerFactory.getLogger(DemoControllerTest.class);

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void transactionTest() throws InterruptedException{
        /**
         * 清空数据，删除 A、B 键
         */
        stringRedisTemplate.delete(DemoController.STR_KEY_A);
        stringRedisTemplate.delete(DemoController.STR_KEY_B);
        /**
         * 线程1：watch A 键
         * 事务：修改A、B 键值，阻塞10秒后exec、unwatch
         * 输出：A、B键值
         */
        Thread thread1 = new Thread(() -> {
            try {
                mockMvc.perform(MockMvcRequestBuilders.get("/watch"));
                logger.info(new StringBuffer(Thread.currentThread().getName()).append("执行结果：\n")
                        .append(DemoController.STR_KEY_A).append(":").append(stringRedisTemplate.opsForValue().get(DemoController.STR_KEY_A))
                        .append("\n").append(DemoController.STR_KEY_B).append(":").append(stringRedisTemplate.opsForValue().get(DemoController.STR_KEY_B))
                        .toString());
            } catch (Exception e) {
                logger.error("/watch",e);
            }
        });
        thread1.setName("Thread1");
        /**
         * 线程2：修改 A 键
         * 事务：无事务，无阻塞
         * 输出：A、B 键值
         */
        Thread thread2 = new Thread(() -> {
            try {
                mockMvc.perform(MockMvcRequestBuilders.get("/change"));
                logger.info(new StringBuffer(Thread.currentThread().getName()).append("执行结果：\n")
                        .append(DemoController.STR_KEY_A).append(":").append(stringRedisTemplate.opsForValue().get(DemoController.STR_KEY_A))
                        .append("\n").append(DemoController.STR_KEY_B).append(":").append(stringRedisTemplate.opsForValue().get(DemoController.STR_KEY_B))
                        .toString());
            } catch (Exception e) {
                logger.error("/change",e);
            }
        });
        thread2.setName("Thread2");
        /**
         * 线程1 比 线程2 先执行
         */
        thread1.start();
        Thread.sleep(500);
        thread2.start();
        /**
         * 主线程，等待 线程1、线程2 执行完成
         */
        thread1.join();
        thread2.join();
    }
}
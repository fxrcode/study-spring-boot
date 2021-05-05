package com.fxr.springboot.messagingredis;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * https://segmentfault.com/a/1190000037434936
 * Redis的线程模型和事务
 */
@RestController
public class DemoController {
    public final static String STR_KEY_A="key_a";
    public final static String STR_KEY_B="key_b";

    private final StringRedisTemplate stringRedisTemplate;

    public DemoController(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @GetMapping("/watch")
    public void watch(){
        stringRedisTemplate.setEnableTransactionSupport(true);
        stringRedisTemplate.watch(STR_KEY_A);
        stringRedisTemplate.multi();
        try {
            stringRedisTemplate.opsForValue().set(STR_KEY_A, "watch_a");
            stringRedisTemplate.opsForValue().set(STR_KEY_B, "watch_b");
            Thread.sleep(3000);
        }catch (Exception e){
            e.printStackTrace();
            stringRedisTemplate.discard();
        }
        stringRedisTemplate.exec();
        stringRedisTemplate.unwatch();
    }

    @GetMapping("/change")
    public void change(){
        stringRedisTemplate.opsForValue().set(STR_KEY_A,"change_a");
    }

}
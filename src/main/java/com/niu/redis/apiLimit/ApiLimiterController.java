package com.niu.redis.apiLimit;

import com.niu.redis.aspect.limit.ApiLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicInteger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@Slf4j
@RequestMapping("/limit")
public class ApiLimiterController {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 10秒内超过三次
     *
     * @param
     * @return org.springframework.http.ResponseEntity<java.lang.Object>
     * @author niuqingsong
     * @date 2021/3/19
     */
    @GetMapping(value = "/test")
    @ApiLimiter(key = "test", time = 10, count = 3)
    public ResponseEntity<Object> test() {

        RedisAtomicInteger limitCounter = new RedisAtomicInteger("limitCounter", redisTemplate.getConnectionFactory());
        String str = new Date() + " 累计访问次数：" + limitCounter.getAndIncrement();
        log.info(str);
        return ResponseEntity.ok(str);
    }
}
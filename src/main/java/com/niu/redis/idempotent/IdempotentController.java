package com.niu.redis.idempotent;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * 接口幂等性测试
 *
 * @return
 * @author niuqingsong
 * @date 2021/3/19
 */
@Slf4j
@RestController
@RequestMapping("/idempotent")
public class IdempotentController {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 接口幂等性测试
     *
     * @param name
     * @return java.lang.String
     * @author niuqingsong
     * @date 2021/3/19
     */
    @PostMapping(value = "/test", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String testIdempotent(@RequestParam String name) {
        String result;
        Boolean setResult = redisTemplate.opsForValue().setIfAbsent(name, name, 30, TimeUnit.SECONDS);
        if (setResult) {
            result = "插入成功";
            log.info("插入成功");
        } else {
            result = "重复插入";
            log.info("重复插入");
        }

        return result;
    }
}

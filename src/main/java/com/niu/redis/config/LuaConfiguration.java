package com.niu.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

/**
 * Lua配置
 *
 * @return
 * @author niuqingsong
 * @date 2021/3/19
 */
@Configuration
public class LuaConfiguration {
    @Bean
    public DefaultRedisScript<Number> redisScript() {
        DefaultRedisScript<Number> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("req_ratelimit.lua")));
        redisScript.setResultType(Number.class);
        return redisScript;
    }
}
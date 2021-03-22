package com.niu.redis.aspect.limit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiLimiter {

    /**
     * 限流唯一标识
     * @return
     */
    String key() default "";

    /**
     * 限流时间
     * @return
     */
    int time();

    /**
     * 限流次数
     * @return
     */
    int count();

}
package com.niu.redis.aspect.log;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 日志注解
 *
 * Target：用于明确注解用于目标类的哪个位置
 * Retention：用于标识自定义注解的生命周期
 *            RUNTIME:生命周期持续到运行时，能够通过反射获取到
 * Documented：用于标识自定义注解能够使用javadoc命令生成关于注解的文档
 *
 * @author niuqingsong
 * @date 2020/11/27
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogMessage {

    /**
     * 使用value值，在使用注解的时候，不需要写@LogMessage(value=xxx）,直接写@LogMessage(xxx）即可
     */
    String value() default "";

    /**
     * 别名使用value，所以在使用注解时，既可以写@LogMessage(xxx），也可以写@LogMessage(description=xxx）
     */
    @AliasFor("value")
    String description() default "";

    /**
     * 是否打印参数
     */
    boolean parameterPrint() default true;
}

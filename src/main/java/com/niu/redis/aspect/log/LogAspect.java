package com.niu.redis.aspect.log;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 日志切面类
 * Aspect ：作用是把当前类标识为一个切面供容器读取
 *
 * @author niuqingsong
 * @date 2020/11/27 
 */
@Slf4j
@Aspect
@Component
public class LogAspect {

    /**
     * 前置增强，目标方法执行之前执行
     * within:用于匹配所以持有指定注解类型内的方法
     * annotation:用于匹配当前执行方法持有指定注解的方法
     * within(com.xx..*) com.xx包及子包下的任何方法执行
     *
     * @param joinPoint
     * @param logManage
     * @return void
     * @author niuqingsong
     * @date 2020/11/27
     */
    @Before("within(com.niu.redis..*) && @annotation(logManage)")
    public void addBeforeLogger(JoinPoint joinPoint, LogMessage logManage) {
        log.info("[{}] Start", logManage.value());
        // 处理参数
        try {
            if (logManage.parameterPrint()) {
                Object[] args = joinPoint.getArgs(); // 参数值
                String[] argNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
                Map<String, Object> paramsMap = new HashMap<>();
                StringBuilder sb = new StringBuilder();
                if (args != null && args.length > 0) {
                    for (int i = 0; i < args.length; i++) {
                        paramsMap.put(argNames[i], args[i] != null ? args[i].toString() : "");
                    }

                    for (Map.Entry<String, Object> entry : paramsMap.entrySet()) {
                        sb.append(entry.getKey()).append(":").append(entry.getValue()).append(";");
                    }
                }
                log.info("[方法名称：{}，方法参数：{}", ((MethodSignature) joinPoint.getSignature()).getMethod().getName(), sb.toString());
            }
        } catch (Exception e) {
            log.info("切面日志打印异常！");
        }
    }

    /**
     * 返回增强，目标方法正常执行完毕时执行
     *
     * within:用于匹配所以持有指定注解类型内的方法
     * annotation:于匹配当前执行方法持有指定注解的方法
     * within(com.niu.redis..*) com.niu.redis 包及子包下的任何方法执行
     *
     * @param joinPoint
     * @param logManage
     * @return void
     * @author niuqingsong
     * @date 2020/11/27
     */
    @AfterReturning("within(com.niu.redis..*) && @annotation(logManage)")
    public void addAfterReturningLogger(JoinPoint joinPoint, LogMessage logManage) {
        log.info("[{}] End", logManage.value());
    }
}

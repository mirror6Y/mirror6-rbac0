package cn.mirror6.rbac.server.component;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @author mirror6
 * @description
 * @date 2019/8/26 11:23
 */

@Slf4j
@Aspect
@Component
public class LogAspect {

    @Pointcut("execution(public * cn.mirror6.rbac.server.controller..*.*(..))")
    public void log() {
    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        // 记录下请求内容
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<");
        log.info("URL : " + request.getRequestURL().toString());
        log.info("HTTP_METHOD : " + request.getMethod());
        log.info("IP : " + request.getRemoteAddr());
        log.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        log.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
//        LogAspect.setStartTime(System.currentTimeMillis());
    }

    @AfterReturning(returning = "ret", pointcut = "log()")
    public void doAfterReturning(Object ret) {
//        long startTime = LogAspect.getStartTime();
//        long endTime = System.currentTimeMillis();
//        log.info("COST_TIME : " + (endTime - startTime));
        // 处理完请求，返回内容
        log.info("RESPONSE : " + ret);
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>");
    }
}

package org.yam.springbootlibrarycrud.common.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    // Intercept all public methods in service package
    @Around("execution(public * org.yam.springbootlibrarycrud.service..*(..))")
    public Object logAndMeasureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();

        logger.info("Entering {} with arguments {}", methodName, args);

        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();  // execute actual method
        long elapsed = System.currentTimeMillis() - start;

        logger.info("Exiting {}; execution time: {} ms", methodName, elapsed);
        return result;
    }
}

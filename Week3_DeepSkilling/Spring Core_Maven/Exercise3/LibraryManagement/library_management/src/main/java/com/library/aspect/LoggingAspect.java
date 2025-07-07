package com.library.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Aspect for logging method execution times
 */
@Aspect
public class LoggingAspect {
    
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
    
    /**
     * Pointcut to match all methods in the service and repository packages
     */
    @Pointcut("execution(* com.library.service..*(..)) || execution(* com.library.repository..*(..))")
    public void applicationMethods() {
        // Pointcut definition
    }
    
    /**
     * Around advice to log method execution time
     * @param joinPoint The join point
     * @return The result of the method execution
     * @throws Throwable If an error occurs during method execution
     */
    @Around("applicationMethods()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        
        logger.info("=== AOP LOGGING ===");
        logger.info("Starting execution of {}.{}", className, methodName);
        
        Object result = null;
        try {
            // Proceed with the actual method execution
            result = joinPoint.proceed();
            
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            
            logger.info("Successfully completed {}.{} in {} ms", 
                       className, methodName, executionTime);
            
            // Also log to console for easier visibility
            System.out.println("*** EXECUTION TIME LOG ***");
            System.out.println("Method: " + className + "." + methodName);
            System.out.println("Execution Time: " + executionTime + " ms");
            System.out.println("Status: SUCCESS");
            System.out.println("**************************");
            
        } catch (Exception e) {
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            
            logger.error("Exception in {}.{} after {} ms: {}", 
                        className, methodName, executionTime, e.getMessage());
            
            // Also log to console for easier visibility
            System.out.println("*** EXECUTION TIME LOG ***");
            System.out.println("Method: " + className + "." + methodName);
            System.out.println("Execution Time: " + executionTime + " ms");
            System.out.println("Status: ERROR - " + e.getMessage());
            System.out.println("**************************");
            
            throw e;
        }
        
        return result;
    }
    
    /**
     * Pointcut for service layer methods only
     */
    @Pointcut("execution(* com.library.service..*(..))")
    public void serviceMethods() {
        // Pointcut definition for service methods
    }
    
    /**
     * Around advice specifically for service methods with additional logging
     * @param joinPoint The join point
     * @return The result of the method execution
     * @throws Throwable If an error occurs during method execution
     */
    @Around("serviceMethods()")
    public Object logServiceMethodDetails(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        
        logger.info(">>> SERVICE METHOD CALL: {} with {} arguments", methodName, args.length);
        
        if (args.length > 0) {
            System.out.println("Service Method Arguments:");
            for (int i = 0; i < args.length; i++) {
                System.out.println("  Arg " + i + ": " + args[i]);
            }
        }
        
        Object result = joinPoint.proceed();
        
        logger.info("<<< SERVICE METHOD COMPLETED: {}", methodName);
        
        return result;
    }
}
package com.library.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * Aspect class for logging method execution times and other cross-cutting concerns
 */
@Aspect
@Component
public class LoggingAspect {

    /**
     * Around advice to log method execution time
     */
    @Around("execution(* com.library.service.BookService.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        
        System.out.println("\n=== AOP Logging Aspect ===");
        System.out.println("LoggingAspect: Starting execution of " + className + "." + methodName + "()");
        
        long startTime = System.currentTimeMillis();
        
        try {
            // Proceed with the method execution
            Object result = joinPoint.proceed();
            
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            
            System.out.println("LoggingAspect: " + className + "." + methodName + "() executed successfully");
            System.out.println("LoggingAspect: Execution time: " + executionTime + " ms");
            System.out.println("=== End AOP Logging ===\n");
            
            return result;
            
        } catch (Exception e) {
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            
            System.out.println("LoggingAspect: " + className + "." + methodName + "() threw exception: " + e.getMessage());
            System.out.println("LoggingAspect: Execution time before exception: " + executionTime + " ms");
            System.out.println("=== End AOP Logging (Exception) ===\n");
            
            throw e;
        }
    }

    /**
     * Before advice to log method entry
     */
    @Before("execution(* com.library.repository.BookRepository.*(..))")
    public void logMethodEntry(org.aspectj.lang.JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        
        System.out.println("LoggingAspect: [BEFORE] Entering " + className + "." + methodName + "()");
        
        // Log method arguments if any
        Object[] args = joinPoint.getArgs();
        if (args.length > 0) {
            System.out.print("LoggingAspect: [BEFORE] Method arguments: ");
            for (int i = 0; i < args.length; i++) {
                System.out.print(args[i]);
                if (i < args.length - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println();
        }
    }

    /**
     * After advice to log method exit
     */
    @After("execution(* com.library.repository.BookRepository.*(..))")
    public void logMethodExit(org.aspectj.lang.JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        
        System.out.println("LoggingAspect: [AFTER] Exiting " + className + "." + methodName + "()");
    }

    /**
     * Around advice for performance monitoring of all library operations
     */
    @Around("execution(* com.library..*(..))")
    public Object monitorPerformance(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        
        // Only log for non-aspect classes to avoid recursion
        if (!className.equals("LoggingAspect")) {
            long startTime = System.nanoTime();
            
            try {
                Object result = joinPoint.proceed();
                
                long endTime = System.nanoTime();
                double executionTimeMs = (endTime - startTime) / 1_000_000.0;
                
                if (executionTimeMs > 1.0) { // Only log if execution time > 1ms
                    System.out.println("LoggingAspect: [PERFORMANCE] " + className + "." + methodName + "() took " + 
                                     String.format("%.2f", executionTimeMs) + " ms");
                }
                
                return result;
                
            } catch (Exception e) {
                long endTime = System.nanoTime();
                double executionTimeMs = (endTime - startTime) / 1_000_000.0;
                
                System.out.println("LoggingAspect: [PERFORMANCE] " + className + "." + methodName + "() failed after " + 
                                 String.format("%.2f", executionTimeMs) + " ms");
                throw e;
            }
        } else {
            return joinPoint.proceed();
        }
    }
}
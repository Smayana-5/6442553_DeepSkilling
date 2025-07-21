package com.cognizant.api_gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class LogFilter implements GlobalFilter, Ordered {
    
    private static final Logger logger = LoggerFactory.getLogger(LogFilter.class);
    
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        logger.info("Request URI: {}", exchange.getRequest().getURI());
        logger.info("Request Method: {}", exchange.getRequest().getMethod());
        logger.info("Request Headers: {}", exchange.getRequest().getHeaders());
        
        return chain.filter(exchange);
    }
    
    @Override
    public int getOrder() {
        return -1; // High priority
    }
}
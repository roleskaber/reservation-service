package com.demo.demo.domain;

import org.jspecify.annotations.NonNull;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerMiddleware implements HandlerInterceptor {

    private static final Logger logger =
            LoggerFactory.getLogger(LoggerMiddleware.class);

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             @NonNull Object handler) {
        logger.info("[{}] {} – {}",
                request.getMethod(),
                request.getRequestURL(),
                response.getStatus()
        );

        return true;
    }
}
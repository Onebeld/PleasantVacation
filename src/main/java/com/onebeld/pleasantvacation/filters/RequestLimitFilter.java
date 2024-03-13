package com.onebeld.pleasantvacation.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class RequestLimitFilter extends OncePerRequestFilter {
    private final ConcurrentHashMap<String, AtomicInteger> requestCountMap = new ConcurrentHashMap<>();
    private final int maxRequestsPerSecond;

    public RequestLimitFilter(int maxRequestsPerSecond) {
        this.maxRequestsPerSecond = maxRequestsPerSecond;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String remoteAddr = request.getRemoteAddr();
        AtomicInteger requestCount = requestCountMap.computeIfAbsent(remoteAddr, k -> new AtomicInteger(0));

        int count = requestCount.incrementAndGet();

        if (count > maxRequestsPerSecond) {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.getWriter().write("Too many requests");

            return;
        }

        filterChain.doFilter(request, response);

        requestCount.decrementAndGet();
    }
}

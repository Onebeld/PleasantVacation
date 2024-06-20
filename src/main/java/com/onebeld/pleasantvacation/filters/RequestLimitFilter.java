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

/**
 * A filter that limits the number of requests from one IP address per second.
 * It is used to prevent DDoS attacks.
 */
public class RequestLimitFilter extends OncePerRequestFilter {
    /**
     * A hash table containing the request counters for each IP address.
     */
    private final ConcurrentHashMap<String, AtomicInteger> requestCountMap = new ConcurrentHashMap<>();
    /**
     * Maximum number of requests from one IP address per second.
     */
    private final int maxRequestsPerSecond;

    /**
     * Creates a filter with the specified maximum number of requests per second.
     *
     * @param maxRequestsPerSecond maximum number of requests per second
     */
    public RequestLimitFilter(int maxRequestsPerSecond) {
        this.maxRequestsPerSecond = maxRequestsPerSecond;
    }

    /**
     * Performs filtering on the request.
     * <p>
     * If the number of requests from the current IP address exceeds the maximum value,
     * sends a response with the code 429 (Too Many Requests) and the message "Too many requests".
     * Otherwise, passes the request on to the filter chain.
     *
     * @param request     Incoming request
     * @param response    Response to request
     * @param filterChain Filter chain
     * @throws IOException If an error occurs when working with threads
     */
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

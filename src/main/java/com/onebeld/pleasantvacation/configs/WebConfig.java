package com.onebeld.pleasantvacation.configs;

import com.onebeld.pleasantvacation.filters.RequestLimitFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * A class that customizes the configuration of the web application
 */
@Configuration
public class WebConfig {
    /**
     * Creates a {@link FilterRegistrationBean} for a {@link RequestLimitFilter} with a maximum value of 30 requests per second.
     * The filter is applied to all URLs (“/*”).
     *
     * @return {@link FilterRegistrationBean} for {@link RequestLimitFilter}
     */
    @Bean
    public FilterRegistrationBean<RequestLimitFilter> requestLimitFilter() {
        FilterRegistrationBean<RequestLimitFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new RequestLimitFilter(30));
        registrationBean.addUrlPatterns("/*");

        return registrationBean;
    }
}

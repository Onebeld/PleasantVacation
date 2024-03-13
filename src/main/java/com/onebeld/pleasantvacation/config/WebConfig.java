package com.onebeld.pleasantvacation.config;

import com.onebeld.pleasantvacation.filters.RequestLimitFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {
    @Bean
    public FilterRegistrationBean<RequestLimitFilter> requestLimitFilter() {
        FilterRegistrationBean<RequestLimitFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new RequestLimitFilter(10));
        registrationBean.addUrlPatterns("/*");

        return registrationBean;
    }
}

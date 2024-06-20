package com.onebeld.pleasantvacation.configs;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.Locale;

/**
 * MVC Configuration
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {
    /**
     * Creates and configures a {@link LocaleResolver} bean to resolve localization based on the Accept-Language header.
     *
     * @return Configured bean {@link LocaleResolver}
     */
    @Bean("localeResolver")
    public LocaleResolver localeResolver() {
        AcceptHeaderLocaleResolver resolver = new AcceptHeaderLocaleResolver();
        resolver.setDefaultLocale(Locale.ENGLISH);

        return resolver;
    }

    /**
     * Creates and configures a {@link LocaleChangeInterceptor} bean to change the localization based on the Accept-Language header.
     *
     * @return Customized Bean {@link LocaleChangeInterceptor}
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("lang");

        return interceptor;
    }

    /**
     * Creates and configures a {@link MessageSource} bean for internationalization.
     *
     * @return Customized bean {@link MessageSource}
     */
    @Bean("messageSource")
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();

        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setBasenames("language/messages");
        messageSource.setDefaultEncoding("UTF-8");

        return messageSource;
    }

    /**
     * Adds a localization change interceptor to the interceptor registry.
     *
     * @param registry Interceptor registry to add an interceptor
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}

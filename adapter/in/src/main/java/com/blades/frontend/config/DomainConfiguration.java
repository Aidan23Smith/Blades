package com.blades.frontend.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class DomainConfiguration {

    @Bean
    public MessageSource propertySource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("classpath:properties/common",
                                   "classpath:properties/login",
                                   "classpath:properties/character");
        messageSource.setCacheSeconds(10);
        return messageSource;
    }

}

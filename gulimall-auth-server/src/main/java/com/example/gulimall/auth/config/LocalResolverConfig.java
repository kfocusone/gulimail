package com.example.gulimall.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import java.util.Locale;

//@Configuration
public class LocalResolverConfig {

    // Config/LocaleConfig.java


    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver resolver = new CookieLocaleResolver();
        resolver.setDefaultLocale(Locale.SIMPLIFIED_CHINESE); // 默认中文
        resolver.setCookieName("language"); // cookie 名
        resolver.setCookieMaxAge(60 * 60 * 24 * 30); // 30天
        return resolver;
    }

}

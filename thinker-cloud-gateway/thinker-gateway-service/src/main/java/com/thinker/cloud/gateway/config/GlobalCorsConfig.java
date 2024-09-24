package com.thinker.cloud.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

/**
 * 全局跨域配置
 *
 * @author admin
 */
@Configuration
public class GlobalCorsConfig {

    private static final String ALLOWED_HEADERS = "*";
    private static final String ALLOWED_METHODS = "GET,POST,PUT,DELETE,PATCH,OPTIONS,HEAD";
    private static final String ALLOWED_ORIGIN = "*";
    private static final String ALLOWED_EXPOSE = "*";
    private static final Long MAX_AGE = 18000L;

    @Bean
    public CorsWebFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedHeader(ALLOWED_HEADERS);
        config.addAllowedMethod(ALLOWED_METHODS);
        config.addAllowedOrigin(ALLOWED_ORIGIN);
        config.addAllowedOriginPattern(ALLOWED_ORIGIN);
        config.addExposedHeader(ALLOWED_EXPOSE);
        config.setAllowCredentials(true);
        config.setMaxAge(MAX_AGE);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
        source.registerCorsConfiguration("/**", config);
        return new CorsWebFilter(source);
    }
}

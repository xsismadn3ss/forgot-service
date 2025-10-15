package com.grupo3.forgotservice.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Configuration
public class SecurityConfig {
    @Value("${app.cors.allowed-origins}")
    private String allowedOriginsProp;


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        List<String> origins = Stream.of(allowedOriginsProp.split(",")).toList();

        // Lista de patrones que no tienen patrones
        List<String> allowedOrigins = origins.stream().filter(o -> !o.contains("*")).toList();
        // Lista de orígenes que tienen patrones
        List<String> allowedOriginsPatterns = origins.stream().filter(o -> o.contains("*")).toList();

        log.info("Origins allowed: {}", allowedOrigins);
        log.info("Origins patterns allowed: {}", allowedOriginsPatterns);

        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(allowedOrigins);
        config.setAllowedOriginPatterns(allowedOriginsPatterns);
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("Content-Type", "Authorization", "X-Requested-With", "Accept", "Origin"));
        config.setAllowCredentials(true);
        config.setExposedHeaders(List.of("Authorization"));
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(org.springframework.http.HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/encryption/**", "/authentication/**").permitAll()
                        .anyRequest().permitAll()
                );
        return http.build();
    }
}

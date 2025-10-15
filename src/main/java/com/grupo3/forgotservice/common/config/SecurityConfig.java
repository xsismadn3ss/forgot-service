package com.grupo3.forgotservice.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Configuration
public class SecurityConfig {
    @Value("${app.cors.allowed-origins}")
    private String allowedOriginsProp;


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        String prop = allowedOriginsProp == null ? "" : allowedOriginsProp;

        // Soportar valores separados por coma o espacios; limpiar comillas y slashes finales
        List<String> rawOrigins = Stream.of(prop.split("[,\\s]+"))
                .map(String::trim)
                .map(s -> s.replaceAll("^\"|\"$", "")) // quita comillas al inicio/fin si hubiera
                .filter(s -> !s.isBlank())
                .map(s -> s.endsWith("/") ? s.substring(0, s.length() - 1) : s) // sin slash final
                .distinct()
                .toList();

        // Normalizar para incluir esquema cuando falte (añadir http y https)
        List<String> normalized = new ArrayList<>();
        for (String o : rawOrigins) {
            String origin = o;
            boolean hasScheme = origin.startsWith("http://") || origin.startsWith("https://");
            if (!hasScheme) {
                // Si no tiene esquema, agregamos ambas variantes
                normalized.add("http://" + origin);
                normalized.add("https://" + origin);
            } else {
                normalized.add(origin);
            }
        }

        CorsConfiguration config = new CorsConfiguration();

        boolean hasPattern = normalized.stream().anyMatch(o -> o.contains("*") || o.contains("?"));
        if (hasPattern) {
            config.setAllowedOriginPatterns(normalized);
        } else {
            config.setAllowedOrigins(normalized);
        }

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
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(org.springframework.http.HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/encryption/**", "/authentication/**").permitAll()
                        .anyRequest().permitAll()
                );
        return http.build();
    }
}

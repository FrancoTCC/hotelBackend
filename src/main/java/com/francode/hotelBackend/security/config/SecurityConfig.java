package com.francode.hotelBackend.security.config;

import java.util.Collections;
import com.francode.hotelBackend.business.services.auth.UserDetailsServiceImpl;
import com.francode.hotelBackend.security.custom.CustomAuthEntryPoint;
import com.francode.hotelBackend.security.jwt.AuthTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;
    private final CustomAuthEntryPoint authEntryPoint;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService, CustomAuthEntryPoint authEntryPoint) {
        this.userDetailsService = userDetailsService;
        this.authEntryPoint = authEntryPoint;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Deshabilitar CSRF
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Habilitar CORS
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(authEntryPoint)
                )
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Sesiones sin estado
                )
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/v1/auth/signin").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll() // Permitir acceso público a Swagger UI
                        .requestMatchers("/v3/api-docs/**").permitAll()
                        .requestMatchers("/ws/**").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

   @Bean
public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    
    // Configuración esencial para resolver el error CORS
    configuration.setAllowedOriginPatterns(List.of("*"));  // Usar patterns en lugar de origins
    configuration.setAllowedMethods(List.of(
        "GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH", "HEAD"
    ));
    configuration.setAllowedHeaders(List.of(
        "Authorization", "Cache-Control", "Content-Type", "Origin", 
        "Accept", "X-Requested-With", "Access-Control-Request-Method",
        "Access-Control-Request-Headers"
    ));
    configuration.setExposedHeaders(List.of(
        "Authorization", "Content-Type", "Content-Length"
    ));
    configuration.setAllowCredentials(false);  // IMPORTANTE: false con wildcard (*)
    configuration.setMaxAge(3600L);  // Cache de 1 hora para preflight
    
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
}

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}

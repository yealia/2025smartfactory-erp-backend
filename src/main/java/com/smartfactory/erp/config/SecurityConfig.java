package com.smartfactory.erp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1. CSRF 보호 기능 비활성화 (기존과 동일)
                .csrf(csrf -> csrf.disable())

                // 2. CORS 설정을 아래에 정의된 Bean을 사용하도록 명시
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // 3. 세션을 사용하지 않는 Stateless 설정 (API 서버에 적합)
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 4. 모든 API 경로에 대한 접근을 허용하여 403 에러 방지
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/**").permitAll() // '/api/'로 시작하는 모든 경로를 인증 없이 허용
                        .anyRequest().authenticated()
                );
        return http.build();
    }

    /**
     * React 앱과의 CORS 통신을 위한 상세 설정 Bean
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // ERP React 앱의 주소
        config.setAllowedOrigins(List.of("http://localhost:5173", "http://localhost:5174", "http://localhost:5175"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        // axios의 withCredentials=true 옵션과 통신하려면 반드시 필요
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // 모든 경로에 대해 위 CORS 정책 적용
        return source;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}


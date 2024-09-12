package com.rljj.switchswitchmemberservice.global.config.security;

import com.rljj.switchswitchcommon.jwt.JwtProvider;
import com.rljj.switchswitchmemberservice.domain.auth.service.AuthLogoutHandler;
import com.rljj.switchswitchmemberservice.domain.auth.service.AuthService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomAuthenticationManager customAuthenticationManager;
    private final JwtProvider jwtProvider;
    private final AuthService authService;
    private final AuthLogoutHandler logoutHandler;
    private final LoginAuthenticationFilter loginAuthenticationFilter;

    @Bean
    public LoginAuthenticationFilter loginAuthenticationFilter() {
        LoginAuthenticationFilter filter = new LoginAuthenticationFilter(customAuthenticationManager, jwtProvider, authService);
        filter.setFilterProcessesUrl("/api/auth/login");
        return filter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, HttpSession httpSession) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().permitAll()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .logout(logout -> logout.logoutUrl("/api/auth/logout")
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true)
                        .permitAll())
                .addFilter(loginAuthenticationFilter)
                .exceptionHandling(
                        exception -> exception.accessDeniedPage("/login?error=403")
                )
        ;

        return http.build();
    }

}

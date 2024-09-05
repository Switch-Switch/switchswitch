package com.rljj.switchswitchmemberservice.global.config.jwt;

import com.rljj.switchswitchcommon.jwt.JwtProvider;
import com.rljj.switchswitchmemberservice.domain.auth.service.AuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtProvider jwtProvider;
    private final List<String> notFiltered = List.of("/api/auth/signup", "/api/auth/login");

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        if (isNotFiltered(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = jwtProvider.parseJwt(request);
        String memberId = jwtProvider.parseSubject(jwt);

        if (memberId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(memberId);
            setSecurityContextHolder(userDetails);
        }

        filterChain.doFilter(request, response);
    }

    private void setSecurityContextHolder(UserDetails userDetails) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }


    private boolean isNotFiltered(HttpServletRequest request) {
        String path = request.getRequestURI();
        for (String notFilter : notFiltered) {
            if (path.startsWith(notFilter)) return true;
        }
        return false;
    }
}

package com.rljj.chatservice.global.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public abstract class SecurityUtils {
    // 인스턴스화 방지를 위한 private 생성자 추가
    private SecurityUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static String getUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }
        return null;
    }

    public static Long getMemberId() {
        String username = getUsername();
        if (username != null) {
            try {
                return Long.parseLong(username);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Username is not a valid number: " + username, e);
            }
        }
        throw new IllegalStateException("Username is null");
    }
}

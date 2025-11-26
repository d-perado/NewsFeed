package org.example.newsfeed.common.auth;

import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
    public static String getLoginUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}

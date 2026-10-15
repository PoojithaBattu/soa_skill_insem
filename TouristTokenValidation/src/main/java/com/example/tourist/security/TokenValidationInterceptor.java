package com.example.tourist.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class TokenValidationInterceptor implements HandlerInterceptor {

    // Demo token for this lab. In a real application, validate a signed/issued token.
    public static final String VALID_TOKEN = "tourist-secret-token";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // Validate only DELETE requests to /api/tourists/{id}.
        if ("DELETE".equalsIgnoreCase(request.getMethod())) {
            String token = request.getHeader("Token");
            if (!VALID_TOKEN.equals(token)) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.setContentType("text/plain");
                response.getWriter().write("Deletion Failed: Token Error");
                return false;
            }
        }
        return true;
    }
}

package com.example.book.security;


import com.example.book.authentication.service.AuthenticationService;
import com.example.book.authentication.service.SessionService;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class BearerTokenFilter extends OncePerRequestFilter {

    @Autowired
    private SessionService sessionService;

    @Override
    public void doFilterInternal(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull FilterChain chain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (request.getServletPath().contains("/api/auth")) {
            chain.doFilter(request, response);
        } else if (request.getServletPath().contains("/api/book")) {
            chain.doFilter(request, response);
        } else if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            if (sessionService.isTokenValid(token)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(token, null, null);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                chain.doFilter(request, response);
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Unauthorized Token");
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
            }
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized");
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
        }
    }
}

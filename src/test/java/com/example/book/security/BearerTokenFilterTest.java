package com.example.book.security;

import com.example.book.service.SessionService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class BearerTokenFilterTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private SessionService sessionService;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain chain;

    @Mock
    private PrintWriter writer;

    @InjectMocks
    private BearerTokenFilter bearerTokenFilter;

    @BeforeEach
    public void setup() throws IOException {
        MockitoAnnotations.openMocks(this);
        when(response.getWriter()).thenReturn(writer);
    }

    @Test
    public void doFilterInternal_BypassFilterForAuthPath_ShouldBypass() throws IOException, ServletException {
        when(request.getServletPath()).thenReturn("/api/auth/login");

        bearerTokenFilter.doFilterInternal(request, response, chain);

        verify(chain, times(1)).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    public void doFilterInternal_WithValidToken_SetsAuthenticationInContext() throws IOException, ServletException {
        when(request.getServletPath()).thenReturn("/api/news");
        when(request.getHeader("Authorization")).thenReturn("Bearer valid-token");
        when(sessionService.isTokenValid("valid-token")).thenReturn(true);

        bearerTokenFilter.doFilterInternal(request, response, chain);

        verify(sessionService, times(1)).isTokenValid("valid-token");
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    public void doFilterInternal_WithInvalidToken_AuthenticationIsNull() throws IOException, ServletException {
        when(request.getServletPath()).thenReturn("/api/news");
        when(request.getHeader("Authorization")).thenReturn("Bearer invalid-token");
        when(sessionService.isTokenValid("invalid-token")).thenReturn(false);

        bearerTokenFilter.doFilterInternal(request, response, chain);

        verify(sessionService, times(1)).isTokenValid("invalid-token");
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    public void doFilterInternal_InvalidAuthorizationHeaderFormat_ShouldReturnUnauthorized() throws IOException, ServletException {
        when(request.getServletPath()).thenReturn("/api/news");
        when(request.getHeader("Authorization")).thenReturn("InvalidHeader");

        bearerTokenFilter.doFilterInternal(request, response, chain);

        verify(response, times(1)).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(response.getWriter(), times(1)).write("Unauthorized");
        verify(response, times(1)).setContentType("application/json");
        verify(response, times(1)).setCharacterEncoding("UTF-8");
        verifyNoInteractions(chain);
    }

    @Test
    public void doFilterInternal_NullAuthorizationHeader_ShouldReturnUnauthorized() throws IOException, ServletException {
        when(request.getServletPath()).thenReturn("/api/news");
        when(request.getHeader("Authorization")).thenReturn(null);

        bearerTokenFilter.doFilterInternal(request, response, chain);

        verify(response, times(1)).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(response.getWriter(), times(1)).write("Unauthorized");
        verify(response, times(1)).setContentType("application/json");
        verify(response, times(1)).setCharacterEncoding("UTF-8");
        verifyNoInteractions(chain);
    }
}
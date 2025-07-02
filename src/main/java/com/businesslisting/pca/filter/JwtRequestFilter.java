package com.businesslisting.pca.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.businesslisting.pca.service.AppUserDetailsService;
import com.businesslisting.pca.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final AppUserDetailsService appUserDetailsService;
    private final JwtUtil jwtUtil;

    private static final List<String> PUBLIC_URLS = List.of(
            "/login", "/health", "/register", "/send-reset-otp", "/reset-password", "/logout", "/send-otp", "/verify-otp",
            "/categories", "/subcategories", "/services/search",
            "/services/autocomplete", "/services/create", "/services/{serviceId}/images",
            "/api/v1.0/login", "/api/v1.0/register", "/api/v1.0/send-reset-otp", "/api/v1.0/reset-password",
            "/api/v1.0/logout", "/api/v1.0/send-otp", "/api/v1.0/verify-otp",
            "/api/v1.0/categories", "/api/v1.0/subcategories", "/api/v1.0/services/search",
            "/api/v1.0/services/autocomplete", "/api/v1.0/services/create", "/api/v1.0/services/{serviceId}/images", "/");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getServletPath();
        System.out.println("🔍 JwtRequestFilter path: " + path);

        // Enhanced public URL check for wildcards
        if (PUBLIC_URLS.stream().anyMatch(path::equals) ||
                path.startsWith("/services/autocomplete") ||
                path.startsWith("/api/v1.0/services/autocomplete")) {
            System.out.println(" Public URL – Skipping JWT check");
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = null;
        String email = null;

        try {
            // 1. Check the authorization Header
            final String authorizationHeader = request.getHeader("Authorization");
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                jwt = authorizationHeader.substring(7);
            }

            // 2. If it is not found in the header. Check the cookies
            if (jwt == null) {
                Cookie[] cookies = request.getCookies();
                if (cookies != null) {
                    for (Cookie cookie : cookies) {
                        if ("jwt".equals(cookie.getName())) {
                            jwt = cookie.getValue();
                            break;
                        }
                    }
                }
            }

            // 3. Validate the token and set the security Context
            if (jwt != null) {
                email = jwtUtil.extractEmail(jwt);
                if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = appUserDetailsService.loadUserByUsername(email);
                    if (jwtUtil.validateToken(jwt, userDetails)) {
                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                }
            }
        } catch (io.jsonwebtoken.ExpiredJwtException ex) {
            request.setAttribute("jwt_exception", "Token expired");
        } catch (io.jsonwebtoken.SignatureException ex) {
            request.setAttribute("jwt_exception", "Invalid token signature");
        } catch (Exception ex) {
            request.setAttribute("jwt_exception", "Invalid token");
        }

        filterChain.doFilter(request, response);
    }
}

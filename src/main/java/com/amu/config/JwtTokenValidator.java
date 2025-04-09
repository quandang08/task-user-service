package com.amu.config;

import com.amu.service.CustomerUserServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Optional;

@Component
public class JwtTokenValidator extends OncePerRequestFilter {
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private CustomerUserServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String path = request.getRequestURI();
        //Sửa ở đây để cho phép signin ko cần xác thực đầu vào
        if (path.startsWith("/auth")) {
            filterChain.doFilter(request, response);
            return;
        }
        Optional.ofNullable(request.getHeader(JWTConstant.JWT_TOKEN_HEADER))
                .filter(header -> header.startsWith("Bearer "))
                .map(header -> {
                    String token = header.substring(7);

                    return token;
                })
                .ifPresent(token -> {
                    if (token.isBlank()) {
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        return;
                    }
                    authenticateRequest(token, response);
                });

        filterChain.doFilter(request, response);
    }

    private void authenticateRequest(String token, HttpServletResponse response) {
        try {
            Claims claims = jwtProvider.parseToken(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(claims.getSubject());
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (JwtException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}

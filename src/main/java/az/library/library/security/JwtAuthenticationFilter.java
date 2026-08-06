package az.library.library.security;

import az.library.library.entity.User;
import az.library.library.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final JwtAuthenticationEntryPoint entryPoint;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        try {
            Claims claims = jwtService.parseToken(token);
            Long userId = Long.valueOf(claims.getSubject());

            User user = userRepository.findById(userId).orElse(null);
            if (user == null) {
                log.warn("JWT valid but user {} not found in DB", userId);
                SecurityContextHolder.clearContext();
                entryPoint.commence(request, response, new InsufficientAuthenticationException("User not found"));
                return;
            }

            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().name());
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(user, null, List.of(authority));
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);

        } catch (ExpiredJwtException e) {
            log.warn("JWT token expired: {}", e.getMessage());
            SecurityContextHolder.clearContext();
            entryPoint.commence(request, response, new InsufficientAuthenticationException("Token expired", e));

        } catch (SignatureException e) {
            log.warn("JWT signature invalid: {}", e.getMessage());
            SecurityContextHolder.clearContext();
            entryPoint.commence(request, response, new InsufficientAuthenticationException("Invalid token signature", e));

        } catch (MalformedJwtException e) {
            log.warn("JWT token malformed: {}", e.getMessage());
            SecurityContextHolder.clearContext();
            entryPoint.commence(request, response, new InsufficientAuthenticationException("Malformed token", e));

        } catch (Exception e) {
            log.error("JWT filter unexpected error", e);
            SecurityContextHolder.clearContext();
            entryPoint.commence(request, response, new InsufficientAuthenticationException("Token validation failed", e));
        }
    }
}

package com.licon.redis.core.security;

import com.licon.redis.core.config.JwtProperties;
import com.licon.redis.core.util.CollectionUtil;
import com.licon.redis.core.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * jwt认证过滤器
 * @author Licon
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final JwtProperties jwtProperties;
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (checkJwtToken(request)){
            Optional<Claims> authorities = validateToken(request)
                    .filter(claims -> claims.get("authorities") != null);
            if (authorities.isPresent()){
                this.setAuthentication(authorities.get());
            }else {
                SecurityContextHolder.clearContext();
            }
        }
        filterChain.doFilter(request, response);
    }
    public void setAuthentication(Claims claims){
        List<?> authList = CollectionUtil.convertObjectToList(claims.get("authorities"));
        List<SimpleGrantedAuthority> authorities = authList.stream()
                .map(String::valueOf)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(claims.getSubject(), null, authorities);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

    }
    public Optional<Claims> validateToken(HttpServletRequest request){
        String accessToken = request.getHeader(jwtProperties.getJwt().getHeader()).replace(jwtProperties.getJwt().getPrefix(), "");
        try {
            return Optional.of(jwtUtil.parsedClaims(accessToken));
        }catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e){
            return Optional.empty();
        }
    }
    public boolean checkJwtToken(HttpServletRequest request){
        String authenticationHeader = request.getHeader(jwtProperties.getJwt().getHeader());
        return authenticationHeader != null && authenticationHeader.startsWith(jwtProperties.getJwt().getPrefix());
    }
}

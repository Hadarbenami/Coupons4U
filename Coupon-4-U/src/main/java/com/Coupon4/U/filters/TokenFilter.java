package com.Coupon4.U.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@Order(2)
public class TokenFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");

        if(token == null){
            //no token from the client
            response.setStatus(401);
            response.getWriter().write("You must LOGIN!");
        } else {
            //we got a token
            token = token.replace("Bearer ", "");
            try {
                JWT.decode(token).getClaim("role").asString();
                //everything checks out, client can move to the next step
                filterChain.doFilter(request, response);
            }catch (JWTDecodeException ex){
                response.setStatus(401);
                response.getWriter().write("You must LOGIN");
            }

        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        List<String> patterns = List.of("/v3/api-docs", "/configuration/", "/swagger", "/webjars", "/auth/login", "/general");
        return patterns.stream().anyMatch( p -> request.getRequestURL().toString().contains(p));
    }
}

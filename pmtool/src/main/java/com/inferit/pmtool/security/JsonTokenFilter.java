package com.inferit.projectmanagementtool.security;

import com.inferit.projectmanagementtool.domain.User;
import com.inferit.projectmanagementtool.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

import static com.inferit.projectmanagementtool.security.SecurityConstants.HEADER;
import static com.inferit.projectmanagementtool.security.SecurityConstants.JWT_PREFIX;


public class JsonTokenFilter extends OncePerRequestFilter {
   @Autowired
   JsonTokenProvider jsonTokenProvider;
   @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getTokenFromRequest(httpServletRequest);
            System.out.println("doFilterInternal "+StringUtils.hasText(jwt));
            if (StringUtils.hasText(jwt) && jsonTokenProvider.validateToken(jwt)) {
                Long userId = jsonTokenProvider.getUserIdFromToken(jwt);
                User userDetails = customUserDetailsService.loadUserByUserId(userId);
                //To conver User to authentication
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, Collections.emptyList());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }catch (Exception ex){
            logger.error("Cant set authentication in context");

        }
     filterChain.doFilter(httpServletRequest,httpServletResponse);

    }

    public String getTokenFromRequest(HttpServletRequest httpServletRequest){
        String bearer=httpServletRequest.getHeader(HEADER);
        if (StringUtils.hasText(bearer)&& bearer.startsWith(JWT_PREFIX)){
            String token=bearer.substring(7,bearer.length());
            return token;
        }
        return null;
    }
}

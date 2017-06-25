package com.easytoolsoft.easyreport.web.spring.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.easytoolsoft.easyreport.support.consts.UserAuthConsts;
import com.easytoolsoft.easyreport.support.security.MembershipFacade;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * @author zhiwei.deng
 * @date 2017-05-12
 **/
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private MembershipFacade<UserDetails> membershipFacade;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws
        ServletException, IOException {
        String authToken = request.getHeader(jwtTokenUtil.getJwtConfig().getHeader());
        if (StringUtils.isBlank(authToken)) {
            chain.doFilter(request, response);
            return;
        }

        String username = jwtTokenUtil.getUsernameFromToken(authToken);
        log.info("checking authentication user {}", username);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // It is not compelling necessary to load the use details from the database.
            // You could also store the information  in the token and read it from it. It's up to you ;)
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // For simple validation it is completely sufficient to just check the token integrity.
            // You don't have to  call the database compellingly. Again it's up to you ;)
            if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                log.info("authenticated user {}, setting security context", username);
                SecurityContextHolder.getContext().setAuthentication(authentication);

                log.info("authenticated user {}, setting request context", username);
                request.setAttribute(UserAuthConsts.CURRENT_USER, this.membershipFacade.getUser(username));
            }
        }

        chain.doFilter(request, response);
    }
}
package com.easytoolsoft.easyreport.web.controller.member;

import javax.servlet.http.HttpServletRequest;

import com.easytoolsoft.easyreport.common.enums.BizErrorCode;
import com.easytoolsoft.easyreport.membership.service.EventService;
import com.easytoolsoft.easyreport.support.model.ResponseResult;
import com.easytoolsoft.easyreport.web.spring.security.EzrptUserDetails;
import com.easytoolsoft.easyreport.web.spring.security.jwt.JwtAuthenticationRequest;
import com.easytoolsoft.easyreport.web.spring.security.jwt.JwtAuthenticationResponse;
import com.easytoolsoft.easyreport.web.spring.security.jwt.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Tom Deng
 * @date 2017-03-25
 */
@RestController
@RequestMapping(value = "/rest/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private EventService eventService;

    @PostMapping(value = "/login")
    public ResponseResult login(@RequestBody JwtAuthenticationRequest authenticationRequest,
                                Device device) throws AuthenticationException {
        // Perform the security
        final Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(),
                authenticationRequest.getPassword()
            )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Reload password post-security so we can generate token
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails, device);
        this.eventService.add("login", userDetails.getUsername(),
            "用户登录", "INFO", "/rest/auth/login");
        return ResponseResult.success(new JwtAuthenticationResponse(token));
    }

    @GetMapping(value = "/refresh")
    public ResponseResult refresh(HttpServletRequest request) {
        String token = request.getHeader(jwtTokenUtil.getJwtConfig().getHeader());
        String username = jwtTokenUtil.getUsernameFromToken(token);
        EzrptUserDetails user = (EzrptUserDetails)userDetailsService.loadUserByUsername(username);

        if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
            String refreshedToken = jwtTokenUtil.refreshToken(token);
            return ResponseResult.success(new JwtAuthenticationResponse(refreshedToken));
        }
        return ResponseResult.failure(BizErrorCode.REFRESH_TOKEN_FAILURE);
    }

    @GetMapping(value = "/logout")
    public ResponseResult logout() {
        return ResponseResult.success(null);
    }
}

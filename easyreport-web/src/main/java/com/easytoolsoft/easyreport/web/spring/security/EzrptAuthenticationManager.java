package com.easytoolsoft.easyreport.web.spring.security;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * @author zhiwei.deng
 * @date 2017-05-24
 **/
public class EzrptAuthenticationManager implements AuthenticationManager {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        throw new NotImplementedException("NotImplementedException");
    }
}

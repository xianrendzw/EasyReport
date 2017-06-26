package com.easytoolsoft.easyreport.membership.service.impl;

import com.easytoolsoft.easyreport.support.security.PasswordService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author zhiwei.deng
 * @date 2017-06-26
 **/
@Service
public class PasswordServiceImpl implements PasswordService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String genreateSalt() {
        return StringUtils.EMPTY;
    }

    @Override
    public String encode(CharSequence rawPassword, String credentialsSalt) {
        return this.passwordEncoder.encode(rawPassword);
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return this.encode(rawPassword, "");
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return this.matches(rawPassword, encodedPassword, "");
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword, String credentialsSalt) {
        return this.passwordEncoder.matches(rawPassword, encodedPassword);
    }
}

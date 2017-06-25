package com.easytoolsoft.easyreport.web.spring.security.jwt;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhiwei.deng
 * @date 2017-05-12
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtAuthenticationRequest implements Serializable {
    private static final long serialVersionUID = -8445943548965154778L;
    private String username;
    private String password;
}

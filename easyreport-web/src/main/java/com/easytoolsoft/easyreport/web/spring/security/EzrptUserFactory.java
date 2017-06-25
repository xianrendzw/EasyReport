package com.easytoolsoft.easyreport.web.spring.security;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.easytoolsoft.easyreport.membership.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * @author zhiwei.deng
 * @date 2017-05-12
 **/
public final class EzrptUserFactory {

    private EzrptUserFactory() {
    }

    public static EzrptUserDetails create(User user, Set<String> authorities) {
        return new EzrptUserDetails(
            Long.valueOf(user.getId()),
            user.getAccount(),
            user.getEmail(),
            user.getPassword(),
            mapToGrantedAuthorities(authorities),
            user.isEnabled(),
            user.getGmtModified()
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(Set<String> authorities) {
        return authorities.stream()
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
    }
}

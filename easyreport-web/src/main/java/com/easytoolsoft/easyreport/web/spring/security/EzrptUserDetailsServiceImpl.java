package com.easytoolsoft.easyreport.web.spring.security;

import java.util.Set;

import com.easytoolsoft.easyreport.membership.domain.User;
import com.easytoolsoft.easyreport.support.security.MembershipFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author zhiwei.deng
 * @date 2017-05-12
 **/
@Service
public class EzrptUserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private MembershipFacade<User> membershipFacade;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.membershipFacade.getUser(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        }
        Set<String> authorities = membershipFacade.getPermissionSet(user.getRoles());
        return EzrptUserFactory.create(user, authorities);
    }
}

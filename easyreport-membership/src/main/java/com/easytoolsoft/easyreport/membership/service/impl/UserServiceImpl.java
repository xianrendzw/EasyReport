package com.easytoolsoft.easyreport.membership.service.impl;

import javax.annotation.Resource;

import com.easytoolsoft.easyreport.membership.data.UserRepository;
import com.easytoolsoft.easyreport.membership.domain.User;
import com.easytoolsoft.easyreport.membership.domain.example.UserExample;
import com.easytoolsoft.easyreport.membership.service.UserService;
import com.easytoolsoft.easyreport.mybatis.service.AbstractCrudService;
import com.easytoolsoft.easyreport.support.security.PasswordService;
import org.springframework.stereotype.Service;

/**
 * @author Tom Deng
 * @date 2017-03-25
 */
@Service("UserService")
public class UserServiceImpl
    extends AbstractCrudService<UserRepository, User, UserExample, Integer>
    implements UserService {

    @Resource(name = "ShiroPasswordService")
    private PasswordService passwordService;

    @Override
    protected UserExample getPageExample(final String fieldName, final String keyword) {
        final UserExample example = new UserExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public User getUserByAccount(final String account) {
        final UserExample example = new UserExample();
        example.or().andAccountEqualTo(account);
        return this.dao.selectOneByExample(example);
    }

    @Override
    public void encryptPassword(final User user) {
        user.setSalt(this.passwordService.genreateSalt());
        user.setPassword(this.passwordService.encode(user.getPassword(), user.getCredentialsSalt()));
    }
}
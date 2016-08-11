package com.easytoolsoft.easyreport.membership.service.impl;

import com.easytoolsoft.easyreport.data.common.helper.ParameterBuilder;
import com.easytoolsoft.easyreport.data.common.service.AbstractCrudService;
import com.easytoolsoft.easyreport.data.membership.dao.IUserDao;
import com.easytoolsoft.easyreport.data.membership.example.UserExample;
import com.easytoolsoft.easyreport.data.membership.po.User;
import com.easytoolsoft.easyreport.membership.security.PasswordService;
import com.easytoolsoft.easyreport.membership.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service("EzrptMemberUserService")
public class UserService
        extends AbstractCrudService<IUserDao, User, UserExample>
        implements IUserService {

    @Resource
    private PasswordService passwordService;

    @Override
    protected UserExample getPageExample(String fieldName, String keyword) {
        UserExample example = new UserExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public User getUserByAccount(String account) {
        UserExample example = new UserExample();
        example.or().andAccountEqualTo(account);
        return this.dao.selectOneByExample(example);
    }

    @Override
    public void encryptPassword(User user) {
        user.setSalt(passwordService.genreateSalt());
        user.setPassword(passwordService.encryptPassword(user.getPassword(), user.getCredentialsSalt()));
    }
}
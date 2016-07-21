package com.easytoolsoft.easyreport.membership.service.impl;

import com.easytoolsoft.easyreport.data.helper.ParameterBuilder;
import com.easytoolsoft.easyreport.data.service.AbstractCrudService;
import com.easytoolsoft.easyreport.membership.dao.IUserDao;
import com.easytoolsoft.easyreport.membership.po.User;
import com.easytoolsoft.easyreport.membership.security.PasswordService;
import com.easytoolsoft.easyreport.membership.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service("EzrptMemberUserService")
public class UserService extends AbstractCrudService<IUserDao, User> implements IUserService {
    @Resource
    private PasswordService passwordService;

    @Override
    public User getUserByAccount(String account) {
        Map<String, Object> params = ParameterBuilder.getQueryParams(
                User.builder().account(account).build());
        return this.dao.selectOne(params);
    }

    @Override
    public void encryptPassword(User user) {
        user.setSalt(passwordService.genreateSalt());
        user.setPassword(passwordService.encryptPassword(user.getPassword(), user.getCredentialsSalt()));
    }
}
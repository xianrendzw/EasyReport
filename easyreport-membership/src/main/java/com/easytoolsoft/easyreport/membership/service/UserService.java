package com.easytoolsoft.easyreport.membership.service;

import com.easytoolsoft.easyreport.membership.domain.User;
import com.easytoolsoft.easyreport.membership.domain.example.UserExample;
import com.easytoolsoft.easyreport.mybatis.service.CrudService;

/**
 * 系统用户服务类
 *
 * @author Tom Deng
 * @date 2017-03-25
 */
public interface UserService extends CrudService<User, UserExample, Integer> {
    /**
     * @param account
     * @return
     */
    User getUserByAccount(String account);

    /**
     * @param user
     */
    void encryptPassword(User user);
}
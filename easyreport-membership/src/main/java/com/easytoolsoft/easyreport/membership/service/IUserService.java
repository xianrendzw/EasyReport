package com.easytoolsoft.easyreport.membership.service;

import com.easytoolsoft.easyreport.data.common.service.ICrudService;
import com.easytoolsoft.easyreport.data.membership.po.User;

/**
 * 系统用户服务类
 *
 * @author Tom Deng
 */
public interface IUserService extends ICrudService<User> {
    User getUserByAccount(String account);

    void encryptPassword(User user);
}
package com.easytoolsoft.easyreport.mybatis.sample.service;

import com.easytoolsoft.easyreport.mybatis.sample.domain.User;
import com.easytoolsoft.easyreport.mybatis.sample.domain.UserExample;
import com.easytoolsoft.easyreport.mybatis.service.CrudService;

/**
 * 系统用户服务类
 *
 * @author Tom Deng
 * @date 2017-03-25
 */
public interface UserService extends CrudService<User, UserExample, Integer> {
}
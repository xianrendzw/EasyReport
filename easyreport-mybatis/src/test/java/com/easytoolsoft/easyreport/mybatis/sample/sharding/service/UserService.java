package com.easytoolsoft.easyreport.mybatis.sample.sharding.service;

import com.easytoolsoft.easyreport.mybatis.sample.domain.User;
import com.easytoolsoft.easyreport.mybatis.sample.domain.UserExample;
import com.easytoolsoft.easyreport.mybatis.sharding.service.CrudService;

/**
 * 分表(sharding)用户服务接口
 *
 * @author Tom Deng
 * @date 2017-03-25
 */
public interface UserService extends CrudService<User, UserExample, Integer> {
}
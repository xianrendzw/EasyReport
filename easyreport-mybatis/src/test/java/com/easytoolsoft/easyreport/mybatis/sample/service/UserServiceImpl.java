package com.easytoolsoft.easyreport.mybatis.sample.service;

import com.easytoolsoft.easyreport.mybatis.sample.domain.User;
import com.easytoolsoft.easyreport.mybatis.sample.domain.UserExample;
import com.easytoolsoft.easyreport.mybatis.sample.repository.UserRepository;
import com.easytoolsoft.easyreport.mybatis.service.AbstractCrudService;
import org.springframework.stereotype.Service;

/**
 * @author Tom Deng
 * @date 2017-03-25
 */
@Service("UserService")
public class UserServiceImpl
    extends AbstractCrudService<UserRepository, User, UserExample, Integer>
    implements UserService {

    @Override
    protected UserExample getPageExample(final String fieldName, final String keyword) {
        final UserExample example = new UserExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }
}
package com.easytoolsoft.easyreport.mybatis.sample.repository;

import com.easytoolsoft.easyreport.mybatis.data.CrudRepository;
import com.easytoolsoft.easyreport.mybatis.sample.domain.User;
import com.easytoolsoft.easyreport.mybatis.sample.domain.UserExample;
import org.springframework.stereotype.Repository;

/**
 * 数据访问接口
 *
 * @author Tom Deng
 * @date 2017-03-25
 */
@Repository("UserRepository")
public interface UserRepository extends CrudRepository<User, UserExample, Integer> {
}

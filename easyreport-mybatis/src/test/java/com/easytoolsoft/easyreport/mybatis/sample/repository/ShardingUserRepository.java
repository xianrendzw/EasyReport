package com.easytoolsoft.easyreport.mybatis.sample.repository;

import com.easytoolsoft.easyreport.mybatis.sample.domain.User;
import com.easytoolsoft.easyreport.mybatis.sample.domain.UserExample;
import com.easytoolsoft.easyreport.mybatis.sharding.data.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * 分表(sharding)数据访问接口
 *
 * @author Tom Deng
 * @date 2017-03-25
 */
@Repository("ShardingUserRepository")
public interface ShardingUserRepository extends CrudRepository<User, UserExample, Integer> {
}

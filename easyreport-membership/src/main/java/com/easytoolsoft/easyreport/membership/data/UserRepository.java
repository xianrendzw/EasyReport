package com.easytoolsoft.easyreport.membership.data;

import com.easytoolsoft.easyreport.membership.domain.User;
import com.easytoolsoft.easyreport.membership.domain.example.UserExample;
import com.easytoolsoft.easyreport.mybatis.data.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * 系统用户(easyreport_member_user表)数据访问类
 *
 * @author Tom Deng
 * @date 2017-03-25
 */
@Repository("UserRepository")
public interface UserRepository extends CrudRepository<User, UserExample, Integer> {
}

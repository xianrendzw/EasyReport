package com.easytoolsoft.easyreport.membership.data;

import com.easytoolsoft.easyreport.membership.domain.Role;
import com.easytoolsoft.easyreport.membership.domain.example.RoleExample;
import com.easytoolsoft.easyreport.mybatis.data.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * 系统角色(easyreport_member_role表)数据访问类
 *
 * @author Tom Deng
 * @date 2017-03-25
 */
@Repository("RoleRepository")
public interface RoleRepository extends CrudRepository<Role, RoleExample, Integer> {
}

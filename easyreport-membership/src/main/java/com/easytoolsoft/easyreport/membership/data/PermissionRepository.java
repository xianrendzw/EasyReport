package com.easytoolsoft.easyreport.membership.data;

import java.util.List;

import com.easytoolsoft.easyreport.membership.domain.Permission;
import com.easytoolsoft.easyreport.membership.domain.example.PermissionExample;
import com.easytoolsoft.easyreport.mybatis.data.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * 系统权限(easyreport_member_permission表)数据访问类
 *
 * @author Tom Deng
 * @date 2017-03-25
 */
@Repository("PermissionRepository")
public interface PermissionRepository extends CrudRepository<Permission, PermissionExample, Integer> {
    List<Permission> selectAllWithModulePath();
}

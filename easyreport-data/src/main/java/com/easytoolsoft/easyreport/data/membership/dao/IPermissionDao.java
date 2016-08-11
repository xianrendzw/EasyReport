package com.easytoolsoft.easyreport.data.membership.dao;

import com.easytoolsoft.easyreport.data.common.dao.ICrudDao;
import com.easytoolsoft.easyreport.data.membership.example.PermissionExample;
import com.easytoolsoft.easyreport.data.membership.po.Permission;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 系统权限(ezrpt_member_permission表)数据访问类
 *
 * @author Tom Deng
 */
@Repository("EzrptMemberIPermissionDao")
public interface IPermissionDao extends ICrudDao<Permission, PermissionExample> {
    List<Permission> selectAllWithModulePath();
}

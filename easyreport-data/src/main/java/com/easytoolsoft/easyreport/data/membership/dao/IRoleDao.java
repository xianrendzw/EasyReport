package com.easytoolsoft.easyreport.data.membership.dao;

import com.easytoolsoft.easyreport.data.common.dao.ICrudDao;
import com.easytoolsoft.easyreport.data.membership.example.RoleExample;
import com.easytoolsoft.easyreport.data.membership.po.Role;
import org.springframework.stereotype.Repository;

/**
 * 系统角色(ezrpt_member_role表)数据访问类
 *
 * @author Tom Deng
 */
@Repository("EzrptMemberIRoleDao")
public interface IRoleDao extends ICrudDao<Role, RoleExample> {
}

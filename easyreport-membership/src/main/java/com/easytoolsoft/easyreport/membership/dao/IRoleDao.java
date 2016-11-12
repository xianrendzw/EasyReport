package com.easytoolsoft.easyreport.membership.dao;

import com.easytoolsoft.easyreport.data.dao.ICrudDao;
import com.easytoolsoft.easyreport.membership.example.RoleExample;
import com.easytoolsoft.easyreport.membership.po.Role;
import org.springframework.stereotype.Repository;

/**
 * 系统角色(ezrpt_member_role表)数据访问类
 *
 * @author Tom Deng
 */
@Repository("EzrptMemberIRoleDao")
public interface IRoleDao extends ICrudDao<Role, RoleExample> {
}
